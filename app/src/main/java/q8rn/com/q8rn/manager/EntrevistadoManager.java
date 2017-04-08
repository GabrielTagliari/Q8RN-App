package q8rn.com.q8rn.manager;

import android.content.Context;

import java.util.List;

import q8rn.com.q8rn.business.EntrevistadoBusiness;
import q8rn.com.q8rn.entity.Entrevistado;

/** Created by gabriel on 07/04/17. */

public class EntrevistadoManager extends BaseManager {

    private EntrevistadoBusiness mEntrevistadoBusiness;

    public EntrevistadoManager(Context context) {
        super(context);

        mEntrevistadoBusiness = new EntrevistadoBusiness(context);
    }

    public String insereEntrevistado(Entrevistado entrevistado) {
        return mEntrevistadoBusiness.insereEntrevistado(entrevistado);
    }

    public List<Entrevistado> findAllEntrevistados() {
        return mEntrevistadoBusiness.findAllEntrevistados();
    }

    public int recuperaLastId() {
        return mEntrevistadoBusiness.recuperaLastId();
    }
}
