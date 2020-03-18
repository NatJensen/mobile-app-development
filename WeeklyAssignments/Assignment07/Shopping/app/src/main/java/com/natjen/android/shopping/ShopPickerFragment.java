package com.natjen.android.shopping;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

public class ShopPickerFragment extends DialogFragment {
    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog
                .Builder(getActivity())
                .setTitle(R.string.shop_picker_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
