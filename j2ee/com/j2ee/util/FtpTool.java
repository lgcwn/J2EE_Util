package com.thunis.qbxs.yjdwbk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpTool {

	public static LoadConfigs.OS osType = LoadConfigs.checkOSType();

	// //ftp常量
	private static Map configMap = LoadConfigs.getConfig();

	public static String FTP_DATA_HOST_IP = (String) configMap
			.get("ftpServer.data.host.ip");
	public static String FTP_DATA_HOST_PORT = (String) configMap
			.get("ftpServer.data.host.port");
	public static String FTP_DATA_HOST_ACCOUNT = (String) configMap
			.get("ftpServer.data.host.account");
	public static String FTP_DATA_HOST_PWD = (String) configMap
			.get("ftpServer.data.host.pwd");
	public static String FTP_DATA_REQUEST_PATH = (String) configMap
			.get("ftpServer.data.request.path");
	public static String FTP_DATA_RESP0NSE_PATH = (String) configMap
			.get("ftpServer.data.response.path");
	public static String FTP_DATA_RESULT_PATH = (String) configMap
			.get("ftpServer.data.result.path");
	public static String FTP_DATA_QUERY_PATH = (String) configMap
			.get("ftpServer.data.query.path");
	public static String FTP_DATA_UPDATE_PATH = (String) configMap
			.get("ftpServer.data.update.path");
	public static String FTP_BACKUP_LOCAL_PATH = (String) configMap
			.get("ftpServer.backup.local.path");
	public static String WIN_BACKUP_LOCAL_REQUEST_PATH = (String) configMap
			.get("winOS.backup.local.request.path");
	public static String WIN_BACKUP_LOCAL_RESULT_PATH = (String) configMap
			.get("winOS.backup.local.result.path");
	public static String WIN_BACKUP_LOCAL_RESPONSE_PATH = (String) configMap
			.get("winOS.backup.local.response.path");
	public static String DATA_LOCAL_PATH = (osType == LoadConfigs.OS.LINUX ? FTP_BACKUP_LOCAL_PATH
			: WIN_BACKUP_LOCAL_REQUEST_PATH);

	private static FTPClient ftpClient = new FTPClient();

	private static FtpTool instance;
	Logger log = Logger.getLogger(FtpTool.class);

	/**
	 * 实例化FtpTool
	 * 
	 * @return
	 */
	public synchronized static FtpTool getInstance() {
		if (instance == null) {
			instance = new FtpTool();
		}
		return instance;
	}

	/**
	 * 获得连接-FTP方式
	 * 
	 * @param hostIp
	 *            FTP服务器地址
	 * @param port
	 *            FTP服务器端口
	 * @param userName
	 *            FTP登录用户名
	 * @param passWord
	 *            FTP登录密码
	 * @return FTPClient
	 */
	public boolean getConnectionFTP(String hostIp, int port, String userName,
			String passWord) throws Exception {
		// 创建FTPClient对象
		ftpClient.setConnectTimeout(3 * 1000);// 设置连接ftp超时时间3秒
		try {
			// 连接FTP服务器
			ftpClient.connect(hostIp, port);

			// 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
			ftpClient.setControlEncoding("UTF-8");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 登录ftp
			ftpClient.login(userName, passWord);
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				ftpClient.disconnect();
				log.info("FTP连接失败");
			}
			System.out.println("Connect FTP Server success.");
		} catch (Exception e) {
			e.printStackTrace();
			closeCon();
			throw e;
		}
		return true;
	}

	/**
	 * FTP参数固定方式，参数在配置文件中
	 * 
	 * @return FTPClient
	 */
	public boolean getConnectionFTP() throws Exception {
		return getConnectionFTP(FTP_DATA_HOST_IP,
				Integer.valueOf(FTP_DATA_HOST_PORT), FTP_DATA_HOST_ACCOUNT,
				FTP_DATA_HOST_PWD);
	}

	/**
	 * <p>
	 * 销毁ftp连接
	 * </p>
	 */
	public static void closeCon() {
		if (ftpClient != null) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.logout();
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 上传文件-FTP方式
	 * 
	 * @param ftp
	 *            FTPClient对象
	 * @param ftpPath
	 *            FTP服务器上传路径
	 * @param localFileIncludePath
	 *            本地文件全路径
	 * @param inputStream
	 *            输入流
	 * @return boolean
	 */
	public boolean uploadFile(String ftpPath, String localFileIncludePath,
			InputStream inputStream, String fileName) throws Exception {
		boolean success = false;
		try {
			if (ftpClient != null) {
				ftpClient.changeWorkingDirectory(ftpPath);// 转移到指定FTP服务器目录
				FTPFile[] fs = ftpClient.listFiles();// 得到目录的相应文件列表
				localFileIncludePath = FtpTool.changeName(localFileIncludePath,
						fs);
				localFileIncludePath = new String(
						localFileIncludePath.getBytes("UTF-8"), "UTF-8");
				ftpPath = new String(ftpPath.getBytes("UTF-8"), "UTF-8");
				// 转到指定上传目录
				ftpClient.changeWorkingDirectory(ftpPath);
				// 将上传文件存储到指定目录
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				// 如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
				success = ftpClient
						.storeFile(localFileIncludePath, inputStream);
				if (success) {
					success = renameFile(fileName + ".tmp", fileName + ".txt");
					if (success) {
						log.info(fileName + ",上传成功且重命名文件也成功!");
					} else {
						log.info(fileName + ",上传成功且重命名文件失败!");
					}
				} else {
					log.info(fileName + ",上传失败");
				}
				// 关闭输入流
				inputStream.close();
				// 退出ftp
				ftpClient.logout();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return success;
	}

	/**
	 * 上传文件到FTP服务器的固定路径，路径见配置文件
	 * 
	 * @param ftp
	 * @param fileName
	 * @param inputStream
	 * @return
	 */
	public boolean uploadFile(String fileName, InputStream inputStream, int step)
			throws Exception {
		String ftpPath = "";
		if (step == 1) {
			ftpPath = FTP_DATA_REQUEST_PATH;
		} else if (step == 2) {
			ftpPath = FTP_DATA_RESP0NSE_PATH;
		} else if (step == 3) {
			ftpPath = FTP_DATA_RESULT_PATH;
		} else if (step == 4) {
			ftpPath = FTP_DATA_QUERY_PATH;
		} else if (step == 5) {
			ftpPath = FTP_DATA_UPDATE_PATH;
		} else {
			throw new NullPointerException("step not null");
		}
		return uploadFile(ftpPath, ftpPath + fileName + ".tmp", inputStream,
				fileName);
	}

	/**
	 * 删除文件-FTP方式
	 * 
	 * @param ftp
	 *            FTPClient对象
	 * @param ftpPath
	 *            FTP服务器上传地址
	 * @param ftpFileName
	 *            FTP服务器上要删除的文件名
	 * @return
	 */
	public boolean deleteFile(String ftpPath, String ftpFileName)
			throws Exception {
		boolean success = false;
		if (ftpClient != null) {
			try {
				ftpClient.changeWorkingDirectory(ftpPath);// 转移到指定FTP服务器目录
				ftpFileName = new String(ftpFileName.getBytes("UTF-8"), "UTF-8");
				ftpPath = new String(ftpPath.getBytes("UTF-8"), "UTF-8");
				success = ftpClient.deleteFile(ftpFileName);
				ftpClient.logout();
			} catch (Exception e) {
				e.printStackTrace();
				closeCon();
				throw e;
			}
		}
		return success;
	}

	/**
	 * 从ftp删除文件
	 * 
	 * @param ftp
	 * @param ftpFileName
	 * @return
	 */
	/*
	 * public boolean deleteFile(FTPClient ftp, String ftpFileName) throws
	 * Exception{ return deleteFile(ftp, FTP_DATA_HOST_PATH, ftpFileName); }
	 */

	/**
	 * 下载文件-FTP方式
	 * 
	 * @param ftp
	 *            FTPClient对象
	 * @param ftpPath
	 *            FTP服务器路径
	 * @param ftpFileName
	 *            FTP服务器文件名
	 * @param localSavePath
	 *            本里存储路径
	 * @return boolean
	 */
	public boolean downFile(String ftpPath, String localSavePath)
			throws Exception {
		boolean success = false;
		try {
			if (ftpClient != null) {
				ftpClient.changeWorkingDirectory(ftpPath);// 转移到FTP服务器目录
				FTPFile[] fs = ftpClient.listFiles(); // 得到目录的相应文件列表
				for (FTPFile ff : fs) {
					if (ff.isDirectory()) {
						continue;
					} else {
						String fileName = ff.getName();
						int b = fileName.lastIndexOf(".");// 最后一出现小数点的位置
						StringBuffer suffix = new StringBuffer(
								fileName.substring(b + 1));// 后缀的名称
						if (suffix.toString().equals("txt")) {
							File localFile = new File(localSavePath
									+ ff.getName());
							OutputStream outputStream = new FileOutputStream(
									localFile);
							// 将文件保存到输出流outputStream中
							ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 定义编码格式
							// 防止文件中的中文出现乱码
							ftpClient.retrieveFile(new String(ff.getName()
									.getBytes("UTF-8"), "UTF-8"), outputStream);
							outputStream.flush();
							outputStream.close();
							System.out.println("Download success.");
						}
					}
				}
				ftpClient.logout();
			}
		} catch (Exception e) {
			e.printStackTrace();
			closeCon();
			throw e;
		}
		return success;
	}

	/**
	 * 从ftp下载文件，固定参数见配置文件
	 * 
	 * @param ftp
	 * @param ftpFileName
	 * @return
	 */
	public Map readFile(int step) throws Exception {
		String ftpPath = "";
		if (step == 2) {
			ftpPath = FTP_DATA_RESP0NSE_PATH;
			return readerBkRespone(ftpPath, WIN_BACKUP_LOCAL_RESPONSE_PATH);
		} else if (step == 3) {
			ftpPath = FTP_DATA_RESULT_PATH;
			return readerBkRespone(ftpPath, WIN_BACKUP_LOCAL_RESULT_PATH);
		} else if (step == 4) {
			ftpPath = FTP_DATA_QUERY_PATH;
		} else if (step == 5) {
			ftpPath = FTP_DATA_UPDATE_PATH;
		} else {
			throw new NullPointerException("step not null");
		}
		return readerBkRespone(ftpPath, DATA_LOCAL_PATH);
	}

	/**
	 * 下载文件-FTP方式
	 * 
	 * @param ftp
	 *            FTPClient对象
	 * @param ftpPath
	 *            FTP服务器路径
	 * @param ftpFileName
	 *            FTP服务器文件名
	 * @param localSavePath
	 *            本里存储路径
	 * @return boolean
	 */
	public Map readerBkRespone(String ftpPath, String localSavePath)
			throws Exception {
		boolean success = false;
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (ftpClient != null) {
				ftpClient.changeWorkingDirectory(ftpPath);// 转移到FTP服务器目录
				FTPFile[] fs = ftpClient.listFiles(); // 得到目录的相应文件列表
				for (FTPFile ff : fs) {
					if (ff.isDirectory()) {
						continue;
					} else {
						String fileName = ff.getName();
						int b = fileName.lastIndexOf(".");// 最后一出现小数点的位置
						StringBuffer suffix = new StringBuffer(
								fileName.substring(b + 1));// 后缀的名称
						if (suffix.toString().equals("txt")) {
							File localFile = new File(localSavePath
									+ ff.getName());
							OutputStream outputStream = new FileOutputStream(
									localFile);
							// 将文件保存到输出流outputStream中
							ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 定义编码格式
							// 防止文件中的中文出现乱码
							ftpClient.retrieveFile(new String(ff.getName()
									.getBytes("UTF-8"), "UTF-8"), outputStream);
							outputStream.flush();
							outputStream.close();
							log.info(" FTP 文件本地保存成功");
							FileInputStream in = new FileInputStream(localFile);
							int len = -1;
							byte[] buffer = new byte[1024];
							while ((len = in.read(buffer)) != -1) {
								String buString = new String(buffer, 0, len);
								map.put(fileName, buString);
							}
							in.close();
							// 处理完数据后将文件移动至FTP备份目录中，当前文件夹中只留未处理的文件
							success = ftpClient.rename(fileName,
									FTP_BACKUP_LOCAL_PATH + fileName);
							log.info("FTP " + fileName + " move success");
						}
					}
				}
				ftpClient.logout();
			}
		} catch (Exception e) {
			e.printStackTrace();
			closeCon();
			throw e;
		}

		return map;
	}

	/**
	 * 改名FTP上的文件
	 */

	public static boolean renameFile(String srcFname, String targetFname) {
		boolean flag = false;
		if (ftpClient != null) {
			try {
				flag = ftpClient.rename(srcFname, targetFname);
			} catch (IOException e) {
				e.printStackTrace();
				closeCon();
			}
		}
		return flag;
	}

	/**
	 * 判断是否有重名文件
	 * 
	 * @param fileName
	 * @param fs
	 * @return
	 */
	public static boolean isFileExist(String fileName, FTPFile[] fs) {
		for (int i = 0; i < fs.length; i++) {
			FTPFile ff = fs[i];
			if (ff.getName().equals(fileName)) {
				return true; // 如果存在返回 正确信号
			}
		}
		return false; // 如果不存在返回错误信号
	}

	/**
	 * 根据重名判断的结果 生成新的文件的名称
	 * 
	 * @param fileName
	 * @param fs
	 * @return
	 */
	public static String changeName(String fileName, FTPFile[] fs) {
		int n = 0;
		// fileName = fileName.append(fileName);
		while (isFileExist(fileName.toString(), fs)) {
			n++;
			String a = "[" + n + "]";
			int b = fileName.lastIndexOf(".");// 最后一出现小数点的位置
			int c = fileName.lastIndexOf("[");// 最后一次"["出现的位置
			if (c < 0) {
				c = b;
			}
			StringBuffer name = new StringBuffer(fileName.substring(0, c));// 文件的名字
			StringBuffer suffix = new StringBuffer(fileName.substring(b + 1));// 后缀的名称
			fileName = name.append(a) + "." + suffix;
		}
		return fileName.toString();
	}
}
