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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q8rn.com.q8rn.R;
import q8rn.com.q8rn.entity.Entrevistado;
import q8rn.com.q8rn.infrastructure.Constants;

import static android.content.Context.MODE_PRIVATE;

/** Created by gabriel on 09/04/17. */

public class StepDadosBiologicosFragment extends Fragment implements BlockingStep {

    private static final String ENTREVISTADO = "entrevistado";


    @BindView(R.id.imcId) TextInputEditText mImc;
    @BindView(R.id.cinturaQuadrilId) TextInputEditText mCinturaQuadril;
    @BindView(R.id.pasId) TextInputEditText mPas;
    @BindView(R.id.glicemiaId) TextInputEditText mGlicemiaCapilar;
    @BindView(R.id.espirometriaId) TextInputEditText mEspirometria;
    @BindView(R.id.doencasId) TextInputEditText mDoencas;

    @BindView(R.id.cinturaId) TextInputEditText mCintura;
    @BindView(R.id.quadrilId) TextInputEditText mQuadril;
    @BindView(R.id.esforcoAntesId) TextInputEditText mEsforcoAntes;
    @BindView(R.id.esforcoDepoisId) TextInputEditText mEsforcoDepois;

    private Entrevistado entrevistado;

    private ProgressDialog dialog;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_dados_biologicos, container, false);

        unbinder = ButterKnife.bind(this, v);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Carregando");

        mCintura.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                setValorCampoRelacaoCinturaQuadril();
            }
        });

        mQuadril.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                setValorCampoRelacaoCinturaQuadril();
            }
        });

        return v;
    }

    private void setValorCampoRelacaoCinturaQuadril() {
        boolean campoCinturaNuloOuVazio = null == mCintura || mCintura.getText().toString().isEmpty();
        boolean campoQuadrilNuloOuVazio = null == mQuadril || mQuadril.getText().toString().isEmpty();
        if (!campoCinturaNuloOuVazio && !campoQuadrilNuloOuVazio) {
            double relacaoCinturaQuadril = calculaRCQ();
            mCinturaQuadril.setText(String.valueOf(relacaoCinturaQuadril));
        }
    }

    private double calculaRCQ() {
        return Double.valueOf(
                mCintura.getText().toString()) / Double.valueOf(mQuadril.getText().toString());
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {
        entrevistado = recuperaEntrevistadoShared();
        mImc.setText(String.valueOf(entrevistado.getIMC()));
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

        if (entrevistado == null) {
            entrevistado = new Entrevistado();
        }

        String cinturaQuadrilTexto = mCinturaQuadril.getText().toString();
        String espirometriaTexto = mEspirometria.getText().toString();
        String glicemiaTexto = mGlicemiaCapilar.getText().toString();

        String cinturaTexto = mCintura.getText().toString();
        String quadrilTexto = mQuadril.getText().toString();
        String esforcoAntesTexto = mEsforcoAntes.getText().toString();
        String esforcoDepoisTexto = mEsforcoDepois.getText().toString();

        boolean imcVazio = isVazioOuZerado(mImc);
        boolean cinturaQuadrilVazio = isVazioOuZerado(mCinturaQuadril);
        boolean pasVazio = isVazioOuZerado(mPas);
        boolean glicemiaVazio = isVazioOuZerado(mGlicemiaCapilar);
        boolean espirometriaVazio = isVazioOuZerado(mEspirometria);

        boolean cinturaVazio = isVazioOuZerado(mCintura);
        boolean quadrilVazio = isVazioOuZerado(mQuadril);
        boolean esforcoAntesVazio = isVazioOuZerado(mEsforcoAntes);
        boolean esforcoDepoisVazio = isVazioOuZerado(mEsforcoDepois);

        entrevistado.setImc(imcVazio ? 0 : Double.parseDouble(mImc.getText().toString()));
        entrevistado.setCinturaQuadril(cinturaQuadrilVazio ? 0 : Double.parseDouble(cinturaQuadrilTexto));
        entrevistado.setPas(pasVazio ? 0 : Double.parseDouble(mPas.getText().toString()));
        entrevistado.setGlicemiaCapilar(glicemiaVazio ? 0 : Double.parseDouble(glicemiaTexto));
        entrevistado.setEspirometria(espirometriaVazio ? 0 : Integer.parseInt(espirometriaTexto));

        entrevistado.setCintura(cinturaVazio ? 0 : Double.parseDouble(cinturaTexto));
        entrevistado.setQuadril(quadrilVazio ? 0 : Double.parseDouble(quadrilTexto));
        entrevistado.setTesteEsforcoAntes(esforcoAntesVazio ? 0 : Double.parseDouble(esforcoAntesTexto));
        entrevistado.setTesteEsforcoDepois(esforcoDepoisVazio ? 0 : Double.parseDouble(esforcoDepoisTexto));

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
