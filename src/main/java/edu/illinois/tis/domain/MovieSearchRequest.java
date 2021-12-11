package edu.illinois.tis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieSearchRequest implements Serializable {

    /**
     * Filter
     */
    private String filter;
    /**
     * start index for pagination
     */
    private Integer startIndex;
    /**
     * Number of results per page
     */
    private Integer count;
    /**
     * Sort by attribute of resource
     */
    private String sortBy;
    /**
     * SortOrder - ASCENDING or DESCENDING
     */
    private SortOrder sortOrder;

}
