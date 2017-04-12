package q8rn.com.q8rn.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.infrastructure.EntrevistadoValidator;

/** Created by gabriel on 09/04/17. */

@SuppressWarnings("FieldCanBeLocal")
public class StepDadosPessoaisFragment extends Fragment implements Step {

    public static final String FAVOR_PREENCHER_OS_CAMPOS_OBRIGATORIOS = "Favor preencher os campos obrigatórios";
    private TextInputEditText mIniciaisNome;
    private TextInputEditText mIdade;
    private TextInputEditText mProfissao;
    private TextInputEditText mPeso;
    private TextInputEditText mAltura;

    private TextInputLayout mTilIniciaisNome;
    private TextInputLayout mTilIdade;
    private TextInputLayout mTilProfissao;

    private RadioGroup mRadioGroupSexo;
    private RadioButton mFemininoRadio;

    private Spinner mCorPeleSpinner;
    private Spinner mEscolaridadeSpinner;

    private View v;

    //private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.form_dados_pessoais, container, false);

        mCorPeleSpinner = (Spinner) v.findViewById(R.id.corPeleIdSpinner);
        mEscolaridadeSpinner = (Spinner) v.findViewById(R.id.escolaridadeIdSpinner);

        //dialog = new ProgressDialog(getActivity());

        instanciaElementosTela();
        populaTodosSpinners();

        return v;
    }

    private void populaTodosSpinners() {
        populaSpinner(R.array.corpele_array, mCorPeleSpinner);
        populaSpinner(R.array.escolaridade_array, mEscolaridadeSpinner);
    }

    private void populaSpinner(int array, Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void instanciaElementosTela() {
        mTilIniciaisNome = (TextInputLayout) v.findViewById(R.id.til_iniciais_nome);
        mTilIdade = (TextInputLayout) v.findViewById(R.id.til_idade);
        mTilProfissao = (TextInputLayout) v.findViewById(R.id.til_profissao);

        mIniciaisNome = (TextInputEditText) v.findViewById(R.id.iniciaisNomeId);
        mIdade = (TextInputEditText) v.findViewById(R.id.idadeId);
        mProfissao = (TextInputEditText) v.findViewById(R.id.profissaoId);
        mPeso = (TextInputEditText) v.findViewById(R.id.pesoId);
        mAltura = (TextInputEditText) v.findViewById(R.id.alturaId);

        mRadioGroupSexo = (RadioGroup) v.findViewById(R.id.radioGroupSexoId);
        mFemininoRadio = (RadioButton) v.findViewById(R.id.femininoId);

        mCorPeleSpinner = (Spinner) v.findViewById(R.id.corPeleIdSpinner);
        mEscolaridadeSpinner = (Spinner) v.findViewById(R.id.escolaridadeIdSpinner);
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
        return listaSpinner;
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
    }

    /*@Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        dialog.show();
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
        Toast.makeText(this.getContext(), "Your custom back action. Here you should cancel currently running operations", Toast.LENGTH_SHORT).show();
        callback.goToPrevStep();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialog != null) {
            dialog.dismiss();
        }
    }*/
}
