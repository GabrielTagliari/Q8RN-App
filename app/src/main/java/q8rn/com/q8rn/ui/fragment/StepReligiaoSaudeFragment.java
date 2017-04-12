package q8rn.com.q8rn.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.infrastructure.EntrevistadoValidator;

import static q8rn.com.q8rn.ui.fragment.StepDadosPessoaisFragment.FAVOR_PREENCHER_OS_CAMPOS_OBRIGATORIOS;

/** Created by gabriel on 09/04/17. */

public class StepReligiaoSaudeFragment extends Fragment implements Step {

    private TextInputEditText mReligiao;
    private TextInputEditText mTempoReligiao;

    private Spinner mSaudeFisicaSpinner;
    private Spinner mSaudeMentalSpinner;

    //private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_dados_religiao, container, false);

        mReligiao = (TextInputEditText) v.findViewById(R.id.religiaoId);
        mTempoReligiao = (TextInputEditText) v.findViewById(R.id.tempoReligiaoId);

        mSaudeFisicaSpinner = (Spinner) v.findViewById(R.id.saudeFisicaIdSpinner);
        mSaudeMentalSpinner = (Spinner) v.findViewById(R.id.saudeMentalIdSpinner);

        //dialog = new ProgressDialog(getActivity());

        populaTodosSpinners();

        return v;
    }

    private void populaTodosSpinners() {
        populaSpinner(R.array.saude_array, mSaudeFisicaSpinner);
        populaSpinner(R.array.saude_array, mSaudeMentalSpinner);
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
