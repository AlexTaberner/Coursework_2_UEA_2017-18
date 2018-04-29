//Van class for toll road simulation
public class Van extends Vehicle {
    //Variables:
    private final int payload;
    
    //Constructors:
    public Van(String makeIn, String numberPlateIn, int payloadIn) {
        super(makeIn, numberPlateIn, CarType.VAN);
        payload = payloadIn;
    }
    
    //Methods:
    //Returns - the payload variable's contents
    public int getPayload() {
        return this.payload;
    }
    
    //Returns - the trip cost for a van
    @Override
    public int calculateBasicTripCost() {
        int output = 500;
        if(this.getPayload() > 600){
            output += 250;
        }
        if (this.getPayload() > 800){
            output += 250;
        }
        return output;
    }
    
    //Tests:
    public static void main(String[] args){
        String numberplateIn = "JF16FKU";
        String makeIn = "Kia";
        int payloadHigh = 801;
        int payloadMid = 601;
        int payloadLow = 600;
        Van testVan1 = new Van(makeIn, numberplateIn, payloadHigh);
        Van testVan2 = new Van(makeIn, numberplateIn, payloadMid);
        Van testVan3 = new Van(makeIn, numberplateIn, payloadLow);
        
        System.out.format("Van 1 (cost: 1000): %s, %s, %d\n", testVan1.getMake(), testVan1.getNumberPlate(), testVan1.calculateBasicTripCost());
        System.out.format("Van 2 (cost: 750): %s, %s, %d\n", testVan2.getMake(), testVan2.getNumberPlate(), testVan2.calculateBasicTripCost());
        System.out.format("Van 3 (cost: 500): %s, %s, %d\n", testVan3.getMake(), testVan3.getNumberPlate(), testVan3.calculateBasicTripCost());
    }
}
