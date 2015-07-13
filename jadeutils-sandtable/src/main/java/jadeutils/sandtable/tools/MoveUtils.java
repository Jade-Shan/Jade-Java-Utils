package jadeutils.sandtable.tools;

import jadeutils.sandtable.model.SandTable;
import jadeutils.sandtable.status.Direction;

public class MoveUtils {

	public static int[] move(SandTable sandTable, int x, int y,
			Direction direction, int speed, int time) {
		int[] result = { x, y };
		if (direction == Direction.d4) {
			return result;
		} else {
			/* caculate the distance */
			int distance = speed * time;
			switch (direction) {
			case d1:
			case d3:
			case d7:
			case d9:
				double tmp = distance * Math.sin(Math.PI / 4);
				distance = (int) Math.ceil(tmp);
				break;
			default:
				// nothing
				break;
			}
			/* caculate the direction */
			int xd = 1, yd = 1;
			switch (direction) {
			case d1:
				yd = -1;
				xd = -1;
				break;
			case d2:
				yd = -1;
				break;
			case d3:
				yd = -1;
				break;
			case d5:
				xd = -1;
				break;
			case d7:
				xd = -1;
				break;
			default:
				break;
			}
			int nx = x + (distance * xd);
			int ny = y + (distance * yd);
			/* wrap if out of map */
			if (nx < 0) {
				result[0] = sandTable.getWidth() - Math.abs(nx);
			} else if (nx >= sandTable.getWidth()) {
				result[0] = Math.abs(nx) - sandTable.getWidth();
			} else {
				result[0] = nx;
			}
			if (ny < 0) {
				result[1] = sandTable.getHeight() - Math.abs(ny);
			} else if (nx >= sandTable.getWidth()) {
				result[1] = Math.abs(ny) - sandTable.getWidth();
			} else {
				result[1] = ny;
			}
		}
		return result;
	}
}
