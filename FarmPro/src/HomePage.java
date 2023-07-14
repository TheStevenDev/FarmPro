import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HomePage extends JFrame implements ActionListener, MouseListener {

    User user;

    //BackGround e colori
    private JLabel backGroundLabel = new JLabel();
    private ImageIcon backGroungIcon = new ImageIcon("Images/FarmPro - Home.png");

    private Color myYellow = new Color(255,255,102);
    private Color myYellowHover = new Color(249, 249, 142);
    private Font buttonsFont = new Font("Arial",Font.BOLD,14);

    //Elementi

    private JButton uovaButton = new JButton("Vendita di uova \uD83E\uDD5A");
    private JButton ortaggiButton = new JButton("Coltivazione di Ortaggi \uD83C\uDF3E");

    //MenuBar
    private JMenuBar bar = new JMenuBar();

    private JMenu azioniMenu = new JMenu("Azioni");
    private JMenuItem uovaItem = new JMenuItem("Vendita di uova");
    private JMenuItem ortaggiItem = new JMenuItem("Coltivazione di Ortaggi");

    //
    private JMenu settingsMenu = new JMenu("Impostazioni");
    private JMenuItem cambiaNomeFattoria = new JMenuItem("Cambia nome Azienda");
    private JMenuItem cambiaNomeProprietario = new JMenuItem("Cambia nome Proprietario");

    private JMenu exitMenu = new JMenu("Esci");
    private JMenuItem exitItem = new JMenuItem("Esci");


    public HomePage(User user){
        this.user = user;

        this.setSize(1300,900);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("HomePage | "+this.user.getFactoryName()+" Di: "+this.user.getOwnerName());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        backGroundLabel.setBounds(0, 0, 1300, 900);
        backGroundLabel.setOpaque(true);
        backGroundLabel.setIcon(backGroungIcon);

        uovaButton.setBounds(508, 247, 270, 78);
        uovaButton.setOpaque(true);
        uovaButton.setForeground(Color.BLACK);
        uovaButton.setBackground(myYellow);
        uovaButton.setFont(buttonsFont);
        uovaButton.setFocusable(false);
        uovaButton.setBorder(null);
        uovaButton.addActionListener(this);
        uovaButton.addMouseListener(this);

        ortaggiButton.setBounds(508, 372, 270, 78);
        ortaggiButton.setOpaque(true);
        ortaggiButton.setForeground(Color.BLACK);
        ortaggiButton.setBackground(myYellow);
        ortaggiButton.setFont(buttonsFont);
        ortaggiButton.setFocusable(false);
        ortaggiButton.setBorder(null);
        ortaggiButton.addActionListener(this);
        ortaggiButton.addMouseListener(this);


        azioniMenu.add(uovaItem);
        azioniMenu.add(ortaggiItem);

        uovaItem.addActionListener(this);
        ortaggiItem.addActionListener(this);

        cambiaNomeFattoria.addActionListener(this);
        cambiaNomeProprietario.addActionListener(this);

        settingsMenu.add(cambiaNomeFattoria);
        settingsMenu.add(cambiaNomeProprietario);

        exitItem.addActionListener(this);
        exitMenu.add(exitItem);

        bar.add(azioniMenu);
        bar.add(settingsMenu);
        bar.add(exitMenu);

        this.setJMenuBar(bar);

        this.add(uovaButton);
        this.add(ortaggiButton);

        this.add(backGroundLabel);
    }

    private void apriUova(User newuser){
        SwingUtilities.invokeLater(() -> {
            DashBoardUova newFrame = new DashBoardUova(newuser);
            newFrame.setVisible(true);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private void apriOrtaggi(User newuser){
        SwingUtilities.invokeLater(() -> {
            DashBoardOrtaggi newFrame = new DashBoardOrtaggi(newuser);
            newFrame.setVisible(true);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==exitItem) this.dispose();

        if(e.getSource()==cambiaNomeFattoria){
            ImageIcon icon = new ImageIcon("images/gear.png");
            String name = (String) JOptionPane.showInputDialog(this, "Inserisci nuovo nome: ", "Cambia nome Azienda",JOptionPane.PLAIN_MESSAGE,icon,null,null);

            if((name.trim()!=null) && (!name.trim().equals(""))){
                user.setFactoryName(name);

                dispose();
                SwingUtilities.invokeLater(() -> {
                    HomePage newFrame = new HomePage(this.user);
                    newFrame.setVisible(true);
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

            }

        }

        if(e.getSource()==cambiaNomeProprietario){
            ImageIcon icon = new ImageIcon("images/gear.png");
            String name = (String) JOptionPane.showInputDialog(this, "Inserisci nuovo nome: ", "Cambia nome Azienda",JOptionPane.PLAIN_MESSAGE,icon,null,null);

            if((name.trim()!=null) && (!name.trim().equals(""))){
                user.setOwnerName(name);

                dispose();
                SwingUtilities.invokeLater(() -> {
                    HomePage newFrame = new HomePage(this.user);
                    newFrame.setVisible(true);
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

        }

        if(e.getSource()==uovaItem || e.getSource()==uovaButton){
            this.dispose();
            apriUova(this.user);
        }

        if(e.getSource()==ortaggiItem || e.getSource()==ortaggiButton) {
            this.dispose();
            apriOrtaggi(this.user);
        }


    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==uovaButton) uovaButton.setBackground(myYellowHover);
        if(e.getSource()==ortaggiButton) ortaggiButton.setBackground(myYellowHover);


    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==uovaButton) uovaButton.setBackground(myYellow);
        if(e.getSource()==ortaggiButton) ortaggiButton.setBackground(myYellow);

    }
}
