package com.natjen.android.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShoppingActivity extends AppCompatActivity {

    // GUI variables
    private Button listItems;
    private TextView items;

    // Model: Database of items
    private ItemsDB itemsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsDB = new ItemsDB();
        itemsDB.fillItemsDB();

        items = (TextView) findViewById(R.id.items);

        listItems = (Button) findViewById(R.id.listItems_button);

        listItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.setBackgroundColor(Color.parseColor("#FFFFFF"));
                items.setText("Shopping List:" + itemsDB.listItems());
            }
        });
    }
}
