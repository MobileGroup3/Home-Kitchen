//package com.backendless.hk3.login.com.backendless.hk3.login.kitchen;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.backendless.Backendless;
//import com.backendless.exceptions.BackendlessFault;
//import com.backendless.hk3.login.HK3User;
//import com.backendless.hk3.login.R;
//
///**
// * Created by zini on 5/21/16.
// */
//public class CreateKitchen extends Activity{
//
//    private EditText nameField;
//    private EditText phoneField;
//    private EditText cityField;
//    private EditText streetField;
//    private EditText zipcodeField;
//    private Spinner foodCategory;
//    private Button uploadButton;
//    private Button createButton;
//
//    private String name;
//    private String phone;
//    private String city;
//    private String street;
//    private String zipCode;
//    private String category;
//
////    private HK3User user;
//    //private Kitchen kitchen;
//
//    public void onCreate( Bundle savedInstanceState ) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.create_kitchen);
//
//        nameField = (EditText) findViewById(R.id.kName);
//        phoneField = (EditText) findViewById(R.id.phone);
//        cityField = (EditText) findViewById(R.id.city);
//        streetField = (EditText) findViewById(R.id.street);
//        zipcodeField = (EditText) findViewById(R.id.zipcode);
//        foodCategory = (Spinner) findViewById(R.id.category);
//        uploadButton = (Button) findViewById(R.id.imageUpload);
//        createButton = (Button) findViewById(R.id.createKitchen);
//
//        createButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                String nameText=nameField.getText().toString().trim();
//                String phoneText=phoneField.getText().toString().trim();
//                String cityText=cityField.getText().toString().trim();
//                String streetText=streetField.getText().toString().trim();
//                String zipText=zipcodeField.getText().toString().trim();
//                String categoryText=foodCategory.getSelectedItem().toString();
//
//                if(nameText.isEmpty()){
//                    showToast("Field 'Name' cannot be empty ");
//                    return;
//                }
//
//                if(phoneText.isEmpty()){
//                    showToast("Field 'Phone' cannot be empty");
//                    return;
//                }
//
//                if (cityText.isEmpty()){
//                    showToast("Field 'City' cannot be empty");
//                }
//
//                if(streetText.isEmpty()){
//                    showToast("Field 'Street' cannot be empty");
//                }
//
//                if(zipText.isEmpty()){
//                    showToast("Field 'Zipcode' cannot be empty");
//                }
//
//                if (categoryText.isEmpty()){
//                    showToast("Field 'Category' cannot be empty");
//                }
//
////                kitchen=new Kitchen();
//                if (!nameText.isEmpty()){
//                    name=nameText;
//
//                }
//
//                if(!phoneText.isEmpty()){
//                    phone=phoneText;
//                }
//
//                if(!cityText.isEmpty()){
//                    city=cityText;
//                }
//
//                if(!streetText.isEmpty()){
//                    street=streetText;
//                }
//
//                if(!zipText.isEmpty()){
//                    zipCode=zipText;
//                }
//
//                if(!categoryText.isEmpty()){
//                    category=categoryText;
//                }
//
//
//
//            }
//
//        });
//
////        uploadButton.setOnClickListener(new View.OnClickListener() {
////            @Override {
////                public void onClick(View v){
////                    openGallery(SELECT_FILE1);
////                }
////            }
////        });
//    }
////
////    public void openGallery(){
////
////    }
////
//
//    private void showToast( String msg )
//    {
//        Toast.makeText( this, msg, Toast.LENGTH_SHORT ).show();
//    }
//}
//
//
