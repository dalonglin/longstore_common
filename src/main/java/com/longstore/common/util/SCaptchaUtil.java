package com.longstore.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Random;

public class SCaptchaUtil {

	// 默认图片的宽度。
	private static int width = 90;
	// 默认图片的高度。
	private static int height = 30;
	// 默认验证码字符个数
	private static int codeCount = 4;
	// 默认验证码干扰线数
	private static int lineCount = 50;
	private static char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static Random random = new Random();

	private SCaptchaUtil() {

	}

	public static CodeResult generateCode() {
		return generateCode(width, height, lineCount, codeCount);
	}

	public static CodeResult generateCode(int width, int height, int lineCount, int codeCount) {
		int fontHeight = height - 5;// 字体的高度
		int codeX = width / (codeCount * 2 - 1);// 每个字符的宽度

		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 图像buffer
		Graphics2D g = buffImg.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		// 绘制干扰线
		for (int i = 0; i < lineCount; i++) {
			int xs = getRandomNumber(width);
			int ys = getRandomNumber(height);
			int xe = xs + getRandomNumber(width / 8);
			int ye = ys + getRandomNumber(height / 8);
			g.setColor(getRandomColor());
			g.drawLine(xs, ys, xe, ye);
		}

		// 随机产生验证码字符
		StringBuffer randomCode = new StringBuffer();
		ImgFontByte imgFont = new ImgFontByte();// 创建字体
		for (int i = 0; i < codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			// 设置字体颜色
			g.setColor(getRandomColor());
			Font font = null;
			if (i % 2 == 0) {
				font = imgFont.getFont(fontHeight);
			} else {
				font = imgFont.getFont(fontHeight - 5);
			}
			g.setFont(font);
			// 设置字体位置
			g.drawString(strRand, (i + 1) * codeX, getRandomNumber(height / 2) + 15);
			randomCode.append(strRand);
		}
		return new CodeResult(randomCode.toString(), buffImg);
	}

	/** 获取随机颜色 */
	private static Color getRandomColor() {
		int r = getRandomNumber(255);
		int g = getRandomNumber(255);
		int b = getRandomNumber(255);
		return new Color(r, g, b);
	}

	/** 获取随机数 */
	private static int getRandomNumber(int number) {
		return random.nextInt(number);
	}

	/** 字体样式类 */
	static class ImgFontByte {
		public Font getFont(int fontHeight) {
			try {
				Font baseFont = Font.createFont(Font.TRUETYPE_FONT, new ByteArrayInputStream(null));
				return baseFont.deriveFont(Font.PLAIN, fontHeight);
			} catch (Exception e) {
				return new Font("Arial", Font.PLAIN, fontHeight);
			}
		}
	}
	public static class CodeResult {
		private String code;
		private BufferedImage img;
		
		public CodeResult(String code, BufferedImage img){
			this.code = code;
			this.img = img;
		}

		public String getCode() {
			return code;
		}
		public BufferedImage getImg() {
			return img;
		}
	}
}
