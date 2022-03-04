package com.example.databaseapplicationtutorial5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.provider.BaseColumns._ID;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_ADDRESS;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_AGE;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_ID;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_NAME;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_POSITION;
import static com.example.databaseapplicationtutorial5.Constant.TABLE_NAME;


public class AddEmployee extends AppCompatActivity {


    private static String[] FROM = {_ID, EMPLOYEE_ID, EMPLOYEE_NAME,EMPLOYEE_AGE,EMPLOYEE_ADDRESS, EMPLOYEE_POSITION};
    private EmployeeData employee;

    private EditText mEmployeeNameEditText;
    private EditText mEmployeeIdEditText;
    private EditText mEmployeeAddressEditText;
    private EditText mEmployeeAgeEditText;
    private EditText mEmployeePositionEditText;
    private Button mEmployeeAddButton;

    private String employeeName;
    private int employeeId;
    private String employeeAddress;
    private String employeePosition;
    private int employeeAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        employee = new EmployeeData(this);


        mEmployeeAddressEditText = findViewById(R.id.employee_address_edittext);
        mEmployeeAgeEditText = findViewById(R.id.employee_age_edittext);
        mEmployeeIdEditText = findViewById(R.id.employee_id_edittext);
        mEmployeeNameEditText = findViewById(R.id.employee_name_edittext);
        mEmployeePositionEditText = findViewById(R.id.employee_position_edittext);
        mEmployeeAddButton = findViewById(R.id.add_employee_button);

    }

    private void addEmployee(int employeeId, String employeeName,String employeeAddress, int employeeAge, String employeePosition){

        SQLiteDatabase db = employee.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EMPLOYEE_ID,employeeId);
        values.put(EMPLOYEE_NAME,employeeName);
        values.put(EMPLOYEE_ADDRESS,employeeAddress);
        values.put(EMPLOYEE_AGE,employeeAge);
        values.put(EMPLOYEE_POSITION,employeePosition);
        db.insertOrThrow(TABLE_NAME,null,values);

    }


    public void getValues(View view) {

        employeeName = mEmployeeNameEditText.getText().toString();
        employeeAge = Integer.parseInt(mEmployeeAgeEditText.getText().toString());
        employeeAddress = mEmployeeAddressEditText.getText().toString();
        employeeId = Integer.parseInt(mEmployeeIdEditText.getText().toString());
        employeePosition = mEmployeePositionEditText.getText().toString();

        addEmployee(employeeId,employeeName,employeeAddress,employeeAge,employeePosition);
        recreate();

        mEmployeeNameEditText.setText("");
        mEmployeePositionEditText.setText("");
        mEmployeeIdEditText.setText("");
        mEmployeeAgeEditText.setText("");
        mEmployeeAddressEditText.setText("");

    }
}