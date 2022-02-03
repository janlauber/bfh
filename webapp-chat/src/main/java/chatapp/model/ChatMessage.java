package chatapp.model;

public class ChatMessage {

	private Integer id;
	private String subject;
	private String text;

	public ChatMessage() {
	}

	public ChatMessage(String subject, String text) {
		this.subject = subject;
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
