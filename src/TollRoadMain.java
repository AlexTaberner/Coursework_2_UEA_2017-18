import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

//Main class for toll road simulation
public class TollRoadMain {
    
    //initializes toll road with a 
    public static TollRoad initialiseTollRoadFromFile() throws FileNotFoundException, IOException {
        TollRoad output = new TollRoad();
        //Use java file picker to find file
        File fileLoc = filePicker();
        String fileText = new String(Files.readAllBytes(fileLoc.toPath()));
        String[] fileTextSplit = fileText.split("\\#");
        for(String line : fileTextSplit){
            output.addCustomer(addLineToTollRoad(line));
        }
        return output;
    }
    
    //simulates from a transactions text file cars going through the toll road
    public static void simulateFromFile(TollRoad road) throws IOException{
        //initialize log file
        OutputToFile output = new OutputToFile("output.txt");
        //Use java file picker to find file
        File fileLoc = filePicker();
        String fileText = new String(Files.readAllBytes(fileLoc.toPath()));
        String[] fileTextSplit = fileText.split("\\$");
        for(String line : fileTextSplit){
            String[] lineSplit = line.split(",");
            
            switch(lineSplit[0].toLowerCase()){
                case "maketrip":
                    try {
                        int charge = road.chargeCustomer(lineSplit[1]);
                        output.addLogLine(String.format("%s: charged %d." ,lineSplit[1], charge));
                    } 
                    catch (AccountOutOfCreditException ex) {
                        output.addLogLine(String.format("%s: makeTrip failed. Insufficient funds.", lineSplit[1]));
                    } 
                    catch (CustomerNotFoundException ex) {
                        output.addLogLine(String.format("%s: makeTrip failed. CustomerAccount does not exsist.", lineSplit[1]));
                    }
                    break;
                case "addfunds":
                    try {
                        CustomerAccount account = road.findCustomer(lineSplit[1]);
                        int amount = Integer.parseInt(lineSplit[2]);
                        account.addFunds(amount);
                        output.addLogLine(String.format("%s: %d added succesfully.", lineSplit[1], amount));
                    }
                    catch (CustomerNotFoundException ex) {
                        output.addLogLine(String.format("%s: addFunds failed. CustomerAccount does not exsist.", lineSplit[1]));
                    }
                    break;
            }
        }
        output.addLogLine(road.toString());
        output.closeFile();
    }
    
    //file picker method
    private static File filePicker() {
        File filePath = null;
        JFileChooser fileBrowser = new JFileChooser();
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileBrowser.setFileFilter(txtFilter);
        fileBrowser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileBrowser.setDialogTitle("Please select a text file to import.");
        int result = fileBrowser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            filePath = fileBrowser.getSelectedFile();
        }
        return filePath;
    }
    
    //Adding a customer to the toll road from the txt file input
    private static CustomerAccount addLineToTollRoad(String input){
        String[] splitLine = input.split(",");
        CustomerAccount customer = new CustomerAccount(splitLine[2], splitLine[3]);
        int specialAttrib = Integer.parseInt(splitLine[5]);
        
        switch(splitLine[0].toLowerCase()){
            case "car":
                customer.addCar(splitLine[4], splitLine[1], specialAttrib);
                break;
            case "van":
                customer.addVan(splitLine[4], splitLine[1], specialAttrib);
                break;
            case "truck":
                customer.addTruck(splitLine[4], splitLine[1], specialAttrib);
                break;
        }
        
        switch(splitLine[7].toLowerCase()){
            case "none":
                customer.deactivateDiscount();
                break;
            case "staff":
                customer.activateStaffDiscount();
                break;
            case "friends_and_family":
                customer.activateFriendsAndFamilyDiscount();
                break;
        }
        int initialFunds = Integer.parseInt(splitLine[6]);
        customer.addFunds(initialFunds);
        
        return customer;
    }

    //PROGRAM MAIN START
    public static void main(String[] args){
        TollRoad tollRoad = null;
        try {
            tollRoad = initialiseTollRoadFromFile();
            tollRoad.sortCustomers();
            simulateFromFile(tollRoad);
        } catch (IOException ex) {
            Logger.getLogger(TollRoadMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
