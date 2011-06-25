/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heartsgame;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author kydrenw
 */
public class UpdateInterface {

    public static String interfacejava() {
        return "javax.swing.plaf.metal.MetalLookAndFeel";
    }

    public static String interfaceWin() {
        return "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    }

    public static String interfacelinux() {
        return new String("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    }

    public UpdateInterface(String LF, Component c) {
        Component root = c;
        root.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            UIManager.setLookAndFeel(LF);
            SwingUtilities.updateComponentTreeUI(c);


        } catch (UnsupportedLookAndFeelException exc) {
            System.err.println("Unsupported LookAndFeel: ");

        } catch (Exception exc2) {
            System.err.println("Couldn't load Look and feel");
        }

        root.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

    }
}
