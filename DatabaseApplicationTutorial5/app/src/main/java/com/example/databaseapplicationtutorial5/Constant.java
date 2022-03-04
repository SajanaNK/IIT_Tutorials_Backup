package com.example.databaseapplicationtutorial5;

import android.provider.BaseColumns;

public interface Constant extends BaseColumns {

    public static final String TABLE_NAME = "Employees";

    //Columns :
    public static final String EMPLOYEE_ID = "EmployeeID";
    public static final String EMPLOYEE_NAME = "Name";
    public static final String EMPLOYEE_ADDRESS = "Address";
    public static final String EMPLOYEE_AGE = "Age";
    public static final String EMPLOYEE_POSITION = "Position";

}
