package oracle.gr.cs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class JdbcMainClass {
    public JdbcMainClass() {
        super();
    }

    static PreparedStatement pstmt = null;
    static ResultSet rset = null;

    private static final String QUERY_1 = "SELECT EMPLID " +
                                            "FROM TEST_EMPLOYEE";
    
    private static final String QUERY_2 = " SELECT * " +
                                             "FROM TEST_EMPLOYEE " +
                                            "WHERE EMPLID= ? ";
    
    private static final String QUERY_3 = "UPDATE " + 
                                                "TEST_EMPLOYEE " + 
                                          "SET " + 
                                                "MOBILE= ?  " + 
                                          "WHERE " + 
                                                  " EMPLID IN (SELECT EMPLID " + 
                                                  "              FROM TEST_EMPLOYEE TE, TEST_JOBCODES TJ " + 
                                                  "             WHERE TE.JOBCODE_NUMBER = TJ.JOBCODE_NUMBER " + 
                                                  "                   AND " + 
                                                  "             TJ.JOBCODE_DESCR= ?)";

    private static final String QUERY_4 = "SELECT * FROM TEST_EMPLOYEE";
       
    /** This function prepares a statement for a query and returns a hashset of employee ids
     * @param conn is the connection
     * @return a set of employee ids
     * @throws SQLException
     */
    public static HashSet<String> getIdSet(Connection conn) throws SQLException{

        try{
            pstmt = conn.prepareStatement(QUERY_1);
            rset = pstmt.executeQuery();
    
            // Creating a hashset to store all employee ids
            HashSet<String> idSet = new HashSet<String>();
    
    
            while (rset.next()){
                idSet.add(rset.getString("EMPLID"));
            }
    
            return idSet;
        }
        catch(SQLException se){
            throw se;
        }
    }

    /** This function gets a query and prints all the table's column names along with the resulting values.
     * @param conn is the Connection to the DB
     * @throws SQLException
     */
    public static void printQueryResultTable(Connection conn, String emplid,String QUERY_2) throws SQLException{
            
        try{
            pstmt = conn.prepareStatement(QUERY_2);
            pstmt.setString(1, emplid);
            rset = pstmt.executeQuery();
            
            // We are going to print all column names and their values            
            ResultSetMetaData rsmd = rset.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rset.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rset.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
        } 
        catch(SQLException se){
            throw se;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    public static void updateEmployeeMobile(Connection conn, String QUERY_3) throws SQLException{
        
        try{
            pstmt = conn.prepareStatement(QUERY_3);
            pstmt.executeUpdate();
        }
        
        catch(SQLException se){
            throw se;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    
    }
    
    public static List<Employee> createListOfEmployees(Connection conn, String QUERY_4) throws SQLException{
        
        // Creating a list to store all employees
        List<Employee> listOfEmployees = new ArrayList<Employee>();
        
        try{
            pstmt = conn.prepareStatement(QUERY_4);
            rset = pstmt.executeQuery();
            
            // For each one of the employees in the database
            while (rset.next()){
                
                // Creating a new Employee object containing all employee's information
                Employee newEmployee = new Employee(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4),  rset.getString(5), rset.getString(6),  rset.getString(7),  rset.getString(8), rset.getString(9),  rset.getString(10),  rset.getString(11), rset.getTimestamp(12),  rset.getTimestamp(13),  rset.getString(14));
            
                // Adding Employee object to the list of employees
                listOfEmployees.add(newEmployee);
            }
        }
        
        catch(SQLException se){
            throw se;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        return listOfEmployees;
    }

   private static final String QUERY = "INSERT INTO TEST_EMPLOYEE (EMPLID, JOBCODE_NUMBER, DEPTID)" + 
                           "VALUES (?,?,?)";
    public static void insertPartialEmployeeToTable(Connection conn, Employee emp) throws SQLException{
        
    String QUERY = "INSERT INTO TEST_EMPLOYEE (EMPLID, JOBCODE_NUMBER, DEPTID)" + 
                        "VALUES ('" + emp.getEMPLID() + "','" + emp.getJOBCODE_NUMBER()+
                        "','" + emp.getDEPTID() + "')";
        
                          // In case we want to insert all info of an employee
                          /* "VALUES ('"+ emp.getEMPLID() + "','" + emp.getFIRST_NAME() +"','" + emp.getLAST_NAME() +
                       "','" + emp.getFIRST_NAME_EN() + "','" + emp.getLAST_NAME_EN() + "','" + emp.getFATHER_NAME() +
                       "','" + emp.getMOBILE() + "','" + emp.getSTATUS() + "','" + emp.getJOBCODE_NUMBER()+
                       "','" + emp.getDEPTID() + "','" + emp.getMANAGER_ID() + "','" + emp.getHIRE_DATE() +
                       "','" + emp.getEND_DATE() + "','" + emp.getEMP_TYPE()  + "')";*/
                       
        
        System.out.println("INSERT QUERY: " + QUERY + "\n");
        
        try{
            pstmt = conn.prepareStatement(QUERY);
            pstmt.setString(1, emp.getEMPLID());
            pstmt.setString(2, emp.getJOBCODE_NUMBER());
            pstmt.setString(3, emp.getDEPTID());         
            pstmt.executeUpdate(); //TODO check return 
        }
        catch(SQLException se){
            throw se;
        }
        
    }

    /** This method checks if there are more than two managers. If yes, it finds the manager who
     * has subordinates, moves all his subordinates to the other manager and then deletes him. 
     * @param conn is the connection with the DB
     * @throws SQLException
     */
    public static void replaceManager(Connection conn) throws SQLException{
        
        // This query gives as the sum of all employees whose job description
        // contains the word "Manager"
        String sumOfManagersQUERY = "SELECT COUNT(EMPLID) AS SUMOFMANAGERS " +
                         "FROM " +
                                "(SELECT * "  +
                                "    FROM TEST_EMPLOYEE TE, TEST_JOBCODES TJ " + 
                                "   WHERE TE.JOBCODE_NUMBER = TJ.JOBCODE_NUMBER " +
                                "         AND " +
                                "         TJ.JOBCODE_DESCR LIKE '%ΔΙΕΥ�?Υ�?ΤΗΣ%') ";
                       
        
        //System.out.println("INSERT QUERY: " + sumOfManagersQUERY + "\n");

        try{
            pstmt = conn.prepareStatement(sumOfManagersQUERY);
            rset = pstmt.executeQuery();
            
            int sumOfManagers = -1;
            while (rset.next()){
                sumOfManagers = rset.getInt(1);
            }
            
            System.out.println("NUMBER OF MANAGERS: " + sumOfManagers);
            
            // If there are more than one managers, we are going to 
            // replace the one who has subordinates with the one who has not.
            if (sumOfManagers > 1){
                
                // This query is going to return a table with all MANAGER_IDs along with 
                // how many people are their subordinates. Since we know that our example 
                // has only one Manager with subordiantes, this table is going to contain
                // ONLY ONE ROW!!!
                String sumOfManagerSubordinatesQUERY = "SELECT MANAGER_ID, COUNT(EMPLID) AS SUM " +
                                                         "FROM  TEST_EMPLOYEE " + 
                                                        "WHERE MANAGER_ID IN " + 
                                                                        "(SELECT EMPLID " +
                                                                           "FROM TEST_EMPLOYEE TE, TEST_JOBCODES TJ " +
                                                                          "WHERE TE.JOBCODE_NUMBER = TJ.JOBCODE_NUMBER " +
                                                                                 "AND " +
                                                                                 "TJ.JOBCODE_DESCR LIKE '%ΔΙΕΥ�?Υ�?ΤΗΣ%') " +
                                                        "GROUP BY MANAGER_ID ";
                
                String managerWithSubordinatesID = null;
                try{
                    pstmt = conn.prepareStatement(sumOfManagerSubordinatesQUERY);
                    rset = pstmt.executeQuery();
                    
                    
                    while (rset.next()){
                        managerWithSubordinatesID = rset.getString(1);
                    }
                    
                    System.out.println("Manager with subordinates " + managerWithSubordinatesID);       
                }
                catch(SQLException se){
                    throw se;
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            
                // Now we are going to find the id of the manager who has NO subordinates
                
                String newManagerIdQUERY = "SELECT EMPLID " +
                                           "  FROM  TEST_EMPLOYEE " +
                                           " WHERE EMPLID IN " +
                                           "     (SELECT EMPLID " +
                                           "       FROM TEST_EMPLOYEE TE, TEST_JOBCODES TJ " + 
                                           "      WHERE TE.JOBCODE_NUMBER = TJ.JOBCODE_NUMBER " +
                                           "        AND " + 
                                           "        TJ.JOBCODE_DESCR LIKE '%ΔΙΕΥ�?Υ�?ΤΗΣ%') " +  
                                           "        AND " + 
                                           "        EMPLID != '" + managerWithSubordinatesID + "' ";                   
                    
                    
                String newManagerID= null;
                try{
                    pstmt = conn.prepareStatement(newManagerIdQUERY);
                    rset = pstmt.executeQuery();
                                    
                                    
                    while (rset.next()){
                        newManagerID = rset.getString(1);
                    }
                                    
                    System.out.println("New Manager ID: " +newManagerID);       
                }
                catch(SQLException se){
                    throw se;
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

                // Now that we know who the Manager with the subordinates is as well as the new Manager,
                // we are going to assign all his subordinates to the new manager
                
                String moveSubordinatesQUERY = "UPDATE TEST_EMPLOYEE " +
                                               "SET MANAGER_ID = '" + newManagerID + "' " +
                                               "WHERE MANAGER_ID = '" + managerWithSubordinatesID + "' "; 
                
                try{
                    pstmt = conn.prepareStatement(moveSubordinatesQUERY);
                    pstmt.executeUpdate();
                }
                
                catch(SQLException se){
                    throw se;
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            
                // And finally we delete the old Manager
                String deleteOldManagerQUERY = "DELETE FROM TEST_EMPLOYEE WHERE EMPLID= '" + managerWithSubordinatesID + "' ";
                
                try{
                    pstmt = conn.prepareStatement(deleteOldManagerQUERY);
                    pstmt.executeUpdate();
                }
                
                catch(SQLException se){
                    throw se;
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
            
            
            
            
        }
        catch(SQLException se){
            throw se;
        }
        
    }

    public static void main(String args[]) throws SQLException {
        
        Connection conn=null;
        ConnectionUtilities myConnection = new ConnectionUtilities();
       
        try{

            // Establishing a new connection with the DB
            conn = myConnection.getDBConnection();
                   
            // Checking isConnectionValid method
            if (myConnection.isConnectionValid(conn)){
                System.out.println("The connection is valid.\n");
            }
            else{
                System.out.println("The connection is NOT valid.\n");
            }
    
            // ======================================================
            // *** ERWTHMA 1 ***
            // ======================================================
            System.out.println("ANSWER TO ERWTHMA 1: ");
    
            // Getting a set of employee ids and printing its content
            //System.out.println(getIdSet(conn));
            HashSet<String> idSet = getIdSet(conn);
            idSet.forEach( (n) -> System.out.println("Employee id: " + n )); // Using Java Lambda Expressions
            System.out.println("");
            // ======================================================
            // *** ERWTHMA 2 ***
            // ======================================================
            System.out.println("\nANSWER TO ERWTHMA 2: ");
            printQueryResultTable(conn,"0001", QUERY_2);
            
            // ======================================================
            // *** ERWTHMA 3 ***
            // ======================================================
            System.out.println("\nANSWER TO ERWTHMA 3: ");
            updateEmployeeMobile(conn, QUERY_3);
            printQueryResultTable(conn, QUERY_2);
            
            
            // ======================================================
            // *** ERWTHMA 4 & 5 ***
            // ======================================================
            
            System.out.println("\nANSWER TO ERWTHMA 4&5: ");
            List<Employee> listOfAllEmployees = createListOfEmployees(conn, QUERY_4);
            
            // Going to print out all employees list
            System.out.println(Arrays.toString(listOfAllEmployees.toArray()));
            
            // ======================================================
            // *** ERWTHMA 6 ***
            // ======================================================
            System.out.println("\nANSWER TO ERWTHMA 6: ");
            Employee newEmployee = new Employee("0005", "11", "3504");
            
            // Updating BD Employee Table
            insertPartialEmployeeToTable(conn, newEmployee);
            
            // Creating a list with all the employees just to make sure
            // that the update was successful.
            System.out.println("\nANSWER TO ERWTHMA 4&5: ");
            listOfAllEmployees = createListOfEmployees(conn, QUERY_4);
            
            // Going to print out all employees list
            System.out.println(Arrays.toString(listOfAllEmployees.toArray()));
            
            // ======================================================
            // *** ERWTHMA 7 ***
            // ======================================================
            System.out.println("\nANSWER TO ERWTHMA 7: ");
            
            replaceManager(conn);
           
        }
        catch (SQLException e){
            System.out.println("An exception was caught.");
            e.printStackTrace();     
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally{
            myConnection.closeConnection(rset, pstmt, conn);
        }

    }
}
