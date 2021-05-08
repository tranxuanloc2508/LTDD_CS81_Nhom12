package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.musicapp.Model.Contants;
import com.example.musicapp.Model.UploadAlbum;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadAlbummActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_Choose;
    private Button btn_upLoad;
    private EditText edt_textName;
    private ImageView imageView;
    String songsCategory;
    StorageReference storageReference;
    private static final  int PICK_IMAGE_REQUEST = 234;
    private Uri fileFath;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_albumm);

        btn_Choose = findViewById(R.id.btn_choose);
        btn_upLoad = findViewById(R.id.btn_upload);
        edt_textName = findViewById(R.id.edt_text);
        imageView = findViewById(R.id.imageView);

        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Contants.DATABASE_PATH_UPLOADS);

        btn_upLoad.setOnClickListener(this);
        btn_Choose.setOnClickListener(this);

        Spinner spinner =findViewById(R.id.spiner);

        List<String> categories = new ArrayList<>();

        categories.add("Love Songs");
        categories.add("Sad Songs");
        categories.add("Party Songs");
        categories.add("BirthDay Songs");
        categories.add("God Songs");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                songsCategory = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(UploadAlbummActivity.this, "Selected : " +songsCategory, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == btn_Choose){
            showFileChoose();
        }else if(view == btn_upLoad)
        {
            uploadFile();
        }
    }

    private void uploadFile() {
        if(fileFath != null){
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("uploading....");
            progressDialog.show();
            final StorageReference sRef = storageReference.child(Contants.STORAGE_PATH_UPLOADS +
                    System.currentTimeMillis() + "." + getFileExtension(fileFath));
            sRef.putFile(fileFath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = uri.toString();
                            UploadAlbum upload = new UploadAlbum(edt_textName.getText().toString().trim(),songsCategory,url);
                            String upLoadId= mDatabase.push().getKey();
                            mDatabase.child(upLoadId).setValue(upload);
                            progressDialog.dismiss();
                            Toast.makeText(UploadAlbummActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UploadAlbummActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("uploaded " + (int)progress+ "%....");
                }
            });
        }
    }

    private void showFileChoose() {
        Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!= null&& data.getData()!=null){

            fileFath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),fileFath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getMimeTypeFromExtension(cr.getType(uri));
    }
}