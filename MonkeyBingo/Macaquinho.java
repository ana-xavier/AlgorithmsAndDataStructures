public class Macaquinho {
    private int id;
    public int pares;
    public int impares;
    public int macaquinhosPares;
    public int macaquinhosImpares;

    public Macaquinho(int id, int pares, int impares, int qtdPares, int qtdImpares){
        this.id = id;
        this.pares = pares;
        this.impares = impares;
        this.macaquinhosPares = qtdPares;
        this.macaquinhosImpares = qtdImpares;
    }
    public int getId(){
        return id;
    }
    public int getPares(){
        return macaquinhosPares;
    }

    public int getImpares(){
        return macaquinhosImpares;
    }
    public int getCocosTotal(){
        return macaquinhosPares+macaquinhosImpares;
    }

    public void setQtdPares(int p){
        macaquinhosPares += p;
    }

    public void setQtdImpares(int i){
        macaquinhosImpares += i;
    }

    public void clear(){
        macaquinhosPares = 0;
        macaquinhosImpares = 0;
    }

    public String toString(){
        return "Macaquinho: " + getId() + " Total de cocos: " + getCocosTotal();
    }

}
