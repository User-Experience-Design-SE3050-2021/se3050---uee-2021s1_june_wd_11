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
import java.util.HashMap;
import java.util.Map;

public class EditOrder extends AppCompatActivity{


    private EditText weightTXT,flavourTXT,requireTXT,nameTXT,addressTXT,dateTXT,imgurlTXT;
    private Button updatebutton,deletebutton,backnav;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Spinner spinner,spinner2;
    String item,item2;
    String[] weights = {"choose weight","1kg","3kg","5kg","7kg"};
    String[] flavours ={"Choose flavour","chocolate","vanila","strawberry"};
    private DatePickerDialog picker;
    private String cid;
    private CakeModel cakeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        firebaseDatabase = FirebaseDatabase.getInstance();


        weightTXT = findViewById(R.id.weighttxt);
        flavourTXT = findViewById(R.id.flvrtxt);



        requireTXT = findViewById(R.id.requirtxt);
        nameTXT = findViewById(R.id.nametxt);
        addressTXT = findViewById(R.id.addrestxt);
        dateTXT = findViewById(R.id.date);
        imgurlTXT = findViewById(R.id.imgurltxt);
        updatebutton = findViewById(R.id.btnUpdate);
        backnav = findViewById(R.id.backNav);
        deletebutton = findViewById(R.id.btnDelete);
        cakeModel = getIntent().getParcelableExtra("cakes");

        backnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToHistory();
            }
        });

        if(cakeModel!=null){

            weightTXT.setText(cakeModel.getWeight());
            flavourTXT.setText(cakeModel.getFalvour());
           requireTXT.setText(cakeModel.getRequirement());
            nameTXT.setText(cakeModel.getName());
            addressTXT.setText(cakeModel.getAddress());
            dateTXT.setText(cakeModel.getDate());
            imgurlTXT.setText(cakeModel.getImage());
            cid = cakeModel.getCid();

        }


        databaseReference = firebaseDatabase.getReference("cakes").child(cid);

        dateTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(EditOrder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateTXT.setText(dayOfMonth + "/"+ (month +1)+"/"+year);
                    }
                },year,month,day);
                picker.show();
            }
        });


        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String weight = weightTXT.getText().toString();
                String flavour = flavourTXT.getText().toString();
                String requirement = requireTXT.getText().toString();
                String name = nameTXT.getText().toString();
                String address = addressTXT.getText().toString();
                String date = dateTXT.getText().toString();
                String image =imgurlTXT.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("weight",weight);
                map.put("falvour",flavour);
                map.put("requirement",requirement);
                map.put("name",name);
                map.put("address",address);
                map.put("date",date);
                map.put("image",image);
                map.put("cid",cid);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference.updateChildren(map);
                        Toast.makeText(EditOrder.this, "order updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditOrder.this,cus_Order_History.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditOrder.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteOrder();

            }
        });
    }

    public void GoToHistory() {
        Intent intent = new Intent(this,cus_Order_History.class);
        startActivity(intent);

    }

    private void  deleteOrder(){
        databaseReference.removeValue();
        Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditOrder.this,cus_Order_History.class));


    }


}