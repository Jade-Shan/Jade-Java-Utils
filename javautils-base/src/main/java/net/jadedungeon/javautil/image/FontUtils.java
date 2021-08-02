package net.jadedungeon.javautil.image;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import sun.font.FontDesignMetrics;

public class FontUtils {

	/**
	 * caculate the image size of a line of chars
	 * 
	 * @param font    using font
	 * @param content text contenx
	 * @return int arr [width, height]
	 */
	public static int[] calLineSize(Font font, String content) {
		int[] result = { 0, 0 };
		FontDesignMetrics fm = FontDesignMetrics.getMetrics(font);
		int height = fm.getHeight();
		int width = 0;
		for (int i = 0; i < content.length(); i++) {
			width += fm.charWidth(content.charAt(i));
		}
		result[0] = width;
		result[1] = height;
		return result;
	}

	/**
	 * print font on image with border
	 * 
	 * @param text    text Str
	 * @param font    text font
	 * @param borderX border x
	 * @param borderY border y
	 * @return image
	 */
	public static BufferedImage drawChars(String text, Font font, int borderX, int borderY) {
		int[] imgSize = calLineSize(font, text);
		int width = imgSize[0];
		int height = imgSize[1];
		int x = borderX;
		int y = height / 2 + borderY;
		width = width + borderX * 2;
		height = height + borderY * 2;
		BufferedImage bimg = drawChars(text, font, width, height, x, y);
		return bimg;
	}

	/**
	 * Print fonts on image
	 * 
	 * @param text   text Str
	 * @param font   text font
	 * @param width  image width
	 * @param height image height
	 * @param x      text x pos
	 * @param y      text y pos
	 * @return image
	 */
	public static BufferedImage drawChars(String text, Font font, int width, int height, int x, int y) {
		BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics2D gph = bimg.createGraphics();
		gph.setFont(font);
		gph.drawString(text, x, y);
		return bimg;
	}

	/**
	 * load a ttf Fonts File
	 * 
	 * @param fontFileName full path name of ttf File
	 * @param fontSize     font size
	 * @return font
	 */
	public static Font loadTTF(String fontFileName, float fontSize) {
		try (FileInputStream fi = new FileInputStream(fontFileName)) {
			Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, fi);
			Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
			return dynamicFontPt;
		} catch (Exception e) {
			e.printStackTrace();
			return new java.awt.Font("宋体", Font.PLAIN, (int) fontSize);
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Font font = loadTTF("/AaSpot.ttf", 20);// 调用
		//
		BufferedImage img = drawChars("Hello World", font, 138, 38, 10, 25);
		ImageIO.write(img, "png", new FileOutputStream(new File("/out.png")));
		//
		img = drawChars("Hello World", font, 1, 1);
		ImageIO.write(img, "png", new FileOutputStream(new File("/out2.png")));
	}

}
