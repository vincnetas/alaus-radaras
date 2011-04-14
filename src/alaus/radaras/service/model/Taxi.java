package alaus.radaras.service.model;

public class Taxi  extends SingleLocation {
	
	private String title;
	
	private String phone;
	
	private String city;

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}
