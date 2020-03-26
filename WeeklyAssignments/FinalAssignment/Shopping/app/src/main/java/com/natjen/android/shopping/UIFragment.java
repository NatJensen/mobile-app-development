package com.natjen.android.shopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class UIFragment extends Fragment implements Observer {

    private static final String DIALOG_SHOP = "DialogShop";
    private static final int REQUEST_SHOP = 0;

    // GUI variables
    private EditText mWhatItem;
    private TextView mWhereItem;

    // Model: Database of mItems
    private static ItemsDB mItemsDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mItemsDB = ItemsDB.get(getActivity());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_ui, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.api_version) {
            Intent intent = VersionActivity.newIntent(getActivity());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ui, container, false);

        mWhatItem = view.findViewById(R.id.whatItem_editText);

        mWhereItem = view.findViewById(R.id.whereItem_textView);
        mWhereItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                ShopPickerFragment dialog = new ShopPickerFragment();
                dialog.setTargetFragment(UIFragment.this, REQUEST_SHOP);
                dialog.show(manager, DIALOG_SHOP);
            }
        });

        Button newItem = view.findViewById(R.id.newItem_button);
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String what = mWhatItem.getText().toString().trim();
                String where = mWhereItem.getText().toString().trim();

                if (!what.isEmpty() && !where.isEmpty()) {
                    mItemsDB.addItem(what, where);
                    mWhatItem.setText("");
                    mWhereItem.setText(R.string.whereItemShop_text);
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
        // Empty for now
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_SHOP) {
            String where = data.getStringExtra(ShopPickerFragment.EXTRA_SHOP);
            mWhereItem.setText(where);
        }
    }

    private static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}