package ch.bfh.spacenews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

/**
 * Article class for Jackson API JSON parsing resources
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
	private int id;
	private String title;
	private String summary;
	private String url;
	private LocalDate publishedAt;
	private LocalDate updatedAt;

	/**
	 * Public constructor for Jackson API JSON parsing resources
	 * @param id of the article
	 * @param title of the article
	 * @param summary of the article
	 * @param url of the article
	 * @param publishedAt of the article
	 * @param updatedAt of the article
	 */
	public Article(
			@JsonProperty("id") int id,
			@JsonProperty("title") String title,
			@JsonProperty("summary") String summary,
			@JsonProperty("url") String url,
			@JsonProperty("publishedAt") LocalDate publishedAt,
			@JsonProperty("updatedAt") LocalDate updatedAt) {
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.url = url;
		this.publishedAt = publishedAt;
		this.updatedAt = updatedAt;
	}

	/**
	 * Getter of the id
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Getter of the title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Getter of the summary
	 * @return summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * Getter of the url
	 * @return url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * Getter of the publishedAt date
	 * @return publishedAt
	 */
	public LocalDate getPublishedAt() {
		return publishedAt;
	}
	/**
	 * Getter of the updatedAt date
	 * @return updatedAt
	 */
	public LocalDate getUpdatedAt() {
		return updatedAt;
	}
	/**
	 * Setter of the id
	 * @param id of the article
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Setter of the title
	 * @param title of the article
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Setter of the summary
	 * @param summary of the article
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * Setter of the url
	 * @param url of the article
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * Setter of the publishedAt date
	 * @param publishedAt of the article
	 */
	public void setPublishedAt(LocalDate publishedAt) {
		this.publishedAt = publishedAt;
	}
	/**
	 * Setter of the updatedAt date
	 * @param updatedAt of the article
	 */
	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}
}
