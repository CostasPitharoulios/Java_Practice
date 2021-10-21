package oracle.gr.cs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashSet;


public class JdbcMainClass {
    public JdbcMainClass() {
        super();
    }

    static PreparedStatement pstmt = null;
    static ResultSet rset = null;

    private static final String QUERY = "SELECT EMPLID from TEST_EMPLOYEE";


    /** This function prepares a statement for a query and returns a hashset of employee ids
     * @param conn is the connection
     * @return a set of employee ids
     * @throws SQLException
     */
    public static HashSet<String> getIdSet(Connection conn) throws SQLException{

        try{
            pstmt = conn.prepareStatement(QUERY);
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
    
            // Getting a set of employee ids and printing its content
            //System.out.println(getIdSet(conn));
            HashSet<String> idSet = getIdSet(conn);
            idSet.forEach( (n) -> System.out.println("Employee id: " + n )); // Using Java Lambda Expressions
    
            // Closing connection
            
        }
        catch (SQLException e){
            System.out.println("An exception was caught.");
            e.printStackTrace();     
        }
        finally{
            myConnection.closeConnection(rset, pstmt, conn);
        }

    }
}
