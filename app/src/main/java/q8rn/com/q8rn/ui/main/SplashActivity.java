package q8rn.com.q8rn.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/* Created by Gabriel on 03/03/2017. */
public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();
    }
}
