import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.awt.*;
import java.awt.Font;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MagazzinoUova{
    private User user;

    //Files
    private File settingsFile = new File("src/Uova/settingsFileUova.txt");
    private File uovaFile = new File("src/Uova/uovaFile.txt");
    private File venditeUovaFile = new File("src/Uova/venditeUova.txt");
    private File fileGalline = new File("src/Uova/fileGalline.txt");

    //Liste
    private ArrayList <Uovo> uovaList= new ArrayList<>();
    private ArrayList <VenditeUova> venditeList = new ArrayList<>();
    private ArrayList <Gallina> gallineList = new ArrayList<>();


    public MagazzinoUova(User user){
        this.user = user;

        setUpListGalline();
        setUpListUova();
        setUpVendite();
    }

    public User getUser(){
        return this.user;
    }

    //List setup

    public ArrayList<Uovo> getUovaList(){
        return this.uovaList;
    }

    private void setUpListGalline(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileGalline));

            String data;
            while ((data=reader.readLine())!=null){
                String datas[] = data.split(",");

                gallineList.add(new Gallina(datas[0],Integer.parseInt(datas[1])));

            }

            reader.close();


        } catch (IOException e) {throw new RuntimeException(e);}
    }

    private void setUpListUova(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(uovaFile));

            String data;
            while ((data=reader.readLine())!=null){
                String datas[] = data.split(",");

                uovaList.add(new Uovo(datas[0],Integer.parseInt(datas[1])));

            }

            reader.close();

        } catch (IOException e) {throw new RuntimeException(e);}
    }

    private void setUpVendite(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(venditeUovaFile));

            String data;
            while ((data=reader.readLine())!=null){
                String[] datas = data.split(",");

                venditeList.add( new VenditeUova(datas[0],Integer.parseInt(datas[1]),LocalDate.parse(datas[2])));

            }

            reader.close();

        } catch (IOException e) {throw new RuntimeException(e);}
    }

    //Elements

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

    //Settings

    public double getPriceForMakingEgg(){
        String datas[] = getElements(settingsFile);

        return Double.parseDouble(datas[0]);
    }

    public void setPriceForMakingEgg(double newPrice){
        String datas[] = getElements(settingsFile);

        try {
            FileWriter writer = new FileWriter(settingsFile);
            writer.write(String.valueOf(newPrice)+","+datas[1]+","+datas[2]);

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}

    }

    public double getPriceForSellEgg(){
        String datas[] = getElements(settingsFile);

        return Double.parseDouble(datas[1]);
    }

    public void setPriceForSellEgg(double newPrice){
        String datas[] = getElements(settingsFile);

        try {
            FileWriter writer = new FileWriter(settingsFile);
            writer.write(datas[0]+","+String.valueOf(newPrice)+","+datas[2]);

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}

    }

    public double getPriceTax(){
        String datas[] = getElements(settingsFile);

        return Double.parseDouble(datas[2]);
    }

    public void setPriceTax(double newPrice){
        String datas[] = getElements(settingsFile);

        try {
            FileWriter writer = new FileWriter(settingsFile);
            writer.write(datas[0]+","+datas[1]+","+String.valueOf(newPrice));

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}

    }

    //Funzioni per le galline
    public void addGalline(String razza, int amount){

        for (Gallina g : gallineList){
            if(g.getName().equalsIgnoreCase(razza)){
                g.setAmount(g.getAmount()+amount);
                return;
            }

        }

        gallineList.add(new Gallina(razza,amount));

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileGalline));

            for (Gallina g : gallineList) writer.write(g.getName()+","+g.getAmount()+"\n");

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}


    }

    public boolean removeGalline(String razza, int amount){
        boolean flag = false;

        for (Gallina g: gallineList){
            if(g.getName().equalsIgnoreCase(razza)){
                if(g.getAmount()<amount) return false;
                else{
                    g.setAmount(g.getAmount()-amount);
                    flag = true;
                }

            }

        }

        if(flag){

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileGalline));

                for (Gallina g : gallineList) writer.write(g.getName()+","+g.getAmount()+"\n");

                writer.close();

            } catch (IOException e) {throw new RuntimeException(e);}

        }

        return flag;

    }

    //Galline per razza in ordine crescente
    public ArrayList<Gallina> gallinePerRazza(){
        ArrayList<Gallina> finalList = new ArrayList<>(gallineList);

        for (int i = 0; i < finalList.size(); i++) {
            for (int j = 0; j < finalList.size() - 1 - i; j++) {
                if (finalList.get(j + 1).getAmount() > finalList.get(j).getAmount()) {
                    Gallina temp = finalList.get(j + 1);
                    finalList.set(j + 1, finalList.get(j));
                    finalList.set(j, temp);
                }
            }
        }

        for (int i=0; i<finalList.size();i++){
            if(finalList.get(i).getAmount()==0) finalList.remove(i);
        }

        return finalList;

    }

    //Galline totali

    public int gallineTotali(){
        int total =0;
        for (int i=0; i<gallineList.size();i++){
            total+=gallineList.get(i).getAmount();
        }
        return total;
    }

    //Monitoraggio uova

    public void addLottoUova(String razza, int amount){
        boolean flag=false;

        for (int i=0; i<uovaList.size();i++){
            if(uovaList.get(i).getRazzaGallina().equalsIgnoreCase(razza)){
                uovaList.get(i).setAmount(uovaList.get(i).getAmount()+amount);
                flag=true;
                break;
            }

        }

        if(!flag){
            uovaList.add(new Uovo(razza,amount));
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(uovaFile));

            for (Uovo u : uovaList) writer.write(u.getRazzaGallina()+","+u.getAmount()+"\n");

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}


    }

    public void addVendita(String razzaGallina, int amount, LocalDate sellDate){
        boolean flag=false;

        for (VenditeUova v: venditeList){
            if(v.getRazzaGallina().equalsIgnoreCase(razzaGallina) && v.getSellDate().equals(sellDate)){
                v.setAmount(v.getAmount()+amount);
                flag=true;
                break;
            }
        }

        if(!flag)venditeList.add(new VenditeUova(razzaGallina,amount,sellDate));

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(venditeUovaFile));

            for (VenditeUova u : venditeList) writer.write(u.getRazzaGallina()+","+u.getAmount()+","+u.getSellDate()+"\n");

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}

        removeUovo(razzaGallina, amount);

    }

    private void removeUovo(String razza, int amount){

        for (Uovo u: uovaList){
            if(u.getRazzaGallina().equalsIgnoreCase(razza)){
                    u.setAmount(u.getAmount()-amount);
            }

        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(uovaFile));

            for (Uovo u : uovaList) writer.write(u.getRazzaGallina()+","+u.getAmount()+"\n");

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}


    }

    public ArrayList<VenditeUova> venditePerGallina(){
        ArrayList <VenditeUova> finalList = venditeList;

        for (int i = 0; i < finalList.size(); i++) {
            for (int j = 0; j < finalList.size() - 1 - i; j++) {
                if (finalList.get(j + 1).getAmount() > finalList.get(j).getAmount()) {
                    VenditeUova temp = finalList.get(j + 1);
                    finalList.set(j + 1, finalList.get(j));
                    finalList.set(j, temp);
                }
            }
        }


        return finalList;
    }

    public int getUovaVendute(){
        int total=0;
        for(VenditeUova v: venditeList){
            total+=v.getAmount();
        }

        return total;
    }

    public double getGuadagnoLordo(){
        double somma=0;
        for (VenditeUova v: venditeList){
            somma+=v.getAmount()*getPriceForSellEgg();
        }

        return somma;

    }

    public double getGuadagnoNetto() {
        double total = 0;
        double lordoNoTax = 0;
        int quantita=0;

        for (VenditeUova v : venditeList) {
            lordoNoTax += (v.getAmount() * getPriceForSellEgg());
            quantita+=v.getAmount();
        }

        double tassazione = getPriceTax() / 100;
        lordoNoTax-=getPriceForMakingEgg()*quantita;

        total = (lordoNoTax - (lordoNoTax * tassazione));

        return total;
    }

    public void generaPdf(ArrayList<Uovo> listaUova) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("FarmPro.pdf"));
            document.open();

            // Aggiunta del titolo "FarmPro" in alto della pagina
            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.RED);
            Phrase titlePhrase = new Phrase("FarmPro", titleFont);
            document.add(titlePhrase);

            // Creazione della tabella
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);

            // Aggiunta delle celle con nome e quantità
            com.itextpdf.text.Font cellFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, Font.BOLD);

            PdfPCell cellNome = new PdfPCell(new Phrase("Nome"));
            PdfPCell cellQuantita = new PdfPCell(new Phrase("Quantità"));

            cellNome.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellQuantita.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(cellNome);
            table.addCell(cellQuantita);

            // Popolamento della tabella con i dati delle uova
            for (Uovo uovo : listaUova) {
                PdfPCell cellNomeDati = new PdfPCell(new Phrase(uovo.getRazzaGallina()));
                PdfPCell cellQuantitaDati = new PdfPCell(new Phrase(String.valueOf(uovo.getAmount())));
                cellNomeDati.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellQuantitaDati.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cellNomeDati);
                table.addCell(cellQuantitaDati);
            }

            // Aggiunta della tabella al documento
            document.add(table);

            document.close();

            // Apertura del file PDF generato
            File file = new File("FarmPro.pdf");
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



    public double getSpese(){
        double total=0;

        for (VenditeUova v: venditeList){
            total+=getPriceForMakingEgg()*v.getAmount();
        }

        return total;
    }

    // Filtri

    public int uovaVenduteFiltro(LocalDate inizio, LocalDate fine){
        int total=0;

        for (VenditeUova v : venditeList){
            if(v.getSellDate().isAfter(inizio) &&v.getSellDate().isBefore(fine)){
                total+=v.getAmount();
            }
        }
        return total;
    }

    public ArrayList<VenditeUova> venditePerGallinaFiltro(LocalDate inizio, LocalDate fine){
        ArrayList <VenditeUova> finalList = new ArrayList<>();

        for (VenditeUova v : venditeList){
            if(v.getSellDate().isAfter(inizio) &&v.getSellDate().isBefore(fine)){
                finalList.add(v);
            }
        }


        for (int i = 0; i < finalList.size(); i++) {
            for (int j = 0; j < finalList.size() - 1 - i; j++) {
                if (finalList.get(j + 1).getAmount() > finalList.get(j).getAmount()) {
                    VenditeUova temp = finalList.get(j + 1);
                    finalList.set(j + 1, finalList.get(j));
                    finalList.set(j, temp);
                }
            }
        }


        return finalList;
    }

    public double getGuadagnoLordoFiltro(LocalDate inizio, LocalDate fine){
        double somma=0;
        for (VenditeUova v: venditeList){
            if(v.getSellDate().isAfter(inizio) && v.getSellDate().isBefore(fine)) somma+=v.getAmount()*getPriceForSellEgg();
        }

        return somma;

    }

    public double getGuadagnoNettoFiltro(LocalDate inizio, LocalDate fine){
        double total = 0;
        double lordoNoTax = 0;
        int quantita=0;

        for (VenditeUova v : venditeList) {
            if(v.getSellDate().isAfter(inizio) && v.getSellDate().isBefore(fine)){
                lordoNoTax += (v.getAmount() * getPriceForSellEgg());
                quantita+=v.getAmount();
            }

        }

        double tassazione = getPriceTax() / 100;
        lordoNoTax-=getPriceForMakingEgg()*quantita;

        total = (lordoNoTax - (lordoNoTax * tassazione));

        return total;
    }


    public double getSpeseFiltro(LocalDate inizio, LocalDate fine){
        double total=0;

        for (VenditeUova v: venditeList){
            if(v.getSellDate().isAfter(inizio) && v.getSellDate().isBefore(fine)) total+=getPriceForMakingEgg()*v.getAmount();
        }

        return total;
    }





}
