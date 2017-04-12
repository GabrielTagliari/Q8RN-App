package q8rn.com.q8rn.infrastructure;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import q8rn.com.q8rn.R;

/* Created by Gabriel on 05/03/2017. */
public class EntrevistadoValidator {

    private static final String CAMPO_OBRIGATORIO = "Campo obrigatório";
    private static final String SELECIONE_UMA_OPÇÃO = "Selecione uma opção";

    public boolean validate(List<TextInputEditText> listaEditText, RadioGroup radioGroupSexo,
                            RadioButton errorRadio, List<Spinner> listaSpinners) {
        boolean erroPreenchimento = false;

        if (errorRadio != null){
            errorRadio.setError(null);
        }

        if (listaEditText != null) {
            for (TextInputEditText editText : listaEditText) {
                if (!preenchido(editText)) {
                    erroPreenchimento = true;
                }
            }
        }

        if (listaSpinners != null) {
            for (Spinner spinner : listaSpinners) {
                if (!isSelecionado(spinner)) {
                    erroPreenchimento = true;
                }
            }
        }

        if (radioGroupSexo != null) {
            if (radioGroupSexo.getCheckedRadioButtonId() == -1) {
                errorRadio.setError(CAMPO_OBRIGATORIO);
                erroPreenchimento = true;
            }
        }

        return !erroPreenchimento;
    }

    private boolean isSelecionado(Spinner spinner) {
        if (spinner.getSelectedItem().toString().trim().equals(SELECIONE_UMA_OPÇÃO)) {
            TextView errorText = (TextView) spinner.getSelectedView();
            errorText.setError(CAMPO_OBRIGATORIO);
            return false;
        }
        return true;
    }

    private boolean preenchido(TextInputEditText editText) {

        String text = editText.getText().toString().trim();

        TextInputLayout parent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            parent = (TextInputLayout) editText.getParentForAccessibility();
            parent.setErrorEnabled(false);
            parent.setError(null);
        } else {
            editText.setError(null);
        }

        if (text.length() == 0) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                if (parent != null) {
                    parent.setError(CAMPO_OBRIGATORIO);
                }
            } else {
                editText.setError(CAMPO_OBRIGATORIO);
            }
            return false;
        }
        return true;
    }
}
