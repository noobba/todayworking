package com.lmh.tw;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class TodayWorkingUtil {

	public static void copy(String text) {
		//System.out.println("##### copy");
        Clipboard clipboard = getSystemClipboard();
        clipboard.setContents(new StringSelection(text), null);
    }

    public static void paste() throws AWTException {
    	//System.out.println("##### paste");
        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
    }

    public static String get() throws Exception {
    	//System.out.println("##### get");
        Clipboard systemClipboard = getSystemClipboard();
        DataFlavor dataFlavor = DataFlavor.stringFlavor;

        if (systemClipboard.isDataFlavorAvailable(dataFlavor)) {
            Object text = systemClipboard.getData(dataFlavor);
            return (String) text;
        }
        return null;
    }

    private static Clipboard getSystemClipboard() {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard systemClipboard = defaultToolkit.getSystemClipboard();

        return systemClipboard;
    }
    
    public static String getLocalServerIp() throws SocketException {
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
        	NetworkInterface intf = (NetworkInterface)en.nextElement();
        	for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
        		InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
        		if ((!inetAddress.isLoopbackAddress()) && (!inetAddress.isLinkLocalAddress()) && (inetAddress.isSiteLocalAddress())) {
        			return inetAddress.getHostAddress().toString();
        		}
        	}
        }
        return null;
      }

      public static String getHostName() throws UnknownHostException {
    	  return InetAddress.getLocalHost().getHostName();
      }

      public static boolean checkAuth(String localIp, String hostName) {
    	  boolean userAuth = false;
    	  if (("192.168.179.101".equals(localIp)) || ("LMH".equals(hostName)) || ("MIKHAIL".equals(hostName)) || ("KSJ".equals(hostName)) || ("GRACE".equals(hostName))) {
    		  userAuth = true;
    	  }
    	  return userAuth;
      }
}
