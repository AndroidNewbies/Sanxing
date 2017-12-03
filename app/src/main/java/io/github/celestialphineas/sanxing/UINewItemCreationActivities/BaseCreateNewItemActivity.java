package io.github.celestialphineas.sanxing.UINewItemCreationActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.celestialphineas.sanxing.R;

public class BaseCreateNewItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_create_new_item);
    }
}
