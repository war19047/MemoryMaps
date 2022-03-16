package com.example.memory_maps;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;


public class photoGallery extends AppCompatActivity {

    // Establish recycler view and create variables for array list.
    private static final int PERMISSION_REQUEST_CODE = 200;
    private ArrayList<String> imagePaths;
    private RecyclerView imagesRV;
    private RecyclerViewAdapter imageRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        //Request permission
        requestPermissions();

        // Create an array list.
        imagePaths = new ArrayList<>();

        // Initialize recycler view
        imagesRV = findViewById(R.id.idRVImages);

        // Method to prepare recycler view.
        prepareRecyclerView();

        // Add action bar to top of gallery with back arrow to map view.
        getSupportActionBar().setTitle("Photo Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private boolean checkPermission() {
        // Check if permissions are granted.
        int checkResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return checkResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (checkPermission()) {
            // If permissions are granted receive images from external storage.
            Toast.makeText(this, "Permissions granted..", Toast.LENGTH_SHORT).show();
            getImagePath();
        } else {
            // Get the permissions.
            requestPermission();
        }
    }

    private void requestPermission() {
        // Request rea external storage permission.
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    private void prepareRecyclerView() {

        // This method prepares recycler view.

        // Initialize adapter class.
        imageRVAdapter = new RecyclerViewAdapter(photoGallery.this, imagePaths);

        // Create a grid layout manager.
        GridLayoutManager manager = new GridLayoutManager(photoGallery.this, 4);

        // Set layout manager and adapter for recycler view.
        imagesRV.setLayoutManager(manager);
        imagesRV.setAdapter(imageRVAdapter);

    }

    private void getImagePath() {
        // Add image paths to the array list.

        // Check if there is an sd card present.
        boolean checkSdCard = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        if (checkSdCard) {


            // If sd card present create a new list.
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};

            // on below line we are creating a new
            // string to order our images by string.
            final String organize = MediaStore.Images.Media._ID;

            // Store all images from gallery in Cursor.
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, organize);

            // Get total image count.
            int count = cursor.getCount();


            // loop through and add file path into array list.
            for (int i = 0; i < count; i++) {

                // move cursor position.
                cursor.moveToPosition(i);

                // Get image filepath.
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

                //Add to array list.
                if (imagePaths != null){
                imagePaths.add(cursor.getString(columnIndex));
                }

            }

            if (imageRVAdapter != null){
                // Check if data has changed.
                imageRVAdapter.notifyDataSetChanged();
            }

            // Close cursor.
            cursor.close();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // Called after permissions have been granted.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // Check permission code.
            case PERMISSION_REQUEST_CODE:

                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        // If granted display Toast message saying permissions granted.
                        Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show();
                        // Get image path.
                        getImagePath();
                    } else {
                        // Permissions denied close the app and display message.
                        Toast.makeText(this, "Permissions denined, Permissions are required to use the app..", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onResume(){
        // onResume method resumes the previous view.

        super.onResume();

        // Check if there is an sd card present.
        boolean checkSdCard = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        if (checkSdCard) {


            // If sd card present create a new list.
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};

            // on below line we are creating a new
            // string to order our images by string.
            final String organize = MediaStore.Images.Media._ID;

            // Store all images from gallery in Cursor.
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, organize);

            // Get total image count.
            int count = cursor.getCount();


            // loop through and add file path into array list.
            for (int i = 0; i < count; i++) {

                // move cursor position.
                cursor.moveToPosition(i);

                // Get image filepath.
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

                // Add the filepath.
                imagePaths.add(cursor.getString(columnIndex));

            }

            // Check if data has changed.
            imageRVAdapter.notifyDataSetChanged();

            // Close cursor.
            cursor.close();
        }
    }

}