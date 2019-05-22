package com.example.mandooe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mandooe.model.Noticies_model;
import com.example.mandooe.model.Policies_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_notices1 extends AppCompatActivity {

    Button choose,submit, cancel;
    TextView notificate;
    int permission_number = 99;
    EditText title, description;
    TextView  todate;
    int mMonth,mDay,mYear;
    Uri fileUri;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    String noti_title, noti_description, noti_fromdate, noti_todate, noti_image;
    String currentDateandTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notices1);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar mToolbar =  findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_notices1.this,DashBoard_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        currentDateandTime = sdf.format(new Date());


        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        choose = findViewById(R.id.upload_notice_file);
        submit = findViewById(R.id.dia_submit_notice);
        cancel = findViewById(R.id.dia_cancel_notice);
        notificate = findViewById(R.id.filename);

        title = findViewById(R.id.edit_title_notice);
        description = findViewById(R.id.edit_decription_notice);
//        fromdate = findViewById(R.id.fromdatetext_notice);
        todate = findViewById(R.id.todatetext_notice);

        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_notices1.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // pick_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                todate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });



        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Add_notices1.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE )== PackageManager.PERMISSION_GRANTED){
                    SelectFile();
                } else {
                    ActivityCompat.requestPermissions(Add_notices1.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},permission_number);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileUri!= null){
                    uploadFile(fileUri);
                } else {
                    Toast.makeText(Add_notices1.this,"Fill the Fields ",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_notices1.this, Policies_Activity.class);
                startActivity(intent);
            }
        });
    }


    private void uploadFile(Uri fileUri) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("loading......");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = System.currentTimeMillis()+"";

        StorageReference storageReference = storage.getReference();
        storageReference.child("Uploads").child(fileName).putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                        DatabaseReference mRefer = database.getReference("Noticies");
                        Noticies_model noticies_model = new Noticies_model();

                        noti_title = title.getText().toString();
                        noticies_model.setTitle(noti_title);

                        noti_description = description.getText().toString();
                        noticies_model.setDescription(noti_description);

                        noti_fromdate = currentDateandTime;
                        noticies_model.setFromdate(noti_fromdate);

                        noti_todate = todate.getText().toString();
                        noticies_model.setTodate(noti_todate);

                        mRefer.push().setValue(noticies_model);

                        progressDialog.hide();

                        Toast.makeText(Add_notices1.this,"Successful",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Add_notices1.this,"Failed two ",Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress = (int)(100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }

    public  void onRequestPermissionResult(int requestCode,String[] permission, int[] grantsResults){
        if(requestCode == 9 && grantsResults [0] == PackageManager.PERMISSION_GRANTED){
            SelectFile();
        } else {
            Toast.makeText(Add_notices1.this,"You Please ",Toast.LENGTH_SHORT).show();
        }
    }

    private void SelectFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setType("images/*");
        intent.setType("video/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null){
            fileUri = data.getData();
            notificate.setText(data.getData().getLastPathSegment());
        } else {
            Toast.makeText(Add_notices1.this,"select file ",Toast.LENGTH_SHORT).show();
        }
    }
}
