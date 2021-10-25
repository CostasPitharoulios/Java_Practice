package oracle.gr.cs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import java.util.HashSet;


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
                                                  "            JOBCODE_DESCR='ΥΠΑΛΛΗΛΟΣ')";

    /*                                        
    private static final String QUERY_3 = "SELECT * " + 
                                                
                                          "FROM TEST_EMPLOYEE " + 
                                               
                                          "WHERE " + 
                                                " EMPLID IN (SELECT EMPLID " + 
                                                "              FROM TEST_EMPLOYEE TE, TEST_JOBCODES TJ " + 
                                                "             WHERE TE.JOBCODE_NUMBER = TJ.JOBCODE_NUMBER " + 
                                                "                   AND " + 
                                                "                   TE.JOBCODE_NUMBER='10') ";
               */                     
       
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
    
            // Getting a set of employee ids and printing its content
            //System.out.println(getIdSet(conn));
            HashSet<String> idSet = getIdSet(conn);
            idSet.forEach( (n) -> System.out.println("Employee id: " + n )); // Using Java Lambda Expressions
            System.out.println("");
            // ======================================================
            // *** ERWTHMA 2 ***
            // ======================================================
            printQueryResultTable(conn, QUERY_2);
            
            // ======================================================
            // *** ERWTHMA 3 ***
            // ======================================================
            updateEmployeeMobile(conn, QUERY_3);
            printQueryResultTable(conn, QUERY_2);
            
            
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
