package com.example.cakehut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class cus_insert extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText weightTXT,flavourTXT,requireTXT,nameTXT,addressTXT,dateTXT,imgurlTXT;
    private Button addbutton;
    private Spinner spinner,spinner2;
    String item,item2;
    String[] weights = {"Choose weight","1kg","3kg","5kg","7kg"};
    String[] flavours ={"Choose flavour","chocolate","vanila","strawberry"};
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private DatePickerDialog picker;

    private String cid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_insert);



        weightTXT = findViewById(R.id.weighttxt);
        flavourTXT = findViewById(R.id.flvrtxt);
         spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,weights);
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,flavours);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner2.setAdapter(arrayAdapter2);
        requireTXT = findViewById(R.id.requirtxt);
        nameTXT = findViewById(R.id.nametxt);
        addressTXT = findViewById(R.id.addrestxt);
       dateTXT = findViewById(R.id.date);
        imgurlTXT = findViewById(R.id.imgurltxt);
        addbutton = findViewById(R.id.btnsubmit);

      firebaseDatabase = FirebaseDatabase.getInstance();
      databaseReference = firebaseDatabase.getReference("cakes");

      dateTXT.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              final Calendar calendar = Calendar.getInstance();
              int day = calendar.get(Calendar.DAY_OF_MONTH);
              int month = calendar.get(Calendar.MONTH);
              int year = calendar.get(Calendar.YEAR);

              picker = new DatePickerDialog(cus_insert.this, new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                      dateTXT.setText(dayOfMonth + "/"+ (month +1)+"/"+year);
                  }
              },year,month,day);
              picker.show();
          }
      });



     addbutton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             String weight = weightTXT.getText().toString();
             String flavour = flavourTXT.getText().toString();
             String requirement = requireTXT.getText().toString();
             String name = nameTXT.getText().toString();
             String address = addressTXT.getText().toString();
             String date = dateTXT.getText().toString();
             String image =imgurlTXT.getText().toString();
             cid = name;
             CakeModel cakeModel = new CakeModel(weight,flavour,requirement,name,address,date,image,cid);

             databaseReference.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {

                     databaseReference.child(cid).setValue(cakeModel);
                     Toast.makeText(cus_insert.this, "order added", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(cus_insert.this,Cus_order_comfirmation.class));
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {
                     Toast.makeText(cus_insert.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();
                 }
             });

         }
     });
    }







    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        item = spinner.getSelectedItem().toString();
        weightTXT.setText(item);

        item2 = spinner2.getSelectedItem().toString();
        flavourTXT.setText(item2);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}