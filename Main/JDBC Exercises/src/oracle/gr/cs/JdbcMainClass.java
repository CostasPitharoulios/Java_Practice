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

    //static PreparedStatement pstmt = null;
    //static ResultSet rset = null;

    private static final String QUERY_1 = "SELECT EMPLID " + "FROM TEST_EMPLOYEE";

    private static final String QUERY_2 = " SELECT * FROM TEST_EMPLOYEE WHERE EMPLID= ? ";

    private static final String QUERY_3 =
        "UPDATE TEST_EMPLOYEE SET  MOBILE= ? WHERE " + " EMPLID IN (SELECT EMPLID " +
        "              FROM TEST_EMPLOYEE TE, TEST_JOBCODES TJ " +
        "             WHERE TE.JOBCODE_NUMBER = TJ.JOBCODE_NUMBER " + "                   AND " +
        "             TJ.JOBCODE_DESCR= ?)";

    private static final String QUERY_4 = "SELECT * FROM TEST_EMPLOYEE";

    /** This function prepares a statement for a query and returns a hashset of employee ids
     * @param conn is the connection
     * @return a set of employee ids
     * @throws SQLException
     */
    public static HashSet<String> getIdSet(Connection conn, ConnectionUtilities myConnection) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            pstmt = conn.prepareStatement(QUERY_1);
            rset = pstmt.executeQuery();

            // Creating a hashset to store all employee ids
            /** **********TODO it is better to declare like this: (always the declaration should be an interface)
            *Set<String> idSet = new HashSet<String>();
            *By declaring a collection using an interface type, 
            *the code would be more flexible as you can change the concrete implementation easily when needed
           */
            HashSet<String> idSet = new HashSet<String>();


            while (rset.next()) {
                idSet.add(rset.getString("EMPLID"));
            }

            return idSet;
        } catch (SQLException se) {
            throw se;
        } finally {
            myConnection.closeResultSet(rset);
            myConnection.closePreparedStatement(pstmt);
        }
    }

    /** This function gets a query and prints all the table's column names along with the resulting values.
     * @param conn is the Connection to the DB
     * @throws SQLException
     */
    public static void printQueryResultTable(Connection conn, String emplid, String QUERY_2,
                                             ConnectionUtilities myConnection) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            pstmt = conn.prepareStatement(QUERY_2);
            pstmt.setString(1, emplid);
            rset = pstmt.executeQuery();

            // We are going to print all column names and their values
            ResultSetMetaData rsmd = rset.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rset.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    String columnValue = rset.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
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

    }

    public static void updateEmployeeMobile(Connection conn, String QUERY_3, String JOBCODE_DESCR,
                                            ConnectionUtilities myConnection) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            pstmt = conn.prepareStatement(QUERY_3);
            pstmt.setString(1, "2310123455");
            pstmt.setString(2, JOBCODE_DESCR);
            int numberOfRowsAffected = pstmt.executeUpdate();
            System.out.println("Number of rows affected after the update: " + numberOfRowsAffected);
            if (numberOfRowsAffected <= 0) {
                throw new Exception("Not a single row was updated!");
            }
        }

        catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            myConnection.closeResultSet(rset);
            myConnection.closePreparedStatement(pstmt);
        }

    }

    public static List<Employee> createListOfEmployees(Connection conn, String QUERY_4,
                                                       ConnectionUtilities myConnection) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        // Creating a list to store all employees
        List<Employee> listOfEmployees = new ArrayList<Employee>();

        try {
            pstmt = conn.prepareStatement(QUERY_4);
            rset = pstmt.executeQuery();

            // For each one of the employees in the database
            while (rset.next()) {

                // Creating a new Employee object containing all employee's information
                Employee newEmployee =
                    new Employee(rset.getString(1), rset.getString(2), rset.getString(3), rset.getString(4),
                                 rset.getString(5), rset.getString(6), rset.getString(7), rset.getString(8),
                                 rset.getString(9), rset.getString(10), rset.getString(11), rset.getTimestamp(12),
                                 rset.getTimestamp(13), rset.getString(14));

                // Adding Employee object to the list of employees
                listOfEmployees.add(newEmployee);
            }
        }

        catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            myConnection.closeResultSet(rset);
            myConnection.closePreparedStatement(pstmt);
        }

        return listOfEmployees;
    }

    private static final String insertPartialEmployeeQUERY =
        "INSERT INTO TEST_EMPLOYEE (EMPLID, JOBCODE_NUMBER, DEPTID)" + "VALUES (?,?,?)";

    public static void insertPartialEmployeeToTable(Connection conn, Employee emp,
                                                    ConnectionUtilities myConnection) throws SQLException {

        // In case we want to insert all info of an employee
        /* "VALUES ('"+ emp.getEMPLID() + "','" + emp.getFIRST_NAME() +"','" + emp.getLAST_NAME() +
                       "','" + emp.getFIRST_NAME_EN() + "','" + emp.getLAST_NAME_EN() + "','" + emp.getFATHER_NAME() +
                       "','" + emp.getMOBILE() + "','" + emp.getSTATUS() + "','" + emp.getJOBCODE_NUMBER()+
                       "','" + emp.getDEPTID() + "','" + emp.getMANAGER_ID() + "','" + emp.getHIRE_DATE() +
                       "','" + emp.getEND_DATE() + "','" + emp.getEMP_TYPE()  + "')";*/

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            pstmt = conn.prepareStatement(insertPartialEmployeeQUERY);
            pstmt.setString(1, emp.getEMPLID());
            pstmt.setString(2, emp.getJOBCODE_NUMBER());
            pstmt.setString(3, emp.getDEPTID());
            int numberOfRowsAffected = pstmt.executeUpdate();
            System.out.println("Number of rows affected after the update: " + numberOfRowsAffected);
            if (numberOfRowsAffected <= 0) {
                throw new Exception("Not a single row was updated!");
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

    }

    // This query gives as the sum of all employees whose job description
    // contains the word "Manager"
    private static final String sumOfManagersQUERY =
        "SELECT COUNT(EMPLID) AS SUMOFMANAGERS " + "FROM " + "(SELECT * " +
        "    FROM TEST_EMPLOYEE TE, TEST_JOBCODES TJ " + "   WHERE TE.JOBCODE_NUMBER = TJ.JOBCODE_NUMBER " +
        "         AND " + "         TJ.JOBCODE_DESCR LIKE '%’–¡ÀÀ«Àœ”%') ";


    // This query is going to return a table with all MANAGER_IDs along with
    // how many people are their subordinates. Since we know that our example
    // has only one Manager with subordiantes, this table is going to contain
    // ONLY ONE ROW!!!
    private static final String sumOfManagerSubordinatesQUERY =
        "SELECT MANAGER_ID, COUNT(EMPLID) AS SUM " + "FROM  TEST_EMPLOYEE " + "WHERE MANAGER_ID IN " +
        "(SELECT EMPLID " + "FROM TEST_EMPLOYEE TE, TEST_JOBCODES TJ " +
        "WHERE TE.JOBCODE_NUMBER = TJ.JOBCODE_NUMBER " + "AND " + "TJ.JOBCODE_DESCR LIKE '%ƒ…≈’»’Õ‘«”%') " +
        "GROUP BY MANAGER_ID ";

    // Finds the id of the manager who has no subordinates
    private static final String newManagerIdQUERY =
        "SELECT EMPLID " + "  FROM  TEST_EMPLOYEE " + " WHERE EMPLID IN " + "     (SELECT EMPLID " +
        "       FROM TEST_EMPLOYEE TE, TEST_JOBCODES TJ " + "      WHERE TE.JOBCODE_NUMBER = TJ.JOBCODE_NUMBER " +
        "        AND " + "        TJ.JOBCODE_DESCR LIKE '%ƒ…≈’»’Õ‘«”%') " + "        AND " + "        EMPLID != ? ";

    private static final String moveSubordinatesQUERY =
        "UPDATE TEST_EMPLOYEE " + "SET MANAGER_ID = ? " + "WHERE MANAGER_ID = ? ";

    private static final String deleteOldManagerQUERY = "DELETE FROM TEST_EMPLOYEE WHERE EMPLID= ? ";

    /** This method checks if there are more than two managers. If yes, it finds the manager who
     * has subordinates, moves all his subordinates to the other manager and then deletes him.
     * @param conn is the connection with the DB
     * @throws SQLException
     */
    public static void replaceManager(Connection conn, ConnectionUtilities myConnection) throws SQLException {

        //System.out.println("INSERT QUERY: " + sumOfManagersQUERY + "\n");
        PreparedStatement pstmt_1 = null;
        ResultSet rset_1 = null;

        try {
            pstmt_1 = conn.prepareStatement(sumOfManagersQUERY);
            rset_1 = pstmt_1.executeQuery();

            int sumOfManagers = -1;
            while (rset_1.next()) {
                sumOfManagers = rset_1.getInt(1);
            }

            System.out.println("NUMBER OF MANAGERS: " + sumOfManagers);

            // If there are more than one managers, we are going to
            // replace the one who has subordinates with the one who has not.
            if (sumOfManagers > 1) {


                String managerWithSubordinatesID = null;
                PreparedStatement pstmt_2 = null;
                ResultSet rset_2 = null;
                try {
                    pstmt_2 = conn.prepareStatement(sumOfManagerSubordinatesQUERY);
                    rset_2 = pstmt_2.executeQuery();


                    while (rset_2.next()) {
                        managerWithSubordinatesID = rset_2.getString(1);
                    }

                    System.out.println("Manager with subordinates " + managerWithSubordinatesID);
                } catch (SQLException se) {
                    throw se;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                } finally {
                    myConnection.closeResultSet(rset_2);
                    myConnection.closePreparedStatement(pstmt_2);
                }

                // Now we are going to find the id of the manager who has NO subordinates

                String newManagerID = null;
                PreparedStatement pstmt_3 = null;
                ResultSet rset_3 = null;
                try {
                    pstmt_3 = conn.prepareStatement(newManagerIdQUERY);
                    pstmt_3.setString(1, managerWithSubordinatesID);
                    rset_3 = pstmt_3.executeQuery();


                    while (rset_3.next()) {
                        newManagerID = rset_3.getString(1);
                    }

                    System.out.println("New Manager ID: " + newManagerID);
                } catch (SQLException se) {
                    throw se;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                } finally {
                    myConnection.closeResultSet(rset_3);
                    myConnection.closePreparedStatement(pstmt_3);
                }

                // Now that we know who the Manager with the subordinates is as well as the new Manager,
                // we are going to assign all his subordinates to the new manager


                PreparedStatement pstmt_4 = null;
                ResultSet rset_4 = null;
                try {
                    pstmt_4 = conn.prepareStatement(moveSubordinatesQUERY);
                    pstmt_4.setString(1, newManagerID);
                    pstmt_4.setString(2, managerWithSubordinatesID);

                    int numberOfRowsAffected = pstmt_4.executeUpdate();
                    System.out.println("Number of rows affected after the update: " + numberOfRowsAffected);
                    if (numberOfRowsAffected <= 0) {
                        throw new Exception("Not a single row was updated!");
                    }
                }

                catch (SQLException se) {
                    throw se;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                } finally {
                    myConnection.closeResultSet(rset_4);
                    myConnection.closePreparedStatement(pstmt_4);
                }

                // And finally we delete the old Manager
                PreparedStatement pstmt_5 = null;
                ResultSet rset_5 = null;
                try {
                    pstmt_5 = conn.prepareStatement(deleteOldManagerQUERY);
                    pstmt_5.setString(1, managerWithSubordinatesID);

                    int numberOfRowsAffected = pstmt_5.executeUpdate();
                    System.out.println("Number of rows affected after the update: " + numberOfRowsAffected);
                    if (numberOfRowsAffected <= 0) {
                        throw new Exception("Not a single row was updated!");
                    }
                }

                catch (SQLException se) {
                    throw se;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                } finally {
                    myConnection.closeResultSet(rset_5);
                    myConnection.closePreparedStatement(pstmt_5);
                }
            }


        } catch (SQLException se) {
            throw se;
        } finally {
            myConnection.closeResultSet(rset_1);
            myConnection.closePreparedStatement(pstmt_1);
        }

    }

    public static void main(String args[]) throws SQLException {

        Connection conn = null;
        
        //TODO ConnectionUtilities should be a class variable and initialized only once, when the class is initialized
        ConnectionUtilities myConnection = new ConnectionUtilities();

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
            // ======================================================
            System.out.println("ANSWER TO ERWTHMA 1: ");

            // Getting a set of employee ids and printing its content
            //System.out.println(getIdSet(conn));
            HashSet<String> idSet = getIdSet(conn, myConnection);
            idSet.forEach((n) -> System.out.println("Employee id: " + n)); // Using Java Lambda Expressions
            System.out.println("");
            // ======================================================
            // *** ERWTHMA 2 ***
            // ======================================================
            System.out.println("\nANSWER TO ERWTHMA 2: ");
            printQueryResultTable(conn, "0001", QUERY_2, myConnection);

            // ======================================================
            // *** ERWTHMA 3 ***
            // ======================================================
            System.out.println("\nANSWER TO ERWTHMA 3: ");
            updateEmployeeMobile(conn, QUERY_3, "’–¡ÀÀ«Àœ”", myConnection);
            printQueryResultTable(conn, "0001", QUERY_2, myConnection);


            // ======================================================
            // *** ERWTHMA 4 & 5 ***
            // ======================================================

            System.out.println("\nANSWER TO ERWTHMA 4&5: ");
            List<Employee> listOfAllEmployees = createListOfEmployees(conn, QUERY_4, myConnection);

            // Going to print out all employees list
            System.out.println(Arrays.toString(listOfAllEmployees.toArray()));

            // ======================================================
            // *** ERWTHMA 6 ***
            // ======================================================
            System.out.println("\nANSWER TO ERWTHMA 6: ");
            Employee newEmployee = new Employee("0005", "11", "3504");

            // Updating BD Employee Table
            insertPartialEmployeeToTable(conn, newEmployee, myConnection);

            // Creating a list with all the employees just to make sure
            // that the update was successful.
            System.out.println("\nANSWER TO ERWTHMA 6: ");
            
            //TODO query_4 is a class variable , so it is reduntant to pass as a method variable 
            listOfAllEmployees = createListOfEmployees(conn, QUERY_4, myConnection);

            // Going to print out all employees list
            System.out.println(Arrays.toString(listOfAllEmployees.toArray()));

            // ======================================================
            // *** ERWTHMA 7 ***
            // ======================================================
            System.out.println("\nANSWER TO ERWTHMA 7: ");

            replaceManager(conn, myConnection);

        } catch (SQLException e) {
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
