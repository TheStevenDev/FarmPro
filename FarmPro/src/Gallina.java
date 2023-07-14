public class Gallina {
    private String name;
    private int amount;

    public Gallina(String name, int amount){
        this.name = name;
        this.amount=amount;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public int getAmount(){
        return this.amount;
    }

    public void setAmount(int newAmount){
        this.amount = newAmount;
    }

}
