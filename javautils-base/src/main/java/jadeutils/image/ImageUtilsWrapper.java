package jadeutils.image;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtilsWrapper {

	/**
	 * 缩放图像（按比例缩放）
	 * 
	 * @param srcFileName
	 *            源图像文件地址
	 * @param destFileName
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @throws IOException
	 */
	public final static void scale(String srcFileName, String destFileName,
			double scale) throws IOException {
		InputStream srcImg = null;
		OutputStream destImg = null;
		try {
			srcImg = new FileInputStream(new File(srcFileName));
			destImg = new FileOutputStream(new File(destFileName));
			ImageUtils.scale(srcImg, destImg, scale);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != srcImg)
					srcImg.close();
			} catch (Exception e2) {
			}
			try {
				if (null != destImg)
					destImg.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 缩放图像（按高度和宽度缩放）
	 * 
	 * @param srcFileName
	 *            源图像文件地址
	 * @param destFileName
	 *            缩放后的图像地址
	 * @param height
	 *            缩放后的高度
	 * @param width
	 *            缩放后的宽度
	 * @param isFill
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 * @throws IOException
	 */
	public final static void scale(String srcFileName, String destFileName,
			int width, int height, boolean isFill) throws IOException {
		InputStream srcImg = null;
		OutputStream destImg = null;
		try {
			srcImg = new FileInputStream(new File(srcFileName));
			destImg = new FileOutputStream(new File(destFileName));
			ImageUtils.scale(srcImg, destImg, width, height, isFill);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != srcImg)
					srcImg.close();
			} catch (Exception e2) {
			}
			try {
				if (null != destImg)
					destImg.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 * 
	 * @param srcFileName
	 *            源图像地址
	 * @param destFileName
	 *            切片后的图像地址
	 * @param x
	 *            目标切片起点坐标X
	 * @param y
	 *            目标切片起点坐标Y
	 * @param width
	 *            目标切片宽度
	 * @param height
	 *            目标切片高度
	 * @throws IOException
	 */
	public final static void cut(String srcFileName, String destFileName,
			int x, int y, int width, int height) throws IOException {
		InputStream srcImg = null;
		OutputStream destImg = null;
		try {
			srcImg = new FileInputStream(new File(srcFileName));
			destImg = new FileOutputStream(new File(destFileName));
			ImageUtils.cut(srcImg, destImg, x, y, width, height);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != srcImg)
					srcImg.close();
			} catch (Exception e2) {
			}
			try {
				if (null != destImg)
					destImg.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 图像切割（指定切片的行数和列数）
	 * 
	 * @param srcFileName
	 *            源图像地址
	 * @param descPrefix
	 *            切片目标文件夹
	 * @param rowCount
	 *            目标切片行数。默认2，必须是范围 [1, 20] 之内
	 * @param colCount
	 *            目标切片列数。默认2，必须是范围 [1, 20] 之内
	 * @throws IOException
	 */
	public final static void divideByGrid(final String srcFileName,
			final String descPrefix, int rowCount, int colCount)
			throws IOException {
		InputStream srcImg = null;
		try {
			srcImg = new FileInputStream(new File(srcFileName));
			ImageUtils.divideByGrid(srcImg,
					new ImageUtils.MutiOutputStreamGenerater() {
						@Override
						public OutputStream genOutputStream(String idx,
								String postFix) throws FileNotFoundException {
							String fileName = descPrefix + idx + postFix;
							return new FileOutputStream(new File(fileName));
						}
					}, rowCount, colCount);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != srcImg)
					srcImg.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 图像切割（指定切片的行数和列数）
	 * 
	 * @param srcFileName
	 *            源图像地址
	 * @param descPrefix
	 *            切片目标文件夹
	 * @param rowCount
	 *            目标切片行数。默认2，必须是范围 [1, 20] 之内
	 * @param colCount
	 *            目标切片列数。默认2，必须是范围 [1, 20] 之内
	 * @throws IOException
	 */
	public final static void divideBySize(final String srcFileName,
			final String descPrefix, int rowCount, int colCount)
			throws IOException {
		InputStream srcImg = null;
		try {
			srcImg = new FileInputStream(new File(srcFileName));
			ImageUtils.divideBySize(srcImg,
					new ImageUtils.MutiOutputStreamGenerater() {
						@Override
						public OutputStream genOutputStream(String idx,
								String postFix) throws FileNotFoundException {
							String fileName = descPrefix + idx + postFix;
							return new FileOutputStream(new File(fileName));
						}
					}, rowCount, colCount);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != srcImg)
					srcImg.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 彩色转为黑白
	 * 
	 * @param srcFileName
	 *            源图像地址
	 * @param destFileName
	 *            目标图像地址
	 * @throws IOException
	 */
	public final static void gray(String srcFileName, String destFileName)
			throws IOException {
		InputStream srcImg = null;
		OutputStream destImg = null;
		try {
			srcImg = new FileInputStream(new File(srcFileName));
			destImg = new FileOutputStream(new File(destFileName));
			ImageUtils.gray(srcImg, destImg);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != srcImg)
					srcImg.close();
			} catch (Exception e2) {
			}
			try {
				if (null != destImg)
					destImg.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
	 * 
	 * @param srcFileName
	 *            源图像地址
	 * @param destFileName
	 *            目标图像地址
	 * @param format
	 *            包含格式非正式名称的 String：如JPG、JPEG、GIF等
	 * @throws IOException
	 */
	public final static void convert(String srcFileName, String destFileName,
			String format) throws IOException {
		InputStream srcImg = null;
		OutputStream destImg = null;
		try {
			srcImg = new FileInputStream(new File(srcFileName));
			destImg = new FileOutputStream(new File(destFileName));
			ImageUtils.convert(srcImg, destImg, format);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != srcImg)
					srcImg.close();
			} catch (Exception e2) {
			}
			try {
				if (null != destImg)
					destImg.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 给图片添加文字水印
	 * 
	 * @param srcFileName
	 *            源图像地址
	 * @param destFileName
	 *            目标图像地址
	 * @param pressText
	 *            水印文字
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param fontName
	 *            水印的字体名称
	 * @param fontStyle
	 *            水印的字体样式
	 * @param fontSize
	 *            水印的字体大小
	 * @param color
	 *            水印的字体颜色
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 * @throws IOException
	 */
	public final static void pressText(String srcFileName, String destFileName,
			String pressText, int x, int y, //
			String fontName, int fontStyle, int fontSize, //
			Color color, float alpha) throws IOException {
		InputStream srcImg = null;
		OutputStream destImg = null;
		try {
			srcImg = new FileInputStream(new File(srcFileName));
			destImg = new FileOutputStream(new File(destFileName));
			ImageUtils.pressText(srcImg, destImg, pressText, x, y, fontName,
					fontStyle, fontSize, color, alpha);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != srcImg)
					srcImg.close();
			} catch (Exception e2) {
			}
			try {
				if (null != destImg)
					destImg.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 给图片添加图片水印
	 * 
	 * @param srcFileName
	 *            源图像地址
	 * @param pressFileName
	 *            水印图片
	 * @param destFileName
	 *            目标图像地址
	 * @param x
	 *            修正值。 默认在中间
	 * @param y
	 *            修正值。 默认在中间
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 * @throws IOException
	 */
	public final static void pressImage(String srcFileName,
			String pressFileName, String destFileName, int x, int y, float alpha)
			throws IOException {
		InputStream srcImg = null;
		InputStream pressImg = null;
		OutputStream destImg = null;
		try {
			srcImg = new FileInputStream(new File(srcFileName));
			pressImg = new FileInputStream(new File(pressFileName));
			destImg = new FileOutputStream(new File(destFileName));
			ImageUtils.pressImage(srcImg, pressImg, destImg, destFileName, x,
					y, alpha);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != srcImg)
					srcImg.close();
			} catch (Exception e2) {
			}
			try {
				if (null != pressImg)
					pressImg.close();
			} catch (Exception e2) {
			}
			try {
				if (null != destImg)
					destImg.close();
			} catch (Exception e2) {
			}
		}
	}
}
