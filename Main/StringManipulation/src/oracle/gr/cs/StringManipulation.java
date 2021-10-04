package oracle.gr.cs;

import java.util.HashMap;
import java.util.HashSet;


public class StringManipulation {
    public StringManipulation() {
        super();
    }

    // Geia sou Konstantine !!

    // We are going to use sets.
    // The idea is that all items of a set are unique.
    // Therefore, we are going to transform the string to a set.
    // If the length of the set equals the length of the string,
    // then all string's characters are unique.

    /**
     * We are going to use sets.
     *    The idea is that all items of a set are unique.
     *    Therefore, we are going to transform the string to a set.
     *    If the length of the set equals the length of the string,
     *    then all string's characters are unique.
     *    
     * @param str the input string of the method
     * @return true if all characters unique , false if not
     */
    public static boolean uniqueCharacters(String str) {

        HashSet<Character> characterSet = new HashSet<>();

        for (int i = 0; i < str.length(); i++) { // For each character of the string
            characterSet.add(str.charAt(i)); // add the character to the set


        }

        if (characterSet.size() == str.length()) // The characters are all unique
            return true;
        else
            return false; // One or more of the characters is repeated
    }

    private static String replaceSpacesWith20s(String str) {
        String newString = str.replaceAll(" ", "%20");
        return newString;
    }

    // We are going to create a Hashmap. The key will be each character
    // of the string and the value will be its occurence
    private static void countCharacterOccurence(String str) {

        HashMap<Character, Integer> characterMap = new HashMap<Character, Integer>(); // Creating the hash map

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

        String input = "abcdA";

        if (uniqueCharacters(input))
            System.out.println("All characters for string " + input + " are unique.\n");
        else
            System.out.println("NOT all characters for string " + input + " are unique.\n");

        //============================================
        //          *** TASK 2 ***
        //============================================

        System.out.println("TASK 2:\n-------");

        input = "Hello World of Oracle.";

        String newString = replaceSpacesWith20s(input);
        System.out.println("Transformed " + input + " into:\n" + newString + "\n");

        //============================================
        //          *** TASK 3 ***
        //============================================
        System.out.println("TASK 3:\n-------");


        input = "dabaaccdc";
        System.out.println("Input string: " + input);
        countCharacterOccurence(input);

    }
}
