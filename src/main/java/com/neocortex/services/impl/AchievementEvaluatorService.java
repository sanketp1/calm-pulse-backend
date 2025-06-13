package com.neocortex.services.impl;

import com.neocortex.models.AchievementDefinition;
import com.neocortex.models.UserStats;
import com.neocortex.models.embeddables.Achievements;
import com.neocortex.models.enums.Status;
import com.neocortex.repositories.AchievementDefinitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementEvaluatorService {

    private final AchievementDefinitionRepository definitionRepository;

    private final ExpressionParser parser = new SpelExpressionParser();

    public void evaluate(UserStats stats) {
        log.info("Evaluating achievements for user: {}", stats.getUser().getId());

        List<Achievements> currentAchievements = Optional.ofNullable(stats.getAchievement())
                .orElseGet(ArrayList::new);
        List<AchievementDefinition> definitions = definitionRepository.findAll();

        for (AchievementDefinition definition : definitions) {
            try {
                StandardEvaluationContext context = new StandardEvaluationContext(stats);
                Expression expression = parser.parseExpression(definition.getCriteriaExpression());
                Boolean result = expression.getValue(context, Boolean.class);

                if (Boolean.TRUE.equals(result)) {

                    boolean alreadyCompleted = currentAchievements.stream()
                            .anyMatch(a -> a.getTag().equals(definition.getTag()) &&
                                           a.getStatus() == Status.COMPLETED);

                    if (!alreadyCompleted) {
                        Achievements achievement = new Achievements(
                                definition.getTitle(),
                                definition.getDescription(),
                                Status.COMPLETED,
                                LocalDate.now(),
                                definition.getTag()
                        );
                        currentAchievements.add(achievement);
                        log.info("Unlocked achievement: {}", definition.getTitle());
                    }
                }

            } catch (Exception e) {
                log.error("Failed to evaluate achievement: {} - {}", definition.getTitle(), e.getMessage());
            }
        }

        stats.setAchievement(currentAchievements);
    }
}
