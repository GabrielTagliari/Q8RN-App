package q8rn.com.q8rn.ui.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.ui.fragment.StepperAdapter;

public class FormStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    public static final String COD_QUESTAO = "codQuestao";

    public static final String ALERTA = "Alerta";
    public static final String QUER_REALMENTE_VOLTAR_AO_MENU = "Quer realmente voltar ao menu?";
    public static final String SIM = "Sim";
    public static final String NAO = "Não";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_stepper);

        ActionBar supportActionBar = this.getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.titulo_form);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }


        StepperLayout mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setListener(this);
        mStepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCompleted(View completeButton) {
        Intent intent = new Intent(FormStepperActivity.this, QuestionarioActivity.class);
        intent.putExtra(COD_QUESTAO, 1);
        startActivity(intent);
    }

    @Override
    public void onError(VerificationError verificationError) {
        //Toast.makeText(this, verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
        //Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReturn() {
        finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setTitle(ALERTA)
            .setMessage(QUER_REALMENTE_VOLTAR_AO_MENU)
            .setPositiveButton(SIM, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }

            })
            .setNegativeButton(NAO, null)
            .show();
    }
}
