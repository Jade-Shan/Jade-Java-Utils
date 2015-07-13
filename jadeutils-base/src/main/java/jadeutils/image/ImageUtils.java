package jadeutils.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * 
 * @author SHAN013
 * 
 */
public class ImageUtils {
	/**
	 * 几种常见的图片格式
	 */
	public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
	public static String IMAGE_TYPE_JPEG = "jpg";// 联合照片专家组
	public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
	public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
	public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

	/**
	 * 按一定规则生成多个输出流的接口
	 * 
	 * @author SHAN013
	 * 
	 */
	public interface MutiOutputStreamGenerater {
		/**
		 * 生成一个新的输出流
		 * 
		 * @param prefix
		 * @param idx
		 * @return
		 */
		public OutputStream genOutputStream(String idx, String postFix)
				throws FileNotFoundException;
	};

	/**
	 * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
	 * 
	 * @param srcFileName
	 *            源图像地址
	 * @param destFileName
	 *            目标图像地址
	 * @param format
	 *            包含格式非正式名称的 String：如JPG、JPEG、GIF等
	 */
	public final static void convert(InputStream srcImg, OutputStream destImg,
			String format) {
		try {
			BufferedImage src = ImageIO.read(srcImg);
			ImageIO.write(src, format, destImg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 彩色转为黑白
	 * 
	 * @param srcImg
	 *            源图像
	 * @param destImg
	 *            处理后图像
	 * @throws IOException
	 */
	public final static void gray(InputStream srcImg, OutputStream destImg)
			throws IOException {
		BufferedImage src = ImageIO.read(srcImg);
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		src = op.filter(src, null);
		ImageIO.write(src, IMAGE_TYPE_JPEG, destImg);
	}

	/**
	 * 缩放图像（按比例缩放）
	 * 
	 * @param srcImg
	 *            源图像
	 * @param destImg
	 *            处理后的图像
	 * @param scale
	 *            缩放比例
	 * @throws IOException
	 */
	public final static void scale(InputStream srcImg, OutputStream destImg,
			double scale) throws IOException {
		BufferedImage src = ImageIO.read(srcImg); // 读入文件
		int width = src.getWidth(); // 得到源图宽
		int height = src.getHeight(); // 得到源图长
		width = (int) (width * scale);
		height = (int) (height * scale);
		Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		BufferedImage tag = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		ImageIO.write(tag, IMAGE_TYPE_JPEG, destImg);// 输出到文件流
	}

	/**
	 * 缩放图像（按高度和宽度缩放）
	 * 
	 * @param srcImg
	 *            源图像
	 * @param destImg
	 *            处理后的图像
	 * @param height
	 *            缩放后的高度
	 * @param width
	 *            缩放后的宽度
	 * @param isFill
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 * @throws IOException
	 */
	public final static void scale(InputStream srcImg, OutputStream destImg,
			int width, int height, boolean isFill) throws IOException {
		BufferedImage bi = ImageIO.read(srcImg);
		Image itemp = bi.getScaledInstance(width, height,
				BufferedImage.SCALE_SMOOTH);
		// 计算比例
		double ratio = caculateScaleRatio(bi.getWidth(), bi.getHeight(), width,
				height); // 缩放比例
		AffineTransformOp op = new AffineTransformOp(
				AffineTransform.getScaleInstance(ratio, ratio), null);
		itemp = op.filter(bi, null);
		if (isFill) {// 补白
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			if (width == itemp.getWidth(null))
				g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
						itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
			else
				g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
						itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
			g.dispose();
			itemp = image;
		}
		ImageIO.write((BufferedImage) itemp, IMAGE_TYPE_JPEG, destImg);
	}

	/**
	 * 根据原始图像与目标图像的大小来算出缩放比例
	 * 
	 * @return 对应的比例
	 */
	public static double caculateScaleRatio(int srcWidth, int srcHeight,
			int tagWidth, int tagHeight) {
		double widthRatio = (new Integer(tagWidth)).doubleValue() / srcWidth;
		double heightRatio = (new Integer(tagHeight)).doubleValue() / srcHeight;
		return widthRatio > heightRatio ? heightRatio : widthRatio;
	}

	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 * 
	 * @param srcImg
	 *            源图像
	 * @param destImg
	 *            处理后图像
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
	public final static void cut(InputStream srcImg, OutputStream destImg,
			int x, int y, int width, int height) throws IOException {
		// 读取源图像
		BufferedImage bi = ImageIO.read(srcImg);
		int srcWidth = bi.getHeight(); // 源图宽度
		int srcHeight = bi.getWidth(); // 源图高度
		if (srcWidth > 0 && srcHeight > 0) {
			Image image = bi.getScaledInstance(srcWidth, srcHeight,
					Image.SCALE_DEFAULT);
			// 四个参数分别为图像起点坐标和宽高
			// 即: CropImageFilter(int x,int y,int width,int height)
			ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
			Image img = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
			g.dispose();
			// 输出为文件
			ImageIO.write(tag, IMAGE_TYPE_JPEG, destImg);
		}
	}

	/**
	 * 给图片添加文字水印
	 * 
	 * @param srcImg
	 *            源图像
	 * @param destImg
	 *            处理后图像
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
	public final static void pressText(InputStream srcImg,
			OutputStream destImg, String pressText, int x, int y, //
			String fontName, int fontStyle, int fontSize, //
			Color color, float alpha) throws IOException {
		Image src = ImageIO.read(srcImg);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(src, 0, 0, width, height, null);
		g.setColor(color);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));
		// 在指定坐标绘制水印文字
		g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2
				+ x, (height - fontSize) / 2 + y);
		g.dispose();
		ImageIO.write((BufferedImage) image, IMAGE_TYPE_JPEG, destImg);// 输出到文件流
	}

	/**
	 * 给图片添加图片水印
	 * 
	 * @param srcImg
	 *            源图像
	 * @param pressImg
	 *            水印图像
	 * @param destImg
	 *            处理后图像
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
	public final static void pressImage(InputStream srcImg,
			InputStream pressImg, OutputStream destImg, String destFileName,
			int x, int y, float alpha) throws IOException {
		Image src = ImageIO.read(srcImg);
		int wideth = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.drawImage(src, 0, 0, wideth, height, null);
		// 水印文件
		Image src_biao = ImageIO.read(pressImg);
		int wideth_biao = src_biao.getWidth(null);
		int height_biao = src_biao.getHeight(null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));
		g.drawImage(src_biao, (wideth - wideth_biao) / 2,
				(height - height_biao) / 2, wideth_biao, height_biao, null);
		// 水印文件结束
		g.dispose();
		ImageIO.write((BufferedImage) image, IMAGE_TYPE_JPEG, new File(
				destFileName));
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
	 */
	public final static void pressText2(String srcFileName,
			String destFileName, String pressText, int x, int y, //
			String fontName, int fontStyle, int fontSize, //
			Color color, float alpha) {
		try {
			File img = new File(srcFileName);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, IMAGE_TYPE_JPEG, new File(
					destFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割（指定切片的行数和列数）
	 * 
	 * @param srcImg
	 *            源图像
	 * @param osGenerater
	 *            生成输出流的接口
	 * @param rowCount
	 *            目标切片行数。默认2，必须是范围 [1, 20] 之内
	 * @param colCount
	 *            目标切片列数。默认2，必须是范围 [1, 20] 之内
	 * 
	 * @throws IOException
	 */
	public final static void divideByGrid(InputStream srcImg,
			MutiOutputStreamGenerater osGenerater, int rowCount, int colCount)
			throws IOException {
		if (rowCount <= 0 || rowCount > 20)
			rowCount = 2; // 切片行数
		if (colCount <= 0 || colCount > 20)
			colCount = 2; // 切片列数
		// 读取源图像
		BufferedImage bi = ImageIO.read(srcImg);
		int srcWidth = bi.getHeight(); // 源图宽度
		int srcHeight = bi.getWidth(); // 源图高度
		if (srcWidth > 0 && srcHeight > 0) {
			Image img;
			ImageFilter cropFilter;
			Image image = bi.getScaledInstance(srcWidth, srcHeight,
					Image.SCALE_DEFAULT);
			int cellWidth = srcWidth; // 每张切片的宽度
			int cellHeight = srcHeight; // 每张切片的高度
			// 计算切片的宽度和高度
			if (srcWidth % colCount == 0) {
				cellWidth = srcWidth / colCount;
			} else {
				cellWidth = (int) Math.floor(srcWidth / colCount) + 1;
			}
			if (srcHeight % rowCount == 0) {
				cellHeight = srcHeight / rowCount;
			} else {
				cellHeight = (int) Math.floor(srcWidth / rowCount) + 1;
			}
			// 循环建立切片
			// 改进的想法:是否可用多线程加快切割速度
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < colCount; j++) {
					// 四个参数分别为图像起点坐标和宽高
					// 即: CropImageFilter(int x,int y,int width,int height)
					cropFilter = new CropImageFilter(j * cellWidth, i
							* cellHeight, cellWidth, cellHeight);
					img = Toolkit.getDefaultToolkit().createImage(
							new FilteredImageSource(image.getSource(),
									cropFilter));
					BufferedImage tag = new BufferedImage(cellWidth,
							cellHeight, BufferedImage.TYPE_INT_RGB);
					Graphics g = tag.getGraphics();
					g.drawImage(img, 0, 0, null); // 绘制缩小后的图
					g.dispose();
					// 输出为文件
					OutputStream os = null;
					try {
						os = osGenerater.genOutputStream("_r" + i + "_c" + j,
								"." + IMAGE_TYPE_JPEG);
						ImageIO.write(tag, IMAGE_TYPE_JPEG, os);
					} catch (Exception e) {
						//
					} finally {
						try {
							if (null != os)
								os.close();
						} catch (Exception e2) {
						}
					}
				}
			}
		}
	}

	/**
	 * 图像切割（指定切片的宽度和高度）
	 * 
	 * @param srcImg
	 *            源图像
	 * @param osGenerater
	 *            生成输出流的接口
	 * @param cellWidth
	 *            目标切片宽度。默认200
	 * @param cellHeight
	 *            目标切片高度。默认150
	 * @throws IOException
	 */
	public final static void divideBySize(InputStream srcImg,
			MutiOutputStreamGenerater osGenerater, int cellWidth, int cellHeight)
			throws IOException {
		if (cellWidth <= 0)
			cellWidth = 200; // 切片宽度
		if (cellHeight <= 0)
			cellHeight = 150; // 切片高度
		// 读取源图像
		BufferedImage bi = ImageIO.read(srcImg);
		int srcWidth = bi.getHeight(); // 源图宽度
		int srcHeight = bi.getWidth(); // 源图高度
		if (srcWidth > cellWidth && srcHeight > cellHeight) {
			Image img;
			ImageFilter cropFilter;
			Image image = bi.getScaledInstance(srcWidth, srcHeight,
					Image.SCALE_DEFAULT);
			int colCount = 0; // 切片横向数量
			int rowCount = 0; // 切片纵向数量
			// 计算切片的横向和纵向数量
			if (srcWidth % cellWidth == 0) {
				colCount = srcWidth / cellWidth;
			} else {
				colCount = (int) Math.floor(srcWidth / cellWidth) + 1;
			}
			if (srcHeight % cellHeight == 0) {
				rowCount = srcHeight / cellHeight;
			} else {
				rowCount = (int) Math.floor(srcHeight / cellHeight) + 1;
			}
			// 循环建立切片
			// 改进的想法:是否可用多线程加快切割速度
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < colCount; j++) {
					// 四个参数分别为图像起点坐标和宽高
					// 即: CropImageFilter(int x,int y,int cellWidth,int
					// cellHeight)
					cropFilter = new CropImageFilter(j * cellWidth, i
							* cellHeight, cellWidth, cellHeight);
					img = Toolkit.getDefaultToolkit().createImage(
							new FilteredImageSource(image.getSource(),
									cropFilter));
					BufferedImage tag = new BufferedImage(cellWidth,
							cellHeight, BufferedImage.TYPE_INT_RGB);
					Graphics g = tag.getGraphics();
					g.drawImage(img, 0, 0, null); // 绘制缩小后的图
					g.dispose();
					// 输出为文件
					// 输出为文件
					OutputStream os = null;
					try {
						os = osGenerater.genOutputStream("_r" + i + "_c" + j,
								"." + IMAGE_TYPE_JPEG);
						ImageIO.write(tag, IMAGE_TYPE_JPEG, os);
					} catch (Exception e) {
						//
					} finally {
						try {
							if (null != os)
								os.close();
						} catch (Exception e2) {
						}
					}
				}
			}
		}
	}

	/**
	 * 计算text的长度（一个中文算两个字符）
	 * 
	 * @param text
	 * @return
	 */
	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
}
