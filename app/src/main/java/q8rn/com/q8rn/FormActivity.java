package q8rn.com.q8rn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
    private Spinner saudeFisicaSpinner;
    private Spinner saudeMentalSpinner;
    private EditText doencas;

    private Button botaoProximo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        instanciaElementosTela();
        populaTodosSpinners();

        radioGroupSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.masculinoId) {
                    Toast.makeText(FormActivity.this, "Masculino", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FormActivity.this, "Feminino", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void populaTodosSpinners() {
        populaSpinner(R.array.corpele_array, corPeleSpinner);
        populaSpinner(R.array.escolaridade_array, escolaridadeSpinner);
        populaSpinner(R.array.saude_array, saudeFisicaSpinner);
        populaSpinner(R.array.saude_array, saudeMentalSpinner);
    }

    private void populaSpinner(int array, Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void instanciaElementosTela() {
        iniciaisNome = (EditText) findViewById(R.id.iniciaisNomeId);
        idade = (EditText) findViewById(R.id.idadeId);
        religiao = (EditText) findViewById(R.id.religiaoId);
        tempoReligiao = (EditText) findViewById(R.id.tempoReligiaoId);
        profissao = (EditText) findViewById(R.id.profissaoId);
        peso = (EditText) findViewById(R.id.pesoId);
        altura = (EditText) findViewById(R.id.alturaId);
        imc = (EditText) findViewById(R.id.imcId);
        cinturaQuadril = (EditText) findViewById(R.id.cinturaQuadrilId);
        pressaoArterial = (EditText) findViewById(R.id.pressaoId);
        glicemiaCapilar = (EditText) findViewById(R.id.glicemiaId);
        espirometria = (EditText) findViewById(R.id.espirometriaId);
        doencas = (EditText) findViewById(R.id.doencasId);

        radioGroupSexo = (RadioGroup) findViewById(R.id.radioGroupSexoId);

        corPeleSpinner = (Spinner) findViewById(R.id.corPeleIdSpinner);
        escolaridadeSpinner = (Spinner) findViewById(R.id.escolaridadeIdSpinner);
        saudeFisicaSpinner = (Spinner) findViewById(R.id.saudeFisicaIdSpinner);
        saudeMentalSpinner = (Spinner) findViewById(R.id.saudeMentalIdSpinner);

        botaoProximo = (Button) findViewById(R.id.botaoProximoId);
    }
}
