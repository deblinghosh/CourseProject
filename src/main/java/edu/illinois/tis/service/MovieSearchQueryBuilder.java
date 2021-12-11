package edu.illinois.tis.service;

import edu.illinois.tis.domain.MovieSearchRequest;
import edu.illinois.tis.domain.SortOrder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MovieSearchQueryBuilder {

    private static final Map<String, String> FIELD_NAME_MAPPING = new HashMap<String, String>() {
        {
            put("name", "title");
            put("rating", "user_rating");
            put("year", "year");
        }
    };

    // This a list of IMDB supported sort fields used by search
    private static final List<String> SUPPORTED_SORT_FIELDS = Arrays.asList("rating", "year");

    public String build(MovieSearchRequest movieSearchRequest) {
        StringBuilder imdbQueryString = new StringBuilder();

        // Convert filter
        String filterRequest = movieSearchRequest.getFilter();
        if(StringUtils.hasLength(filterRequest)) {
            String[] requestParams = filterRequest.split("and");
            for(String requestParam : requestParams) {
                String[] params = requestParam.trim().split("=");
                imdbQueryString.append("&").append(FIELD_NAME_MAPPING.get(params[0])).append("=").append(params[1]);
            }
        }

        // Convert sort and order
        String sortByField = movieSearchRequest.getSortBy();
        SortOrder sortOrder = movieSearchRequest.getSortOrder();
        if (SUPPORTED_SORT_FIELDS.contains(sortByField)) {
            imdbQueryString.append("&sort=").append(FIELD_NAME_MAPPING.get(sortByField)).append(",").append(sortOrder.getName());
        }

        // Convert pagination
        imdbQueryString.append("&count=").append(movieSearchRequest.getCount()).append("&start=").append(movieSearchRequest.getStartIndex());

        return imdbQueryString.toString();
    }

}
