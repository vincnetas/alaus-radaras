package alaus.radaras.dao.model;

public class Brand {

	private String id;
	
	private String title;
	
	private String icon;
	
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return title;
	}

	public void setBrand(String brand) {
		this.title = brand;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
