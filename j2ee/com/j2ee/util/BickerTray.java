package com.j2ee.util;   
   
import java.applet.Applet;   
import java.applet.AudioClip;   
import java.awt.AWTException;   
import java.awt.Image;   
import java.awt.MenuItem;   
import java.awt.PopupMenu;   
import java.awt.SystemTray;   
import java.awt.TextArea;   
import java.awt.TrayIcon;   
import java.awt.event.ActionEvent;   
import java.awt.event.ActionListener;   
import java.awt.event.MouseAdapter;   
import java.awt.event.MouseEvent;   
import java.awt.event.WindowAdapter;   
import java.awt.event.WindowEvent;   
import java.net.MalformedURLException;   
import java.net.URL;   
import java.util.Date;   
   
import javax.swing.ImageIcon;   
import javax.swing.JFrame;   
import javax.swing.SwingUtilities;   
import javax.swing.UIManager;   
import javax.swing.UnsupportedLookAndFeelException;   
   
   
   
/**  
 *   
 * ��������������ͼ��  
 * @author Everest  
 *  
 */  
public class BickerTray extends JFrame implements Runnable {   
   
    private static final long serialVersionUID = -3115128552716619277L;   
   
    private SystemTray sysTray;// ��ǰ����ϵͳ�����̶���   
    private TrayIcon trayIcon;// ��ǰ���������   
   
    private ImageIcon icon = null;   
    private TextArea ta = null;   
        
    private static int count = 1; //��¼��Ϣ�����Ĵ���   
    private boolean flag = false; //�Ƿ�������Ϣ   
    private static int times = 1; //������Ϣ����   
   
    public BickerTray() {   
        this.createTrayIcon();// �������̶���   
        Image image = this.getToolkit().getImage(getRes("com/img/f32.gif"));   
        this.setIconImage(image);   
        init();   
    }   
   
    public URL getRes(String str){   
         return this.getClass().getClassLoader().getResource(str);   
    }   
        
    /**  
     * ��ʼ������ķ���  
     */  
    public void init() {   
        this.setTitle("��Ϣ����");   
        ta = new TextArea("");   
        ta.setEditable(false);   
        this.add(ta);   
        this.setSize(400, 400);   
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        this.setLocationRelativeTo(null);   
        // ��Ӵ�����С���¼�,��������ӵ�����ϵͳ������   
        /*this.addWindowListener(new WindowAdapter() {  
            public void windowIconified(WindowEvent e) {  
                addTrayIcon();  
            }  
        });*/  
        addTrayIcon();   
        this.setVisible(true);   
    }   
   
    /**  
     * ������̵ķ���  
     */  
    public void addTrayIcon() {   
        try {   
            sysTray.add(trayIcon);// ��������ӵ�����ϵͳ������   
            setVisible(false);    // ʹ�õ�ǰ�Ĵ�������   
            new Thread(this).start();   
        } catch (AWTException e1) {   
            e1.printStackTrace();   
        }   
    }   
   
    /**  
     * ����ϵͳ���̵Ķ��� ����:   
     * 1,��õ�ǰ����ϵͳ�����̶���   
     * 2,���������˵�popupMenu   
     * 3,��������ͼ��icon  
     * 4,����ϵͳ�����̶���trayIcon  
     */  
    public void createTrayIcon() {   
        sysTray = SystemTray.getSystemTray();// ��õ�ǰ����ϵͳ�����̶���   
        icon = new ImageIcon(getRes("com/img/f17.gif"));// ����ͼ��   
        PopupMenu popupMenu = new PopupMenu();// �����˵�   
        MenuItem mi = new MenuItem("��");   
        MenuItem exit = new MenuItem("�˳�");   
        popupMenu.add(mi);   
        popupMenu.add(exit);   
        // Ϊ�����˵�������¼�   
        mi.addActionListener(new ActionListener() {   
            public void actionPerformed(ActionEvent e) {   
                ta.setText(ta.getText()+"\n==============================================\n ��֪ͨ�� ��������4:00�������ÿ��ᡣ \n ��"+times+"�ν���ʱ�䣺"+ new Date().toLocaleString()); // ����֪ͨ��Ϣ����   
                BickerTray.this.setExtendedState(JFrame.NORMAL);   
                BickerTray.this.setVisible(true); // ��ʾ����   
                BickerTray.this.toFront(); //��ʾ���ڵ���ǰ��   
                flag = false;  //��Ϣ����   
                count = 0; times++;   
            }   
        });   
        exit.addActionListener(new ActionListener() {   
            public void actionPerformed(ActionEvent e) {   
                System.exit(0);   
            }   
        });   
        trayIcon = new TrayIcon(icon.getImage(), "��Ϣ����", popupMenu);   
        /** ������������������������ͼ����˫��ʱ��Ĭ����ʾ���� */  
        trayIcon.addMouseListener(new MouseAdapter() {   
            public void mouseClicked(MouseEvent e) {   
                if (e.getClickCount() == 2) { // ���˫��   
                    ta.setText(ta.getText()+"\n==============================================\n ��֪ͨ�� ��������4:00�������ÿ��ᡣ \n ��"+times+"�ν���ʱ�䣺"+ new Date().toLocaleString()); // ����֪ͨ��Ϣ����   
                    BickerTray.this.setExtendedState(JFrame.NORMAL);   
                    BickerTray.this.setVisible(true); // ��ʾ����   
                    BickerTray.this.toFront();   
                    flag = false;  //��Ϣ����   
                    count = 0; times++;   
                }   
            }   
        });   
    }   
   
    /**  
     * �߳̿�������   
     */  
    public void run() {   
        while (true) {   
            if(flag){ // ������Ϣ   
                try {   
                    if(count == 1){   
                        // ������Ϣ��ʾ��   
                        //AudioPlayer p = new AudioPlayer(getRes("file:com/sound/Msg.wav"));   
                        //p.play(); p.stop();   
                        try {   
                            AudioClip p = Applet.newAudioClip(new URL("file:sound/msg.wav"));   
                            p.play();   
                        } catch (MalformedURLException e) {   
                            e.printStackTrace();   
                        }   
                    }   
                    // ������Ϣ�Ŀհ�ʱ��   
                    this.trayIcon.setImage(new ImageIcon("").getImage());   
                    Thread.sleep(500);   
                    // ������Ϣ����ʾͼƬ   
                    this.trayIcon.setImage(icon.getImage());   
                    Thread.sleep(500);   
                } catch (Exception e) {   
                    e.printStackTrace();   
                }   
                count++;   
            }else{ // ����Ϣ������Ϣ�Ѿ��򿪹�   
                this.trayIcon.setImage(icon.getImage());   
                try {   
                    Thread.sleep(20000);   
                    flag = true;   
                } catch (InterruptedException e) {   
                    e.printStackTrace();   
                }   
            }   
        }   
    }   
   
    /**  
     * @param args  
     */  
    public static void main(String[] args) {   
        JFrame.setDefaultLookAndFeelDecorated(true);   
       /* try {   
           // UIManager.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());   
        } catch (UnsupportedLookAndFeelException e) {   
            e.printStackTrace();   
        }   
 
        SwingUtilities.invokeLater(new Runnable() {   
            public void run() {   
                new BickerTray();   
            }     
        });   */
    }   
   
}