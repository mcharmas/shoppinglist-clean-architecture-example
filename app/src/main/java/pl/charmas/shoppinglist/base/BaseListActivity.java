package pl.charmas.shoppinglist.base;

import android.app.ListActivity;
import android.os.Bundle;

import pl.charmas.shoppinglist.ProductListApp;

public class BaseListActivity extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductListApp.get(this).inject(this);
    }
}
