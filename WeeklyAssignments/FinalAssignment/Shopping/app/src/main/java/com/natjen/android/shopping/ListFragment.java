package com.natjen.android.shopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ListFragment extends Fragment implements Observer {

    // Model: Database of mItems
    private static ItemsDB mItemsDB;
    private RecyclerView itemList;
    private ItemAdapter mAdapter;

    private NetworkDB netDB;

    private class FetchJSONTask extends AsyncTask<String, Void, ArrayList<Item>> {
        @Override
        protected ArrayList<Item> doInBackground(String... params) {
            ArrayList<Item> litems = netDB.getItemListFromServer(params[0]);
            return litems;
        }
    }

    private class SendTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            netDB.send(params[0]);
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        netDB = new NetworkDB();
        FetchJSONTask fetchJSON = new FetchJSONTask();
        fetchJSON.execute(netDB.Shopping_URL + "&op=list");
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
        updateUI();
    }

    private void updateUI() {
        List<Item> items = mItemsDB.getItems();

        if (mAdapter == null) {
            mAdapter = new ItemAdapter(items);
            itemList.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
            mAdapter.setItems(items);
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView mNoView;
        private TextView mWhatTextView;
        private TextView mWhereTextView;

        private Item mItem;

        ItemHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.sync_image);
            //mNoView = itemView.findViewById(R.id.item_no);
            mWhatTextView = itemView.findViewById(R.id.item_what);
            mWhereTextView = itemView.findViewById(R.id.item_where);
            itemView.setOnClickListener(this);
        }

        void bind(Item item, int position) {
            mItem = item;
            //mNoView.setText(getString(R.string.space, position));
            mWhatTextView.setText(mItem.getWhat());
            mWhereTextView.setText(mItem.getWhere());
        }

        @Override
        public void onClick(View v) {
            mItemsDB.deleteItem(mItem);
            SendTask sendURL = new SendTask();
            sendURL.execute(netDB.Shopping_URL +
                    "&op=delete&what=" + mWhatTextView.getText().toString().trim());
            Toast.makeText(getActivity(), R.string.deleteItem_toast, Toast.LENGTH_SHORT).show();
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        private List<Item> mItems;

        ItemAdapter(List<Item> items) {
            mItems = items;
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

        public void setItems(List<Item> items) {
            mItems = items;
        }
    }
}