package oracle.gr.cs;

import java.util.HashMap;
import java.util.HashSet;


public class StringManipulation {
    public StringManipulation() {
        super();
    }


    /**
     * We are going to use sets.
     * The idea is that all items of a set are unique.
     * Therefore, we are going to transform the string to a set.
     * If the length of the set equals the length of the string,
     * then all string's characters are unique.
     * @param str the input string of the method
     * @return true if all characters unique, false if not
     */
    public static boolean uniqueCharacters(String str) {
        boolean ret = false;
        
        if (str ==null || str.isEmpty()){
            return ret;
        }
        
        HashSet<Character> characterSet = new HashSet<>();
        
        for (int i = 0; i < str.length(); i++) { // For each character of the string
            characterSet.add(str.charAt(i)); // add the character to the set
        }

        if (characterSet.size() == str.length()) // The characters are all unique
            ret = true;
        
        return ret;
    }


  
    private static String replaceSpacesWith20s(String str) {
        
        String newString = null;
        
        if (str ==null || str.isEmpty()){
            return newString;
        }
        
        newString = str.replaceAll(" ", "%20");
        return newString;
    }

    /**
     * We are going to create a Hashmap. The key will be each character
     * of the string and the value will be its occurence
     * @param str
     */
    private static void countCharacterOccurence(String str) {
        
        if (str == null || str.isEmpty() ){    
            System.out.println("The given string is null or empty." + "\n");
            return;
        }

        HashMap<Character, Integer> characterMap = new HashMap<Character, Integer>(); // Creating the hash map

        str = str.toLowerCase(); // Making string lower case only

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (characterMap.containsKey(c)) { // If this character already exists in the hash map
                characterMap.put(c, characterMap.get(c) + 1);
            } else { // if this is the first occurence of the character

                characterMap.put(c, 1); // Initializing character's occurence as 1
            }
        }

        System.out.print("Compressed version: ");
        for (HashMap.Entry<Character, Integer> entry : characterMap.entrySet()) {
            System.out.print(entry.getKey() + "" + entry.getValue());
        }
        System.out.println("");


    }
    
    private static boolean assertIsTrue(String str){
        boolean isTrue=false;
        
        if (str == null || str.isEmpty()){
            System.out.println("The given strig is either null or empty.");
        }
        
        if (str.equals("1") || str.equals("yes") || str.equals("true"))
            isTrue = true;
        
        return isTrue;
    }


    public static void main(String args[]) {

        //============================================
        //          *** TASK 1 ***
        //============================================

        /*
            String str = new String();
            // Going to read a string

            Scanner sc= new Scanner(System.in);
            System.out.print("Plase provide a string: ");

            // Reading string
            String strGiven = sc.nextLine();
            System.out.print("You have given string: " + strGiven);
            */

        System.out.println("TASK 1:\n-------");

     //   String input = "abcdA";
          String input = null;  //Check for null pointer exception!

        if (uniqueCharacters(input))
            System.out.println("All characters for string " + input + " are unique.\n");
        else
            System.out.println("NOT all characters for string " + input + " are unique or the given string was null.\n");

        //============================================
        //          *** TASK 2 ***
        //============================================

        System.out.println("TASK 2:\n-------");

        //input = "Hello World of Oracle.";
        input = null;

        String newString = replaceSpacesWith20s(input);
        if (newString != null)
            System.out.println("Transformed " + input + " into:\n" + newString + "\n");
        else
            System.out.println("The string was null" + "\n");

        //============================================
        //          *** TASK 3 ***
        //============================================
        System.out.println("TASK 3:\n-------");


        input = "dabaaccdcAA";
        //input = "";
        System.out.println("Input string: " + input);
        countCharacterOccurence(input);
        System.out.println("");
        
        //============================================
        //          *** TASK 4 ***
        //============================================
        System.out.println("TASK 4:\n-------");
        
        System.out.println("Value of isTrue: " + assertIsTrue("1") + "\n");
        
        //============================================
        //          *** TASK 5 ***
        //============================================
        System.out.println("TASK 5:\n-------");
        Boolean.parseBoolean("true") == true; 
}
