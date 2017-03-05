package q8rn.com.q8rn.validators;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import q8rn.com.q8rn.R;

/**
 * Created by Gabriel on 05/03/2017.
 */

public class EntrevistadoValidator {
    private static final String CAMPO_OBRIGATORIO = "Campo obrigatório";
    public static final String SELECIONE_UMA_OPÇÃO = "Selecione uma opção";

    public boolean validate(List<EditText> listaEditText, RadioGroup radioGroupSexo,
                            RadioButton errorRadio, List<Spinner> listaSpinners) {
        boolean erroPreenchimento = false;
        errorRadio.setError(null);

        for (EditText editText : listaEditText) {
            if (!preenchido(editText)) {
                erroPreenchimento = true;
            }
        }

        for (Spinner spinner : listaSpinners) {
            if (!isSelecionado(spinner)) {
                erroPreenchimento = true;
            }
        }

        if (radioGroupSexo.getCheckedRadioButtonId() == -1) {
            errorRadio.setError(CAMPO_OBRIGATORIO);
            erroPreenchimento = true;
        }

        if (erroPreenchimento) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isSelecionado(Spinner spinner) {
        if (spinner.getSelectedItem().toString().trim().equals(SELECIONE_UMA_OPÇÃO)) {
            TextView errorText = (TextView) spinner.getSelectedView();
            errorText.setError(CAMPO_OBRIGATORIO);
            return false;
        }
        return true;
    }

    public boolean preenchido(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0) {
            editText.setError(CAMPO_OBRIGATORIO);
            return false;
        }
        return true;
    }
}
