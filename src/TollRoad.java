import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//toll road class
public class TollRoad {
    //Variables:
    private final List<CustomerAccount> customers;
    private int moneyMade;
    
    //Constructors:
    public TollRoad(){
        customers = new ArrayList();
        moneyMade = 0;
    }
    
    //Methods:
    //Returns amount of money made by the instance
    public int getMoneyMade(){
        return this.moneyMade;
    }
    
    //Adds a customer to the list via constructed customerAccount object
    public void addCustomer(CustomerAccount customerIn) {
        this.customers.add(customerIn);
    }
    
    //Finds a customer in the list that has the numberplate input in their car list
    public CustomerAccount findCustomer(String numPlateIn) throws CustomerNotFoundException{
        CustomerAccount output = null;
        for(CustomerAccount current : customers){
            try {
                output = current.compareNumberPlate(numPlateIn);
                
            }
            catch (VehicleNotFoundException ex) {
            }
        }
        if(output == null){
            throw new CustomerNotFoundException();
        }
        
        return output;
    }

    //Charges a customer for using the toll road
    public int chargeCustomer(String regNumber) throws AccountOutOfCreditException, CustomerNotFoundException {
            CustomerAccount customer = findCustomer(regNumber);
            int totalTripCost = customer.makeTrip(regNumber);
            moneyMade += totalTripCost;
            return totalTripCost;
    }
    
    public void sortCustomers() {
        customers.sort(null);
    }
    
    //toString override
    @Override
    public String toString() {
        return String.format("This toll road has made a revenue of %d.", getMoneyMade());
    }
    
    //Tests
    public static void main(String[] args){
        TollRoad testRoad = new TollRoad();
        CustomerAccount testCustomer = new CustomerAccount("Bravo", "Deltaco");
        testCustomer.addCar("kia", "HV59WXW", 5);
        testCustomer.addFunds(1000);
        testRoad.addCustomer(testCustomer);
        
        System.out.println(testRoad.getMoneyMade());
        try {
            testRoad.chargeCustomer("HV59WXW");
        } catch (CustomerNotFoundException | AccountOutOfCreditException ex) {
            Logger.getLogger(TollRoad.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(testRoad.toString());
    }
}
