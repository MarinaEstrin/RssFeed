package Rss.Rss;

// a POJO to represent the data to the client with only the necessary information.
public class FeedItemRO {

	private String title;
	
	private String link;
	
	private String body;

	public FeedItemRO() {
		
	}
	
	public FeedItemRO(String title, String link, String body) {
		this.title = title;
		this.link = link;
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "FeedItemRO [title=" + title + ", link=" + link + ", body=" + body + "]";
	}
	
	
	
}
