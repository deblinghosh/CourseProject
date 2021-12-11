package edu.illinois.tis.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortOrder {
    /**
     * ASCENDING (low to high)
     */
    @JsonProperty("asc")
    ASC("asc"),
    /**
     * DESCENDING (high to low)
     */
    @JsonProperty("desc")
    DESC("desc");

    private String name;

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static SortOrder getByName(String name) {
        if (null == name) {
            return null;
        }
        return SortOrder.valueOf(name.toUpperCase());
    }
}
