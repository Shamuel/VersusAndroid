package com.vs.versus.versus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ImageVersus extends AppCompatActivity {

    EditText Comment;
    ImageView addimage,addimage2;
    ImageButton VSImage,VSImage2,VSImage4,VSImage5;
    private static final int Gallery_Pick =1;
    private static final int Gallery_Pick2 =2;
    private static final int Gallery_Pick3 =3;
    private static final int Gallery_Pick4 =4;
    Uri imageUri;
    Uri imageUri2;
    Uri imageUri3;
    Uri imageUri4;
    Uri downloadURL;
    Button Save;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase,versusDBRef;
    StorageReference mStorage;

    String comment,current_userID;
    List<Uri> URLs = new ArrayList<Uri>();

    //Resize Images
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_versus);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mStorage = FirebaseStorage.getInstance().getReference();
        current_userID =mAuth.getCurrentUser().getUid();
        versusDBRef = FirebaseDatabase.getInstance().getReference().child("ImageVersus");


        Comment = (EditText) findViewById(R.id.comment);
        addimage = (ImageView) findViewById(R.id.addaddimage);
        addimage2 = (ImageView) findViewById(R.id.addaddimage2);
        VSImage = (ImageButton) findViewById(R.id.ImageButton);
        VSImage2 = (ImageButton) findViewById(R.id.ImageButton2);
        VSImage4 = (ImageButton) findViewById(R.id.ImageButton4);
        VSImage5 = (ImageButton) findViewById(R.id.ImageButton5);
        Save = (Button) findViewById(R.id.Save);

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addimage.setVisibility(View.INVISIBLE);
                VSImage4.setVisibility(View.VISIBLE);
                addimage2.setVisibility(View.VISIBLE);

            }
        });

        addimage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addimage2.setVisibility(View.INVISIBLE);
                VSImage5.setVisibility(View.VISIBLE);
            }
        });

        VSImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        VSImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery2();

            }
        });

        VSImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               OpenGallery3();
            }
        });

        VSImage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery4();

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveVersus();
            }
        });

    }

    private void SaveVersus() {

        comment = Comment.getText().toString();

        if (imageUri==null||imageUri2==null){
            Toast.makeText(this, "...Select a Image...", Toast.LENGTH_SHORT).show();
        }
        else if(imageUri!=null&&imageUri2!=null&&imageUri3==null&&imageUri4==null) {
            //Save2ImagesFirebase();
            List<Uri> uri = Arrays.asList(imageUri,imageUri2);
            storeMultipleImages(uri);
        }
        else if(imageUri!=null&&imageUri2!=null&&imageUri3!=null&&imageUri4==null){
            //Save3ImagesFirebase();
            List<Uri> uri2 = Arrays.asList(imageUri,imageUri2,imageUri3);
            storeMultipleImages(uri2);
        }
        else if(imageUri!=null&&imageUri2!=null&&imageUri3!=null&&imageUri4!=null){
            //Save4ImagesFirebase();
            List<Uri> uri3 = Arrays.asList(imageUri,imageUri2,imageUri3,imageUri4);
            storeMultipleImages(uri3);
        }
    }


    public void storeImage(Uri imageUri) {
        StorageReference filepath = mStorage.child("Versus Images").child(imageUri.getLastPathSegment());
        filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if (task.isSuccessful()){

                    downloadURL = task.getResult().getUploadSessionUri();

                    Toast.makeText(ImageVersus.this, "Versus Published", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(ImageVersus.this, "..Error..", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void GetInDB() {

        mDatabase.child(current_userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String username = dataSnapshot.child("Username").getValue().toString();
                    HashMap InfoMap = new HashMap<>();
                    InfoMap.put("username",username);
                    InfoMap.put("imageURL1",URLs.get(0).toString());
                    InfoMap.put("imageURL2",URLs.get(1).toString());
                    InfoMap.put("imageURL3",URLs.get(2).toString());
                    InfoMap.put("imageURL4",URLs.get(3).toString());

                    versusDBRef.child(current_userID).updateChildren(InfoMap);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void storeMultipleImages(List<Uri> imageUris) {
        for (Uri uri : imageUris) {
            storeImage(uri);
            URLs.add(downloadURL);
        }
        GetInDB();

    }




    //Functions to open gallery for each image view
    void OpenGallery(){

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallery_Pick);
    }

    void OpenGallery2(){

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallery_Pick2);
    }

    void OpenGallery3(){

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallery_Pick3);
    }

    void OpenGallery4(){

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallery_Pick4);
    }

    //This is what hapen when the image is selected
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){

            try {
                imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                selectedImage = getResizedBitmap(selectedImage, 450);// 400 is for example, replace with desired size

                VSImage.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            /*VSImage.setImageURI(imageUri);
            VSImage = getResizedBitmap(VSImage,100);*/

        }

        if(requestCode==Gallery_Pick2 && resultCode==RESULT_OK && data!=null){

            try {
                imageUri2 = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri2);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                selectedImage = getResizedBitmap(selectedImage, 450);// 400 is for example, replace with desired size

                VSImage2.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            /*imageUri2 = data.getData();
            VSImage2.setImageURI(imageUri2);*/

        }

        if(requestCode==Gallery_Pick3 && resultCode==RESULT_OK && data!=null){

            try {
                imageUri3 = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri3);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                selectedImage = getResizedBitmap(selectedImage, 450);// 400 is for example, replace with desired size

                VSImage4.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            /*imageUri3 = data.getData();
            VSImage4.setImageURI(imageUri3);*/

        }

        if(requestCode==Gallery_Pick4 && resultCode==RESULT_OK && data!=null){

            try {
                imageUri4 = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri4);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                selectedImage = getResizedBitmap(selectedImage, 450);// 400 is for example, replace with desired size

                VSImage5.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            /*imageUri4 = data.getData();
            VSImage5.setImageURI(imageUri4);*/

        }
    }
}


