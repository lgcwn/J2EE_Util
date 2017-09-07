package com.thunis.qbxs.yjdwbk.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LoadConfigs {
	/**
	 * 系统的配置文件类路径
	 */
	public static final String CONFIG_PATH = "config.properties";

	private static Properties configs = new Properties();

	private static String propertiesPath = ResourceUtil
			.getAbsolutePath(CONFIG_PATH);

	private static void initConfigs() {
		try {
			Reader inStream = new InputStreamReader(new FileInputStream(
					propertiesPath), "UTF-8");
			configs.load(inStream);
		} catch (IOException e) {
			System.err.println("load config.properties error");
		}
	}

	public static Map getConfig() {
		initConfigs();
		Map<String, String> configMap = new HashMap<String, String>();
		configMap.put("ftpServer.data.host.ip",
				configs.getProperty("ftpServer.data.host.ip"));
		configMap.put("ftpServer.data.host.port",
				configs.getProperty("ftpServer.data.host.port"));
		configMap.put("ftpServer.data.host.account",
				configs.getProperty("ftpServer.data.host.account"));
		configMap.put("ftpServer.data.host.pwd",
				configs.getProperty("ftpServer.data.host.pwd"));
		configMap.put("ftpServer.data.request.path",
				configs.getProperty("ftpServer.data.request.path"));
		configMap.put("ftpServer.data.response.path",
				configs.getProperty("ftpServer.data.response.path"));
		configMap.put("ftpServer.data.result.path",
				configs.getProperty("ftpServer.data.result.path"));
		configMap.put("ftpServer.data.query.path",
				configs.getProperty("ftpServer.data.query.path"));
		configMap.put("ftpServer.data.update.path",
				configs.getProperty("ftpServer.data.update.path"));
		configMap.put("ftpServer.backup.local.path",
				configs.getProperty("ftpServer.backup.local.path"));
		configMap.put("winOS.backup.local.request.path",
				configs.getProperty("winOS.backup.local.request.path"));
		configMap.put("winOS.backup.local.result.path",
				configs.getProperty("winOS.backup.local.result.path"));
		configMap.put("winOS.backup.local.response.path",
				configs.getProperty("winOS.backup.local.response.path"));
		configMap.put("os.name", configs.getProperty("os.name"));
		return configMap;
	}

	public static String getConfig(String key) {
		return configs.getProperty(key);
	}

	public enum OS {
		WIN, LINUX, OTHER
	}

	public static OS checkOSType() {
		String osName = (String) getConfig().get("os.name");
		if (osName.toLowerCase().startsWith("win")) {
			return OS.WIN;
		} else if (osName.toLowerCase().startsWith("lin")) {
			return OS.LINUX;
		} else {
			return OS.OTHER;
		}
	}

}