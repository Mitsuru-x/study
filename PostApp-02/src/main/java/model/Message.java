package model;

public class Message {
	private final String text;
	private final String createdAt;
	
	public Message(String text,String createdAt) {
		this.text = text;
		this.createdAt = createdAt;
	}

	public String getText() {
		return text;
	}

	public String getCreatedAt() {
		return createdAt;
	}
}
