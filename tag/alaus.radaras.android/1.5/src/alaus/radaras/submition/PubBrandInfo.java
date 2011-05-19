package alaus.radaras.submition;

import android.location.Location;

public class PubBrandInfo {
	
	private PubBrandStatus status;
	
	private String pubId;
	
	private String brandId;
	
	private String message;
	
	private Location location;

	public void setStatus(PubBrandStatus status) {
		this.status = status;
	}

	public PubBrandStatus getStatus() {
		return status;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getPubId() {
		return pubId;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

}
