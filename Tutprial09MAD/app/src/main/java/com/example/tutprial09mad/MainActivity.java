package com.example.tutprial09mad;
;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Clock;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView mShowTime;

    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.text_view_contextual_menu);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (mActionMode != null){
                    return false;
                }
                mActionMode = MainActivity.this.startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });

        TextView favourites_text = findViewById(R.id.text_view_contextual_menu);
        registerForContextMenu(favourites_text);

        mShowTime = findViewById(R.id.show_time_text_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.options_settings:
                settings();
                return true;

            case R.id.option_02:
                option02();
                return true;

            case R.id.options_favourites:
                favourites();
                return true;

            case R.id.show_url:
                showURL();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual_actions,menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.edit :
                editContextual();
                return true;
            case R.id.share:
                shareContextual();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void option02() {

        Toast toast = Toast.makeText(getApplicationContext(),"I Selected : Options 02",Toast.LENGTH_LONG);
        toast.show();
    }

    private void settings() {

        Toast toast = Toast.makeText(getApplicationContext(),"I Selected : Settings",Toast.LENGTH_LONG);
        toast.show();
    }
    private void favourites() {

        Toast toast = Toast.makeText(getApplicationContext(),"I Selected : Favourites",Toast.LENGTH_LONG);
        toast.show();
    }

    private void showURL() {
        String url = "https://www.google.com/";
        Intent openUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        this.startActivity(openUrl);
    }


    private void shareContextual() {

        System.out.println("XXXXXXXXXXXXXXXXX");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"GGGG");
        intent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(intent,"null");
        startActivity(shareIntent);

    }

    private void editContextual() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setMessage("Edit choice2227227");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

    }

    public ActionMode.Callback mActionModeCallback = new ActionMode.Callback(){
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contextual_actions,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()){
                case R.id.edit :
                    editContextual();
                    mode.finish();
                    return true;

                case R.id.share :
                    System.out.println("SHARE");
                    shareContextual();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    public void showMenu(View view) {

        PopupMenu popupMenu = new PopupMenu(this,view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.image_view_actions,popupMenu.getMenu());
        popupMenu.show();
    }

    public void showDatePicker(View view) {

        StringBuilder date = new StringBuilder();

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay =  c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                date.append(year).append(" - ").append(month).append(" - ").append(dayOfMonth);
                Toast toast = Toast.makeText(getApplicationContext(),"Date is : " + date.toString() ,Toast.LENGTH_LONG);
                toast.show();
            }
        },mYear,mMonth,mDay);


        datePickerDialog.show();
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Ok",datePickerDialog);

    }


    public void showTImePicker(View view) {

        int mHour;
        int mMinute;

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        StringBuilder time = new StringBuilder();

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.append(hourOfDay).append(" : ").append(minute);
                Toast toast = Toast.makeText(getApplicationContext(),"Time is = " + time.toString() ,Toast.LENGTH_LONG);
                toast.show();
            }
        },mHour,mMinute,true);

        timePickerDialog.show();
    }

    public void setTextTime(View view) {

        int mHour;
        int mMinute;

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        StringBuilder time = new StringBuilder();

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.append(hourOfDay).append(" : ").append(minute);
                mShowTime.setText(time.toString());

            }
        },mHour,mMinute,true);

        timePickerDialog.show();
    }
}