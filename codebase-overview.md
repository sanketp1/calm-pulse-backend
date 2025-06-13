# Codebase Overview
================

## Entities

### User

* Represents a user in the system
* Creates journals and reminders
* Checks in their mood

### Journal

* Represents a journal entry created by a user
* Has attachments

### Reminder

* Represents a reminder created by a user
* Has reminder days

### Mood

* Represents a mood entry created by a user
* Has a mood type

### UserStats

* Represents statistical data for a user
* Tracks journal entries, mood check-ins, resources used, and achievements

## Services

### JournalService

* Handles journal-related operations
* Creates, reads, updates, and deletes journals

### ReminderService

* Handles reminder-related operations
* Creates, reads, updates, and deletes reminders

### MoodService

* Handles mood-related operations
* Creates, reads, updates, and deletes moods

### AchievementEvaluatorService

* Evaluates user achievements based on their stats

## Repositories

### JournalRepository

* Handles journal data access

### ReminderRepository

* Handles reminder data access

### MoodRepository

* Handles mood data access

### UserRepository

* Handles user data access

## Models

### Journal

* Represents a journal entry
* Has attachments

### Reminder

* Represents a reminder
* Has reminder days

### Mood

* Represents a mood entry
* Has a mood type

### UserStats

* Represents statistical data for a user
* Tracks journal entries, mood check-ins, resources used, and achievements

## Enums

### MoodType

* Represents a mood type (e.g. happy, sad, neutral)

### ReminderDays

* Represents the days of the week for a reminder (e.g. Monday, Tuesday, etc.)