package oracle.gr.cs;

import java.text.SimpleDateFormat;

import java.time.LocalDateTime;

import java.sql.Timestamp;

public class Employee {
   
    private String EMPLID;
    private String FIRST_NAME;
    private String LAST_NAME;
    private String FIRST_NAME_EN;
    private String LAST_NAME_EN;
    private String FATHER_NAME;
    private String MOBILE;
    private String STATUS;
    private String JOBCODE_NUMBER;
    private String DEPTID;
    private String MANAGER_ID;
    private LocalDateTime HIRE_DATE;
    private LocalDateTime END_DATE;
    private String EMP_TYPE;

    public String toString() {
        /*
        String hireDateSmall = null;
        if (HIRE_DATE != null){

            int yearHire = this.HIRE_DATE.getYear();
            int monthHire = this.HIRE_DATE.getMonth();
            int dayHire = this.HIRE_DATE.getDay();

            hireDateSmall =  dayHire + "/" + monthHire + "/" + yearHire;
        }

        String endDateSmall = null;
        if (END_DATE != null){
            int yearEnd = this.END_DATE.getYear();
            int monthEnd = this.END_DATE.getMonth();
            int dayEnd = this.END_DATE.getDay();
        }
        */
        return EMPLID + " " + FIRST_NAME + " " + LAST_NAME + " " + FIRST_NAME_EN + " " + LAST_NAME_EN + " " +
               FATHER_NAME + " " + MOBILE + " " + STATUS + " " + JOBCODE_NUMBER + " " + DEPTID + " " + MANAGER_ID +
               " " + HIRE_DATE + " " + END_DATE + " " + EMP_TYPE + "\n";
    }


    //==================================================================================

    /** This function compares an Employee with another.
     * @param e is an object employee that we want to compare
     * @return areEqual=true if the employees have the same EMP_ID. In any other case areEqual=false is returned.
     */
    //-----------------------------------------------------------------------------------
    public boolean equals(Employee e) {
        boolean areEqual = false;

        if (e == this) {
            areEqual = true;
            return areEqual;
        }
        if (e == null)
            return areEqual;

        /*
        // THIS IS IN CASE WE WANT TO COMPARE ALL VALUES!!!
        if (EMPLID.equals(e.EMPLID) && FIRST_NAME.equals(e.FIRST_NAME) && LAST_NAME.equals(e.LAST_NAME)
            && FIRST_NAME_EN.equals(e.FIRST_NAME_EN) && LAST_NAME_EN.equals(e.LAST_NAME_EN)
            && FATHER_NAME.equals(e.FATHER_NAME) && MOBILE.equals(e.MOBILE) && STATUS.equals(e.STATUS)
            && JOBCODE_NUMBER.equals(e.JOBCODE_NUMBER) && DEPTID.equals(e.DEPTID) && MANAGER_ID.equals(e.MANAGER_ID)
            && EMP_TYPE.equals(e.EMP_TYPE) && HIRE_DATE.equals(e.HIRE_DATE) && END_DATE.equals(e.END_DATE)){

                areEqual = true;
            }
        */

        //One Employee is equal to another employee if they have the same EMPLID
        if (EMPLID.equals(e.EMPLID)) {
            areEqual = true;
        }

        return areEqual;
    }
    //-----------------------------------------------------------------------------------

    //==================================================================================
    // *** CONSTRUCTORS ***
    //-----------------------------------------------------------------------------------
    public Employee() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }
    
    public Employee(String EMPLID, String JOBCODE_NUMBER, String DEPTID){
        this(EMPLID, null, null, null, null, null, null, null,JOBCODE_NUMBER, DEPTID, null, null, null, null );
    }
    
    public Employee(String EMPLID, String FIRST_NAME, String LAST_NAME, String FIRST_NAME_EN, String LAST_NAME_EN,
                    String FATHER_NAME, String MOBILE, String STATUS, String JOBCODE_NUMBER, String DEPTID,
                    String MANAGER_ID, Timestamp HIRE_DATE, Timestamp END_DATE, String EMP_TYPE) {

        this.EMPLID = EMPLID;
        this.FIRST_NAME = FIRST_NAME;
        this.LAST_NAME = LAST_NAME;
        this.FIRST_NAME_EN = FIRST_NAME_EN;
        this.LAST_NAME_EN = LAST_NAME_EN;
        this.FATHER_NAME = FATHER_NAME;
        this.MOBILE = MOBILE;
        this.STATUS = STATUS;
        this.JOBCODE_NUMBER = JOBCODE_NUMBER;
        this.DEPTID = DEPTID;
        this.MANAGER_ID = MANAGER_ID;

        if (HIRE_DATE != null) {
            this.HIRE_DATE = HIRE_DATE.toLocalDateTime();
        } else {
            this.HIRE_DATE = null;
        }

        if (END_DATE != null) {
            this.END_DATE = END_DATE.toLocalDateTime();
        } else {
            this.END_DATE = null;
        }

        this.EMP_TYPE = EMP_TYPE;
    }

   
    //-----------------------------------------------------------------------------------


    //==================================================================================
    // *** GETTERS AND SETTERS ***
    //-----------------------------------------------------------------------------------
    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public void setFIRST_NAME(String FIRST_NAME) {
        this.FIRST_NAME = FIRST_NAME;
    }

    public String getLAST_NAME() {
        return LAST_NAME;
    }

    public void setLAST_NAME(String LAST_NAME) {
        this.LAST_NAME = LAST_NAME;
    }

    public String getFIRST_NAME_EN() {
        return FIRST_NAME_EN;
    }

    public void setFIRST_NAME_EN(String FIRST_NAME_EN) {
        this.FIRST_NAME_EN = FIRST_NAME_EN;
    }

    public String getLAST_NAME_EN() {
        return LAST_NAME_EN;
    }

    public void setLAST_NAME_EN(String LAST_NAME_EN) {
        this.LAST_NAME_EN = LAST_NAME_EN;
    }

    public String getFATHER_NAME() {
        return FATHER_NAME;
    }

    public void setFATHER_NAME(String FATHER_NAME) {
        this.FATHER_NAME = FATHER_NAME;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getJOBCODE_NUMBER() {
        return JOBCODE_NUMBER;
    }

    public void setJOBCODE_NUMBER(String JOBCODE_NUMBER) {
        this.JOBCODE_NUMBER = JOBCODE_NUMBER;
    }

    public String getDEPTID() {
        return DEPTID;
    }

    public void setDEPTID(String DEPTID) {
        this.DEPTID = DEPTID;
    }

    public String getMANAGER_ID() {
        return MANAGER_ID;
    }

    public void setMANAGER_ID(String MANAGER_ID) {
        this.MANAGER_ID = MANAGER_ID;
    }

    public LocalDateTime getHIRE_DATE() {
        return HIRE_DATE;
    }

    public void setHIRE_DATE(LocalDateTime HIRE_DATE) {
        this.HIRE_DATE = HIRE_DATE;
    }

    public LocalDateTime getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(LocalDateTime END_DATE) {
        this.END_DATE = END_DATE;
    }

    public String getEMP_TYPE() {
        return EMP_TYPE;
    }

    public void setEMP_TYPE(String EMP_TYPE) {
        this.EMP_TYPE = EMP_TYPE;
    }

    public String getEMPLID() {
        return EMPLID;
    }

    public void setEMPLID(String EMPLID) {
        this.EMPLID = EMPLID;
    }
    //-----------------------------------------------------------------------------------

}
