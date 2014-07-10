package pl.charmas.shoppinglist.base;

import android.app.Activity;
import android.os.Bundle;

import pl.charmas.shoppinglist.ProductListApp;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductListApp.get(this).inject(this);
    }
}
