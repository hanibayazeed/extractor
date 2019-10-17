package tahawl.com.extracter.model;

public class HelperModel {
	String date;
	String duration;
	String user;
	String type;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public HelperModel(String date, String duration, String user, String type) {
		super();
		this.date = date;
		this.duration = duration;
		this.user = user;
		this.type = type;
	}
	public HelperModel() {

	}
	@Override
	public String toString() {
		return "HelperModel [date=" + date + ", duration=" + duration + ", user=" + user + ", type=" + type + "]";
	}
	
	

}
