package com.example.databaseapplicationtutorial5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_ADDRESS;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_AGE;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_ID;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_NAME;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_POSITION;
import static com.example.databaseapplicationtutorial5.Constant.TABLE_NAME;

public class EditEmployee extends AppCompatActivity {

    private EditText mEmployeeIdEditText;

    private EditText mEmployeeNameEditText;
    private TextView mEmployeeNameTextView;

    private EditText mEmployeeAddressEditText;
    private TextView mEmployeeAddressTextView;

    private EditText mEmployeeAgeEditText;
    private TextView mEmployeeAgeTextView;

    private EditText mEmployeePositionEditText;
    private TextView mEmployeePositionTextView;

    private String employeeName;
    private int employeeId;
    private String employeeAddress;
    private String employeePosition;
    private int employeeAge;

    private EmployeeData employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        mEmployeeIdEditText = findViewById(R.id.check_id_update_textview);
        mEmployeeNameEditText = findViewById(R.id.name_update_textview);
        mEmployeeNameTextView = findViewById(R.id.name_update_label);
        mEmployeeAddressEditText = findViewById(R.id.address_update_edittext);
        mEmployeeAddressTextView = findViewById(R.id.address_update_label);
        mEmployeeAgeEditText = findViewById(R.id.age_update_edittext);
        mEmployeeAgeTextView = findViewById(R.id.age_update_label);
        mEmployeePositionEditText = findViewById(R.id.possition_update_edittext);
        mEmployeePositionTextView = findViewById(R.id.possition_update_label);

        employee = new EmployeeData(this);
    }


    public void updateRecords(View view) {

        employeeName = mEmployeeNameEditText.getText().toString();
        employeeAge = Integer.parseInt(mEmployeeAgeEditText.getText().toString());
        employeeAddress = mEmployeeAddressEditText.getText().toString();
        employeeId = Integer.parseInt(mEmployeeIdEditText.getText().toString());
        employeePosition = mEmployeePositionEditText.getText().toString();

        update(employeeId,employeeName,employeeAge,employeeAddress,employeePosition);
        recreate();

        mEmployeeNameEditText.setText("");
        mEmployeePositionEditText.setText("");
        mEmployeeIdEditText.setText("");
        mEmployeeAgeEditText.setText("");
        mEmployeeAddressEditText.setText("");
    }

    public void update(int employeeId,String employeeName,int employeeAge,String employeeAddress,String employeePosition){

        SQLiteDatabase db = employee.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EMPLOYEE_ID,employeeId);
        values.put(EMPLOYEE_NAME,employeeName);
        values.put(EMPLOYEE_ADDRESS,employeeAddress);
        values.put(EMPLOYEE_AGE,employeeAge);
        values.put(EMPLOYEE_POSITION,employeePosition);

        String[] where = {String.valueOf(employeeId)};
        db.update(TABLE_NAME,values," EmployeeID = ?",where);

    }

}