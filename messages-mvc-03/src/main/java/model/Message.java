package model;
public class Message {
	private final String text;
	private final String author;
	
	public Message(String text,String author) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.text = text;
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public String getAuthor() {
		return author;
	}
	
}
