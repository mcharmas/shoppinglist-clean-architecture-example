package pl.charmas.shoppinglist.ui.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.mcharmas.myapplication.R;

import java.util.List;

import pl.charmas.shoppinglist.model.ProductViewModel;

public class ProductListAdapter extends ArrayAdapter<ProductViewModel> {
    private final OnProductStatusChangedListener listener;

    public ProductListAdapter(Context context, List<ProductViewModel> objects, OnProductStatusChangedListener listener) {
        super(context, R.layout.view_product_item, objects);
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_product_item, parent, false);
        }

        final ProductViewModel viewModel = getItem(position);
        ((TextView) view.findViewById(R.id.product_name_view)).setText(viewModel.getName());
        CheckBox boughtCheckBox = (CheckBox) view.findViewById(R.id.product_is_bough_checkbox);
        boughtCheckBox.setOnCheckedChangeListener(null);
        boughtCheckBox.setChecked(viewModel.isBought());
        boughtCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onProductStatusChanged(viewModel.getId(), isChecked);
            }
        });

        return view;
    }

    public interface OnProductStatusChangedListener {
        void onProductStatusChanged(long productId, boolean isBought);
    }
}
