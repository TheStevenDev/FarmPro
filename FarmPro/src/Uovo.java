import java.time.LocalDate;

public class Uovo {
    private String razzaGallina;
    private int amount;

    public Uovo(String razzaGallina, int amount){
        this.razzaGallina = razzaGallina;
        this.amount=amount;
    }

    public void setRazzaGallina (String newRazza){
        this.razzaGallina = newRazza;
    }


    public String getRazzaGallina(){
        return this.razzaGallina;
    }


    @Override
    public String toString(){
        return this.razzaGallina +" - "+this.amount;
    }

    public void setAmount(int newAmount){
        this.amount = newAmount;
    }

    public int getAmount(){
        return this.amount;
    }



}
