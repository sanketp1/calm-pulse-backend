package com.neocortex.payloads;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PaginatedResponse<T> {
    private List<T> data;
    private int totalElements;
    private String nextCursor;
    private boolean hasNext;
}
