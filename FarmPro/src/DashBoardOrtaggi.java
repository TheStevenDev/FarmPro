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

public class DashBoardOrtaggi extends JFrame implements ActionListener, MouseListener {
    private User user;

    //BackGround e colori
    private JLabel backGroundLabel = new JLabel();
    private ImageIcon backGroungIcon = new ImageIcon("Images/Ortaggi.jpg");

    private Color myYellow = new Color(255,255,102);
    private Color myYellowHover = new Color(249, 249, 142);
    private Color myOrange = new Color(245,192,106);
    private Font buttonsFont = new Font("Roboto",Font.PLAIN,16);

    private Font labelsFont = new Font("Times new Roman",Font.PLAIN,18);

    //Barra dei menu
    private JMenuBar bar = new JMenuBar();
    private JMenu homeMenu = new JMenu("Home");
    private JMenuItem homeItem = new JMenuItem("Home");
    private JMenu azioniMenu = new JMenu("Lotti di Terra");
    private JMenuItem addLottoItem = new JMenuItem("Aggiungi Lotto");
    private JMenuItem removeLottoItem = new JMenuItem("Rimuovi Lotto");
    private JMenuItem vendiLottoItem = new JMenuItem("Vendi Lotto");

    private JMenu impostazioniMenu = new JMenu("Impostazioni");
    private JMenuItem innaffiamentoGiornaliero = new JMenuItem();
    private JMenuItem prezzoTasseItem = new JMenuItem();
    private JMenuItem terraDispinibileItem = new JMenuItem();

    private JMenu reportMenu = new JMenu("Genera report");
    private JMenuItem reportItem = new JMenuItem("Genera report vendite");

    //Parte Sinistra
    private JLabel percentualeDisponibileLabel = new JLabel();
    private JButton addLottoButton = new JButton();
    private JButton removeLottoButton = new JButton();
    private JTextArea lottiArea = new JTextArea();

    //Parte Centrale
    private JButton vendiLottoButton = new JButton();
    private JLabel prossimiRaccoltiLabel = new JLabel();
    private JTextArea prossimiRaccoltiArea = new JTextArea();

    //Parte destra
    private JLabel lordoLabel = new JLabel();
    private JLabel nettoLabel = new JLabel();
    private JButton filtroDataButton = new JButton();
    private JButton periodoCompletoButton = new JButton();
    private JTextArea venditePerPiantaArea = new JTextArea();


