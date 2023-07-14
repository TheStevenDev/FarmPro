import javax.swing.*;
import java.awt.*;

public class LoadingPage extends JFrame {

    //BackGround
    private JLabel backGroundLabel = new JLabel();
    private ImageIcon backGroungIcon = new ImageIcon("Images/FarmPro - Loading.png");

    //Elementi
    JProgressBar progressBar = new JProgressBar(0,100);
    User user = new User();

    public LoadingPage(){
        this.setSize(1300,900);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Caricamento 0% | FarmPro");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        backGroundLabel.setBounds(0, 0, 1300, 900);
        backGroundLabel.setIcon(backGroungIcon);
        backGroundLabel.setOpaque(true);

        progressBar.setBounds(390,426,519,50);
        progressBar.setForeground(Color.WHITE);


        this.add(progressBar);
        this.add(backGroundLabel);



        fill();
    }


    private void fill(){
        int totalSeconds = 4;
        int progress = 0;
        int sleepTime = (totalSeconds * 1000) / 100;

        while (progress < 100) {
            progressBar.setValue(progress);
            this.setTitle("Caricamento "+progress+"% | FarmPro");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progress++;
        }
        this.dispose();

        SwingUtilities.invokeLater(() -> {
            HomePage newFrame = new HomePage(this.user);
            newFrame.setVisible(true);
        });

        try {
            Thread.sleep(130);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }


    }

}
