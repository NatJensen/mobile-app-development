package com.natjen.android.shopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class UIFragment extends Fragment implements Observer {

    // GUI variables
    private Button mNewItem;
    private Button mDeleteItem;
    private Button mListItems;
    private EditText mWhatItem;
    private EditText mWhereItem;

    // Model: Database of mItems
    private static ItemsDB mItemsDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemsDB = ItemsDB.get(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ui, container, false);

        mWhatItem = view.findViewById(R.id.whatItem_editText);
        mWhereItem = view.findViewById(R.id.whereItem_editText);

        mNewItem = view.findViewById(R.id.newItem_button);
        mNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String what = mWhatItem.getText().toString().trim();
                String where = mWhereItem.getText().toString().trim();

                if (!what.isEmpty() && !where.isEmpty()) {
                    mItemsDB.addItem(what, where);
                    addedItemToast();
                    mWhatItem.setText("");
                    mWhereItem.setText("");
                } else {
                    inputItemToast();
                }

                hideKeyboardFrom(getContext(), getView());
            }
        });

        mDeleteItem = view.findViewById(R.id.deleteItem_button);
        mDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String what = mWhatItem.getText().toString().trim();
                Item item = mItemsDB.getItem(what);

                if (mItemsDB.dbSize() != 0) {
                    if (!what.isEmpty()) {
                        if (item != null && item.getWhat().equals(what)) {
                            mItemsDB.deleteItem(what);
                            mWhatItem.setText("");
                            itemDeletedToast();
                        } else {
                            noItemExistToast();
                        }
                    } else {
                        typeWhatToast();
                    }
                } else {
                    emptyListToast();
                    mWhatItem.setText("");
                }

                hideKeyboardFrom(getContext(), getView());
            }
        });

        mListItems = view.findViewById(R.id.listItems_button);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mListItems.setEnabled(false);
        } else {
            mListItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ListActivity.newIntent(getActivity());
                    startActivity(intent);
                }
            });
        }

        return view;
    }

    @Override
    public void update(Observable o, Object arg) {
        mItemsDB.listItems();
    }

    private void itemDeletedToast() {
        int messageResId = R.string.deleteItem_toast;
        Toast toast = Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.show();
    }

    private void noItemExistToast() {
        int messageResId = R.string.noItem_toast;
        Toast toast = Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.show();
    }

    private void emptyListToast() {
        int messageResId = R.string.emptyList_toast;
        Toast toast = Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.show();
    }

    private void typeWhatToast() {
        int messageResId = R.string.typeWhat_toast;
        Toast toast = Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.show();
    }

    private void addedItemToast() {
        int messageResId = R.string.addedItem_toast;
        Toast toast = Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.show();
    }

    private void inputItemToast() {
        int messageResId = R.string.inputItem_toast;
        Toast toast = Toast.makeText(getActivity(), messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.show();
    }

    private static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
