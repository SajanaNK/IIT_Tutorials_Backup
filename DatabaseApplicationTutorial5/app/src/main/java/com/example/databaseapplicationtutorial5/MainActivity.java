package com.example.databaseapplicationtutorial5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.provider.BaseColumns._ID;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_ADDRESS;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_AGE;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_ID;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_NAME;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_POSITION;
import static com.example.databaseapplicationtutorial5.Constant.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private static String[] FROM = {_ID, EMPLOYEE_ID, EMPLOYEE_NAME,EMPLOYEE_AGE,EMPLOYEE_ADDRESS, EMPLOYEE_POSITION};
    private EmployeeData employee;


    private EditText mDeleteEmployeeEditText;

    private int deleteEmployeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDeleteEmployeeEditText = findViewById(R.id.id_edittext_delete);

        employee = new EmployeeData(this);

        try{

            Cursor cursor = getEvents();
            showEvent(cursor);

        }
        finally {
            employee.close();
        }
    }

    private Cursor getEvents(){

        SQLiteDatabase db = employee.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,FROM,null,null,null,null,null);
        return cursor;
    }

    private void showEvent(Cursor cursor){

        StringBuilder builder = new StringBuilder("Saved Employees: \n");

        while (cursor.moveToNext()){

            long id = cursor.getLong(0);
            int eid = cursor.getInt(1);
            String name = cursor.getString(2);
            String address = cursor.getString(3);
            int age = cursor.getInt(4);
            String position = cursor.getString(5);

            builder.append(id).append(": ");
            builder.append(eid).append(": ");
            builder.append(name).append(": ");
            builder.append(address).append(": ");
            builder.append(age).append(": ");
            builder.append(position).append("\n");


        }

        cursor.close();

        TextView text = (TextView) findViewById(R.id.text_view);
        text.setText(builder);
    }



    public void deleteRecord(View view) {

        deleteEmployeeId = Integer.parseInt(mDeleteEmployeeEditText.getText().toString());
        deleteEmployee(deleteEmployeeId);
        recreate();
    }

    private void deleteEmployee(int employeeId){
        SQLiteDatabase db = employee.getWritableDatabase();
        db.delete(TABLE_NAME,EMPLOYEE_ID + "=" + employeeId,null);
    }

    public void openAddEmployee(View view) {
        Intent intent = new Intent(this,AddEmployee.class);
        startActivity(intent);
    }


    public void editRecord(View view) {
        Intent intent = new Intent(this,EditEmployee.class);
        startActivity(intent);
    }
}