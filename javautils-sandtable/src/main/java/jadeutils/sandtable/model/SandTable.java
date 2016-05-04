package jadeutils.sandtable.model;

import jadeutils.sandtable.SandTableConstants;

import java.util.HashMap;
import java.util.Map;

public class SandTable {
	private int width = 65535;
	private int height = 65535;
	private Map<String, Area> areaMap = new HashMap<>();

	public SandTable(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void putArea(Area area) {
		StringBuffer sb = new StringBuffer();
		sb.append(area.getCordX()).append(",").append(area.getCordY());
		// System.out.print(sb.toString() + " | ");
		this.areaMap.put(sb.toString(), area);
	}

	public Area getArea(int x, int y) {
		StringBuffer sb = new StringBuffer();
		sb.append(x).append(",").append(y);
		// System.out.print(sb.toString()+" | ");
		return this.areaMap.get(sb.toString());
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int y = 0; y < this.getWidth(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				Area area = this.getArea(x, y);
				String formId = area.getLandformId();
				LandForm lf = SandTableConstants.LAND_FORMS.get(formId);
				sb.append(lf.getDisplayChar());
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
