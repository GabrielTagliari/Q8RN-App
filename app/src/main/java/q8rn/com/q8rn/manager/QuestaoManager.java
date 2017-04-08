package q8rn.com.q8rn.manager;

import android.content.Context;

import q8rn.com.q8rn.business.QuestaoBusiness;
import q8rn.com.q8rn.entity.Questao;

/** Created by gabriel on 07/04/17. */

public class QuestaoManager extends BaseManager {

    private QuestaoBusiness mQuestaoBusiness;

    public QuestaoManager(Context context) {
        super(context);

        mQuestaoBusiness = new QuestaoBusiness(context);
    }

    public String insereQuestao(long codQuestao, String titulo, String alternativa1,
                                String alternativa2, String alternativa3, String alternativa4,
                                String alternativa5, String dominio) {
        return mQuestaoBusiness.insereQuestao(codQuestao, titulo, alternativa1, alternativa2,
                alternativa3, alternativa4, alternativa5, dominio);
    }

    public Questao findQuestaoByCod(long codQuestao) {
        return mQuestaoBusiness.findQuestaoByCod(codQuestao);
    }

    public void deletaAllQuestoes() {
        mQuestaoBusiness.deletaAllQuestoes();
    }
}
