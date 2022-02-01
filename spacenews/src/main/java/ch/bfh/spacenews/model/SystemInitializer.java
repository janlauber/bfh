package ch.bfh.spacenews.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * This class is used to initialize the system API.
 */
public class SystemInitializer {

	Article[] articles;

	/**
	 * Public constructor.
	 */
	public SystemInitializer() {
		URL url = null;
		try {
			url = new URL("https://api.spaceflightnewsapi.net/v3/articles");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HttpURLConnection conn = null;
		try {
			assert url != null;
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assert conn != null;
		conn.setRequestProperty("Accept", "application/json");

		InputStream in = null;
		try {
			in = conn.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.registerModule(new JavaTimeModule());
		try {
			articles = mapper.readValue(in, Article[].class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Article article : articles) {
			System.out.println("ID: " + article.getId() + " | Title: " + article.getTitle());
			System.out.println("Date: " + article.getPublishedAt());
		}
	}

	/**
	 * Getter for the articles.
	 * @return articles
	 */
	public Article[] getArticles() {
		return articles;
	}
}
