package jadeutils.sandtable.model;

import jadeutils.sandtable.status.Direction;

public class Area {
	private int cordX = 0;
	private int cordY = 0;

//	public enum String {
//		GRASS, SAND, ROCK, CLIFF, SWAMP, STREAM, RIVER, SEA
//	}

	private String landformId;
	private Direction direction;

	public Area(int cordX, int cordY) {
		this.cordX = cordX;
		this.cordY = cordY;
		this.landformId ="GRASS";
		this.direction = Direction.d6;
	}

	public Area(int cordX, int cordY, String landformId, Direction direction) {
		this.cordX = cordX;
		this.cordY = cordY;
		this.landformId = landformId;
		this.direction = direction;
	}

	public int getCordX() {
		return cordX;
	}

	public int getCordY() {
		return cordY;
	}

	public String getLandformId() {
		return landformId;
	}

	public void setLandformId(String landformId) {
		this.landformId = landformId;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
