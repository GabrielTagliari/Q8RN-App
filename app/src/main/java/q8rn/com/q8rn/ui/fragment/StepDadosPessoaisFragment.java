package q8rn.com.q8rn.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.ui.main.FormStepperActivity;

/** Created by gabriel on 09/04/17. */

public class StepDadosPessoaisFragment extends Fragment implements Step {

    private Spinner mCorPeleSpinner;
    private Spinner mEscolaridadeSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_dados_pessoais, container, false);

        mCorPeleSpinner = (Spinner) v.findViewById(R.id.corPeleIdSpinner);
        mEscolaridadeSpinner = (Spinner) v.findViewById(R.id.escolaridadeIdSpinner);

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

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }
}
