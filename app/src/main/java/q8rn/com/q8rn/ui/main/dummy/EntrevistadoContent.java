package q8rn.com.q8rn.ui.main.dummy;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import q8rn.com.q8rn.entity.Entrevistado;
import q8rn.com.q8rn.infrastructure.Constants;
import q8rn.com.q8rn.manager.EntrevistadoManager;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class EntrevistadoContent {

    public static List<EntrevistadoItem> ITEMS = new ArrayList<>();

    public static Map<String, EntrevistadoItem> ITEM_MAP = new HashMap<>();

    public static void populaHistorico(Context context) {

        ITEMS = new ArrayList<>();
        ITEM_MAP = new HashMap<>();

        EntrevistadoManager em = new EntrevistadoManager(context);

        List<Entrevistado> mEntrevistados = em.findAllEntrevistados();

        if (mEntrevistados != null) {
            for (Entrevistado e : mEntrevistados) {
                addItem(createDummyItem(e));
            }
        }
    }

    private static void addItem(EntrevistadoItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static EntrevistadoItem createDummyItem(Entrevistado e) {
        return new EntrevistadoItem(String.valueOf(e.getId()), e.getIniciaisNome(), makeDetails(e));
    }

    private static String makeDetails(Entrevistado e) {
        StringBuilder builder = new StringBuilder();
        builder.append("Idade: ").append(e.getIdade()).append(" anos");
        builder.append("\nSexo: ").append(e.getSexo());
        builder.append("\nCor da pele: ").append(e.getCorPele());
        builder.append("\nProfissão: ").append(e.getProfissao());
        builder.append("\nEscolaridade: ").append(e.getEscolaridade());
        builder.append("\nAltura: ").append(e.getAltura()).append(" m");
        builder.append("\nPeso: ").append(e.getPeso()).append(" kg");

        if (e.getImc() != 0) {
            builder.append("\nIMC: ").append(e.getImc()).append(" kg/m²");
        } else {
            builder.append("\nIMC: ").append("Não preenchido");
        }

        if (e.getCinturaQuadril() != 0) {
            builder.append("\nCintura-quadril: ").append(e.getCinturaQuadril()).append(" cm");
        } else {
            builder.append("\nCintura-quadril: ").append("Não preenchido");
        }

        if (e.getPas() != 0) {
            builder.append("\nPAS: ").append(e.getPas()).append(" mm/hg");
        } else {
            builder.append("\nPAS: ").append("Não preenchido");
        }

        if (e.getPad() != 0) {
            builder.append("\nPAD: ").append(e.getPad()).append(" mm/hg");
        } else {
            builder.append("\nPAD: ").append("Não preenchido");
        }

        if (e.getGlicemiaCapilar() != 0) {
            builder.append("\nGlicemia capilar: ").append(e.getGlicemiaCapilar()).append(" mg/dl");
        } else {
            builder.append("\nGlicemia capilar: ").append("Não preenchido");
        }

        if (e.getEspirometria() != 0) {
            builder.append("\nEspirometria: ").append(e.getEspirometria()).append(" dl");
        } else {
            builder.append("\nEspirometria: ").append("Não preenchido");
        }

        if (e.getEspirometria() != 0) {
            builder.append("\nEspirometria: ").append(e.getEspirometria()).append(" dl");
        } else {
            builder.append("\nEspirometria: ").append("Não preenchido");
        }

        if (!e.getDoencas().equals(Constants.VAZIO)) {
            builder.append("\nDoenças referidas: ").append(e.getDoencas());
        } else {
            builder.append("\nDoenças referidas: ").append("Não preenchido");
        }

        builder.append("\nReligião referidas: ").append(e.getReligiao());
        builder.append("\nHá quantos anos: ").append(e.getDoencas()).append(" anos");
        builder.append("\nSaúde física: ").append(e.getSaudeFisica());
        builder.append("\nSaúde mental: ").append(e.getSaudeMental());
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class EntrevistadoItem {
        public final String id;
        public final String content;
        public final String details;

        public EntrevistadoItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
