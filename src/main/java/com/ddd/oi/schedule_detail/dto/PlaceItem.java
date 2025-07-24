package com.ddd.oi.schedule_detail.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceItem {
    private String title;
    private String link;
    private String category;
    private String description;
    private String telephone;
    private String address;
    private String roadAddress;
    private Double latitude;
    private Double longitude;
    private String mainCategory;
    private String categoryColor;

    @JsonSetter("title")
    public void setTitle(String title) {
        this.title = removeHtmlTags(title);
    }

    @JsonSetter("category")
    public void setCategory(String category) {
        this.category = removeHtmlTags(category);
    }

    @JsonSetter("description")
    public void setDescription(String description) {
        this.description = removeHtmlTags(description);
    }

    private String removeHtmlTags(String text) {
        if (text == null)
            return null;
        return text.replaceAll("<[^>]*>", "").trim();
    }
}
