//Truck class for toll road simulation
public class Truck extends Vehicle {
    //Variables:
    private final int numTrailers;
    
    //Constructors:
    public Truck(String makeIn, String numberPlateIn, int numTrailersIn) {
        super(makeIn, numberPlateIn, CarType.TRUCK);
        numTrailers = numTrailersIn;
    }
    
    //Methods:
    //Returns - the number of trailers stored in the variable
    public int getNumberOfTrailers() {
        return this.numTrailers;
    }
    
    //Returns - the trip cost for a truck
    @Override
    public int calculateBasicTripCost() {
        int output = 1250;
        if(this.getNumberOfTrailers() > 1){
            output += 250;
        }
        return output;
    }
    
        //Tests:
    public static void main(String[] args){
        String numberplateIn = "JF16FKU";
        String makeIn = "Vauxhall";
        int trailersHigh = 2;
        int trailersLow = 1;
        Truck testTruck1 = new Truck(makeIn, numberplateIn, trailersHigh);
        Truck testTruck2 = new Truck(makeIn, numberplateIn, trailersLow);
        
        System.out.format("Truck 1 (cost: 1500): %s, %s, %d\n", testTruck1.getMake(), testTruck1.getNumberPlate(), testTruck1.calculateBasicTripCost());
        System.out.format("Truck 2 (cost: 1250): %s, %s, %d\n", testTruck2.getMake(), testTruck2.getNumberPlate(), testTruck2.calculateBasicTripCost());
    }
}
