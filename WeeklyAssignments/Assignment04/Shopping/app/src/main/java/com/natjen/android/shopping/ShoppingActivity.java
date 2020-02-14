package com.natjen.android.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShoppingActivity extends AppCompatActivity {

    // GUI variables
    private Button mNewItem;
    private Button mListItems;
    private EditText mWhatItem;
    private EditText mWhereItem;

    // Model: Database of mItems
    private static ItemsDB itemsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        itemsDB = ItemsDB.get(this);

        mNewItem = findViewById(R.id.newItem_button);
        mListItems = findViewById(R.id.listItems_button);

        mWhatItem = findViewById(R.id.whatItem_editText);
        mWhereItem = findViewById(R.id.whereItem_editText);

        mNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String what = mWhatItem.getText().toString().trim();
                String where = mWhereItem.getText().toString().trim();

                if (!what.isEmpty() && !where.isEmpty()) {
                    itemsDB.addItem(what, where);
                    addedItemToast();
                    mWhatItem.setText("");
                    mWhereItem.setText("");
                } else {
                    inputItemToast();
                }
                hideKeyboardFrom(ShoppingActivity.this);
            }
        });

        mListItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ListActivity.newIntent(ShoppingActivity.this);
                startActivity(intent);
            }
        });
    }

    private void addedItemToast() {
        int messageResId = R.string.addedItem_toast;
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.show();
    }

    private void inputItemToast() {
        int messageResId = R.string.inputItem_toast;
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.show();
    }

    private void hideKeyboardFrom(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
