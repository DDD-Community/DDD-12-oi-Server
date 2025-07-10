package com.ddd.oi.schedule_detail.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<PlaceItem> items = new ArrayList<>();
    private String category;
    private boolean hasMore;
}