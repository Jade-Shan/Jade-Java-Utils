package jadeutils.sandtable.tools;

public class MapUtils {
	// 地球直径
	public static final double RADIUS_EARTH = 6378100.00;
	public static final double PI_DEG = Math.PI / 180;

	/*
	 * 根据两个坐标取得 使用的公式：
	 * 
	 * @param startLat
	 *            起点纬度
	 * @param startLon
	 *            起点经度
	 * @param endLat
	 *            终点纬度
	 * @param endLon
	 *            终点经度
	 * @return 距离，单位米
	 */
	public static final double getDistanceByCoords(double startLat,
			double startLon, double endLat, double endLon) {
		double lat1 = PI_DEG * startLat;
		double lon1 = PI_DEG * startLon;
		double lat2 = PI_DEG * endLat;
		double lon2 = PI_DEG * endLon;

		return Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1)
				* Math.cos(lat2) * Math.cos(lon2 - lon1))
				* MapUtils.RADIUS_EARTH;
	}

	/*
	 * 取得纬度角度的距离
	 * 
	 * @param latitudeAngle
	 *            　纬度跨度
	 * @return 距离，单位米　
	 */
	public static final double getLatitueDistance(double latitudeAngle) {
		return latitudeAngle * 111000;
	}

	/*
	 * 纬度距离的角度
	 * 
	 * @param distance
	 *            距离，单位米
	 * @return 纬度距离的角度
	 */
	public static final double getLatitueAngle(double distance) {
		return distance / 111000;
	}

	/*
	 * 取得指定纬度下，指定经度的地面距离
	 * 
	 * @param longtitudeAngle
	 *            经度跨度
	 * @param latitude
	 *            所处的纬度
	 * @return 经度距离，单位米
	 */
	public static final double getLongtitudeDistance(double longtitudeAngle,
			double latitude) {
		// 纬度的每个度的距離大约相当于111km，但经度的每个度的距离从0km到111km不等。
		// 它的距离随纬度的不同而变化，等于111km乘纬度的余弦 。不过这个距离还不是相隔
		// 一经度的两点之间最短的距离，最短的距离是连接这两点之间的大圆的弧的距离，
		// 它比上面所计算出来的距离要小一些。
		// 不过在角度小的时候可以忽略……这里用的是直线，不是弧线距离
		return 111000 * Math.cos(PI_DEG * latitude) * longtitudeAngle;
		// return MapUtils.getDistanceByCoords(latitude, 0.0, latitude,
		// longtitudeAngle);
	}

	/*
	 * 经度距离的角度
	 * 
	 * @param distance
	 *            距离，单位米
	 * @return 经度距离的角度
	 */
	public static final double getLongitueAngle(double distance, double latitude) {
		return distance / 111000 * Math.cos(PI_DEG * latitude);
	}

}
