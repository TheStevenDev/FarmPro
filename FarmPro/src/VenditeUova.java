import java.time.LocalDate;

public class VenditeUova {
    private String razzaGallina;
    private int amount;
    private LocalDate sellDate;

    public VenditeUova(String razzaGallina, int amount, LocalDate sellDate){
        this.razzaGallina = razzaGallina;
        this.amount = amount;
        this.sellDate = sellDate;
    }
    public void setRazzaGallina(String newName){
        this.razzaGallina = newName;
    }

    public void setSellDate(LocalDate sellDate) {
        this.sellDate = sellDate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRazzaGallina(){
        return this.razzaGallina;
    }

    public LocalDate getSellDate(){
        return this.sellDate;
    }

    public int getAmount() {
        return this.amount;
    }

}
