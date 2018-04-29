//Car class for toll road simulation
public class Car extends Vehicle {
    //Variables:
    private final int numberOfSeats;

    //Constructors:
    public Car(String makeIn, String numberPlateIn, int numberOfSeatsIn) {
        super(makeIn, numberPlateIn, CarType.CAR);
        numberOfSeats = numberOfSeatsIn;
    }
    
    //Methods:
    //Returns - integer of the number of seats in a car
    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }
    
    //Returns - the trip cost for a car
    @Override
    public int calculateBasicTripCost() {
        int output = 600;
        if(this.getNumberOfSeats() < 6){
            output -= 100;
        }
        return output;
    }
    
    //Tests:
    public static void main(String[] args){
        String numberplateIn = "JF16FKU";
        String makeIn = "Vauxhall";
        int seatsHigh = 8;
        int seatsLow = 5;
        Car testCar1 = new Car(makeIn, numberplateIn, seatsHigh);
        Car testCar2 = new Car(makeIn, numberplateIn, seatsLow);
        
        System.out.format("Car 1 (cost: 600): %s, %s, %d\n", testCar1.getMake(), testCar1.getNumberPlate(), testCar1.calculateBasicTripCost());
        System.out.format("Car 2 (cost: 500): %s, %s, %d\n", testCar2.getMake(), testCar2.getNumberPlate(), testCar2.calculateBasicTripCost());
    }
}
