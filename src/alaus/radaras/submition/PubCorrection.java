package alaus.radaras.submition;

public class PubCorrection {
	
	private PubCorrectionReason reason;
	
	private String message;
	
	private String extraInfo;
	
	private String pubId;

	public void setReason(PubCorrectionReason reason) {
		this.reason = reason;
	}

	public PubCorrectionReason getReason() {
		return reason;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getPubId() {
		return pubId;
	}

}
