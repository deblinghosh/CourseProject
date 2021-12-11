package edu.illinois.tis.controller;

import edu.illinois.tis.domain.ListResponse;
import edu.illinois.tis.domain.MovieSearchRequest;
import edu.illinois.tis.domain.SortOrder;
import edu.illinois.tis.model.Movie;
import edu.illinois.tis.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movies")
public class MovieController {

	@Autowired
	MovieService movieService;
	
	@GetMapping(value = "search")
	public ListResponse<Movie> search(@RequestParam(name = "filter", required = false) String filter,
									  @RequestParam(name = "startIndex", required = false) Integer startIndex,
									  @RequestParam(name = "count", required = false) Integer count,
									  @RequestParam(name = "sortBy", required = false) String sortBy,
									  @RequestParam(name = "sortOrder", required = false) String sortOrder) {

		return movieService.search(MovieSearchRequest.builder()
				.filter(filter)
				.count(count)
				.startIndex(startIndex)
				.sortBy(sortBy)
				.sortOrder((sortOrder == null) ? null : SortOrder.getByName(sortOrder))
				.build());
	}
}
