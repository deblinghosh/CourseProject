package edu.illinois.tis.interfaces;

import edu.illinois.tis.domain.ListResponse;
import edu.illinois.tis.domain.MovieSearchRequest;

public interface IMovie<T> {
	public ListResponse<T> search(MovieSearchRequest movieSearchRequest);
}
