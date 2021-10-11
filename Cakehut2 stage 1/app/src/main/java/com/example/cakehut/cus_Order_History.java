package com.example.cakehut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class cus_Order_History extends AppCompatActivity implements OrderRVAdapter.OrderClickInterface {

    private RecyclerView cakerv;

    private FloatingActionButton floatingActionButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<CakeModel> cakeModelArrayList;
    private RelativeLayout bottomsheet;
    private OrderRVAdapter orderRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus__order__history);

        cakerv = findViewById(R.id.idrvOrders);

        floatingActionButton = findViewById(R.id.flotbtn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("cakes");
        cakeModelArrayList = new ArrayList<>();
        bottomsheet = findViewById(R.id.IdRlbottomsheeet);
        orderRVAdapter =new OrderRVAdapter(cakeModelArrayList,this,this);
        cakerv.setLayoutManager(new LinearLayoutManager(this));
        cakerv.setAdapter(orderRVAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cus_Order_History.this,cus_insert.class));
            }
        });

        getAllOrders();


    }

    private void getAllOrders() {

        cakeModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                cakeModelArrayList.add(snapshot.getValue(CakeModel.class));
                orderRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                orderRVAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {


                orderRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                orderRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onOrderClick(int position) {
        displaybottomsheet(cakeModelArrayList.get(position));


    }

    private  void displaybottomsheet(CakeModel cakeModel){

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottomsheet);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView flavourTV = layout.findViewById(R.id.idtvflavour);
        TextView requirementTV = layout.findViewById(R.id.idtvRequire);
        TextView addressTV = layout.findViewById(R.id.idtvadress);
        TextView dateTV = layout.findViewById(R.id.idTVdate);
        ImageView imageView = layout.findViewById(R.id.idIvOrder);
        Button editbtn = layout.findViewById(R.id.idBtnEdit);


        flavourTV.setText(cakeModel.getFalvour());
        requirementTV.setText(cakeModel.getRequirement());
        addressTV.setText(cakeModel.getAddress());
        dateTV.setText(cakeModel.getDate());
        Picasso.get().load(cakeModel.getImage()).into(imageView);


        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cus_Order_History.this,EditOrder.class);
                i.putExtra("cakes",cakeModel);
                startActivity(i);
            }
        });




    }
}