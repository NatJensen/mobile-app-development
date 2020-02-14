package com.natjen.android.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    // GUI variables
    private Button mDeleteItem;
    private TextView mItems;
    private EditText mDeleteWhat;

    // Model: Database of mItems
    private static ItemsDB mItemsDB;

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, ListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mItemsDB = ItemsDB.get(this);

        mDeleteItem = findViewById(R.id.deleteItem_button);

        mDeleteWhat = findViewById(R.id.deleteItem_editText);

        mItems = findViewById(R.id.items);

        listItems();

        mDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String what = mDeleteWhat.getText().toString().trim();
                Item item = mItemsDB.getItem(what);

                if (mItemsDB.dbSize() != 0) {
                    if (!what.isEmpty()) {
                        if (item != null && item.getWhat().equals(what)) {
                            mItemsDB.deleteItem(item);
                            listItems();
                            mDeleteWhat.setText("");
                            itemDeletedToast();
                        } else {
                            noItemExistToast();
                        }
                    } else {
                        typeWhatToast();
                    }
                } else {
                    emptyListToast();
                    mDeleteWhat.setText("");
                }
                hideKeyboardFrom(ListActivity.this);
            }
        });
    }

    private void listItems() {
        mItems.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mItems.setText(mItemsDB.listItems());
    }

    private void itemDeletedToast() {
        int messageResId = R.string.deleteItem_toast;
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.show();
    }

    private void noItemExistToast() {
        int messageResId = R.string.noItem_toast;
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.show();
    }

    private void emptyListToast() {
        int messageResId = R.string.emptyList_toast;
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.show();
    }

    private void typeWhatToast() {
        int messageResId = R.string.typeWhat_toast;
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
