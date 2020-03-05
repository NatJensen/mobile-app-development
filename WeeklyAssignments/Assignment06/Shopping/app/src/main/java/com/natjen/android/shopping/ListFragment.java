package com.natjen.android.shopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ListFragment extends Fragment implements Observer {

    // Model: Database of mItems
    private static ItemsDB mItemsDB;
    private RecyclerView itemList;
    private ItemAdapter mAdapter;

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

        itemList = view.findViewById(R.id.list_recycler_view);
        itemList.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    public void update(Observable observable, Object data) {
        mAdapter.notifyDataSetChanged();
    }

    private void updateUI() {
        List<Item> items = mItemsDB.getItems();

        mAdapter = new ItemAdapter(items);
        itemList.setAdapter(mAdapter);
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mWhatTextView, mWhereTextView, mNoView;

        ItemHolder(View itemView) {
            super(itemView);
            mNoView = itemView.findViewById(R.id.item_no);
            mWhatTextView = itemView.findViewById(R.id.item_what);
            mWhereTextView = itemView.findViewById(R.id.item_where);
            itemView.setOnClickListener(this);
        }

        void bind(Item item, int position) {
            mNoView.setText(getString(R.string.space, position));
            mWhatTextView.setText(item.getWhat());
            mWhereTextView.setText(item.getWhere());
        }

        @Override
        public void onClick(View v) {
            String what = mWhatTextView.getText().toString().trim();
            Item item = mItemsDB.getItem(what);

            if (item != null && item.getWhat().equals(what)) {
                mItemsDB.deleteItem(what);
                Toast.makeText(getActivity(), R.string.deleteItem_toast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        private List<Item> mItems;

        ItemAdapter(List<Item> data) {
            mItems = data;
        }

        @NotNull
        @Override
        public ItemHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.one_row, parent, false);

            return new ItemHolder(v);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            final Item item = mItems.get(position);

            holder.bind(item, position);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }
}