    public DashBoardOrtaggi(User user){
        this.user=user;

        this.setSize(1300,900);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Vendita di Ortaggi | "+user.getFactoryName());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        backGroundLabel.setBounds(0, 0, 1300, 900);
        backGroundLabel.setOpaque(true);
        backGroundLabel.setIcon(backGroungIcon);

        //Menu bar
        bar.add(homeMenu);
        homeMenu.add(homeItem);

        bar.add(azioniMenu);
        azioniMenu.add(addLottoItem);
        azioniMenu.add(removeLottoItem);
        azioniMenu.add(vendiLottoItem);

        bar.add(impostazioniMenu);
        impostazioniMenu.add(innaffiamentoGiornaliero);
        impostazioniMenu.add(prezzoTasseItem);
        impostazioniMenu.add(terraDispinibileItem);

        bar.add(reportMenu);
        reportMenu.add(reportItem);

        homeItem.addActionListener(this);
        innaffiamentoGiornaliero.addActionListener(this);
        prezzoTasseItem.addActionListener(this);
        terraDispinibileItem.addActionListener(this);
        addLottoItem.addActionListener(this);
        removeLottoItem.addActionListener(this);
        vendiLottoItem.addActionListener(this);
        reportItem.addActionListener(this);

        innaffiamentoGiornaliero.setText("Imposta prezzo giornaliero per annaffiare un m² di terra - corrente: "+this.user.getVenditaOrtaggi().getPrezzoPerInnaffiare()+"€");
        terraDispinibileItem.setText("Imposta m² di terra disponibili - corrente: "+this.user.getVenditaOrtaggi().getTerraDisponibile()+" m²");
        prezzoTasseItem.setText("Imposta tassazione - corrente: "+this.user.getVenditaOrtaggi().getPriceTax()+"%");

        //Parte Sinistra

        percentualeDisponibileLabel.setBounds(47, 80, 270, 70);
        percentualeDisponibileLabel.setOpaque(true);
        percentualeDisponibileLabel.setBackground(myOrange);
        percentualeDisponibileLabel.setForeground(Color.WHITE);
        percentualeDisponibileLabel.setFont(labelsFont);
        percentualeDisponibileLabel.setHorizontalAlignment(JLabel.CENTER);
        percentualeDisponibileLabel.setText("Disponibile: "+Math.round(this.user.getVenditaOrtaggi().getPercentualeDisponibile())+"%");

        addLottoButton.setBounds(47, 174, 270, 64);
        addLottoButton.setText("Aggiungi Lotto");
        addLottoButton.setBorder(null);
        addLottoButton.setOpaque(true);
        addLottoButton.setForeground(Color.BLACK);
        addLottoButton.setBackground(myYellow);
        addLottoButton.setFont(buttonsFont);
        addLottoButton.setFocusable(false);
        addLottoButton.addActionListener(this);
        addLottoButton.addMouseListener(this);

        removeLottoButton.setBounds(47, 252, 270, 64);
        removeLottoButton.setText("Rimuovi Lotto");
        removeLottoButton.setBorder(null);
        removeLottoButton.setOpaque(true);
        removeLottoButton.setForeground(Color.BLACK);
        removeLottoButton.setBackground(myYellow);
        removeLottoButton.setFont(buttonsFont);
        removeLottoButton.setFocusable(false);
        removeLottoButton.addActionListener(this);
        removeLottoButton.addMouseListener(this);

        lottiArea.setBounds(47, 341, 270,477);
        lottiArea.setEditable(false);
        lottiArea.setForeground(Color.BLACK);
        lottiArea.setBackground(myOrange);
        lottiArea.setFont(labelsFont);


        String finalString="";
        ArrayList<LottoTerreno> listaLotti = this.user.getVenditaOrtaggi().getLottiList();
        for (LottoTerreno g : listaLotti) finalString += g.getNomeLotto() + " | "+g.getPiantaColtivata()+" | "+g.getGrandezza()+" m²\n";
        lottiArea.setText(finalString);

        //Parte Centrale

        vendiLottoButton.setBounds(404, 79, 270, 90);
        vendiLottoButton.setText("Vendi Lotto");
        vendiLottoButton.setBorder(null);
        vendiLottoButton.setOpaque(true);
        vendiLottoButton.setForeground(Color.BLACK);
        vendiLottoButton.setBackground(myYellow);
        vendiLottoButton.setFont(buttonsFont);
        vendiLottoButton.setFocusable(false);
        vendiLottoButton.addActionListener(this);
        vendiLottoButton.addMouseListener(this);

        prossimiRaccoltiLabel.setBounds(404, 186, 270, 64);
        prossimiRaccoltiLabel.setOpaque(true);
        prossimiRaccoltiLabel.setText("Prossimi Raccolti:");
        prossimiRaccoltiLabel.setBackground(myOrange);
        prossimiRaccoltiLabel.setForeground(Color.WHITE);
        prossimiRaccoltiLabel.setFont(labelsFont);
        prossimiRaccoltiLabel.setHorizontalAlignment(JLabel.CENTER);

        prossimiRaccoltiArea.setBounds(404, 252, 270,563);
        prossimiRaccoltiArea.setEditable(false);
        prossimiRaccoltiArea.setForeground(Color.BLACK);
        prossimiRaccoltiArea.setBackground(myOrange);
        prossimiRaccoltiArea.setFont(labelsFont);

        String finalString1="";
        ArrayList<LottoTerreno> listaLotti1 = this.user.getVenditaOrtaggi().getProssimiRaccolti();
        for (LottoTerreno g : listaLotti1) finalString1 += g.getNomeLotto() + " | "+g.getPiantaColtivata()+" | "+g.getDataDiRaccolta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"\n";
        prossimiRaccoltiArea.setText(finalString1);

        //Parte Destra

        lordoLabel.setBounds(761, 79, 230, 87);
        lordoLabel.setOpaque(true);
        lordoLabel.setText("<html>Lordo:<br>"+this.user.getVenditaOrtaggi().getGuadagnoLordo()+"€</html>");
        lordoLabel.setBackground(Color.YELLOW);
        lordoLabel.setForeground(Color.BLACK);
        lordoLabel.setFont(labelsFont);
        lordoLabel.setHorizontalAlignment(JLabel.CENTER);

        nettoLabel.setBounds(1006, 79, 230, 87);
        nettoLabel.setOpaque(true);
        nettoLabel.setText("<html>Netto:<br>"+this.user.getVenditaOrtaggi().getGuadagnoNetto()+"€</html>");
        nettoLabel.setBackground(Color.GREEN);
        nettoLabel.setForeground(Color.BLACK);
        nettoLabel.setFont(labelsFont);
        nettoLabel.setHorizontalAlignment(JLabel.CENTER);

        filtroDataButton.setBounds(760, 186, 230, 40);
        filtroDataButton.setText("Filtro Data");
        filtroDataButton.setBorder(null);
        filtroDataButton.setOpaque(true);
        filtroDataButton.setForeground(Color.BLACK);
        filtroDataButton.setBackground(myYellow);
        filtroDataButton.setFont(buttonsFont);
        filtroDataButton.setFocusable(false);
        filtroDataButton.addActionListener(this);
        filtroDataButton.addMouseListener(this);

        periodoCompletoButton.setBounds(1006, 186, 230, 40);
        periodoCompletoButton.setText("Periodo Com.");
        periodoCompletoButton.setBorder(null);
        periodoCompletoButton.setOpaque(true);
        periodoCompletoButton.setForeground(Color.BLACK);
        periodoCompletoButton.setBackground(myYellow);
        periodoCompletoButton.setFont(buttonsFont);
        periodoCompletoButton.setFocusable(false);
        periodoCompletoButton.addActionListener(this);
        periodoCompletoButton.addMouseListener(this);

        venditePerPiantaArea.setBounds(761, 231, 476,580);
        venditePerPiantaArea.setEditable(false);
        venditePerPiantaArea.setForeground(Color.BLACK);
        venditePerPiantaArea.setBackground(myOrange);
        venditePerPiantaArea.setFont(labelsFont);

        String finalString2="";
        ArrayList<VenditaLotto> listaLotti2 = this.user.getVenditaOrtaggi().getVenditePerPiante();
        for (VenditaLotto g : listaLotti2) finalString2 += g.getNomePianta() + " | "+g.getQuatitaPiante()+" Vendite"+"\n";
        venditePerPiantaArea.setText(finalString2);



        //Aggiunta Al frame
        this.add(venditePerPiantaArea);
        this.add(periodoCompletoButton);
        this.add(filtroDataButton);
        this.add(nettoLabel);
        this.add(lordoLabel);
        this.add(prossimiRaccoltiArea);
        this.add(prossimiRaccoltiLabel);
        this.add(vendiLottoButton);
        this.add(lottiArea);
        this.add(removeLottoButton);
        this.add(addLottoButton);
        this.add(percentualeDisponibileLabel);
        this.setJMenuBar(bar);
        this.add(backGroundLabel);

    }

