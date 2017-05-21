package com.up72.hq.utils;

import com.up72.hq.conf.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件发送实现类
 */
public class MailUtil {
    private MimeMessage mimeMsg; // MIME邮件对象
    private Session session; // 邮件会话对象
    private Properties props; // 系统属性
    private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成
    private String username;// 发件人的用户名
    private String password;// 发件人的密码
    private String nickname;// 发件人的昵称
    private static Logger log = LoggerFactory.getLogger(MailUtil.class);

    public MailUtil(){
        String mailName = SystemConfig.instants().getValue("MAIL_ACCOUNT");
        String mail_smtp = MailUtil.getMailSmtp(mailName);
        setSmtpHost(mail_smtp);
        createMimeMessage();
    }

    /**
     * 有参构造器
     *
     * @param smtp
     */
    public MailUtil(String smtp) {
        setSmtpHost(smtp);
        createMimeMessage();
    }

    /**
     * 设置邮件发送的SMTP主机
     */
    public void setSmtpHost(String hostName) {
        if (props == null)
            props = System.getProperties();
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        log.debug("set system properties success ：mail.smtp.host= " + hostName);

    }

    /**
     * 创建邮件对象
     */
    public void createMimeMessage() {
        // 获得邮件会话对象
        session = Session.getDefaultInstance(props, null);
        // 创建MIME邮件对象
        mimeMsg = new MimeMessage(session);
        mp = new MimeMultipart();
        log.debug(" create session and mimeMessage success");
    }

    /**
     * 设置权限鉴定配置
     */
    public void setNeedAuth(boolean need) {
        if (props == null) {
            props = System.getProperties();
        }
        if (need) {
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }
        log.debug("set smtp auth success：mail.smtp.auth= " + need);

    }

    /**
     * 设置发送邮件的主题
     */
    public void setSubject(String subject) throws UnsupportedEncodingException, MessagingException {
        mimeMsg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
        log.debug("set mail subject success, subject= " + subject);

    }

    /**
     * 设置邮件正文
     */
    public void setBody(String mailBody) throws MessagingException {
        BodyPart bp = new MimeBodyPart();
        bp.setContent("" + mailBody, "text/html;charset=utf-8");
        mp.addBodyPart(bp);
        log.debug("set mail body content success,mailBody= " + mailBody);
    }

    /**
     * 添加多个附件
     */

    public void addFileAffix(List<File> fileList) throws MessagingException, UnsupportedEncodingException {
        for (int i = 0; i < fileList.size(); i++) {
            MimeBodyPart mailArchieve = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(fileList.get(i));
            mailArchieve.setDataHandler(new DataHandler(fds));
            mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
            mp.addBodyPart(mailArchieve);
        }
    }


    /**
     * 设置发件人邮箱地址
     */
    public void setSender(String sender) throws UnsupportedEncodingException, AddressException, MessagingException {
        nickname = MimeUtility.encodeText(nickname, "utf-8", "B");
        mimeMsg.setFrom(new InternetAddress(nickname + " <" + sender + ">"));
        log.debug(" set mail sender and nickname success , sender= " + sender + ",nickname=" + nickname);
    }

    /**
     * 设置收件人邮箱地址
     */
    public void setToReceiver(String receiver) throws AddressException, MessagingException {
        InternetAddress[] iaToList = new InternetAddress().parse(receiver);
        mimeMsg.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
        log.debug("set mail receiver success,receiver = " + receiver);
    }

    /**
     * 设置密送人邮箱地址
     */
    public void setBccReceiver(String receiver) throws AddressException, MessagingException {
        InternetAddress[] iaToList = new InternetAddress().parse(receiver);
        mimeMsg.setRecipients(Message.RecipientType.BCC, iaToList); // 收件人
        log.debug("set mail receiver success,receiver = " + receiver);
    }

