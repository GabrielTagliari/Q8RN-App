package q8rn.com.q8rn.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import q8rn.com.q8rn.R;

public class MainActivity extends AppCompatActivity {

    private Button botaoOnline;
    private Button botaoOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoOnline = (Button) findViewById(R.id.botaoOnlineId);
        botaoOffline = (Button) findViewById(R.id.botaoOfflineId);

        botaoOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        botaoOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuestionarioActivity.class);
                intent.putExtra("codQuestao", 1);
                startActivity(intent);
            }
        });
    }
}
