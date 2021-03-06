package q8rn.com.q8rn.ui.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
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

import static android.content.Context.MODE_PRIVATE;
import static q8rn.com.q8rn.ui.fragment.StepDadosPessoaisFragment.FAVOR_PREENCHER_OS_CAMPOS_OBRIGATORIOS;

/** Created by gabriel on 09/04/17. */

public class StepReligiaoSaudeFragment extends Fragment implements BlockingStep {

    private static final String ENTREVISTADO = "entrevistado";

    @BindView(R.id.religiaoId) TextInputEditText mReligiao;
    @BindView(R.id.tempoReligiaoId) TextInputEditText mTempoReligiao;
    @BindView(R.id.saudeFisicaIdSpinner) Spinner mSaudeFisicaSpinner;
    @BindView(R.id.saudeMentalIdSpinner) Spinner mSaudeMentalSpinner;

    @BindView(R.id.qualidadeVidaSpinnerId) Spinner mQualidadeVidaSpinner;
    @BindView(R.id.oQueMelhorarId) TextInputEditText mOQueMelhorar;

    private ProgressDialog dialog;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_dados_religiao, container, false);

        unbinder = ButterKnife.bind(this, v);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Carregando questionário");

        populaTodosSpinners();

        return v;
    }

    private void populaTodosSpinners() {
        populaSpinner(R.array.saude_array, mSaudeFisicaSpinner);
        populaSpinner(R.array.saude_array, mSaudeMentalSpinner);
        populaSpinner(R.array.saude_array, mQualidadeVidaSpinner);
    }

    private void populaSpinner(int array, Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public VerificationError verifyStep() {
        List<TextInputEditText> camposText = new ArrayList<TextInputEditText>();
        camposText.add(mReligiao);
        camposText.add(mTempoReligiao);

        EntrevistadoValidator validator = new EntrevistadoValidator();
        boolean permiteProsseguir = validator
                .validate(camposText, null, null, montaListaSpinners());

        if (permiteProsseguir) {
            return null;
        } else {
            return new VerificationError(FAVOR_PREENCHER_OS_CAMPOS_OBRIGATORIOS);
        }
    }

    private List<Spinner> montaListaSpinners() {
        List<Spinner> listaSpinner = new ArrayList<>();
        listaSpinner.add(mSaudeFisicaSpinner);
        listaSpinner.add(mSaudeMentalSpinner);
        listaSpinner.add(mQualidadeVidaSpinner);
        return listaSpinner;
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
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

        Entrevistado entrevistado = recuperaEntrevistadoShared();

        if (entrevistado == null) {
            entrevistado = new Entrevistado();
        }

        entrevistado.setReligiao(mReligiao.getText().toString());
        entrevistado.setTempoReligiao(Integer.parseInt(mTempoReligiao.getText().toString()));
        entrevistado.setSaudeFisica(mSaudeFisicaSpinner.getSelectedItem().toString());
        entrevistado.setSaudeMental(mSaudeMentalSpinner.getSelectedItem().toString());
        entrevistado.setQualidadeVida(mQualidadeVidaSpinner.getSelectedItem().toString());
        entrevistado.setoQueMelhorar(mOQueMelhorar.getText().toString());

        salvarEntrevistadoSharedPreferences(entrevistado);

        Log.i("saude", entrevistado.toString());

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
