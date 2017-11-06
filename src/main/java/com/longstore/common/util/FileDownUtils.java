package com.longstore.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载处理
 */
public class FileDownUtils {
    
	private final static int TIME_OUT = 3000;

	public static void setHeader(HttpServletResponse response, String fileName) throws IOException {
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
        response.setHeader("Pragma", "public");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
	
	/**
	 * 从网络Url中下载文件
	 * 
	 * @param url        文件的网络地址
	 * @param filePath   要保存的文件路径
	 * @param fileName   要保存的文件名称
	 */
	public static void downFromUrl(String url, String filePath, String fileName) throws Exception{
		InputStream is = null;
		try {
			URL _url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)_url.openConnection();
			conn.setConnectTimeout(TIME_OUT);
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
			// 得到输入流
			is = conn.getInputStream();
			FileUtils.writeFile(is, filePath, fileName, false);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) { }
			}
		}
	}
	
	/**
	 * 从网络Url中下载文件
	 * 
	 * @param url        文件的网络地址
	 */
	public static byte[] downFromUrl(String url) throws Exception{
		InputStream is = null;
		try {
			URL _url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)_url.openConnection();
			conn.setConnectTimeout(TIME_OUT);
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
			// 得到输入流
			is = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while ((len = is.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) { }
			}
		}
	}

}
