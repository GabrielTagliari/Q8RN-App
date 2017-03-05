package q8rn.com.q8rn.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import q8rn.com.q8rn.R;

public class MainActivity extends AppCompatActivity {

    private Button botaoRealizaQuestionario;
    private Button botaoSimularId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRealizaQuestionario = (Button) findViewById(R.id.botaoMainId);
        botaoSimularId = (Button) findViewById(R.id.botaoSimularId);

        botaoRealizaQuestionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        botaoSimularId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuestionarioActivity.class);
                startActivity(intent);
            }
        });
    }
}
