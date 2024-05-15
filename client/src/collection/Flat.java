package collection;


import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * Модель объекта "Квартира"
 * Содержит геттеры/сеттеры каждого поля класса
 * Некоторые поля имеют ограничения. Они подписаны
 *
 * @author keri
 * @since 1.0
 */

public class Flat implements Comparable<Flat>, Serializable {
    private static final long serialVersionUID = -654255827288257429L;
    /**
     * Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически при помощи IdGenerator
     *
     * @see
     * @since 1.0
     */
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /**
     * Название транспортного средства
     * Поле не может быть null, Строка не может быть пустой
     *
     * @since 1.0
     */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * Координаты
     * Поле не может быть null
     *
     * @see Coordinates
     * @since 1.0
     */
    private Coordinates coordinates; //Поле не может быть null
    /**
     * Дата создания квартиры
     * Поле не может быть null, Значение этого поля должно генерироваться автоматически
     *
     * @since 1.0
     */
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**
     * Область
     * Значение этого поля должно быть больше 0 и меньше 673
     *
     * @since 1.0
     */
    private Long area; //Максимальное значение поля: 672, Значение поля должно быть больше 0
    /**
     * Колл-во квартир
     * Значение этого поля должно быть больше 0
     *
     * @since 1.0
     */
    private Integer numberOfRooms; //Значение поля должно быть больше 0
    /**
     * Тип фурнитуры
     * Значение этого поле не должно быть null
     *
     * @since 1.0
     */
    private Furnish furnish; //Поле не может быть null
    /**
     *
     * Значение этого поле не должно быть null
     *
     * @since 1.0
     */
    private View view; //Поле не может быть null
    /**
     * Транспорт
     * Значение этого поле не должно быть null
     *
     * @since 1.0
     */
    private Transport transport; //Поле не может быть null
    /**
     * Тип дома
     * Значение этого поле не должно быть null
     *
     * @since 1.0
     */
    private House house; //Поле не может быть null
    public Flat(){

    }
    /**
     * Конструктор с заданными полями
     *
     * @author keri
     * @since 1.0
     */
    public Flat(long id, String name, Coordinates coordinates, java.util.Date creationDate, Long area, Integer numberOfRooms, Furnish furnish, View view, Transport transport, House house){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.furnish = furnish;
        this.view = view;
        this.transport = transport;
        this.house = house;
    }

    public Flat(String[] data) throws Exception {
        this.id = 0L;
        this.name = data[0];
        this.coordinates = new Coordinates(Long.parseLong(data[1]), Double.parseDouble(data[2]));
        this.creationDate = new Date(0);
        this.area = Long.parseLong(data[3]);
        this.numberOfRooms = Integer.parseInt(data[4]);
        this.furnish = Furnish.valueOf(data[5]);
        this.view = View.valueOf(data[6]);
        this.transport = Transport.valueOf(data[7]);
        this.house = new House(data[8], Integer.parseInt(data[9]), Long.parseLong(data[10]));
    }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }
    public java.util.Date getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {this.creationDate = creationDate;}
    public Long getArea(){
        return area;
    }
    public void setArea(Long area){
        this.area = area;
    }
    public Integer getNumberOfRooms(){
        return numberOfRooms;
    }
    public void setNumberOfRooms(Integer numberOfRooms){
        this.numberOfRooms =numberOfRooms;
    }
    public Furnish getFurnish(){
        return furnish;
    }
    public void setFurnish(Furnish furnish){
        this.furnish =furnish;
    }
    public View getView(){
        return view;
    }
    public void setView(View view){
        this.view = view;
    }
    public Transport getTransport(){
        return transport;
    }
    /**
     * Вторая версия сеттера для ID
     * Используется для обновления полей элемента, без изменения ID
     *
     * @author keri
     * @since 1.0
     */
    public void setIdForUpdate(long id) {
        this.id = id;
    }
        public void setTransport(Transport transport){
        this.transport = transport;
    }
    public House getHouse(){
        return house;
    }
    public void setHouse(House house){
        this.house = house;
    }

    @Override
    public String toString() {
        String res = String.format("Id: %d\nName: %s\nCoordinates: {x: %d, y: %f}\nCreation Time: %s\n" +
                "Area: %d\nNumber of Rooms: %d\nFurnish: %s\nView: %s\nTransport: %s\n" +
                "House: {name: %s, year: %d, Number flats on floor: %d}\n", getId(), getName(), getCoordinates().getX(),
                getCoordinates().getY(),getCreationDate(),getArea(),getNumberOfRooms(),getFurnish(),getView(),
                getTransport(),getHouse().getName(),getHouse().getYear(),getHouse().getNumberOfFlatsOnFloor());
        return res;
    }

    @Override
    public int compareTo(Flat o) {
        return (int) (this.id - o.getId());
    }
}
