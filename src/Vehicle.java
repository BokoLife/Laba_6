import java.io.Serializable;
import java.util.Date;

public class Vehicle implements Comparable<Vehicle>, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer enginePower; //Поле может быть null, Значение поля должно быть больше 0
    private VehicleType type; //Поле может быть null
    private FuelType fuelType; //Поле может быть null

    private static final long serialVersionUID = -6426555102326948503L;

    public Vehicle(){
        this.id = (long) ((Math.random() + Math.random()) * 223);
        this.creationDate = new Date();
    }

    public Vehicle(String name, Coordinates coordinates, Integer power, VehicleType type, FuelType fuelType){
        this.id = (long) ((Math.random() + Math.random()) * 223);
        this.setName(name);
        this.setCoordinates(coordinates);
        this.creationDate = new Date();
        this.setEnginePower(power);
        this.setType(type);
        this.setFuelType(fuelType);
    }

    @Override
    public int compareTo(Vehicle another){
        return this.id.intValue() - another.id.intValue();
        //return this.enginePower - another.enginePower;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name){
        if ((name == null) || name.isEmpty()){
            System.out.println("Неверно задано имя объекта" + " : " + name);
            System.out.println("Задано имя по умолчанию: Транспорт");
            this.name = "Транспорт";
        }
        else this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCoordinates(Coordinates coordinates){
        if (coordinates == null) {
            System.out.println("Координаты не заданы");
            System.out.println("Заданы координаты по умолчанию: 100, 100");
            this.coordinates = new Coordinates(100, 100);
        }
        else this.coordinates = coordinates;
    }
    public Coordinates getCoordinates(){
        return this.coordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreationDate() {
        return creationDate.getTime();
    }
    public Date getDate(){
        return this.creationDate;
    }

    public void setEnginePower(Integer power){
        if (power == null || power <= 0){
            System.out.println("Мощность двигателя не задана");
            System.out.println("Задана мощность по умолчанию: 10");
            this.enginePower = 10;
        }
        else this.enginePower = power;
    }
    public Integer getEnginePower() {
        return enginePower;
    }

    public void setType(VehicleType type) {
        if (type == null){
            System.out.println("Задан тип транспорта по умолчанию: машина");
            this.type = VehicleType.CAR;
        }
        else this.type = type;
    }

    public VehicleType getType() {
        return type;
    }

    public void setFuelType(FuelType fuelType) {
        if (type == null){
            System.out.println("Задан тип топлива по умолчанию: лошадиные силы");
            this.fuelType = FuelType.MANPOWER;
        }
        else this.fuelType = fuelType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    @Override
    public String toString(){
        return "ID: " + this.id.toString() + ";  имя: " + this.name +
                ";  координаты: " + coordinates.toString() + ";  мощность: " + this.enginePower + "  "
                + this.getType() + " " + this.getFuelType() + " | ";
    }
}
