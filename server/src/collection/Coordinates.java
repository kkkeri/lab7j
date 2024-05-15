package collection;
import java.io.Serializable;
import java.util.Objects;
/**
 * Модель объекта "Координаты"
 * Содержит геттеры/сеттеры каждого поля класса
 * Некоторые поля имеют ограничения
 *
 * @author keri
 * @since 1.0
 */

public class Coordinates implements Serializable {
    /**
     * Координата X
     * Значение поля должно быть больше -952
     *
     * @since 1.0
     */
    private long x;
    /**
     * Координата Y
     * Значение поля должно быть больше -952
     *
     * @since 1.0
     */
    private Double y;
    /**
     * Базовый конструктор
     *
     * @since 1.0
     */

    public  Coordinates(long x, Double y){
        this.x = x;
        this.y = y;
    }
    public long getX(){
        return x;
    }
    public Double getY(){
        return y;
    }
    public void setY(Double y){
        this.y = y;
    }
    public void setX(long x){
        this.x = x;
    }
}
