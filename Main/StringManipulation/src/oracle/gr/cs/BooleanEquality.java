package oracle.gr.cs;

public class BooleanEquality {
    public BooleanEquality() {
        super();
    }
    
    public void booleanChecks() {
        
        
        //===================================
        // !!!nImportant notice!!!
        //-----------------------------------
        // == compares object REFERENCES
        // whereas
        // equals() compares object CONTENTS
        //===================================
    
        // 1. Correct
        // Here two different references are created and then compared.
        // The references are unique thus NOT SAME therefore we get "false" //CORRECT!
        System.out.println("1: " + (new Boolean("true") == new Boolean("true")));  
        
        // 2. Correct, it works fine. 
        // new Boolean("true") creates a new boolean object with value "true"
        // and this is compared with 
        // Boolean.parseBoolean("true") which creates a primitive boolean value (namely "true")
        System.out.println("2: " + (new Boolean("true") == Boolean.parseBoolean("true")));  
        
        // 3. Absolutely correct.
        // CORRECT ! it compares primitive with primitive
        System.out.println("3: " + (Boolean.parseBoolean("true") == true)) ;
        
        // 4. Correct
        //(Boolean.parseBoolean("TrUe") this returns primitive "true" because it is not case sensitive
        // thew new Boolean(null) returns a reference false
        // So it is like comparing
        // true == new Boolean(“false”);
        System.out.println("4: " + (Boolean.parseBoolean("TrUe") == new Boolean(null)));  
        
        // 5. Although Boolean is not case sensitive and will return true in both cases,
        // we will get "false" for the same reason as in number 1. //CORRECT!
        System.out.println("5: " + (new Boolean("TrUe") == new Boolean(true)));  
        
        // 6. Compilation Error
        // There is no suitable constructor for Boolean which does not get any arguments //CORRECT !
        //System.out.println("6: " + (new Boolean() == false)); 
        
        // 7. Correct 
        // We inevitably get false since we are trying to compare two different objects'
        // references. :)
        System.out.println("7: " + (new Boolean("true") == Boolean.TRUE));  
        
        // 8. Absolutely correct, nothing to point out
        // It is the same as no.3
        System.out.println("8: " + (new Boolean("no") == false)); 
        
        System.out.println(); // print new line
        

    }
}
