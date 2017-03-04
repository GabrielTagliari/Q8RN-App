package q8rn.com.q8rn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class FormActivity extends AppCompatActivity {

    private EditText iniciaisNome;
    private EditText idade;
    private RadioGroup radioGroupSexo;
    private Spinner corPeleSpinner;
    private EditText religiao;
    private EditText tempoReligiao;
    private EditText profissao;
    private Spinner escolaridadeSpinner;
    private EditText peso;
    private EditText altura;
    private EditText imc;
    private EditText cinturaQuadril;
    private EditText pressaoArterial;
    private EditText glicemiaCapilar;
    private EditText espirometria;
    private Spinner saudeFisica;
    private Spinner saudeMental;
    private EditText doencas;

    private Button botaoProximo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
    }
}
