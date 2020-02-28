package com.natjen.android.shopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class ListFragment extends Fragment implements Observer {

    // GUI variables
    private TextView mItems;

    // Model: Database of mItems
    private static ItemsDB mItemsDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemsDB = ItemsDB.get(getActivity());
        mItemsDB.addObserver(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mItems = view.findViewById(R.id.items);

        listItems();

        return view;
    }

    public void update(Observable observable, Object data) {
        mItems.setText(mItemsDB.listItems());
    }

    private void listItems() {
        mItems.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mItems.setText(mItemsDB.listItems());
    }
}