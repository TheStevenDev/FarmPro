import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class VenditaOrtaggi {
    User user;
    //Files
    private File settingsFile = new File("src/Agricoltura/settingsAgricoltura.txt");
    private File lottiFile = new File("src/Agricoltura/lottiFile.txt");
    private File venditeFile = new File("src/Agricoltura/fileVenditeLotti.txt");

    //Lists
    private ArrayList<LottoTerreno> lottiList = new ArrayList<>();
    private ArrayList<VenditaLotto> venditeList = new ArrayList<>();

    //----//


    public ArrayList<VenditaLotto> getVenditeList() {
        return venditeList;
    }

    public ArrayList<LottoTerreno> getLottiList() {
        return lottiList;
    }

    public void setLottiList(ArrayList<LottoTerreno> lottiList) {
        this.lottiList = lottiList;
    }

    public void setVenditeList(ArrayList<VenditaLotto> venditeList) {
        this.venditeList = venditeList;
    }

    public User getUser() {
        return user;
    }

    public VenditaOrtaggi(User user) {
        this.user = user;

        setUpLotti();
        setUpVendite();
    }

    //SetUp lotti

    private void setUpLotti(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(lottiFile));

            String data;
            while ((data=reader.readLine()) !=null){
                String[] datas = data.split(",");

                lottiList.add(new LottoTerreno(datas[0],datas[1],Double.parseDouble(datas[2]),Integer.parseInt(datas[3]), LocalDate.parse(datas[4]),Integer.parseInt(datas[5])));

            }
            reader.close();

        } catch (IOException e) {throw new RuntimeException(e);}
    }

    //SetUpVendite
    private void setUpVendite(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(venditeFile));

            String data;
            while ((data=reader.readLine()) !=null){
                String[] datas = data.split(",");

               venditeList.add(new VenditaLotto(datas[0],datas[1],Integer.parseInt(datas[2]),Double.parseDouble(datas[3]),Integer.parseInt(datas[4]),LocalDate.parse(datas[5])));
            }
            reader.close();

        } catch (IOException e) {throw new RuntimeException(e);}
    }

    //Settings
    private String[] getElements(File file){
        String[] datas;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String data = reader.readLine();

            datas = data.split(",");

            reader.close();

        } catch (IOException e) {throw new RuntimeException(e);}


        return datas;
    }

    //Settings get and setters

    public double getPrezzoPerInnaffiare(){
        String datas[] = getElements(settingsFile);

        return Double.parseDouble(datas[0]);
    }

    public void setPrezzoPerInnaffiare(double newPrice){
        String datas[] = getElements(settingsFile);

        try {
            FileWriter writer = new FileWriter(settingsFile);
            writer.write(String.valueOf(newPrice)+","+datas[1]+","+datas[2]);

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}

    }

    public double getPriceTax(){
        String datas[] = getElements(settingsFile);

        return Double.parseDouble(datas[1]);
    }

    public void setPriceTax(double newPrice){
        String datas[] = getElements(settingsFile);

        try {
            FileWriter writer = new FileWriter(settingsFile);
            writer.write(datas[0]+","+String.valueOf(newPrice)+","+datas[2]);

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}

    }


    public double getTerraDisponibile(){
        String datas[] = getElements(settingsFile);

        return Double.parseDouble(datas[2]);
    }

    public void setTerraDisponibile(double newPrice){
        String datas[] = getElements(settingsFile);

        try {
            FileWriter writer = new FileWriter(settingsFile);
            writer.write(datas[0]+","+datas[1]+","+String.valueOf(newPrice));

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}

    }

    //Parte Sinistra
    public double getPercentualeDisponibile(){
        double totaleDispinibile = getTerraDisponibile();
        double totaleOccupato=0;

        for (LottoTerreno l : lottiList){
            totaleOccupato+=l.getGrandezza();

        }

        double percent = (totaleOccupato/totaleDispinibile)*100;

        return 100-percent;
    }

    public boolean addLotto(String piantaColtivata, String nomeLotto ,double prezzoPerPianta, int grandezza, LocalDate dataDiRaccolta, int quantitaPiante){

        for (LottoTerreno lt: lottiList){
            if(lt.getNomeLotto().equalsIgnoreCase(nomeLotto)) return false;
        }

        LottoTerreno l;
        try {
            l = new LottoTerreno(piantaColtivata,nomeLotto,prezzoPerPianta,grandezza,dataDiRaccolta,quantitaPiante);
        }
        catch (Exception e){
            return false;
        }

        lottiList.add(l);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(lottiFile));

            for (LottoTerreno l1 : lottiList){
                writer.write(l1.getPiantaColtivata()+","+l1.getNomeLotto()+","+l1.getPrezzoPerPianta()+","+l1.getGrandezza()+","+l1.getDataDiRaccolta().toString()+","+l1.getQuantitaPiante()+"\n");
            }

            writer.close();


        } catch (IOException e) {throw new RuntimeException(e);}


        return true;

    }

    public boolean removeLotto(String nomeLotto){
        boolean flag = false;

        for (LottoTerreno lt: lottiList){
            if(lt.getNomeLotto().equalsIgnoreCase(nomeLotto)){
                flag = true;
                lottiList.remove(lt);
                break;
            }
        }

        if(flag == false) return false;


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(lottiFile));

            for (LottoTerreno l1 : lottiList){
                writer.write(l1.getPiantaColtivata()+","+l1.getNomeLotto()+","+l1.getPrezzoPerPianta()+","+l1.getGrandezza()+","+l1.getDataDiRaccolta().toString()+","+l1.getQuantitaPiante()+"\n");
            }

            writer.close();


        } catch (IOException e) {throw new RuntimeException(e);}


        return true;

    }

    public ArrayList<LottoTerreno> getLottiPerGrandezza(){
        ArrayList<LottoTerreno> temp = this.getLottiList();
        for (int i = 0; i < temp.size(); i++) {
            for (int j = 0; j < temp.size() - 1 - i; j++) {
                if (temp.get(j + 1).getGrandezza() > temp.get(j).getGrandezza()) {
                    LottoTerreno l = temp.get(j + 1);
                    temp.set(j + 1, temp.get(j));
                    temp.set(j, l);
                }
            }
        }

        return temp;

    }


    //Parte Centrale
    public boolean vendiLotto(String nomeLotto, LocalDate dataVendita){
        boolean flag = false;
        LottoTerreno lottoDaVendere = null;

        for (LottoTerreno l: lottiList){
            if(l.getNomeLotto().equalsIgnoreCase(nomeLotto)){
                flag=true;
                lottoDaVendere = l;
                break;
            }
        }

        if(!flag) return false;

        venditeList.add(new VenditaLotto(nomeLotto,lottoDaVendere.getPiantaColtivata(),lottoDaVendere.getGrandezza(), lottoDaVendere.getPrezzoPerPianta(), lottoDaVendere.getQuantitaPiante(), dataVendita));
        removeLotto(lottoDaVendere.getNomeLotto());

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(venditeFile));

            for (VenditaLotto l1 : venditeList){
                writer.write(l1.getNomeLotto()+","+l1.getNomePianta()+","+l1.getGrandezzaLotto()+","+l1.getPrezzoPerPianta()+","+l1.getQuatitaPiante()+","+l1.getDataVendita().toString()+"\n");
            }

            writer.close();


        } catch (IOException e) {throw new RuntimeException(e);}

        return true;

    }

    public void generaPdf() {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("FarmPro.pdf"));
            document.open();

            // Aggiunta del titolo "FarmPro" in alto della pagina
            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.RED);
            Phrase titlePhrase = new Phrase("FarmPro", titleFont);
            document.add(titlePhrase);

            // Creazione della tabella
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);

            // Aggiunta delle celle con gli attributi
            com.itextpdf.text.Font cellFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, Font.BOLD);

            PdfPCell cellNomeLotto = new PdfPCell(new Phrase("Nome Lotto"));
            PdfPCell cellNomePianta = new PdfPCell(new Phrase("Nome Pianta"));
            PdfPCell cellPrezzoPerPianta = new PdfPCell(new Phrase("Prezzo per Pianta"));
            PdfPCell cellQuantitaPiante = new PdfPCell(new Phrase("Quantità Piante"));
            PdfPCell cellDataVendita = new PdfPCell(new Phrase("Data Vendita"));

            cellNomeLotto.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellNomePianta.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellPrezzoPerPianta.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellQuantitaPiante.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellDataVendita.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(cellNomeLotto);
            table.addCell(cellNomePianta);
            table.addCell(cellPrezzoPerPianta);
            table.addCell(cellQuantitaPiante);
            table.addCell(cellDataVendita);

            // Popolamento della tabella con i dati
            for (VenditaLotto v : this.user.getVenditaOrtaggi().getVenditeList()) {
                PdfPCell cellNomeLottoDati = new PdfPCell(new Phrase(v.getNomeLotto()));
                PdfPCell cellNomePiantaDati = new PdfPCell(new Phrase(v.getNomePianta()));
                PdfPCell cellPrezzoPerPiantaDati = new PdfPCell(new Phrase(String.valueOf(v.getPrezzoPerPianta())));
                PdfPCell cellQuantitaPianteDati = new PdfPCell(new Phrase(String.valueOf(v.getQuatitaPiante())));
                PdfPCell cellDataVenditaDati = new PdfPCell(new Phrase(v.getDataVendita().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

                cellNomeLottoDati.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellNomePiantaDati.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellPrezzoPerPiantaDati.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellQuantitaPianteDati.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDataVenditaDati.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cellNomeLottoDati);
                table.addCell(cellNomePiantaDati);
                table.addCell(cellPrezzoPerPiantaDati);
                table.addCell(cellQuantitaPianteDati);
                table.addCell(cellDataVenditaDati);
            }

            // Aggiunta della tabella al documento
            document.add(table);

            document.close();

            // Apertura del file PDF generato
            File file = new File("FarmPro1.pdf");
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Errore: Il file PDF non è stato generato correttamente.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }


    //Ordinamento per prossimi raccolti
    public ArrayList<LottoTerreno> getProssimiRaccolti(){
        ArrayList<LottoTerreno> temp = this.getLottiList();
        for (int i = 0; i < temp.size(); i++) {
            for (int j = 0; j < temp.size() - 1 - i; j++) {
                if (temp.get(j + 1).getDataDiRaccolta().isBefore( temp.get(j).getDataDiRaccolta())) {
                    LottoTerreno l = temp.get(j + 1);
                    temp.set(j + 1, temp.get(j));
                    temp.set(j, l);
                }
            }
        }

        return temp;

    }

    //Parte destre (Guadagni)
    public double getGuadagnoLordo(){
        double total=0;

        for (VenditaLotto venditaLotto : venditeList){
            total+=venditaLotto.getPrezzoPerPianta()*venditaLotto.getQuatitaPiante();
        }

        return total;
    }

    public double getGuadagnoNetto(){
        double total = this.getGuadagnoLordo();
        double tax = this.getPriceTax();

        total = total - (total * (tax / 100));

        return total;
    }


    public ArrayList<VenditaLotto> getVenditePerPiante(){
        ArrayList<VenditaLotto> vendite = this.getVenditeList();

        for (int i=0; i<vendite.size();i++){
            String name = vendite.get(i).getNomePianta();

            for (int j=i+1; j<vendite.size();j++){
                if(vendite.get(j).getNomePianta().equalsIgnoreCase(name)){
                    vendite.get(i).setQuatitaPiante(vendite.get(i).getQuatitaPiante()+vendite.get(j).getQuatitaPiante());
                    vendite.remove(vendite.get(j));
                }
            }

        }

        for (int i = 0; i < vendite.size(); i++) {
            for (int j = 0; j < vendite.size() - 1 - i; j++) {
                if (vendite.get(j + 1).getQuatitaPiante() > vendite.get(j).getQuatitaPiante()) {
                    VenditaLotto temp = vendite.get(j + 1);
                    vendite.set(j + 1, vendite.get(j));
                    vendite.set(j, temp);
                }
            }
        }

        return vendite;

    }



    //Filtri Data

    public ArrayList<LottoTerreno> getProssimiRaccoltiFiltrato(LocalDate inizio, LocalDate fine){
        ArrayList<LottoTerreno> temp = this.getLottiList();

        for (LottoTerreno x : temp){
            if(!x.getDataDiRaccolta().isAfter(inizio) && !x.getDataDiRaccolta().isBefore(fine)) temp.remove(x);
        }

        for (int i = 0; i < temp.size(); i++) {
            for (int j = 0; j < temp.size() - 1 - i; j++) {
                if (temp.get(j + 1).getDataDiRaccolta().isBefore( temp.get(j).getDataDiRaccolta())) {
                    LottoTerreno l = temp.get(j + 1);
                    temp.set(j + 1, temp.get(j));
                    temp.set(j, l);
                }
            }
        }

        return temp;

    }

    public double getGuadagnoLordoFiltrato(LocalDate inizio, LocalDate fine){
        double total=0;

        for (VenditaLotto venditaLotto : venditeList){
            if(venditaLotto.getDataVendita().isAfter(inizio) && venditaLotto.getDataVendita().isBefore(fine)){
                total+=venditaLotto.getPrezzoPerPianta()*venditaLotto.getQuatitaPiante();
            }
        }

        return total;
    }

    public double getGuadagnoNettoFiltrato(LocalDate inizio, LocalDate fine){
        double total = this.getGuadagnoLordoFiltrato(inizio, fine);
        double tax = this.getPriceTax();

        total = total - (total * (tax / 100));

        return total;
    }

    public ArrayList<VenditaLotto> getVenditePerPianteFiltrato(LocalDate inizio, LocalDate fine){
        ArrayList<VenditaLotto> vendite = this.getVenditeList();
        ArrayList<VenditaLotto> newVendite = new ArrayList<>();

        for (VenditaLotto venditaLotto : venditeList){
            if(venditaLotto.getDataVendita().isAfter(inizio) && venditaLotto.getDataVendita().isBefore(fine)){
                newVendite.add(venditaLotto);
            }
        }

        for (int i=0; i<newVendite.size();i++){
            String name = newVendite.get(i).getNomePianta();

            for (int j=i+1; j<vendite.size();j++){
                if(newVendite.get(j).getNomePianta().equalsIgnoreCase(name)){
                    newVendite.get(i).setQuatitaPiante(newVendite.get(i).getQuatitaPiante()+newVendite.get(j).getQuatitaPiante());
                    newVendite.remove(newVendite.get(j));
                }
            }

        }

        for (int i = 0; i < newVendite.size(); i++) {
            for (int j = 0; j < newVendite.size() - 1 - i; j++) {
                if (newVendite.get(j + 1).getQuatitaPiante() > newVendite.get(j).getQuatitaPiante()) {
                    VenditaLotto temp = newVendite.get(j + 1);
                    newVendite.set(j + 1, newVendite.get(j));
                    newVendite.set(j, temp);
                }
            }
        }

        return newVendite;

    }



}
