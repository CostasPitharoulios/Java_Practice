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
                                            "WHERE EMPLID=0001";
    
    private static final String QUERY_3 = "UPDATE " + 
                                                "TEST_EMPLOYEE " + 
                                          "SET " + 
                                                "MOBILE='2310123456' " + 
                                          "WHERE " + 
                                                  " EMPLID IN (SELECT EMPLID " + 
                                                  "              FROM TEST_EMPLOYEE TE, TEST_JOBCODES TJ " + 
                                                  "             WHERE TE.JOBCODE_NUMBER = TJ.JOBCODE_NUMBER " + 
                                                  "                   AND " + 
                                                  "             TJ.JOBCODE_DESCR='ΥΠΑΛΛΗΛΟΣ')";

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
    public static void printQueryResultTable(Connection conn, String QUERY_2) throws SQLException{
        try{
            pstmt = conn.prepareStatement(QUERY_2);
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
            pstmt.executeUpdate();
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
            printQueryResultTable(conn, QUERY_2);
            
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
