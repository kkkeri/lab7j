package collection;

import java.io.Serializable;

/**
 * Модель объекта "Дом"
 * Содержит геттеры/сеттеры каждого поля класса
 * Некоторые поля имеют ограничения. Они подписаны
 *
 * @author keri
 * @since 1.0
 */

public class House implements Serializable {
    /**
     * Название дома
     * Поле не может быть null
     *
     * @since 1.0
     */
    private String name; //Поле может быть null
    /**
     * Год пострйоки
     * Значение поля должно быть больше 0
     *
     * @since 1.0
     */
    private int year; //Значение поля должно быть больше 0
    /**
     * Колл-во квартир на этаже
     * Значение поля должно быть больше 0
     *
     * @since 1.0
     */
    private Long numberOfFlatsOnFloor; //Значение поля должно быть больше 0
    /**
     * Конструктор с задаными полями
     *
     * @since 1.0
     */
    public House(String name, int year, Long numberOfFlatsOnFloor){
        this.name= name;
        this.year = year;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getYear(){
        return year;
    }
    public void setYear(int year){
        this.year = year;
    }
    public Long getNumberOfFlatsOnFloor(){
        return numberOfFlatsOnFloor;
    }
    public void setNumberOfFlatsOnFloor(Long numberOfFlatsOnFloor){
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }
}
