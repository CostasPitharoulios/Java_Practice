package oracle.gr.cs;

public class StringEquality {
    public StringEquality() {
        super();
    }
    
    public void stringChecks(){
        

        // We get "false" and this is absolutely justifiable because == is not 
        // the proper way to compare to strings. We should use equals() insted.
        // What == basically does is check whether the references to the objects are equal,
        // AND OF COURSE THEY ARE NOT!!! 
        // Observation: String x is made in compile time. This means that String x goes to the String Pool
        // String y , because it is modified, is made during runtime. So that is the reason that are two different objects
        
        String x = "Hello World";
        String y = "     Hello World".trim();
        System.out.println("1: " + (x==y));
        
        // 2. Correct
        // According to documentation...f the trim() method would do nothing 
        // (because the string is already trimmed), the same string object -> CORRECT !
        // (ie this) is returned.
        // Therefore, the same object reference is compared and thus we get "true" (INTERESTING!!!)
        // This is the same object, because it is created in compile time, and it is the same in the String pool.
        String a = "Hello World";
        String b = "Hello World".trim();
        System.out.println("2: " + (a==b));
        //System.out.println(a.equals(b));
        
        // 3. Correct
        // Here, we create a new object for string. Thus we have a new reference which is different
        // from the next string. And since we compare object references with the == operator...we get false!!!
       // CORRECT !!
        String c = new String("Hello World");
        String d = "Hello World";
        System.out.println("3: " + (c==d));
        
        String s1 = "java";
        StringBuilder s2 = new StringBuilder("java");
        //4_1: This part is faulty. We have a compilation error.
        // We are trying to compare the references of two different objects //CORRECT 
        //if (s1==s2 ) 
          //  System.out.println("4: 1");
        //4_2: Correct
        // We get false because StringBuilder does not override equals() method
        // of String class.
        if (s1.equals(s2)) // It would work if we had s1.equals(s2.toString)) //CORRECT
            System.out.println("4: 2");
        
    }
    
    public static void lexicographicalComparison(String str1, String str2){
        
        if ((str1==null || str1.isEmpty()) || (str2==null || str2.isEmpty())){
            System.out.println("One or both strings are either null or empty.");
            return;
        }
        
        if ((str1.compareTo(str2) == 0)){ // strings are equal
            System.out.println("The strings: " + str1 + " and " + str2 + " are equal.\n");
        }
        else{
            System.out.println("The strings: " + str1 + " and " + str2 + " are NOT equal.\n");
        }
        
        return;
    }
    
    
}
