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

	// //ftp����
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
	 * ʵ����FtpTool
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
	 * �������-FTP��ʽ
	 * 
	 * @param hostIp
	 *            FTP��������ַ
	 * @param port
	 *            FTP�������˿�
	 * @param userName
	 *            FTP��¼�û���
	 * @param passWord
	 *            FTP��¼����
	 * @return FTPClient
	 */
	public boolean getConnectionFTP(String hostIp, int port, String userName,
			String passWord) throws Exception {
		// ����FTPClient����
		ftpClient.setConnectTimeout(3 * 1000);// ��������ftp��ʱʱ��3��
		try {
			// ����FTP������
			ftpClient.connect(hostIp, port);

			// �������д������Ҫ�����Ҳ��ܸı�����ʽ����������ȷ���������ļ�
			ftpClient.setControlEncoding("UTF-8");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// ��¼ftp
			ftpClient.login(userName, passWord);
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				ftpClient.disconnect();
				log.info("FTP����ʧ��");
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
	 * FTP�����̶���ʽ�������������ļ���
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
	 * ����ftp����
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
	 * �ϴ��ļ�-FTP��ʽ
	 * 
	 * @param ftp
	 *            FTPClient����
	 * @param ftpPath
	 *            FTP�������ϴ�·��
	 * @param localFileIncludePath
	 *            �����ļ�ȫ·��
	 * @param inputStream
	 *            ������
	 * @return boolean
	 */
	public boolean uploadFile(String ftpPath, String localFileIncludePath,
			InputStream inputStream, String fileName) throws Exception {
		boolean success = false;
		try {
			if (ftpClient != null) {
				ftpClient.changeWorkingDirectory(ftpPath);// ת�Ƶ�ָ��FTP������Ŀ¼
				FTPFile[] fs = ftpClient.listFiles();// �õ�Ŀ¼����Ӧ�ļ��б�
				localFileIncludePath = FtpTool.changeName(localFileIncludePath,
						fs);
				localFileIncludePath = new String(
						localFileIncludePath.getBytes("UTF-8"), "UTF-8");
				ftpPath = new String(ftpPath.getBytes("UTF-8"), "UTF-8");
				// ת��ָ���ϴ�Ŀ¼
				ftpClient.changeWorkingDirectory(ftpPath);
				// ���ϴ��ļ��洢��ָ��Ŀ¼
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				// ���ȱʡ�þ� ����txt���� ��ͼƬ��������ʽ���ļ������������
				success = ftpClient
						.storeFile(localFileIncludePath, inputStream);
				if (success) {
					success = renameFile(fileName + ".tmp", fileName + ".txt");
					if (success) {
						log.info(fileName + ",�ϴ��ɹ����������ļ�Ҳ�ɹ�!");
					} else {
						log.info(fileName + ",�ϴ��ɹ����������ļ�ʧ��!");
					}
				} else {
					log.info(fileName + ",�ϴ�ʧ��");
				}
				// �ر�������
				inputStream.close();
				// �˳�ftp
				ftpClient.logout();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return success;
	}

	/**
	 * �ϴ��ļ���FTP�������Ĺ̶�·����·���������ļ�
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
	 * ɾ���ļ�-FTP��ʽ
	 * 
	 * @param ftp
	 *            FTPClient����
	 * @param ftpPath
	 *            FTP�������ϴ���ַ
	 * @param ftpFileName
	 *            FTP��������Ҫɾ�����ļ���
	 * @return
	 */
	public boolean deleteFile(String ftpPath, String ftpFileName)
			throws Exception {
		boolean success = false;
		if (ftpClient != null) {
			try {
				ftpClient.changeWorkingDirectory(ftpPath);// ת�Ƶ�ָ��FTP������Ŀ¼
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
	 * ��ftpɾ���ļ�
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
	 * �����ļ�-FTP��ʽ
	 * 
	 * @param ftp
	 *            FTPClient����
	 * @param ftpPath
	 *            FTP������·��
	 * @param ftpFileName
	 *            FTP�������ļ���
	 * @param localSavePath
	 *            ����洢·��
	 * @return boolean
	 */
	public boolean downFile(String ftpPath, String localSavePath)
			throws Exception {
		boolean success = false;
		try {
			if (ftpClient != null) {
				ftpClient.changeWorkingDirectory(ftpPath);// ת�Ƶ�FTP������Ŀ¼
				FTPFile[] fs = ftpClient.listFiles(); // �õ�Ŀ¼����Ӧ�ļ��б�
				for (FTPFile ff : fs) {
					if (ff.isDirectory()) {
						continue;
					} else {
						String fileName = ff.getName();
						int b = fileName.lastIndexOf(".");// ���һ����С�����λ��
						StringBuffer suffix = new StringBuffer(
								fileName.substring(b + 1));// ��׺������
						if (suffix.toString().equals("txt")) {
							File localFile = new File(localSavePath
									+ ff.getName());
							OutputStream outputStream = new FileOutputStream(
									localFile);
							// ���ļ����浽�����outputStream��
							ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// ��������ʽ
							// ��ֹ�ļ��е����ĳ�������
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
	 * ��ftp�����ļ����̶������������ļ�
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
	 * �����ļ�-FTP��ʽ
	 * 
	 * @param ftp
	 *            FTPClient����
	 * @param ftpPath
	 *            FTP������·��
	 * @param ftpFileName
	 *            FTP�������ļ���
	 * @param localSavePath
	 *            ����洢·��
	 * @return boolean
	 */
	public Map readerBkRespone(String ftpPath, String localSavePath)
			throws Exception {
		boolean success = false;
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (ftpClient != null) {
				ftpClient.changeWorkingDirectory(ftpPath);// ת�Ƶ�FTP������Ŀ¼
				FTPFile[] fs = ftpClient.listFiles(); // �õ�Ŀ¼����Ӧ�ļ��б�
				for (FTPFile ff : fs) {
					if (ff.isDirectory()) {
						continue;
					} else {
						String fileName = ff.getName();
						int b = fileName.lastIndexOf(".");// ���һ����С�����λ��
						StringBuffer suffix = new StringBuffer(
								fileName.substring(b + 1));// ��׺������
						if (suffix.toString().equals("txt")) {
							File localFile = new File(localSavePath
									+ ff.getName());
							OutputStream outputStream = new FileOutputStream(
									localFile);
							// ���ļ����浽�����outputStream��
							ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// ��������ʽ
							// ��ֹ�ļ��е����ĳ�������
							ftpClient.retrieveFile(new String(ff.getName()
									.getBytes("UTF-8"), "UTF-8"), outputStream);
							outputStream.flush();
							outputStream.close();
							log.info(" FTP �ļ����ر���ɹ�");
							FileInputStream in = new FileInputStream(localFile);
							int len = -1;
							byte[] buffer = new byte[1024];
							while ((len = in.read(buffer)) != -1) {
								String buString = new String(buffer, 0, len);
								map.put(fileName, buString);
							}
							in.close();
							// ���������ݺ��ļ��ƶ���FTP����Ŀ¼�У���ǰ�ļ�����ֻ��δ������ļ�
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
	 * ����FTP�ϵ��ļ�
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
	 * �ж��Ƿ��������ļ�
	 * 
	 * @param fileName
	 * @param fs
	 * @return
	 */
	public static boolean isFileExist(String fileName, FTPFile[] fs) {
		for (int i = 0; i < fs.length; i++) {
			FTPFile ff = fs[i];
			if (ff.getName().equals(fileName)) {
				return true; // ������ڷ��� ��ȷ�ź�
			}
		}
		return false; // ��������ڷ��ش����ź�
	}

	/**
	 * ���������жϵĽ�� �����µ��ļ�������
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
			int b = fileName.lastIndexOf(".");// ���һ����С�����λ��
			int c = fileName.lastIndexOf("[");// ���һ��"["���ֵ�λ��
			if (c < 0) {
				c = b;
			}
			StringBuffer name = new StringBuffer(fileName.substring(0, c));// �ļ�������
			StringBuffer suffix = new StringBuffer(fileName.substring(b + 1));// ��׺������
			fileName = name.append(a) + "." + suffix;
		}
		return fileName.toString();
	}
}
