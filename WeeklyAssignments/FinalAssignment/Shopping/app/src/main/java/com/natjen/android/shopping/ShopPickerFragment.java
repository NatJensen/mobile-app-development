package com.natjen.android.shopping;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

public class ShopPickerFragment extends DialogFragment {

    public static final String EXTRA_SHOP = "com.natjen.android.shopping.shop.where";

    ListView mWhereList;

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_shop_picker, null);

        Resources res = getResources();
        final String[] shops = res.getStringArray(R.array.shops);

        mWhereList = view.findViewById(R.id.shop_picker_listView);

        return new AlertDialog
                .Builder(getActivity())
                .setView(view)
                .setTitle(R.string.shop_picker_title)
                .setItems(shops, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ui, null);
                        TextView mWhere;

                        mWhere = v.findViewById(R.id.whereItem_textView);

                        mWhere.setText(shops[which]);

                        sendResult(Activity.RESULT_OK, mWhere.toString());
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, String shop) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_SHOP, shop);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
