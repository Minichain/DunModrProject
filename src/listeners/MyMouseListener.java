package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener {
    private static MyMouseListener instance = null;

    private MyMouseListener() {
    }

    public static MyMouseListener getInstance() {
        if (instance == null) {
            instance = new MyMouseListener();
        }
        return instance;
    }


    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed at coordinates " + e.getX() + ", " + e.getY());
    }

    public void mouseReleased(MouseEvent e) {
//        System.out.println("mouseReleased event: " + e);
    }

    public void mouseEntered(MouseEvent e) {
//        System.out.println("mouseEntered event: " + e);
    }

    public void mouseExited(MouseEvent e) {
//        System.out.println("mouseExited event: " + e);
    }

    public void mouseClicked(MouseEvent e) {
//        System.out.println("mouseClicked event: " + e);
    }
}