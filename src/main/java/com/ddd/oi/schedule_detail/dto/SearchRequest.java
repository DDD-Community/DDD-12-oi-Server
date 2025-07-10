package com.ddd.oi.schedule_detail.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchRequest {
    private String query;
    private String category;
    private int display = 5;
    private int start = 1;
    private String sort = "random";
}