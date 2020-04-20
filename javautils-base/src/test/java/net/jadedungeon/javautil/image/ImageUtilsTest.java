package net.jadedungeon.javautil.image;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Font;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.OutputStream;
//import java.util.Properties;

//import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
*/

public class ImageUtilsTest {

	private String imgSrc;
	private String imgTag;

	@Before
	public void setUp() throws Exception {
		String base = System.getProperty("user.dir");// user.dir指定了当前的路径
		this.imgSrc = base + "/images/src/";
		this.imgTag = base + "/images/tag/";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCaculateRatio() {
		double ratio = 1.0;

		ratio = ImageUtils.caculateScaleRatio(100, 100, 200, 250);
		assertTrue(1.99 < ratio && ratio < 2.01);

		ratio = ImageUtils.caculateScaleRatio(100, 100, 250, 200);
		assertTrue(1.99 < ratio && ratio < 2.01);

		ratio = ImageUtils.caculateScaleRatio(100, 100, 80, 30);
		assertTrue(0.29 < ratio && ratio < 0.31);

		ratio = ImageUtils.caculateScaleRatio(100, 100, 30, 80);
		assertTrue(0.29 < ratio && ratio < 0.31);
	}

	@Test
	public void testScale() throws IOException {
		// 1-缩放图像：
		// 方法一：按比例缩放
		ImageUtilsWrapper.scale(this.imgSrc + "abc.jpg", this.imgTag
				+ "abc_scale1.jpg", 2);
		ImageUtilsWrapper.scale(this.imgSrc + "abc.jpg", this.imgTag
				+ "abc_scale2.jpg", 0.5);
		// 方法二：按高度和宽度缩放
		ImageUtilsWrapper.scale(this.imgSrc + "abc.jpg",//
				this.imgTag + "abc_scale3.jpg", 300, 3000, true);
		ImageUtilsWrapper.scale(this.imgSrc + "abc.jpg",//
				this.imgTag + "abc_scale4.jpg", 3000, 300, true);
	}

	@Test
	public void testCut() throws IOException {
		// 2-切割图像：
		// 方法一：按指定起点坐标和宽高切割
		ImageUtilsWrapper.cut(this.imgSrc + "abc.jpg",//
				this.imgTag + "abc_cut.jpg", 0, 0, 400, 400);
		// 方法二：指定切片的行数和列数
		ImageUtilsWrapper.divideByGrid(this.imgSrc + "abc.jpg",//
				this.imgTag + "abc_divide2", 2, 2);
		// 方法三：指定切片的宽度和高度
		ImageUtilsWrapper.divideBySize(this.imgSrc + "abc.jpg", //
				this.imgTag + "abc_divide3", 300, 300);
	}

	@Test
	public void testConv() throws IOException {
		// 3-图像类型转换：
		ImageUtilsWrapper.convert(
				this.imgSrc + "01.jpg",//
				this.imgTag + "conv_01." + ImageUtils.IMAGE_TYPE_JPEG,
				ImageUtils.IMAGE_TYPE_JPEG);
		// ImageUtilsWrapper.convert(
		// this.imgSrc + "01.gif",//
		// this.imgTag + "conv_02." + ImageUtils.IMAGE_TYPE_JPEG,
		// ImageUtils.IMAGE_TYPE_JPEG);
		// ImageUtilsWrapper.convert(
		// this.imgSrc + "01.png",//
		// this.imgTag + "conv_03." + ImageUtils.IMAGE_TYPE_JPEG,
		// ImageUtils.IMAGE_TYPE_JPEG);
		ImageUtilsWrapper.convert(
				this.imgSrc + "01.bmp",//
				this.imgTag + "conv_04." + ImageUtils.IMAGE_TYPE_JPEG,
				ImageUtils.IMAGE_TYPE_JPEG);
	}

	@Test
	public void testGray() throws IOException {
		// 4-彩色转黑白：
		ImageUtilsWrapper.gray(this.imgSrc + "abc.jpg", //
				this.imgTag + "abc_gray.jpg");
	}

	@Test
	public void testPressText() throws IOException {
		// 5-给图片添加文字水印：
		// 方法一：
		ImageUtilsWrapper.pressText(this.imgSrc + "abc.jpg",//
				this.imgTag + "abc_pressText.jpg", //
				"我是水印文字", 0, 0, //
				"宋体", Font.BOLD, 80, Color.white, 0.5f);
		// 方法二：
		ImageUtils.pressText2(this.imgSrc + "abc.jpg",//
				this.imgTag + "abc_pressText2.jpg", //
				"我也是水印文字", 0, 0, //
				"黑体", 36, 80, Color.white, 0.5f);
	}

	@Test
	public void testPressImage() throws IOException {
		// 6-给图片添加图片水印：
		ImageUtilsWrapper.pressImage(this.imgSrc + "abc.jpg",//
				this.imgSrc + "logo.jpg", //
				this.imgTag + "abc_pressImage.jpg", 0, 0, 0.5f);
	}

	@Test
	public void testKaptcha() throws IOException {
		/*
		DefaultKaptcha productor = new DefaultKaptcha();
		productor.setConfig(new Config(new Properties()));

		BufferedImage image = productor.createImage("Hello World");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", out);
		byte[] imageData = out.toByteArray();

		File imageFile = new File(this.imgTag + "kaptcha.jpg");
		OutputStream os = null;
		try {
			os = new FileOutputStream(imageFile);
			os.write(imageData);
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != os) {
				os.close();
			}
		}
		*/
	}
}
