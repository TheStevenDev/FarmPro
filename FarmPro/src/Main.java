import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashBoardOrtaggi newFrame = new DashBoardOrtaggi(new User());
            newFrame.setVisible(true);
        });

        try {
            Thread.sleep(150);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

    }
}