package edu.illinois.tis.interfaces;

import org.jsoup.nodes.Document;

public interface IJSoup<T> {
	public Document initialize(String queryParam);
}