    /**
     * 设置抄送人的邮箱地址
     */
    public void setCcReceiver(String copyto) throws AddressException, MessagingException {
        InternetAddress[] iaToListcs = new InternetAddress().parse(copyto);
        mimeMsg.setRecipients(Message.RecipientType.CC, iaToListcs); // 抄送人
        log.debug("set mail receiver success,receiver = " + copyto);
    }

    /**
     * 设置图片
     */
    public void setImgs(String imgPath) throws AddressException, MessagingException {
        for (String s : imgPath.split(",")) {
            MimeBodyPart mailArchieve = new MimeBodyPart();
            DataSource fds = new FileDataSource(MailUtil.class.getClassLoader().getResource("").getFile() + "\\" + s);
            mailArchieve.setContent("<img src=\"cid:" + s + "\">", "text/html");
            mailArchieve.setDataHandler(new DataHandler(fds));
            mailArchieve.setHeader("Content-ID", "<" + s + ">");
            mp.addBodyPart(mailArchieve);
            log.debug("set mail success,img = " + fds.getName());
        }
    }

    /**
     * 设置图片
     */
    public void setImgs(Map<String, File> imgMap) throws AddressException, MessagingException {
        for (String key : imgMap.keySet()) {
            MimeBodyPart mailArchieve = new MimeBodyPart();
            DataSource fds = new FileDataSource(imgMap.get(key));
            mailArchieve.setContent("<img src=\"cid:" + key + "\">", "text/html");
            mailArchieve.setDataHandler(new DataHandler(fds));
            mailArchieve.setHeader("Content-ID", "<" + key + ">");
            mp.addBodyPart(mailArchieve);
            log.debug("set mail success,img = " + fds.getName());
        }
    }

    /**
     * 设置发件人用户名密码进行发送邮件操作
     */
    public void sendout() throws MessagingException {
        mimeMsg.setContent(mp);
        mimeMsg.saveChanges();
        Session mailSession = Session.getInstance(props, null);
        Transport transport = mailSession.getTransport("smtp");
        transport.connect((String) props.get("mail.smtp.host"), username, password);
        transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
        transport.close();
        log.debug(" send mail success");
    }

    /**
     * 注入发件人用户名 ，密码 ，昵称
     */
    public void setNamePass(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public static String getMailSmtp(String username) {
        return "smtp." + username.substring(username.indexOf("@") + 1, username.indexOf(".")) + ".com";
    }

    /**
     * 发送邮件
     * @param title 邮件标题
     * @param content 邮件内容
     * @param toReceivers 接收人
     */
    public static void sendEmail(String title,String content,String toReceivers){
        try {
            String username = SystemConfig.instants().getValue("MAIL_ACCOUNT"); // 发件人用户名
            String password = SystemConfig.instants().getValue("MAIL_PWD"); // 发件人密码
            String nickname = SystemConfig.instants().getValue("NICK_NAME"); // 显示的发件人昵称

            MailUtil mailUtil = new MailUtil(MailUtil.getMailSmtp(username));
            mailUtil.setNamePass(username, password, nickname);
            mailUtil.setNeedAuth(false);
            mailUtil.setSubject(title);
            mailUtil.setBody(content);
            mailUtil.setToReceiver(toReceivers);
            mailUtil.setSender(username);
            mailUtil.sendout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            String username = "support@dahuaishu365.com"; // 发件人用户名
            String password = "DHS_Support"; // 发件人密码
            String nickname = "大槐树电商"; // 显示的发件人昵称
            String title = "标题"; // 邮件标题
            String content = "您的验证码是：<h1>336048</h1>"; // 邮件内容
            String toReceivers = "605629803@qq.com"; // 收件人邮箱，多邮箱以逗号分隔

            MailUtil mailUtil = new MailUtil(MailUtil.getMailSmtp(username));
            mailUtil.setNamePass(username, password, nickname);
            mailUtil.setNeedAuth(false);
            mailUtil.setSubject(title);
            mailUtil.setBody(content);
            mailUtil.setToReceiver(toReceivers);
            mailUtil.setSender(username);
            mailUtil.sendout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
