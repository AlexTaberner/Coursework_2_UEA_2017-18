import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//Customer account class
public final class CustomerAccount implements Comparable<CustomerAccount> {

    //Variables:
    private final String firstName;
    private final String lastName;
    private final List<Vehicle> customerVehicle;
    private int accountBalance;
    private DiscountType discountType;

    
    //Constructors:
    public CustomerAccount(String firstNameIn, String lastNameIn) {
        firstName = firstNameIn;
        lastName = lastNameIn;
        accountBalance = 0;
        deactivateDiscount();
        customerVehicle = new ArrayList();
    }

    
    //Methods:
    //NAME GETTERS
     public String getFullName() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }
    private String getFirstName() {
        return this.firstName;
    }
    private String getLastName() {
        return this.lastName;
    }
    
    //CAR SEARCH GETTER
    private Vehicle getVehicle(String numPlateIn) throws VehicleNotFoundException {
        for(Vehicle current : this.customerVehicle){
            if(current.getNumberPlate().equalsIgnoreCase(numPlateIn)){
                return  current;
            }
        }
        throw new VehicleNotFoundException();
    }

    //FUNDS GETTER
    public int getAccountBalance() {
        return this.accountBalance;
    }
    
    //DISCOUNT TYPE GETTER
    public DiscountType getDiscountType() {
        return this.discountType;
    }
    
    //STAFF DISCOUNT SETTERS
    public void activateStaffDiscount() {
        this.discountType = DiscountType.STAFF;
    }

    public void activateFriendsAndFamilyDiscount() {
        this.discountType = DiscountType.FRIENDS_AND_FAMILY;
    }

    public void deactivateDiscount() {
        this.discountType = DiscountType.NONE;
    }
    
    //FUNDS SETTERS
    private void addFundsPrivate(int amount) throws InvalidInputException {
        if (amount>=0) {
            accountBalance += amount;
        }
        else {
            throw new InvalidInputException();
        }
    }
    
    //VEHICLE SETTERS
    public void addCar(String make, String numberPlate, int numberOfSeats) {
        customerVehicle.add(new Car(make, numberPlate, numberOfSeats));
    }

    public void addVan(String make, String numberPlate, int payload) {
        customerVehicle.add(new Van(make, numberPlate, payload));
    }

    public void addTruck(String make, String numberPlate, int trailers) {
        customerVehicle.add(new Truck(make, numberPlate, trailers));
    }
    
    //calculates and returns the discount that the account has activated
    private double discountPercent(){
        double output = 0f;
        switch(discountType) {
            case STAFF:
                output = 50f;
                break;
            case FRIENDS_AND_FAMILY:
                output = 10f;
                break;
            default:
                break;
        }
        return output;
    }
    
    //Adds amount to customer account and rejects if there is an error
     public void addFunds(int amount) {
        try {
            addFundsPrivate(amount);
        } catch (InvalidInputException ex) {
            System.out.println("Error adding funds.");
        }
    }
    
    //Finds car customer has used with their numberplate as an input 
    //and charges their account for one trip through the toll road
    public int makeTrip(String numPlateIn) throws AccountOutOfCreditException, CustomerNotFoundException{
        int tripCost = 0;     
        try {
            Vehicle currentTrip = getVehicle(numPlateIn);
            tripCost = currentTrip.calculateBasicTripCost();
            int discount = (int)(tripCost * (this.discountPercent()/100f));
            tripCost -= discount;
            this.spendFunds(tripCost);
        } catch (VehicleNotFoundException ex) {
            throw new CustomerNotFoundException();
        }
        catch (InsufficientAccountBalanceException ex) {
            throw new AccountOutOfCreditException();
        }
        return tripCost;
    }
    
    //Deducts the intended amount from the customer account instance
    public void spendFunds(int input) throws InsufficientAccountBalanceException {
        int current = this.getAccountBalance();
        if((current - input) < 0) {
            throw new InsufficientAccountBalanceException();
        }
        this.accountBalance -= input;
    }

    //Finds if this instance of a customer account contains a car with the input numberplate
    public CustomerAccount compareNumberPlate(String numPlateIn) throws VehicleNotFoundException {
        for(Vehicle current : this.customerVehicle){
            if(current.getNumberPlate().equalsIgnoreCase(numPlateIn)){
                return this;
            }
        }
        throw new VehicleNotFoundException();
    }
    
    //COMPARABLE INTERFACE SORT
    @Override
    public int compareTo(CustomerAccount input) {
        customerVehicle.sort(null);
        input.customerVehicle.sort(null);
        return customerVehicle.get(0).compareTo(input.customerVehicle.get(0));
    }
    
    //ToString override
    @Override
    public String toString() {
        return String.format("The customer %s %s, has %dp in their account.", firstName, lastName, accountBalance);
    }
    
    //TESTS
    public static void main(String[] args) {
        CustomerAccount testCustomer = new CustomerAccount("Brian", "Limmond");
        testCustomer.addCar("Kia", "HV59WXW", 5);
        testCustomer.addFunds(20000);
        System.out.println(testCustomer.getFullName());
        System.out.println(testCustomer.getAccountBalance());
        
        try {
            testCustomer.makeTrip("HV59WXW");
        } catch (AccountOutOfCreditException ex) {
            Logger.getLogger(CustomerAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CustomerNotFoundException ex) {
            Logger.getLogger(CustomerAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(testCustomer.getAccountBalance());
        
        try {
            System.out.println(testCustomer.getVehicle("HV59WXW").toString());
        } catch (VehicleNotFoundException ex) {
            Logger.getLogger(CustomerAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
