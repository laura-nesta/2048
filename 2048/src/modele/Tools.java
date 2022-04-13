package modele;

public class Tools {

    private Case[][] tabCases;
    public int getX(int ind){
        return (int)ind/tabCases.length;
    }

    public int getY(int ind){
        return (int)ind%tabCases.length;
    }
}
