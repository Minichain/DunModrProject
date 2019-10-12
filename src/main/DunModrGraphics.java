package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DunModrGraphics {
    private DunModrJPanel dunModrJPanel;
    private JFrame frame;

    public void createJFrame() {
        //create JFrame Instance
        frame = new JFrame();
        frame.setTitle("main.DunModrJPanel");
        frame.setLayout(new BorderLayout());

        frame.setSize(new Dimension(Parameters.getInstance().getWindowWidth(), Parameters.getInstance().getWindowHeight()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //this will make the app to always display at the center
        frame.setLocationRelativeTo(null);
    }

    public void createJPanel() {
        dunModrJPanel = new DunModrJPanel(Parameters.getInstance().getWindowWidth(), Parameters.getInstance().getWindowHeight());
        dunModrJPanel.setVisible(true);
        dunModrJPanel.addMyKeyListener();
        dunModrJPanel.addMyMouseListener();

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                int newWidth = componentEvent.getComponent().getWidth();
                int newHeight = componentEvent.getComponent().getHeight();
                Log.l("JFrame resized, newWidth: " + newWidth + ", newHeight: " + newHeight);
                Parameters.getInstance().setWindowWidth(newWidth);
                Parameters.getInstance().setWindowHeight(newHeight);
            }
        });
    }

    public void addJPanelToJFrame() {
        frame.add(dunModrJPanel);
        frame.add(dunModrJPanel);
    }

    public void updateFrame(long timeElapsed) {
        dunModrJPanel.setTimeElapsed(timeElapsed);
        dunModrJPanel.repaint();
    }
}