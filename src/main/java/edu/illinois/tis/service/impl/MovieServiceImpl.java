package edu.illinois.tis.service.impl;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.tis.domain.ListResponse;
import edu.illinois.tis.domain.MovieSearchRequest;
import edu.illinois.tis.domain.SortOrder;
import edu.illinois.tis.model.Movie;
import edu.illinois.tis.service.MovieSearchQueryBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.illinois.tis.service.JSoupService;
import edu.illinois.tis.service.MovieService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import static edu.illinois.tis.domain.Constants.RATING;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	JSoupService jsoupService;

	@Autowired
	MovieSearchQueryBuilder movieSearchQueryBuilder;

	@Override
	public ListResponse<Movie> search(MovieSearchRequest movieSearchRequest) {
		addSearchRequestDefaults(movieSearchRequest);
		String queryParam = movieSearchQueryBuilder.build(movieSearchRequest);
		Document dom = jsoupService.initialize(queryParam);
		List<Movie> movies = extractMovieDetails(dom);
		Integer totalResult = getTotalResult(dom);
		return generateListResponse(movieSearchRequest, movies, totalResult);
	}

	private Integer getTotalResult(Document dom) {
		String navDesc = dom.select(".nav").select(".desc").text();
		int startIndex = navDesc.indexOf("of ");
		int endIndex = navDesc.indexOf(" titles");
		return Integer.parseInt(
				navDesc.substring(startIndex + 3, endIndex)
				.replace(",","")
		);
	}

	private List<Movie> extractMovieDetails(Document dom) {
		Elements movieElements = dom.select("div[class='lister-list']").first().children();
		List<Movie> moviesList = new ArrayList<>();
		for (Element movieElement : movieElements) {
			Movie movie = new Movie();
			movie.setName(movieElement.getElementsByClass("lister-item-header").get(0)
					.getElementsByAttribute("href").text());
			movie.setYear(movieElement.getElementsByClass("lister-item-year").get(0).text()
					.replaceAll("\\(", "")
					.replaceAll("\\)", ""));
			movie.setUrl(movieElement.getElementsByClass("lister-item-header").get(0)
					.getElementsByAttribute("href").attr("href"));
			Elements ratings = movieElement.getElementsByClass("ratings-imdb-rating");
			// Not all movies have rating
			movie.setRating(ratings.size() != 0 ? ratings.get(0).getElementsByAttribute("data-value").text() : null);
			moviesList.add(movie);
		}
		return moviesList;
	}

	/**
	 * Add default values to optional fields on a Search Request.
	 * @param movieSearchRequest Movie search request
	 */
	public static void addSearchRequestDefaults(MovieSearchRequest movieSearchRequest) {
		if (ObjectUtils.isEmpty(movieSearchRequest.getSortBy())) {
			movieSearchRequest.setSortBy(RATING);
		}
		if (movieSearchRequest.getSortOrder() == null) {
			movieSearchRequest.setSortOrder(SortOrder.DESC);
		}
		if (movieSearchRequest.getStartIndex() == null || movieSearchRequest.getStartIndex() < 1) {
			movieSearchRequest.setStartIndex(1);
		}
		if (movieSearchRequest.getCount() == null || movieSearchRequest.getCount() > 250) {
			movieSearchRequest.setCount(10);
		}
	}

	/**
	 * Given a list of movies, generate paginated list response.
	 *
	 * @param movieSearchRequest Original search request
	 * @param totalResults       Total result count matching filter
	 * @param movies             Movies matching the specified filter
	 * @return listResponse
	 */
	private ListResponse<Movie> generateListResponse(
			MovieSearchRequest movieSearchRequest, List<Movie> movies, Integer totalResults) {
		ListResponse<Movie> listResponse = new ListResponse<>();

		if (CollectionUtils.isEmpty(movies)) {
			listResponse.setTotalResults(totalResults);
			listResponse.setMovies(new ArrayList<>());
			listResponse.setStartIndex(1);
			listResponse.setItemsPerPage(0);
			return listResponse;
		}

		listResponse.setTotalResults(totalResults);
		listResponse.setMovies(movies);
		listResponse.setStartIndex(movieSearchRequest.getStartIndex());
		listResponse.setItemsPerPage(movies.size());
		return listResponse;
	}
}
