package com.longstore.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class FileUtils {

    public static String file2String(File file) throws IOException {
        if (!file.exists()) {
            return null;
        }
        return in2String(new FileInputStream(file));
    }
    private static String in2String(InputStream in) throws IOException {
        if (in == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        InputStreamReader is = null;
        BufferedReader reader = null;
        try {
            is = new InputStreamReader(in);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static byte[] file2byte(File file) throws IOException {
        if (!file.exists()) {
            return null;
        }
        FileInputStream in = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        try {
            byte[] b = new byte[1000];
            int n;
            while ((n = in.read(b)) != -1) {
                bos.write(b, 0, n);
            }
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return bos.toByteArray();
    }

    /**
     * 把输入流写入文件
     * 
     * @param is 输入流
     * @param path 要写入的文件夹
     * @param fileName 要写入的文件名
     * @param isAppend 如何存在这个文件，是否追加在文件内容后面。true：追加，false：不追加
     * @return boolean 写入是否成功
     * @throws IOException
     */
    public static boolean writeFile(InputStream is, String path, String fileName, boolean isAppend) throws IOException {
        if (is == null) {
            return true;
        }
        File root = new File(path);
        if (!root.exists()) {
            root.mkdirs();
        }
        FileOutputStream os = null;
        if (isAppend) {
            os = new FileOutputStream(path + "/" + fileName, isAppend);
        } else {
            os = new FileOutputStream(path + "/" + fileName);
        }
        byte[] bytes = new byte[8192];
        int c = -1;
        try {
            while ((c = is.read(bytes)) != -1) {
                os.write(bytes, 0, c);
            }
            os.flush();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
        return true;
    }

    /**
     * 把内容写入文件
     * 
     * @param content 内容
     * @param path 要写入的文件夹
     * @param fileName 要写入的文件名
     * @param isAppend 如何存在这个文件，是否追加在文件内容后面。true：追加，false：不追加
     * @return boolean 写入是否成功
     * @throws IOException
     */
    public static boolean writeFile(String content, String path, String fileName, boolean isAppend) throws IOException {
        if (StringUtils.isBlank(content)) {
            return true;
        }
        File root = new File(path);
        if (!root.exists()) {
            root.mkdirs();
        }
        FileWriter fw = null;
        try {
            if (isAppend) {
                fw = new FileWriter(path + "/" + fileName, isAppend);
            } else {
                fw = new FileWriter(path + "/" + fileName);
            }
            fw.write(content);
            fw.flush();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (Exception e) {
                }
            }
        }
        return true;
    }

    /**
     * 把生成的文件，下载到本地
     * 
     * @param csvFile 要下载的文件
     * @param fileName 下载下来的文件的名称
     * @param filePrefix 下载下来的文件的后缀
     * @param encode 下载下来的文件的编码
     * @param response
     * @throws Exception
     */
    public static void downloadCsvToLocal(File csvFile, String fileName, String filePrefix, String encode, 
    		HttpServletResponse response, String contentType) throws IOException {
        String fileNm = new StringBuilder(fileName).append(".").append(filePrefix).toString();

        if (StringUtils.isBlank(contentType)) {
            response.setContentType("application/x-msdownload");
        } else {
            response.setContentType(contentType);
        }
        InputStream in = null;
        OutputStream tempOS = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileNm, encode));
            in = new FileInputStream(csvFile);
            tempOS = response.getOutputStream();
            byte[] b = new byte[1024];
            int hasRead = 0;
            while ((hasRead = in.read(b)) != -1) {
                tempOS.write(b, 0, hasRead);
            }
            tempOS.flush();
		} finally {
			if (tempOS != null) {
				try {
			        tempOS.close();
				} catch (Exception e) { }
			}
			if (in != null) {
				try {
			        in.close();
				} catch (Exception e) { }
			}
		}
    }

    public static void downloadCsvToLocal(File csvFile, String fileName, String filePrefix, String encode, 
    		HttpServletResponse response) throws Exception {
    	downloadCsvToLocal(csvFile, fileName, filePrefix, encode, response, null);
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * 
     * @param dir 将要删除的文件目录
     */
    public static boolean deleteDir(String dir) throws Exception {
    	File file = new File(dir);
        return deleteDir(file);
    }
    
    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * 
     * @param dir 将要删除的文件目录
     */
    public static boolean deleteDir(File dir) throws Exception {
    	if (dir.exists()) {
            if (dir.isDirectory()) {
            	File[] children = dir.listFiles();
                for (File c : children) {
                    deleteDir(c);
                }
            }
            return dir.delete();
		}
    	return true;
    }

}
