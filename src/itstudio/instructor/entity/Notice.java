package itstudio.instructor.entity;

/**
* 
* @Description  HomeFragement Listview Entity

* @author MR.Wang

* @date 2014-7-5 上午10:45:40 

* @version V1.0
*/
public class Notice {

	private String author;
	private String title;
	private String picUrl;
	private String detailUrl;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

}
