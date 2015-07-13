package jadeutils.sandtable.model;

public class LandForm {
	private String landformId;
	private String displayChar;
	private String displayImg;
	private int speedAdjust;

	public LandForm(String landformId, String displayChar, String displayImg,
			int speedAdjust) {
		super();
		this.landformId = landformId;
		this.displayChar = displayChar;
		this.displayImg = displayImg;
		this.speedAdjust = speedAdjust;
	}

	public String getLandformId() {
		return landformId;
	}

	public String getDisplayChar() {
		return displayChar;
	}

	public String getDisplayImg() {
		return displayImg;
	}

	public int getSpeedAdjust() {
		return speedAdjust;
	}
}
