import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

//output handler, writes to console and a file
public class OutputToFile {
    
    //variables
    private final PrintWriter writer;
    
    //constructor
    public OutputToFile(String filename) throws FileNotFoundException, UnsupportedEncodingException{
        writer = new PrintWriter(filename, "UTF-8");
    }
    
    //methods
    //writes string to console and file
    public void addLogLine(String input){
        System.out.println(input);
        writer.println(input);
    }
    
    //closes the file writer
    public void closeFile(){
        writer.close();
    }
}
