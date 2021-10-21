package oracle.gr.cs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;


public class ConnectionUtilities {
    public ConnectionUtilities() {
        super();
    }

   // *** ENTER SECRET CODE HERE *** //
   
   
  
    // *** END OF SECRET CODE HERE ***//

    public Connection getDBConnection() throws SQLException{
        OracleDataSource ds;
        ds = new OracleDataSource();
        ds.setURL(jdbcUrl);
        
        conn = ds.getConnection(userid,password);
        
        return conn;
    }

    public boolean isConnectionValid(Connection conn) throws SQLException {
        boolean isValid = false;

        if (conn !=null && conn.isValid(10)){
            isValid = true;
        }

        return isValid;
    }


    public void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection){
        closeResultSet(resultSet);
        closePreparedStatement(preparedStatement);
        closeConnection(connection);

        System.out.println("\nConnection is closed.");
    }

    public void closeConnection(CallableStatement callableStatement, Connection connection){
        closeCallableStatement(callableStatement);
        closeConnection(connection);

        System.out.println("\nConnection is closed.");
    }

    public void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement, CallableStatement callableStatement, Connection connection){
        closeResultSet(resultSet);
        closePreparedStatement(preparedStatement);
        closeCallableStatement(callableStatement);
        closeConnection(connection);

        System.out.println("\nConnection is closed.");
    }

    private static void closeResultSet(ResultSet rset){
        try{
            if (rset != null){
                rset.close();
            }
        } 
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }                                                            
    }

    private static void closePreparedStatement(PreparedStatement pstmt) {
       try {
          if (pstmt != null)
            pstmt.close();
       }
       catch (SQLException e) { 
           System.out.println(e.getMessage());
        }
    }

    private static void closeCallableStatement(CallableStatement cstmt){
        try {
           if (cstmt != null)
             cstmt.close();
        } 
        catch (SQLException e) { 
            System.out.println(e.getMessage());
        }
    }

    private static void closeConnection(Connection conn) {
       try {
          if (conn != null)
             conn.close();
       } 
       catch (SQLException e) {
           System.out.println(e.getMessage());
        }
    }


}
