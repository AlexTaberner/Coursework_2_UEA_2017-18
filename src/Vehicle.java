//Root class for all types of vehicles
public abstract class Vehicle implements Comparable<Vehicle> {
    //Variables:
    protected final String make;
    protected final String numberPlate;
    protected final CarType type;

    //Constructors:
    public Vehicle(String makeIn, String numberPlateIn, CarType typeIn) {
        make = makeIn;
        numberPlate = numberPlateIn;
        type = typeIn;
    }

    
    //Abstract Methods:
    //Returns - an integer amount that relates to cost of car passing the toll road
    public abstract int calculateBasicTripCost();
    
    //Defined methods:
    //Returns - the make variable's contents
    public String getMake() {
        return this.make;
    }
    
    //Returns - the type variable's contents
    public CarType getType() {
        return this.type;
    }
    
    //Returns - the numberplate's contents
    public String getNumberPlate() {
        return this.numberPlate;
    }
    
    //Returns - either {-1, 0, 1} depending on if the object instance is >, <, or == to another of the same class
    @Override
    public int compareTo(Vehicle input){
        return String.CASE_INSENSITIVE_ORDER.compare(this.getNumberPlate(), input.getNumberPlate());
    }
    
    @Override
    public String toString(){
        return this.getNumberPlate();
    }
}
