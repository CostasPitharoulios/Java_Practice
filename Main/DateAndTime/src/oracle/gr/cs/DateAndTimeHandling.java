package oracle.gr.cs;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateAndTimeHandling {
    public DateAndTimeHandling() {
        super();
    }
    
    
    private static String timeStampToLocalDate(String timeStamp){
        
        if (timeStamp == null || timeStamp.isEmpty() || timeStamp.length()<10 ){
            System.out.println("ERROR: The given string is either null or empty or has less than 10 digits.\n");
            return null;
        }
        String localDate = null;
        
        // Unix timestamp is a 10-digit number
        // Going to cut all extra digits after the 10th one
        timeStamp = timeStamp.substring(0,10);
        
        // This is the format in which we want to print the date
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        
        final long unixTimeStamp = Long.valueOf(timeStamp).longValue(); // converting timestap string to long
        
        localDate = Instant.ofEpochSecond(unixTimeStamp).atZone(ZoneId.of("GMT+3")).format(formatter);
        
        return localDate;
    }
    
    
    public static void main(String args[]) {
        
        String localDate = timeStampToLocalDate("1592813538000");
        
        if (localDate != null)
            System.out.println(localDate);
    }
    
    
    
}
