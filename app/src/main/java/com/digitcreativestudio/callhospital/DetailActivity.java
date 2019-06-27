package com.digitcreativestudio.callhospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    ImageView image;
    TextView name, region, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image = findViewById(R.id.photo_detail);
        name = findViewById(R.id.name_detail);
        region = findViewById(R.id.region_detail);
        email = findViewById(R.id.email_detail);
        phone = findViewById(R.id.phone_detail);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            name.setText(bundle.getString(MainActivity.NAME));
            Glide.with(getApplicationContext()).load(bundle.getString(MainActivity.PHOTO)).into(image);
            region.setText(bundle.getString(MainActivity.REGION));
            email.setText(bundle.getString(MainActivity.EMAIL));
            phone.setText(bundle.getString(MainActivity.PHONE));
        }
    }
}
