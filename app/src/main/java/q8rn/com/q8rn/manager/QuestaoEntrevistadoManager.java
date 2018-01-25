package q8rn.com.q8rn.manager;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.List;

import q8rn.com.q8rn.business.QuestaoEntrevistadoBusiness;
import q8rn.com.q8rn.entity.QuestaoEntrevistado;

/** Created by gabriel on 07/04/17. */

public class QuestaoEntrevistadoManager extends BaseManager {

    private QuestaoEntrevistadoBusiness mQEBusiness;

    public QuestaoEntrevistadoManager(Context context) {
        super(context);

        mQEBusiness = new QuestaoEntrevistadoBusiness(context);
    }

    public String insereQuestaoEntrevistado(int idEntrevistado, int idQuestao, int escore) {
        return mQEBusiness.insereQuestaoEntrevistado(idEntrevistado, idQuestao, escore);
    }

    public List<QuestaoEntrevistado> findAllQuestaoEntrevistadoByIdEntrevistado(long idEntrevistado) {
        return mQEBusiness.findAllQuestaoEntrevistadoByIdEntrevistado(idEntrevistado);
    }

    public File gerarExcel(Context context) throws IOException {
        return mQEBusiness.gerarExcel(context);
    }
}
