package com.thunis.qbxs.yjdwbk.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceUtil {

	/**
	 * 工程内资源到文件系统的拷贝。 从类路径到文件系统路径
	 * 
	 * @param resourceAbsoluteClassPath
	 *            绝对类路径
	 * @param targetFile
	 *            目标文件
	 * @throws java.io.IOException
	 */
	public static void copyResourceToFile(String resourceAbsoluteClassPath,
			File targetFile) throws IOException {
		InputStream is = ResourceUtil.class
				.getResourceAsStream(resourceAbsoluteClassPath);
		if (is == null) {
			throw new IOException("Resource not found! "
					+ resourceAbsoluteClassPath);
		}
		OutputStream os = null;
		try {
			os = new FileOutputStream(targetFile);
			byte[] buffer = new byte[2048];
			int length;
			while ((length = is.read(buffer)) != -1) {
				os.write(buffer, 0, length);
			}
			os.flush();
		} finally {
			try {
				is.close();
				if (os != null) {
					os.close();
				}
			} catch (Exception ignore) {
				// ignore
			}
		}
	}

	public static String getAbsolutePath(String classPath) {
		URL configUrl = ResourceUtil.class.getResource(classPath);
		if (configUrl == null) {
			return null;
		}
		try {
			return configUrl.toURI().getPath();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}