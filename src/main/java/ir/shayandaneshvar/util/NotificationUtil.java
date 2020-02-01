package ir.shayandaneshvar.util;

import java.awt.*;

public final class NotificationUtil {

    private NotificationUtil() {
    }

    public static void displayTray(String text) {
        displayTray(text, TrayIcon.MessageType.INFO);
    }

    public static void displayTray(String text, TrayIcon.MessageType type) {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("");
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        trayIcon.setToolTip("Huffman Compressor");
        try {
            tray.add(trayIcon);
            trayIcon.displayMessage("Huffman Compressor", text,
                    type);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

}
