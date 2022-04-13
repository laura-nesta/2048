package modele;

public class Tools {

    public static int getX(int ind, int size){
        return (int)ind/size;
    }

    public int getY(int ind, int size){
        return (int)ind%size;
    }
}
