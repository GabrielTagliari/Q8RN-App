package q8rn.com.q8rn.ui.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q8rn.com.q8rn.R;
import q8rn.com.q8rn.entity.Entrevistado;
import q8rn.com.q8rn.infrastructure.Constants;
import q8rn.com.q8rn.infrastructure.EntrevistadoValidator;
import q8rn.com.q8rn.manager.EntrevistadoManager;

import static android.content.Context.MODE_PRIVATE;

/** Created by gabriel on 09/04/17. */

@SuppressWarnings("FieldCanBeLocal")
public class StepDadosPessoaisFragment extends Fragment implements BlockingStep {

    public static final String FAVOR_PREENCHER_OS_CAMPOS_OBRIGATORIOS = "Favor preencher os campos obrigatórios";
    private static final String ENTREVISTADO = "entrevistado";

    @BindView(R.id.iniciaisNomeId) TextInputEditText mIniciaisNome;
    @BindView(R.id.idadeId) TextInputEditText mIdade;
    @BindView(R.id.profissaoId) TextInputEditText mProfissao;
    @BindView(R.id.pesoId) TextInputEditText mPeso;
    @BindView(R.id.alturaId) TextInputEditText mAltura;

    @BindView(R.id.til_iniciais_nome) TextInputLayout mTilIniciaisNome;
    @BindView(R.id.til_idade) TextInputLayout mTilIdade;
    @BindView(R.id.til_profissao) TextInputLayout mTilProfissao;

    @BindView(R.id.radioGroupSexoId) RadioGroup mRadioGroupSexo;
    @BindView(R.id.femininoId) RadioButton mFemininoRadio;

    @BindView(R.id.corPeleIdSpinner) Spinner mCorPeleSpinner;
    @BindView(R.id.escolaridadeIdSpinner) Spinner mEscolaridadeSpinner;
    @BindView(R.id.estadoCivilIdSpinner) Spinner mEstadoCivilSpinner;
    @BindView(R.id.comQuemMoraIdSpinner) Spinner mComQuemMoraSpinner;

    @BindView(R.id.codIdentificacaoId) TextInputEditText mCodIdentificacao;

    private Unbinder unbinder;

    private View v;

    private ProgressDialog dialog;
    private EntrevistadoManager mEntrevistadoManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.form_dados_pessoais, container, false);

        unbinder = ButterKnife.bind(this, v);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Carregando");

        populaTodosSpinners();

        return v;
    }

    private void populaTodosSpinners() {
        populaSpinner(R.array.corpele_array, mCorPeleSpinner);
        populaSpinner(R.array.escolaridade_array, mEscolaridadeSpinner);
        populaSpinner(R.array.estado_civil_array, mEstadoCivilSpinner);
        populaSpinner(R.array.com_quem_mora_array, mComQuemMoraSpinner);
    }

    private void populaSpinner(int array, Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public VerificationError verifyStep() {
        List<TextInputEditText> camposText = new ArrayList<TextInputEditText>();
        camposText.add(mIniciaisNome);
        camposText.add(mIdade);
        camposText.add(mProfissao);
        camposText.add(mAltura);
        camposText.add(mPeso);

        EntrevistadoValidator validator = new EntrevistadoValidator();
        boolean permiteProsseguir = validator
                .validate(camposText, mRadioGroupSexo, mFemininoRadio, montaListaSpinners());

        if (permiteProsseguir) {
            return null;
        } else {
            return new VerificationError(FAVOR_PREENCHER_OS_CAMPOS_OBRIGATORIOS);
        }
    }

    private List<Spinner> montaListaSpinners() {
        List<Spinner> listaSpinner = new ArrayList<>();
        listaSpinner.add(mCorPeleSpinner);
        listaSpinner.add(mEscolaridadeSpinner);
        listaSpinner.add(mEstadoCivilSpinner);
        listaSpinner.add(mComQuemMoraSpinner);
        return listaSpinner;
    }

    private void salvarEntrevistadoSharedPreferences(Entrevistado entrevistado) {
        SharedPreferences.Editor editor;
        editor = getActivity().getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(entrevistado);
        editor.putString(ENTREVISTADO, json);
        editor.apply();
    }

    private Entrevistado recuperaEntrevistadoShared() {
        SharedPreferences preferences;
        preferences = getActivity().getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(ENTREVISTADO, "");
        return gson.fromJson(json, Entrevistado.class);
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
    }

    @Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        dialog.show();

        Entrevistado entrevistado = recuperaEntrevistadoShared();

        if (entrevistado == null) {
            entrevistado = new Entrevistado();
        }

        RadioButton rb = (RadioButton) v.findViewById(mRadioGroupSexo.getCheckedRadioButtonId());
        int codEscolaridade = entrevistado
                .getCodEscolaridade(mEscolaridadeSpinner.getSelectedItem().toString());

        String alturaFormatada = mAltura.getText().toString().replace(",", ".");
        String pesoFormatado = mPeso.getText().toString().replace(",", ".");

        entrevistado.setIniciaisNome(mIniciaisNome.getText().toString());
        entrevistado.setIdade(Integer.parseInt(mIdade.getText().toString()));
        entrevistado.setSexo(rb.getText().toString());
        entrevistado.setCorPele(mCorPeleSpinner.getSelectedItem().toString());
        entrevistado.setProfissao(mProfissao.getText().toString());
        entrevistado.setEscolaridade(codEscolaridade);
        entrevistado.setAltura(Double.parseDouble(alturaFormatada));
        entrevistado.setPeso(Double.parseDouble(pesoFormatado));
        entrevistado.setCodIdentificacao(mCodIdentificacao.getText().toString());
        entrevistado.setEstadoCivil(mEstadoCivilSpinner.getSelectedItem().toString());
        entrevistado.setComQuemMora(mComQuemMoraSpinner.getSelectedItem().toString());

        salvarEntrevistadoSharedPreferences(entrevistado);

        Log.i("biologicos", entrevistado.toString());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                callback.goToNextStep();
            }
        }, 2000L);
    }

    @Override
    @UiThread
    public void onCompleteClicked(final StepperLayout.OnCompleteClickedCallback callback) {
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                callback.complete();
            }
        }, 2000L);
    }

    @Override
    @UiThread
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        //Toast.makeText(this.getContext(), "Your custom back action. Here you should cancel currently running operations", Toast.LENGTH_SHORT).show();
        callback.goToPrevStep();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
