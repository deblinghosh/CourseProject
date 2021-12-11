package edu.illinois.tis.service.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import edu.illinois.tis.service.JSoupService;

@Service
public class JSoupServiceImpl implements JSoupService {

	private static final String movieDBSearchUrl = "https://www.imdb.com/search/title/?title_type=feature";

	@Override
	public Document initialize(String queryParam) {
		Document doc = null;
		try {
			doc = Jsoup
					.connect(movieDBSearchUrl + queryParam)
					.header("Accept-Language", "en")
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}


}