    public static LocalDate convertiStringaInLocalDate(String dataString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dataString, formatter);
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ImageIcon settingsIcon = new ImageIcon("images/gear.png");
        ImageIcon dateIcon = new ImageIcon("images/calendar.png");
        ImageIcon alertIcon = new ImageIcon("images/alert.png");
        ImageIcon ortaggiIcon = new ImageIcon("images/grano.png");
        ImageIcon validIcon = new ImageIcon("images/valid.png");

        if(e.getSource()==homeItem){
            this.dispose();

            SwingUtilities.invokeLater(() -> {
                HomePage newFrame = new HomePage(this.user);
                newFrame.setVisible(true);
            });

            try {
                Thread.sleep(110);
            } catch (InterruptedException e1) {e1.printStackTrace();}

        }

        //Operazioni con i lotti

        if(e.getSource()==addLottoButton || e.getSource()==addLottoItem){
            try {
                String nomeLotto = (String) JOptionPane.showInputDialog(null, "Inserisci il nome del lotto:","Aggiungi Lotto",JOptionPane.PLAIN_MESSAGE,ortaggiIcon,null,null);
                String piantaColtivata = (String) JOptionPane.showInputDialog(null, "Inserisci il nome della pianta nel lotto:","Aggiungi Lotto",JOptionPane.PLAIN_MESSAGE,ortaggiIcon,null,null);
                double prezzoPerPianta = Double.parseDouble((String) JOptionPane.showInputDialog(null, "Inserisci il prezzo per pianta:","Aggiungi Lotto",JOptionPane.PLAIN_MESSAGE,ortaggiIcon,null,null));
                int grandezza = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Inserisci la grandezza in  m²:","Aggiungi Lotto",JOptionPane.PLAIN_MESSAGE,ortaggiIcon,null,null));
                String dataRaccolta = (String) JOptionPane.showInputDialog(null, "Inserisci la data futura di raccolta:","Aggiungi Lotto",JOptionPane.PLAIN_MESSAGE,ortaggiIcon,null,null);
                int quantitaPiante = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Inserisci la quantità di piante:","Aggiungi Lotto",JOptionPane.PLAIN_MESSAGE,ortaggiIcon,null,null));

                if ((prezzoPerPianta <= 0 || grandezza <= 0 || quantitaPiante <= 0) || convertiStringaInLocalDate(dataRaccolta)==null) {
                    JOptionPane.showMessageDialog(this, "Uno o più valori inseriti non sono validi!", "Errore!",JOptionPane.PLAIN_MESSAGE,alertIcon);
                }
                else{
                    this.user.getVenditaOrtaggi().addLotto(piantaColtivata, nomeLotto, prezzoPerPianta, grandezza, convertiStringaInLocalDate(dataRaccolta),quantitaPiante);
                    JOptionPane.showMessageDialog(this, "Dati inseriti correttamente.","Aggiunta Completata",JOptionPane.PLAIN_MESSAGE,validIcon);

                    this.dispose();

                    SwingUtilities.invokeLater(() -> {
                        DashBoardOrtaggi newFrame = new DashBoardOrtaggi(this.user);
                        newFrame.setVisible(true);
                    });

                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e1) {e1.printStackTrace();}

                }

            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Errore!",JOptionPane.PLAIN_MESSAGE,alertIcon);
            } catch (IllegalArgumentException e1) {
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Errore!",JOptionPane.PLAIN_MESSAGE,alertIcon);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Errore!",JOptionPane.PLAIN_MESSAGE,alertIcon);
            }


        }

        if(e.getSource()==removeLottoButton || e.getSource()==removeLottoItem) {
            try {
                String nomeLotto = (String) JOptionPane.showInputDialog(null, "Inserisci il nome del lotto:","Rimuovi Lotto",JOptionPane.PLAIN_MESSAGE,ortaggiIcon,null,null);
                boolean flag = this.user.getVenditaOrtaggi().removeLotto(nomeLotto);

                if(flag==false){
                    JOptionPane.showMessageDialog(this, "Lotto inesistente", "Errore!",JOptionPane.PLAIN_MESSAGE,alertIcon);
                }
                else{

                    JOptionPane.showMessageDialog(this, "Rimozione Completata","Rimuovi Lotto",JOptionPane.PLAIN_MESSAGE,validIcon);

                    this.dispose();

                    SwingUtilities.invokeLater(() -> {
                        DashBoardOrtaggi newFrame = new DashBoardOrtaggi(this.user);
                        newFrame.setVisible(true);
                    });

                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e1) {e1.printStackTrace();}
                }

            }
            catch (Exception e1e){
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Errore!",JOptionPane.PLAIN_MESSAGE,alertIcon);

            }

        }

        if(e.getSource()==vendiLottoButton || e.getSource()==vendiLottoItem){

            String nomeLotto=null;
            String dataRaccolta=null;
            LocalDate data=null;

            try {
                nomeLotto = (String) JOptionPane.showInputDialog(null, "Inserisci il nome del lotto:","Vendi Lotto",JOptionPane.PLAIN_MESSAGE,ortaggiIcon,null,null);
                dataRaccolta = (String) JOptionPane.showInputDialog(null, "Inserisci la data di raccolta:","Vendi Lotto",JOptionPane.PLAIN_MESSAGE,ortaggiIcon,null,null);

                data = convertiStringaInLocalDate(dataRaccolta);

                this.user.getVenditaOrtaggi().vendiLotto(nomeLotto,data);


                JOptionPane.showMessageDialog(this, "Vendita Completata","Vendi Lotto",JOptionPane.PLAIN_MESSAGE,validIcon);

                this.dispose();

                SwingUtilities.invokeLater(() -> {
                    DashBoardOrtaggi newFrame = new DashBoardOrtaggi(this.user);
                    newFrame.setVisible(true);
                });

                try {
                    Thread.sleep(150);
                } catch (InterruptedException e1) {e1.printStackTrace();}

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Errore!", JOptionPane.PLAIN_MESSAGE, alertIcon);
            }

        }

        if(e.getSource()==filtroDataButton){
            String inputStart="";
            LocalDate startDate;
            String inputEnd="";
            LocalDate endDate;

            try {

                inputStart = (String) JOptionPane.showInputDialog(null, "Inserisci la data di inizio (formato gg/mm/aaaa):", "Data di inizio", JOptionPane.QUESTION_MESSAGE,dateIcon,null,null);
                startDate = convertiStringaInLocalDate(inputStart);

                // Chiedi la data di fine
                inputEnd = (String) JOptionPane.showInputDialog(null, "Inserisci la data di fine (formato gg/mm/aaaa):", "Data di fine", JOptionPane.QUESTION_MESSAGE,dateIcon,null,null);
                endDate = convertiStringaInLocalDate(inputEnd);

                //Operazioni

                String finalString1="";
                ArrayList<LottoTerreno> listaLotti1 = this.user.getVenditaOrtaggi().getProssimiRaccoltiFiltrato(startDate, endDate);
                for (LottoTerreno g : listaLotti1) finalString1 += g.getNomeLotto() + " | "+g.getPiantaColtivata()+" | "+g.getDataDiRaccolta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"\n";
                prossimiRaccoltiArea.setText(finalString1);

                lordoLabel.setText("<html>Lordo:<br>"+this.user.getVenditaOrtaggi().getGuadagnoLordoFiltrato(startDate, endDate)+"€</html>");
                nettoLabel.setText("<html>Netto:<br>"+this.user.getVenditaOrtaggi().getGuadagnoNettoFiltrato(startDate, endDate)+"€</html>");

                String finalString2="";
                ArrayList<VenditaLotto> listaLotti2 = this.user.getVenditaOrtaggi().getVenditePerPianteFiltrato(startDate, endDate);
                for (VenditaLotto g : listaLotti2) finalString2 += g.getNomePianta() + " | "+g.getQuatitaPiante()+" Vendite"+"\n";
                venditePerPiantaArea.setText(finalString2);




            }
            catch (Exception e1){
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Prezzo creazione uovo",JOptionPane.PLAIN_MESSAGE,alertIcon);

            }


        }

        if(e.getSource()==periodoCompletoButton){
            this.dispose();

            SwingUtilities.invokeLater(() -> {
                DashBoardOrtaggi newFrame = new DashBoardOrtaggi(this.user);
                newFrame.setVisible(true);
            });

            try {
                Thread.sleep(150);
            } catch (InterruptedException e1) {e1.printStackTrace();}
        }

        //Impostazioni

        if(e.getSource()==reportItem){
            this.user.getVenditaOrtaggi().generaPdf();

        }

        if(e.getSource()==innaffiamentoGiornaliero){
            try{
                String x = (String) JOptionPane.showInputDialog(null, "Inserisci prezzo per innaffiare un m² di terra: ", "Prezzo innaffiamento",JOptionPane.PLAIN_MESSAGE,settingsIcon,null,null);
                double newPrice = Double.parseDouble(x);
                this.user.getVenditaOrtaggi().setPrezzoPerInnaffiare(newPrice);
            }
            catch (Exception e1){
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Prezzo innaffiamento",JOptionPane.PLAIN_MESSAGE,alertIcon);
            }

            SwingUtilities.invokeLater(() -> {
                DashBoardOrtaggi newFrame = new DashBoardOrtaggi(this.user);
                newFrame.setVisible(true);
            });

            try {Thread.sleep(100);}catch (InterruptedException e1) {e1.printStackTrace();}
        }

        if(e.getSource()==prezzoTasseItem){
            try{
                String x = (String) JOptionPane.showInputDialog(null, "Inserisci nuovo prezzo: ", "Prezzo tasse",JOptionPane.PLAIN_MESSAGE,settingsIcon,null,null);
                double newPrice = Double.parseDouble(x);
                this.user.getVenditaOrtaggi().setPriceTax(newPrice);
            }
            catch (Exception e1){
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "Prezzo tasse",JOptionPane.PLAIN_MESSAGE,alertIcon);
            }

            SwingUtilities.invokeLater(() -> {
                DashBoardOrtaggi newFrame = new DashBoardOrtaggi(this.user);
                newFrame.setVisible(true);
            });

            try {Thread.sleep(100);}catch (InterruptedException e1) {e1.printStackTrace();}
        }

        if(e.getSource()==terraDispinibileItem){
            try{
                String x = (String) JOptionPane.showInputDialog(null, "Inserisci nuova misura in  m²: ", "m² di terra disponibile",JOptionPane.PLAIN_MESSAGE,settingsIcon,null,null);
                double newPrice = Double.parseDouble(x);
                this.user.getVenditaOrtaggi().setTerraDisponibile(newPrice);
            }
            catch (Exception e1){
                JOptionPane.showMessageDialog(this, "Errore in inserimento", "m² di terra disponibile",JOptionPane.PLAIN_MESSAGE,alertIcon);
            }

            SwingUtilities.invokeLater(() -> {
                DashBoardOrtaggi newFrame = new DashBoardOrtaggi(this.user);
                newFrame.setVisible(true);
            });

            try {Thread.sleep(100);}catch (InterruptedException e1) {e1.printStackTrace();}

        }

        //Report di Magazzino



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
        if(e.getSource()==addLottoButton) addLottoButton.setBackground(myYellowHover);
        if(e.getSource()==removeLottoButton) removeLottoButton.setBackground(myYellowHover);
        if(e.getSource()==vendiLottoButton) vendiLottoButton.setBackground(myYellowHover);
        if(e.getSource()==filtroDataButton) filtroDataButton.setBackground(myYellowHover);
        if(e.getSource()==periodoCompletoButton) periodoCompletoButton.setBackground(myYellowHover);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==addLottoButton) addLottoButton.setBackground(myYellow);
        if(e.getSource()==removeLottoButton) removeLottoButton.setBackground(myYellow);
        if(e.getSource()==vendiLottoButton) vendiLottoButton.setBackground(myYellow);
        if(e.getSource()==filtroDataButton) filtroDataButton.setBackground(myYellow);
        if(e.getSource()==periodoCompletoButton) periodoCompletoButton.setBackground(myYellow);
    }
}
