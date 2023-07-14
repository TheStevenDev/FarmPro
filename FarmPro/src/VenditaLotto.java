import java.time.LocalDate;

public class VenditaLotto {
    private String nomeLotto;
    private String nomePianta;
    private int grandezzaLotto=0;
    private double prezzoPerPianta;
    private int quatitaPiante;
    private LocalDate dataVendita;

    public VenditaLotto(String nomeLotto,String nomePianta, int grandezzaLotto, double prezzoPerPianta, int quatitaPiante, LocalDate dataVendita) {
        this.nomeLotto=nomeLotto;
        this.nomePianta = nomePianta;
        this.grandezzaLotto = grandezzaLotto;
        this.prezzoPerPianta = prezzoPerPianta;
        this.quatitaPiante = quatitaPiante;
        this.dataVendita = dataVendita;
    }

    public void setPrezzoPerPianta(double prezzoPerPianta) {
        this.prezzoPerPianta = prezzoPerPianta;
    }

    public double getPrezzoPerPianta() {
        return prezzoPerPianta;
    }

    public int getGrandezzaLotto() {
        return grandezzaLotto;
    }

    public int getQuatitaPiante() {
        return quatitaPiante;
    }

    public LocalDate getDataVendita() {
        return dataVendita;
    }

    public String getNomePianta() {
        return nomePianta;
    }

    public void setDataVendita(LocalDate dataVendita) {
        this.dataVendita = dataVendita;
    }

    public void setGrandezzaLotto(int grandezzaLotto) {
        this.grandezzaLotto = grandezzaLotto;
    }

    public void setNomePianta(String nomePianta) {
        this.nomePianta = nomePianta;
    }

    public void setQuatitaPiante(int quatitaPiante) {
        this.quatitaPiante = quatitaPiante;
    }

    public String getNomeLotto() {
        return nomeLotto;
    }

    public void setNomeLotto(String nomeLotto) {
        this.nomeLotto = nomeLotto;
    }
}
