package com.example.cakehut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Cus_order_comfirmation extends AppCompatActivity {

    private Button sendanother, orderhistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_order_comfirmation);

        sendanother = findViewById(R.id.sendanother);
        orderhistory = findViewById(R.id.orderHistory);

        sendanother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoInsert();
            }
        });

        orderhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoHistory();
            }
        });
    }

    public void GotoHistory() {

        Intent intent = new Intent(this,cus_Order_History.class);
        startActivity(intent);
    }

    public void GotoInsert() {
        Intent intent = new Intent(this,cus_insert.class);
        startActivity(intent);
    }
}