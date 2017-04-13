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
import android.widget.Toast;

import com.google.gson.Gson;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.entity.Entrevistado;
import q8rn.com.q8rn.infrastructure.Constants;

import static android.content.Context.MODE_PRIVATE;

/** Created by gabriel on 09/04/17. */

public class StepDadosBiologicosFragment extends Fragment implements BlockingStep {

    private static final String ENTREVISTADO = "entrevistado";

    private ProgressDialog dialog;
    private TextInputEditText mImc;
    private TextInputEditText mCinturaQuadril;
    private TextInputEditText mPas;
    private TextInputEditText mPad;
    private TextInputEditText mGlicemiaCapilar;
    private TextInputEditText mEspirometria;
    private TextInputEditText mDoencas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_dados_biologicos, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Carregando");

        mImc = (TextInputEditText) v.findViewById(R.id.imcId);
        mCinturaQuadril = (TextInputEditText) v.findViewById(R.id.cinturaQuadrilId);
        mPas = (TextInputEditText) v.findViewById(R.id.pasId);
        mPad = (TextInputEditText) v.findViewById(R.id.padId);
        mGlicemiaCapilar = (TextInputEditText) v.findViewById(R.id.glicemiaId);
        mEspirometria = (TextInputEditText) v.findViewById(R.id.espirometriaId);
        mDoencas = (TextInputEditText) v.findViewById(R.id.doencasId);

        return v;
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

        Entrevistado entrevistado = recuperaEntrevistadoShared();

        if (entrevistado == null) {
            entrevistado = new Entrevistado();
        }

        String cinturaTexto = mCinturaQuadril.getText().toString();
        String espirometriaTexto = mEspirometria.getText().toString();
        String glicemiaTexto = mGlicemiaCapilar.getText().toString();

        boolean imcVazio = isVazioOuZerado(mImc);
        boolean cinturaVazio = isVazioOuZerado(mCinturaQuadril);
        boolean pasVazio = isVazioOuZerado(mPas);
        boolean padVazio = isVazioOuZerado(mPad);
        boolean glicemiaVazio = isVazioOuZerado(mGlicemiaCapilar);
        boolean espirometriaVazio = isVazioOuZerado(mEspirometria);

        entrevistado.setImc(imcVazio ? 0 : Double.parseDouble(mImc.getText().toString()));
        entrevistado.setCinturaQuadril(cinturaVazio ? 0 : Double.parseDouble(cinturaTexto));
        entrevistado.setPas(pasVazio ? 0 : Double.parseDouble(mPas.getText().toString()));
        entrevistado.setPad(padVazio ? 0 : Double.parseDouble(mPad.getText().toString()));
        entrevistado.setGlicemiaCapilar(glicemiaVazio ? 0 : Double.parseDouble(glicemiaTexto));
        entrevistado.setEspirometria(espirometriaVazio ? 0 : Integer.parseInt(espirometriaTexto));
        entrevistado.setDoencas(mDoencas.getText().toString());

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

    private boolean isVazioOuZerado(TextInputEditText etText) {
        boolean valorVazio = etText.getText().toString().trim().length() <= 0;

        if (!valorVazio) {
            return Double.parseDouble(etText.getText().toString()) == 0;
        }

        return true;
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
    }
}
