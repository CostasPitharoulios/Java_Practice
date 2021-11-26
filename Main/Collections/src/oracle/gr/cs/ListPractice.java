package oracle.gr.cs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListPractice {
    public ListPractice() {
        ConnectionUtilities myConnection = new ConnectionUtilities();
    }
    
    private static ConnectionUtilities myConnection = null;
   
   /*
    public static List<Employee> createListOfAllActiveEmployees(Connection conn){
        
    }*/
    
    private static final String getAllUserGroupsQUERY = "SELECT TG.GROUP_NAME " + 
                                                          "FROM TEST_USER_GROUP_MAP UGM, TEST_GROUPS TG " + 
                                                         "WHERE UGM.GROUP_CODE = TG.GROUP_CODE " +
                                                            "AND " +
                                                               "EMPLID = ?";
    
    private static final String getAllUserGroupsAccordigToJobcodeQUERY = "SELECT TG.GROUP_NAME " + 
                                                                        "FROM TEST_EMPLOYEE TE, TEST_JOBCODE_ROLE_MAP JRM, TEST_ROLE_GROUPS RG, TEST_GROUPS TG " + 
                                                                        "WHERE TE.JOBCODE_NUMBER=JRM.JOBCODE_NUMBER  " + 
                                                                        "       AND " + 
                                                                        "       JRM.ROLE_CODE=RG.ROLE_CODE " + 
                                                                        "       AND " + 
                                                                        "       RG.GROUP_CODE=TG.GROUP_CODE " + 
                                                                        "       AND TE.EMPLID=? ";
    
    public static List<String> getAllUserGroups(Connection conn, String emplid, String QUERY) throws SQLException{
        
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        // Creating a list to store all user's groups
        List<String> employeeGroupsList = new ArrayList<String>();

        try {
            pstmt = conn.prepareStatement(QUERY);
            pstmt.setString(1, emplid);
            rset = pstmt.executeQuery();

            // We are going to print all column names and their values
            //ResultSetMetaData rsmd = rset.getMetaData();
           // int columnsNumber = rsmd.getColumnCount();
            
            while (rset.next()) {
                /*for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnValue = rset.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue );
                }
                System.out.println("");*/
                
                //TODO ******* please get the String with column name and not a number.
                employeeGroupsList.add(rset.getString(1));
            }
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            myConnection.closeResultSet(rset);
            myConnection.closePreparedStatement(pstmt);
        }
        
        
        return employeeGroupsList;
    }
    
    public static HashMap<String,List<String>> deepCopyHashMap(Map<String,List<String>> originalHashMap){
    //TODO *** just observation this can be done with lambda stream much more quicker.
    
        HashMap<String,List<String>> newHashMap = new  HashMap<>();
        for (Map.Entry<String, List<String>> entry : originalHashMap.entrySet()){
            newHashMap.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        
        return newHashMap;
    }
    
    public static void main(String args[]) throws SQLException {

        Connection conn = null;
        myConnection = new ConnectionUtilities();
        
        try {

            // Establishing a new connection with the DB
            conn = myConnection.getDBConnection();

            // Checking isConnectionValid method
            if (myConnection.isConnectionValid(conn)) {
                System.out.println("The connection is valid.\n");
            } else {
                System.out.println("The connection is NOT valid.\n");
            }
        
            // ======================================================
            // *** ERWTHMA 1 ***
            // Getting all active employees
            // ======================================================
            
            JdbcMainClass jdbcInstance = new JdbcMainClass();
            
            System.out.println("\nANSWER TO ERWTHMA 1: ");
            List<Employee> listOfAllEmployees = jdbcInstance.createListOfEmployees(conn);
    
            // Going to print out all employees list
            System.out.println(Arrays.toString(listOfAllEmployees.toArray()));
            
            // ======================================================
            // *** ERWTHMATA 2a-b-c-d ***
            // ======================================================
            // Here we are going to describe the way in which the 
            // answers to the questions are going to be implemented.
            
            // We will create a HashMap which will contain all employees
            // and their respective groups. 
            // The keys of the HashMap are going to be the employees' IDs.
            // Since each one of the employees can be assigned to more than
            // one groups, the value of each key (employee) will be a List
            // containing all the groups to which they belong.
                  
            // ======================================================
            // *** ERWTHMA 2a ***
            // ======================================================
            
            // Creating a HashMap to store all the employees and the 
            // groups each one of them has.
            Map<String, List<String>> employeeGroups = new HashMap<>();
              
            // For each one of the employees (EMPLID)
            for (int i=0; i<listOfAllEmployees.size(); i++){
                
                // This is going to store an employee's groups
                List<String> tempGroupList = new ArrayList<String>();
                
                //Get ith employee's ID
                String tempEMPLID = listOfAllEmployees.get(i).getEMPLID();
                
                // Get all the groups to which ith employee is assigned
                tempGroupList = getAllUserGroups(conn, tempEMPLID, getAllUserGroupsQUERY );
                
                //System.out.println(tempGroupList);
                
                // Add the employee and his groups to the map
                employeeGroups.put(tempEMPLID, tempGroupList);
            }
            
            System.out.println("\nANSWER TO ERWTHMA 2a: ");
            System.out.println("(List of groups that users actually have.) ");
            
            System.out.println(employeeGroups);
            
            // ======================================================
            // *** ERWTHMA 2b ***
            // ======================================================   
            // Creating a HashMap to store all the employees and the 
            // groups to which they should be assigned ACCORDING TO THEIR
            // JOBCODE
            Map<String, List<String>> employeeGroupsAccordingJobcode = new HashMap<>();
            
            // For each one of the employees (EMPLID)
            for (int i=0; i<listOfAllEmployees.size(); i++){
                
                // This is going to store an employee's groups
                List<String> tempGroupList = new ArrayList<String>();
                
                //Get ith employee's ID
                String tempEMPLID = listOfAllEmployees.get(i).getEMPLID();
                
                // Get all the groups to which ith employee should be assigned
                // ACCORDING TO THEIR JOB CODE
                tempGroupList = getAllUserGroups(conn, tempEMPLID, getAllUserGroupsAccordigToJobcodeQUERY);
                
                //System.out.println(tempGroupList);
                
                // Add the employee and his groups to the map
                employeeGroupsAccordingJobcode.put(tempEMPLID, tempGroupList);
            }
            
            System.out.println("\nANSWER TO ERWTHMA 2b: ");
            System.out.println("(List of groups that users should have according to their jobcode.) ");
            
            System.out.println(employeeGroupsAccordingJobcode);
            
            
            // ======================================================
            // *** ERWTHMA 2c ***
            // ======================================================   
            // In order to find out for each user, the groups that they
            // actually have but which are not defined by their joccode...
            
            System.out.println("\nANSWER TO ERWTHMA 2c: ");
            System.out.println("These are the groups that each user has direct grant to.");
            
            // This is useful for erwthma 2d
            Map<String, List<String>> newEmployeeGroups = new HashMap<>();
            //newEmployeeGroups.putAll(employeeGroups);
            newEmployeeGroups = deepCopyHashMap(employeeGroups);
            
            // For each one of the employees (EMPLID)
            for (int i=0; i<listOfAllEmployees.size(); i++){
                
                //Get ith employee's ID
                String tempEMPLID = listOfAllEmployees.get(i).getEMPLID();
                
                // Subtracting all jobcode groups from the actual groups that each user has
                employeeGroups.get(tempEMPLID).removeAll(employeeGroupsAccordingJobcode.get(tempEMPLID));  
                
            }
            
            System.out.println(employeeGroups);
            

            
            
            // ======================================================
            // *** ERWTHMA 2d ***
            // ======================================================   
            System.out.println("\nANSWER TO ERWTHMA 2d: ");
            
            // For each one of the employees (EMPLID)
            for (int i=0; i<listOfAllEmployees.size(); i++){
                
                //Get ith employee's ID
                String tempEMPLID = listOfAllEmployees.get(i).getEMPLID();
                
                System.out.print(tempEMPLID + " has group with name \"Exodologion Users\": ");
                //System.out.println(newEmployeeGroups);
                if (newEmployeeGroups.get(tempEMPLID).contains("Exodologion_Users")){
                    System.out.println("True");
                }
                else 
                    System.out.println("False");
                
            }
            
            
            
        }
        catch (SQLException e) {
            System.out.println("An exception was caught.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            myConnection.closeConnection(null, null, conn);
        }
    }
}

