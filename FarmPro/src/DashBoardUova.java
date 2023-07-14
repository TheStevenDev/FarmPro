import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class DashBoardUova extends JFrame implements ActionListener, MouseListener {
    private User user;

    //BackGround e colori
    private JLabel backGroundLabel = new JLabel();
    private ImageIcon backGroungIcon = new ImageIcon("Images/Uova.jpg");

    private Color myYellow = new Color(255,255,102);
    private Color myYellowHover = new Color(249, 249, 142);
    private Color myOrange = new Color(245,192,106);
    private Font buttonsFont = new Font("Roboto",Font.PLAIN,16);

    private Font labelsFont = new Font("Times new Roman",Font.PLAIN,18);

    //Barra dei Menu
    private JMenuBar bar = new JMenuBar();
    private JMenu homeMenu = new JMenu("Home");
    private JMenuItem homeItem = new JMenuItem("Home");

    private JMenu gallineMenu = new JMenu("Galline");
    private JMenuItem aggiungiGallineItem = new JMenuItem("Aggiungi Galline");
    private JMenuItem rimuoviGallineItem = new JMenuItem("Rimuovi Galline");
    private JMenuItem gallineTotaliItem = new JMenuItem("Galline totali");

    private JMenu uovaMenu = new JMenu("Uova");
    private JMenuItem aggiungiUovaItem = new JMenuItem("Aggiungi Uova");
    private JMenuItem vendiUovaItem = new JMenuItem("Vendi Uova");

    private JMenu impostazioniMenu = new JMenu("Impostazioni");
    private JMenuItem prezzoFareUovo = new JMenuItem();
    private JMenuItem prezzoVenditaUovo = new JMenuItem();
    private JMenuItem prezzoTasse = new JMenuItem();

    private JMenu reportMenu = new JMenu("Genera report");
    private JMenuItem reportItem = new JMenuItem("Genera report magazzino");


    //Lato sinistro
    private JLabel numeroGallineLabel = new JLabel();
    private JButton addGalline = new JButton();
    private JButton removeGalline = new JButton();
    private JTextArea rankGallineArea = new JTextArea();

    //Lato Centrale
    private JButton addRaccolto = new JButton();
    private JButton addVendite = new JButton();
    private JTextArea rankVenditeArea = new JTextArea();

    //Parte Sinistra
    private JLabel lordoLabel = new JLabel();
    private JLabel nettoLabel = new JLabel();
    private JLabel speseLabel = new JLabel();
    private JLabel uovaVendute = new JLabel();

    private JButton filtroDataButton = new JButton();
    private JButton periodoCompletoButton = new JButton();


    public DashBoardUova(User user){
        this.user = user;

        this.setSize(1300,900);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Vendita di Uova | "+user.getFactoryName());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        backGroundLabel.setBounds(0, 0, 1300, 900);
        backGroundLabel.setOpaque(true);
        backGroundLabel.setIcon(backGroungIcon);

        //Barra del menu
        bar.add(homeMenu);
        bar.add(gallineMenu);
        bar.add(uovaMenu);
        bar.add(impostazioniMenu);
        bar.add(reportMenu);

        homeMenu.add(homeItem);

        gallineMenu.add(aggiungiGallineItem);
        gallineMenu.add(rimuoviGallineItem);
        gallineMenu.add(gallineTotaliItem);

        uovaMenu.add(aggiungiUovaItem);
        uovaMenu.add(vendiUovaItem);

        impostazioniMenu.add(prezzoFareUovo);
        impostazioniMenu.add(prezzoVenditaUovo);
        impostazioniMenu.add(prezzoTasse);

        reportMenu.add(reportItem);

        homeItem.addActionListener(this);
        aggiungiGallineItem.addActionListener(this);
        rimuoviGallineItem.addActionListener(this);
        gallineTotaliItem.addActionListener(this);
        aggiungiUovaItem.addActionListener(this);
        vendiUovaItem.addActionListener(this);
        prezzoFareUovo.addActionListener(this);
        prezzoVenditaUovo.addActionListener(this);
        prezzoTasse.addActionListener(this);
        reportItem.addActionListener(this);

        prezzoFareUovo.setText("Imposta prezzo per fare un uovo - corrente: "+this.user.getMagazzinoUova().getPriceForMakingEgg()+"€");
        prezzoVenditaUovo.setText("Imposta prezzo di vendita - corrente: "+this.user.getMagazzinoUova().getPriceForSellEgg()+"€");
        prezzoTasse.setText("Imposta tassazione - corrente: "+this.user.getMagazzinoUova().getPriceTax()+"%");


        //Lato sinistro
        numeroGallineLabel.setBounds(47, 79, 270, 70);
        numeroGallineLabel.setBackground(myOrange);
        numeroGallineLabel.setOpaque(true);
        numeroGallineLabel.setText("Galline: "+String.valueOf(this.user.getMagazzinoUova().gallineTotali()));
        numeroGallineLabel.setForeground(Color.WHITE);
        numeroGallineLabel.setFont(labelsFont);
        numeroGallineLabel.setHorizontalAlignment(JLabel.CENTER);

        addGalline.setBounds(47, 174, 270, 64);
        addGalline.setText("Aggiungi Galline");
        addGalline.setBorder(null);
        addGalline.setOpaque(true);
        addGalline.setForeground(Color.BLACK);
        addGalline.setBackground(myYellow);
        addGalline.setFont(buttonsFont);
        addGalline.setFocusable(false);
        addGalline.addActionListener(this);
        addGalline.addMouseListener(this);

        removeGalline.setBounds(47, 252, 270, 64);
        removeGalline.setText("Rimuovi Galline");
        removeGalline.setBorder(null);
        removeGalline.setOpaque(true);
        removeGalline.setForeground(Color.BLACK);
        removeGalline.setBackground(myYellow);
        removeGalline.setFont(buttonsFont);
        removeGalline.setFocusable(false);
        removeGalline.addActionListener(this);
        removeGalline.addMouseListener(this);

        rankGallineArea.setBounds(47, 341, 270,469);
        rankGallineArea.setEditable(false);
        rankGallineArea.setForeground(Color.BLACK);
        rankGallineArea.setBackground(myOrange);
        rankGallineArea.setFont(labelsFont);


        String finalString="Galline per razza:\n";
        ArrayList <Gallina> listaGallinePerRazza = this.user.getMagazzinoUova().gallinePerRazza();
        for (Gallina g : listaGallinePerRazza) finalString += g.getName() + " - "+g.getAmount()+"\n";
        rankGallineArea.setText(finalString);

        //Parte Centrale

        addRaccolto.setBounds(405, 79, 270, 64);
        addRaccolto.setText("Aggiungi Uova Raccolte");
        addRaccolto.setBorder(null);
        addRaccolto.setOpaque(true);
        addRaccolto.setForeground(Color.BLACK);
        addRaccolto.setBackground(myYellow);
        addRaccolto.setFont(buttonsFont);
        addRaccolto.setFocusable(false);
        addRaccolto.addActionListener(this);
        addRaccolto.addMouseListener(this);

        addVendite.setBounds(405, 158, 270, 64);
        addVendite.setText("Aggiungi Vendita");
        addVendite.setBorder(null);
        addVendite.setOpaque(true);
        addVendite.setForeground(Color.BLACK);
        addVendite.setBackground(myYellow);
        addVendite.setFont(buttonsFont);
        addVendite.setFocusable(false);
        addVendite.addActionListener(this);
        addVendite.addMouseListener(this);

        rankVenditeArea.setBounds(405, 238, 270,572);
        rankVenditeArea.setEditable(false);
        rankVenditeArea.setForeground(Color.BLACK);
        rankVenditeArea.setBackground(myOrange);
        rankVenditeArea.setFont(labelsFont);

        String finalStringSell="Vendite per razza:\n";
        ArrayList<VenditeUova> listaGallinePerSell = this.user.getMagazzinoUova().venditePerGallina();

        for (VenditeUova g : listaGallinePerSell) finalStringSell += g.getRazzaGallina() + " | "+g.getAmount()+" | "+g.getSellDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"\n";
        rankVenditeArea.setText(finalStringSell);

        lordoLabel.setBounds(776, 79, 459,140);
        lordoLabel.setOpaque(true);
        lordoLabel.setBackground(Color.YELLOW);
        lordoLabel.setFont(labelsFont);
        lordoLabel.setHorizontalAlignment(JLabel.CENTER);
        lordoLabel.setText("<html>Lordo:<br>"+this.user.getMagazzinoUova().getGuadagnoLordo()+"€"+"</html>");

        nettoLabel.setBounds(776, 239, 459,140);
        nettoLabel.setOpaque(true);
        nettoLabel.setBackground(Color.GREEN);
        nettoLabel.setFont(labelsFont);
        nettoLabel.setHorizontalAlignment(JLabel.CENTER);
        nettoLabel.setText("<html>Netto:<br>"+this.user.getMagazzinoUova().getGuadagnoNetto()+"€"+"</html>");

        speseLabel.setBounds(776, 399, 459,140);
        speseLabel.setOpaque(true);
        speseLabel.setBackground(Color.RED);
        speseLabel.setFont(labelsFont);
        speseLabel.setHorizontalAlignment(JLabel.CENTER);
        speseLabel.setText("<html>Spese:<br>"+this.user.getMagazzinoUova().getSpese()+"€"+"</html>");

        //Label e pulsanti in basso

        uovaVendute.setBounds(870, 560, 270,50);
        uovaVendute.setOpaque(true);
        uovaVendute.setBackground(myOrange);
        uovaVendute.setFont(labelsFont);
        uovaVendute.setHorizontalAlignment(JLabel.CENTER);
        uovaVendute.setText("Uova vendute: "+this.user.getMagazzinoUova().getUovaVendute());

        filtroDataButton.setBounds(917, 635, 177, 40);
        filtroDataButton.setText("Filtro data");
        filtroDataButton.setBorder(null);
        filtroDataButton.setOpaque(true);
        filtroDataButton.setForeground(Color.BLACK);
        filtroDataButton.setBackground(myYellow);
        filtroDataButton.setFont(buttonsFont);
        filtroDataButton.setFocusable(false);
        filtroDataButton.addActionListener(this);
        filtroDataButton.addMouseListener(this);

        periodoCompletoButton.setBounds(917, 696, 177, 40);
        periodoCompletoButton.setText("Periodo Completo");
        periodoCompletoButton.setBorder(null);
        periodoCompletoButton.setOpaque(true);
        periodoCompletoButton.setForeground(Color.BLACK);
        periodoCompletoButton.setBackground(myYellow);
        periodoCompletoButton.setFont(buttonsFont);
        periodoCompletoButton.setFocusable(false);
        periodoCompletoButton.addActionListener(this);
        periodoCompletoButton.addMouseListener(this);



        this.add(periodoCompletoButton);
        this.add(filtroDataButton);
        this.add(uovaVendute);
        this.add(speseLabel);
        this.add(nettoLabel);
        this.add(lordoLabel);

        this.add(rankVenditeArea);
        this.add(addRaccolto);
        this.add(addVendite);

        this.add(rankGallineArea);
        this.add(removeGalline);
        this.add(addGalline);
        this.add(numeroGallineLabel);

        this.setJMenuBar(bar);
        this.add(backGroundLabel);
    }

    private static LocalDate parseDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(input, formatter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==homeItem){
            this.dispose();

            SwingUtilities.invokeLater(() -> {
                HomePage newFrame = new HomePage(this.user);
                newFrame.setVisible(true);
            });

            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {e1.printStackTrace();}

        }

        //Parte galline

        if(e.getSource()==gallineTotaliItem){
            ImageIcon icon = new ImageIcon("images/pollo.png");
            JOptionPane.showMessageDialog(this, numeroGallineLabel.getText(), "Gallina",JOptionPane.PLAIN_MESSAGE,icon);
        }

        if(e.getSource()==addGalline || e.getSource()==aggiungiGallineItem){
            ImageIcon icon = new ImageIcon("images/pollo.png");
            boolean valid = true;
            String razza="";
            int amount=1;


            try {
                razza = (String) JOptionPane.showInputDialog(null, "Inserisci razza della/e galline: ", "Aggiungi Gallina",JOptionPane.PLAIN_MESSAGE,icon,null,null);
                String amountString = (String) JOptionPane.showInputDialog(null, "Inserisci quantità della/e galline: ", "Aggiungi Gallina",JOptionPane.PLAIN_MESSAGE,icon,null,null);
                amount = Integer.parseInt(amountString);

                if(amount<=0) valid=false;

            } catch (Exception ex) {
                valid = false;
            }

            if(valid){
                ImageIcon validIcon = new ImageIcon("images/valid.png");
                JOptionPane.showMessageDialog(this, "Inserimento completato", "Aggiungi Gallina",JOptionPane.PLAIN_MESSAGE,validIcon);
                this.user.getMagazzinoUova().addGalline(razza,amount);

                SwingUtilities.invokeLater(() -> {
                    DashBoardUova newFrame = new DashBoardUova(this.user);
                    newFrame.setVisible(true);
                });

                try {Thread.sleep(100);}catch (InterruptedException e1) {e1.printStackTrace();}

            }
            else{
                ImageIcon alertIcon = new ImageIcon("images/alert.png");
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Aggiungi Gallina",JOptionPane.PLAIN_MESSAGE,alertIcon);

            }
        }

        if(e.getSource()==rimuoviGallineItem || e.getSource()==removeGalline){
            ImageIcon icon = new ImageIcon("images/pollo.png");
            boolean valid = true;
            String razza="";
            int amount=1;


            try {
                razza = (String) JOptionPane.showInputDialog(null, "Inserisci razza della/e galline: ", "Rimuovi Gallina/e",JOptionPane.PLAIN_MESSAGE,icon,null,null);
                String amountString = (String) JOptionPane.showInputDialog(null, "Inserisci quantità della/e galline: ", "Rimuovi Gallina/e",JOptionPane.PLAIN_MESSAGE,icon,null,null);
                amount = Integer.parseInt(amountString);

                if(amount<=0) valid=false;

            } catch (Exception ex) {
                valid = false;
            }

            if(valid){
                boolean numValid = this.user.getMagazzinoUova().removeGalline(razza, amount);

                if(numValid){
                    ImageIcon validIcon = new ImageIcon("images/valid.png");
                    JOptionPane.showMessageDialog(this, "Rimozione completata", "Rimuovi Gallina/e",JOptionPane.PLAIN_MESSAGE,validIcon);

                    SwingUtilities.invokeLater(() -> {
                        DashBoardUova newFrame = new DashBoardUova(this.user);
                        newFrame.setVisible(true);
                    });

                    try {Thread.sleep(100);}catch (InterruptedException e1) {e1.printStackTrace();}
                }
                else {
                    ImageIcon alertIcon = new ImageIcon("images/alert.png");
                    JOptionPane.showMessageDialog(this, "Errore in inserimento", "Rimuovi Gallina/e",JOptionPane.PLAIN_MESSAGE,alertIcon);

                }



            }
            else{
                ImageIcon alertIcon = new ImageIcon("images/alert.png");
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Rimuovi Gallina/e",JOptionPane.PLAIN_MESSAGE,alertIcon);

            }
        }

        //Gestione Uova
        if(e.getSource()==addRaccolto || e.getSource()==aggiungiUovaItem){
            ImageIcon icon = new ImageIcon("images/egg.png");
            boolean valid = true;
            String razza="";
            int amount=1;


            try {
                razza = (String) JOptionPane.showInputDialog(null, "Inserisci razza della gallina: ", "Aggiungi Lotto Uova",JOptionPane.PLAIN_MESSAGE,icon,null,null);
                String amountString = (String) JOptionPane.showInputDialog(null, "Inserisci quantità della/e uova: ", "Aggiungi Lotto Uova",JOptionPane.PLAIN_MESSAGE,icon,null,null);
                amount = Integer.parseInt(amountString);

                if(amount<=0) valid=false;

            } catch (Exception ex) {
                valid = false;
            }

            if(valid){
                ImageIcon validIcon = new ImageIcon("images/valid.png");
                JOptionPane.showMessageDialog(this, "Inserimento completato", "Aggiungi Lotto Uova",JOptionPane.PLAIN_MESSAGE,validIcon);
                this.user.getMagazzinoUova().addLottoUova(razza, amount);

                SwingUtilities.invokeLater(() -> {
                    DashBoardUova newFrame = new DashBoardUova(this.user);
                    newFrame.setVisible(true);
                });

                try {Thread.sleep(100);}catch (InterruptedException e1) {e1.printStackTrace();}

            }
            else{
                ImageIcon alertIcon = new ImageIcon("images/alert.png");
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Aggiungi Lotto Uova",JOptionPane.PLAIN_MESSAGE,alertIcon);

            }
        }

        if(e.getSource()==addVendite||e.getSource()==vendiUovaItem){
            try{
                ImageIcon icon = new ImageIcon("images/egg.png");

                String razza = (String) JOptionPane.showInputDialog(null, "Inserisci la razza della gallina:", "Prova", JOptionPane.PLAIN_MESSAGE,icon,null,null);

                String quantitaInput = (String) JOptionPane.showInputDialog(null, "Inserisci la quantità:", "Prova", JOptionPane.PLAIN_MESSAGE,icon,null,null);
                int quantita = Integer.parseInt(quantitaInput);


                String dataInput = (String) JOptionPane.showInputDialog(null, "Inserisci la data nel formato dd/MM/yyyy:", "Prova", JOptionPane.PLAIN_MESSAGE,icon,null,null);
                LocalDate data = LocalDate.parse(dataInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                this.user.getMagazzinoUova().addVendita(razza, quantita, data);

                ImageIcon validIcon = new ImageIcon("images/valid.png");
                JOptionPane.showMessageDialog(this, "Inserimento completato", "Aggiungi Lotto Uova",JOptionPane.PLAIN_MESSAGE,validIcon);


                SwingUtilities.invokeLater(() -> {
                    DashBoardUova newFrame = new DashBoardUova(this.user);
                    newFrame.setVisible(true);
                });

                try {Thread.sleep(100);}catch (InterruptedException e1) {e1.printStackTrace();}

            } catch (Exception e1) {
                ImageIcon alertIcon = new ImageIcon("images/alert.png");
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Aggiungi Vendita",JOptionPane.PLAIN_MESSAGE,alertIcon);

            }
        }

        if(e.getSource()==periodoCompletoButton){
            SwingUtilities.invokeLater(() -> {
                DashBoardUova newFrame = new DashBoardUova(this.user);
                newFrame.setVisible(true);
            });

            try {Thread.sleep(100);}catch (InterruptedException e1) {e1.printStackTrace();}
        }

        if(e.getSource()==filtroDataButton){
            ImageIcon icon = new ImageIcon("images/calendar.png");
            ImageIcon alertIcon = new ImageIcon("images/alert.png");

            String inputStart="";
            LocalDate startDate;
            String inputEnd="";
            LocalDate endDate;

            try {

                inputStart = (String) JOptionPane.showInputDialog(null, "Inserisci la data di inizio (formato gg/mm/aaaa):", "Data di inizio", JOptionPane.QUESTION_MESSAGE,icon,null,null);
                startDate = parseDate(inputStart);

                // Chiedi la data di fine
                inputEnd = (String) JOptionPane.showInputDialog(null, "Inserisci la data di fine (formato gg/mm/aaaa):", "Data di fine", JOptionPane.QUESTION_MESSAGE,icon,null,null);
                endDate = parseDate(inputEnd);

                //Operazioni

                String finalStringSell="Vendite per razza:\n";
                ArrayList<VenditeUova> listaGallinePerSell = this.user.getMagazzinoUova().venditePerGallinaFiltro(startDate, endDate);

                for (VenditeUova g : listaGallinePerSell) finalStringSell += g.getRazzaGallina() + " | "+g.getAmount()+" | "+g.getSellDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"\n";
                rankVenditeArea.setText(finalStringSell);

                lordoLabel.setText("<html>Lordo:<br>"+this.user.getMagazzinoUova().getGuadagnoLordoFiltro(startDate,endDate)+"€"+"</html>");
                nettoLabel.setText("<html>Netto:<br>"+this.user.getMagazzinoUova().getGuadagnoNettoFiltro(startDate,endDate)+"€"+"</html>");
                speseLabel.setText("<html>Spese:<br>"+this.user.getMagazzinoUova().getSpeseFiltro(startDate,endDate)+"€"+"</html>");

                uovaVendute.setText("Uova vendute: "+this.user.getMagazzinoUova().uovaVenduteFiltro(startDate, endDate));

            }
            catch (Exception e1){
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Prezzo creazione uovo",JOptionPane.PLAIN_MESSAGE,alertIcon);

            }


        }

        if(e.getSource()==reportItem){
            this.user.getMagazzinoUova().generaPdf(this.user.getMagazzinoUova().getUovaList());
        }

        if(e.getSource()==prezzoFareUovo){
            ImageIcon icon = new ImageIcon("images/gear.png");
            try{
                String x = (String) JOptionPane.showInputDialog(null, "Inserisci nuovo prezzo: ", "Prezzo creazione uovo",JOptionPane.PLAIN_MESSAGE,icon,null,null);
                double newPrice = Double.parseDouble(x);
                this.user.getMagazzinoUova().setPriceForMakingEgg(newPrice);
            }
            catch (Exception e1){
                ImageIcon alertIcon = new ImageIcon("images/alert.png");
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Prezzo creazione uovo",JOptionPane.PLAIN_MESSAGE,alertIcon);
            }

            SwingUtilities.invokeLater(() -> {
                DashBoardUova newFrame = new DashBoardUova(this.user);
                newFrame.setVisible(true);
            });

            try {Thread.sleep(100);}catch (InterruptedException e1) {e1.printStackTrace();}

        }

        if(e.getSource()==prezzoVenditaUovo){
            ImageIcon icon = new ImageIcon("images/gear.png");
            try{
                String x = (String) JOptionPane.showInputDialog(null, "Inserisci nuovo prezzo: ", "Prezzo vendita uovo",JOptionPane.PLAIN_MESSAGE,icon,null,null);
                double newPrice = Double.parseDouble(x);
                this.user.getMagazzinoUova().setPriceForSellEgg(newPrice);
            }
            catch (Exception e1){
                ImageIcon alertIcon = new ImageIcon("images/alert.png");
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Prezzo Creazione uovo",JOptionPane.PLAIN_MESSAGE,alertIcon);
            }

            SwingUtilities.invokeLater(() -> {
                DashBoardUova newFrame = new DashBoardUova(this.user);
                newFrame.setVisible(true);
            });

            try {Thread.sleep(100);}catch (InterruptedException e1) {e1.printStackTrace();}

        }

        if(e.getSource()==prezzoTasse){
            ImageIcon icon = new ImageIcon("images/gear.png");
            try{
                String x = (String) JOptionPane.showInputDialog(null, "Inserisci nuovo prezzo: ", "Prezzo tasse",JOptionPane.PLAIN_MESSAGE,icon,null,null);
                double newPrice = Double.parseDouble(x);
                this.user.getMagazzinoUova().setPriceTax(newPrice);
            }
            catch (Exception e1){
                ImageIcon alertIcon = new ImageIcon("images/alert.png");
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Prezzo tasse",JOptionPane.PLAIN_MESSAGE,alertIcon);
            }

            SwingUtilities.invokeLater(() -> {
                DashBoardUova newFrame = new DashBoardUova(this.user);
                newFrame.setVisible(true);
            });

            try {Thread.sleep(100);}catch (InterruptedException e1) {e1.printStackTrace();}

        }


    }




    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==addGalline) addGalline.setBackground(myYellowHover);
        if(e.getSource()==removeGalline) removeGalline.setBackground(myYellowHover);
        if(e.getSource()==addRaccolto) addRaccolto.setBackground(myYellowHover);
        if(e.getSource()==addVendite) addVendite.setBackground(myYellowHover);
        if(e.getSource()==filtroDataButton) filtroDataButton.setBackground(myYellowHover);
        if(e.getSource()==periodoCompletoButton) periodoCompletoButton.setBackground(myYellowHover);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==addGalline) addGalline.setBackground(myYellow);
        if(e.getSource()==removeGalline) removeGalline.setBackground(myYellow);
        if(e.getSource()==addRaccolto) addRaccolto.setBackground(myYellow);
        if(e.getSource()==addVendite) addVendite.setBackground(myYellow);
        if(e.getSource()==filtroDataButton) filtroDataButton.setBackground(myYellow);
        if(e.getSource()==periodoCompletoButton) periodoCompletoButton.setBackground(myYellow);

    }
}
