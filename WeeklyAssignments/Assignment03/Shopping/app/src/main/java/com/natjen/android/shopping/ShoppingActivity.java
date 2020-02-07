package com.natjen.android.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingActivity extends AppCompatActivity {

    // GUI variables
    private Button mNewItem;
    private Button mListItems;
    private TextView mItems;
    private EditText mWhatItem;
    private EditText mWhereItem;

    // Model: Database of mItems
    private ItemsDB mItemsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        mItemsDB = new ItemsDB();
        mItemsDB.fillItemsDB();

        mItems = (TextView) findViewById(R.id.items);

        mNewItem = (Button) findViewById(R.id.newItem_button);
        mListItems = (Button) findViewById(R.id.listItems_button);

        mWhatItem = (EditText) findViewById(R.id.whatItem_editText);
        mWhereItem = (EditText) findViewById(R.id.whereItem_editText);

        mNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String what = mWhatItem.getText().toString();
                String where = mWhereItem.getText().toString();

                if (what.isEmpty() || where.isEmpty()) {
                    checkInput(false);
                } else {
                    mItemsDB.addItem(what, where);
                    checkInput(true);
                }
                hideKeyboardFrom(ShoppingActivity.this);
            }
        });

        mListItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItems.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mItems.setText("Shopping List:" + mItemsDB.listItems());
            }
        });
    }

    private void checkInput(boolean userPressedNewItem) {
        int messageResId = 0;

        if (userPressedNewItem) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
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
