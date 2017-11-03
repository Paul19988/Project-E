package uk.co.paulcodes.projecte;

import redis.clients.jedis.Jedis;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by paulb on 31/10/2017.
 */
public class Main extends JFrame {

    static String version = "0.4-PreAlpha";

    private static Jedis jedis;

    public static String clientID;

    static Dimension screenSize;

    public static void main(String[] args) {
        try {
            clientID = InetAddress.getLocalHost().getHostAddress();
            System.out.println("My client ID is: " + clientID);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // Load Jedis
        jedis = new Jedis("217.182.252.128", 6379, 5000);
        jedis.auth("65d2b76cde158e6df8c4d35b02e9b54297248a5c");

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        ScreenCheck check = new ScreenCheck();
        Loaded loaded = new Loaded();

        Thread thread1 = new Thread(check, "Thread 1");
        thread1.start();

//        Thread thread2 = new Thread(loaded, "Thread 2");
//        thread2.start();

        buildFrame();
        updateFrame();
        generate();
        maximize();
    }

    public Main() {
        add(new JTextField());
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new Main.MyDispatcher());
    }

    static Main f = new Main();
    public static Screens currentScreen = Screens.OFFLINE;
    public static int currentIndex = 3;
    public static void buildFrame() {
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setUndecorated(true);

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        f.setCursor(blankCursor);

        f.setTitle("Project-E: " + version);
        f.setVisible(true);
    }

    public static void generate() {
//        f.setSize(1920, 1080);
        f.pack();
    }

    public static void minimize() {
        System.out.println("Minimizing");
        f.setVisible(false);
        generate();
        f.setState(Frame.ICONIFIED);
    }

    public static void maximize() {
        System.out.println("Maximizing");
        f.setVisible(true);
        generate();
        f.setState(Frame.NORMAL);
    }

    public static void updateFrame() {

        ImageIcon offlineicon = new ImageIcon(Main.class.getResource("/" + Screens.OFFLINE.getFilename()));
        Image offlineimg = offlineicon.getImage();
        Image newofflineimg = offlineimg.getScaledInstance((int)screenSize.getWidth(), (int)screenSize.getHeight(), Image.SCALE_DEFAULT);
        offlineicon = new ImageIcon(newofflineimg);
        JLabel offline = new JLabel(offlineicon);

        ImageIcon resettingicon = new ImageIcon(Main.class.getResource("/" + Screens.RESETTING.getFilename()));
        Image resettingimg = resettingicon.getImage();
        Image newresettingimg = resettingimg.getScaledInstance((int)screenSize.getWidth(), (int)screenSize.getHeight(), Image.SCALE_DEFAULT);
        resettingicon = new ImageIcon(newresettingimg);
        JLabel resetting = new JLabel(resettingicon);

        ImageIcon maintenanceicon = new ImageIcon(Main.class.getResource("/" + Screens.MAINTENANCE.getFilename()));
        Image maintenanceimg = maintenanceicon.getImage();
        Image newmaintenanceimg = maintenanceimg.getScaledInstance((int)screenSize.getWidth(), (int)screenSize.getHeight(), Image.SCALE_DEFAULT);
        maintenanceicon = new ImageIcon(newmaintenanceimg);
        JLabel maintenance = new JLabel(maintenanceicon);

        ImageIcon waitingicon = new ImageIcon(Main.class.getResource("/" + Screens.WAITING.getFilename()));
        Image waitingimg = waitingicon.getImage();
        Image newwaitingimg = waitingimg.getScaledInstance((int)screenSize.getWidth(), (int)screenSize.getHeight(), Image.SCALE_DEFAULT);
        waitingicon = new ImageIcon(newwaitingimg);
        JLabel waiting = new JLabel(waitingicon);

        ImageIcon waitingforplayersicon = new ImageIcon(Main.class.getResource("/" + Screens.WAITINGFORPLAYERS.getFilename()));
        Image waitingforplayersimg = waitingforplayersicon.getImage();
        Image newwaitingforplayersimg = waitingforplayersimg.getScaledInstance((int)screenSize.getWidth(), (int)screenSize.getHeight(), Image.SCALE_DEFAULT);
        waitingforplayersicon = new ImageIcon(newwaitingforplayersimg);
        JLabel waitingforplayers = new JLabel(waitingforplayersicon);

        switch(currentIndex) {
            case -1:
                minimize();
                f.setContentPane(resetting);
                break;
            case 1:
                f.setContentPane(offline);
                maximize();
                break;
            case 2:
                f.setContentPane(resetting);
                maximize();
                break;
            case 3:
                f.setContentPane(maintenance);
                maximize();
                break;
            case 4:
                f.setContentPane(waiting);
                maximize();
                break;
            case 5:
                f.setContentPane(waitingforplayers);
                maximize();
                break;
        }
    }

    private class MyDispatcher implements KeyEventDispatcher {

        public boolean dispatchKeyEvent(KeyEvent e) {
            if((e.isControlDown() && e.isShiftDown()) && (e.getKeyCode() == e.VK_0)) {
                try {
                    System.out.println("Your Host addr: " + InetAddress.getLocalHost().getHostAddress());

                    NetworkInterface network = null;
                    try {
                        network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
                    } catch (SocketException e1) {
                        e1.printStackTrace();
                    }

                    byte[] mac = new byte[0];
                    try {
                        mac = network.getHardwareAddress();
                    } catch (SocketException e1) {
                        e1.printStackTrace();
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    System.out.println(sb.toString());

                    JOptionPane.showMessageDialog(null, InetAddress.getLocalHost().getHostAddress() + "\n" + sb.toString());
                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if(e.getKeyCode() == e.VK_ESCAPE) {
                    System.exit(0);
                }
                if((e.isAltDown()) && (e.getKeyCode() == e.VK_TAB)) {
                    System.out.println("ALT + TAB");
                    new AltTabStopper(f);
                }
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
            } else if (e.getID() == KeyEvent.KEY_TYPED) {
            }
            return false;
        }
    }

    public static Jedis getJedis() {
        return jedis;
    }

}
