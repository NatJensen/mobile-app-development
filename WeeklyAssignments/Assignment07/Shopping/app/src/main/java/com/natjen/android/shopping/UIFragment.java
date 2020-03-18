package com.natjen.android.shopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class UIFragment extends Fragment implements Observer {

    // GUI variables
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

        Button newItem = view.findViewById(R.id.newItem_button);
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String what = mWhatItem.getText().toString().trim();
                String where = mWhereItem.getText().toString().trim();

                if (!what.isEmpty() && !where.isEmpty()) {
                    mItemsDB.addItem(what, where);
                    mWhatItem.setText("");
                    mWhereItem.setText("");
                    Toast.makeText(getActivity(), R.string.addedItem_toast, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.inputItem_toast, Toast.LENGTH_SHORT).show();
                }

                hideKeyboardFrom(Objects.requireNonNull(getContext()), Objects.requireNonNull(getView()));
            }
        });

        Button listItems = view.findViewById(R.id.listItems_button);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            listItems.setEnabled(false);
        } else {
            listItems.setOnClickListener(new View.OnClickListener() {
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
        // Empty for now - Shopping v. 6
    }

    private static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
