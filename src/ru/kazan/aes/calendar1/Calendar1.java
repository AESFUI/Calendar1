package ru.kazan.aes.calendar1;

import java.awt.*;

/**
 * Created by AES on 04.04.2016.
 */
class Calendar1 {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame CalFrame = new MainFrame();
            CalFrame.setVisible(true);
        });
    }
}
