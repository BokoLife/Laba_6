import java.io.Serializable;
import java.sql.SQLOutput;

public class Coordinates implements Serializable {
    private Integer x; //Максимальное значение поля: 956, Поле не может быть null
    private int y; //Максимальное значение поля: 368
    public Coordinates(int x, int y){
        this.setX(x);
        this.setY(y);
    }

    public void setX(Integer x) {
        if ((x == null) || (x >= 956)){
            System.out.println("Значение координаты x не может быть больше 956; " + x + " - задано неверно");
            System.out.println("Координата x задана по умолчанию: 100");
            this.x = 100;
        }
        else this.x = x;

    }

    public void setY(int y) {
        if (y >= 368){
            System.out.println("Значение координаты y не может быть больше 368; " + y + " - задано неверно");
            System.out.println("Координата y задана по умолчанию: 100");
            this.y = 100;
        }
        else this.y = y;
    }

    @Override
    public String toString(){
        return x + "," + y;
    }
}
