import java.time.LocalDate;

public class LottoTerreno {
    private String piantaColtivata;
    private String nomeLotto;
    private double prezzoPerPianta;
    private int grandezza;
    private LocalDate dataDiRaccolta;
    private int quantitaPiante;

    public LottoTerreno(String piantaColtivata, String nomeLotto,double prezzoPerPianta, int grandezza, LocalDate dataDiRaccolta, int quantitaPiante){
        this.piantaColtivata=piantaColtivata;
        this.nomeLotto=nomeLotto;
        this.grandezza=grandezza;
        this.prezzoPerPianta = prezzoPerPianta;
        this.dataDiRaccolta=dataDiRaccolta;
        this.quantitaPiante = quantitaPiante;
    }

    public String getPiantaColtivata() {
        return this.piantaColtivata;
    }

    public String getNomeLotto(){
        return this.nomeLotto;
    }

    public int getGrandezza() {
        return grandezza;
    }

    public LocalDate getDataDiRaccolta() {
        return dataDiRaccolta;
    }

    public int getQuantitaPiante() {
        return quantitaPiante;
    }

    public void setPiantaColtivata(String newPianta){
        this.piantaColtivata=newPianta;
    }

    public void setNomeLotto(String nomeLotto) {
        this.nomeLotto = nomeLotto;
    }

    public void setGrandezza(int grandezza) {
        this.grandezza = grandezza;
    }

    public void setDataDiRaccolta(LocalDate dataDiRaccolta) {
        this.dataDiRaccolta = dataDiRaccolta;
    }

    public void setQuantitaPiante(int quantitaPiante) {
        this.quantitaPiante = quantitaPiante;
    }
    public double getPrezzoPerPianta() {
        return prezzoPerPianta;
    }

    public void setPrezzoPerPianta(double prezzoPerPianta) {
        this.prezzoPerPianta = prezzoPerPianta;
    }

}

