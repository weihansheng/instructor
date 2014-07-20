package itstudio.instructor.entity;

/**
* 
* @Description  HomeFragement Listview Entity

* @author MR.Wang

* @date 2014-7-5 上午10:45:40 

* @version V1.0
*/
public class News {
	
	private String author;
	private String headUrl;
	private String title;
	private String content;
	private String date;
	private String detailUrl;

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	

}
