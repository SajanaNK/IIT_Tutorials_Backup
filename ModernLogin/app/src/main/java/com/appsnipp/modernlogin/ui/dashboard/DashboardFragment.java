package com.appsnipp.modernlogin.ui.dashboard;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;

import com.appsnipp.modernlogin.R;
import com.appsnipp.modernlogin.ShowResults;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Button btn;
    private ImageView imageView;
    private TextView filePathTextView;

    private static final int REQUEST_CODE_PERMISSION = 1000;
    private static final int REQUEST_CODE_FILE_CHOOSER = 2000;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        dashboardViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        btn = root.findViewById(R.id.submit_audio_file_program_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShowResults.class);
                startActivity(intent);
            }
        });


        imageView = root.findViewById(R.id.imageView2);
        filePathTextView = root.findViewById(R.id.file_path_text_view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTheFileFromDevice();
            }
        });


        return root;
    }

    private void getTheFileFromDevice() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){

            int permisson = ActivityCompat.checkSelfPermission(this.getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permisson != PackageManager.PERMISSION_GRANTED){

                this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_PERMISSION);
                return;
            }

        }

        browseFile();

    }

    private void browseFile() {

        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");

        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);

        chooseFile = Intent.createChooser(chooseFile,"Choose File");
        startActivityForResult(chooseFile,REQUEST_CODE_FILE_CHOOSER);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_CODE_PERMISSION :{

                if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    System.out.println("PERMISSION GRANTED");
                    browseFile();
                }

                else {

                    System.out.println("PERMISSION DENIED");
                }
                break;

            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case REQUEST_CODE_FILE_CHOOSER :

                if (resultCode == Activity.RESULT_OK){

                    if (data != null){

                        Uri fileUri = data.getData();
                        System.out.println(fileUri);

                        String filePath = null;

                        filePath = fileUri.getPath();

                        filePathTextView.setText(filePath);

                    }
                }
                break;
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    public String getPath(){
        return filePathTextView.getText().toString();
    }

    //    public void submitFile(View view){
//
//        Intent intent = new Intent(getContext(), ShowResults.class);
//        startActivity(intent);
//
//    }

}