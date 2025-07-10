package com.ddd.oi.schedule_detail.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoCompleteResponse {
    private List<String> suggestions;
    private String category;
}