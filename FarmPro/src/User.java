import java.io.*;

public class User {
    private File settingFile = new File("src/Files/settings.txt");
    private MagazzinoUova magazzinoUova = new MagazzinoUova(this);
    private VenditaOrtaggi venditaOrtaggi = new VenditaOrtaggi(this);


    public User(){

    }

    public void setSettingFile(File newFile){
        settingFile = newFile;
    }

    public File getSettingFile(){
        return this.settingFile;
    }

    public void setMagazzinoUova(MagazzinoUova newMagazzino){
        this.magazzinoUova = newMagazzino;
    }

    public MagazzinoUova getMagazzinoUova(){
        return this.magazzinoUova;
    }

    public VenditaOrtaggi getVenditaOrtaggi(){
        return this.venditaOrtaggi;
    }

    public void setVenditaOrtaggi(VenditaOrtaggi venditaOrtaggi){
        this.venditaOrtaggi=venditaOrtaggi;
    }

    private String[] getElements(){
        String[] datas;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingFile));

            String data = reader.readLine();

            datas = data.split(",");

            reader.close();

        } catch (IOException e) {throw new RuntimeException(e);}


        return datas;
    }

    public String getFactoryName(){
        String[] datas = getElements();

        return datas[0];
    }

    public String getOwnerName(){
        String[] datas = getElements();

        return datas[1];
    }

    public void setFactoryName(String newName){
        String[] datas = getElements();

        String owner = datas[1];

        try {
            FileWriter writer = new FileWriter(settingFile);

            writer.write(newName+","+owner);

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public void setOwnerName(String newName){
        String[] datas = getElements();

        String factoryName = datas[0];

        try {
            FileWriter writer = new FileWriter(settingFile);

            writer.write(factoryName+","+newName);

            writer.close();

        } catch (IOException e) {throw new RuntimeException(e);}
    }



}
