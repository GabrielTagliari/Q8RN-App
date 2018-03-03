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

    public static final String NÃO_PREENCHIDO = "Não preenchido";
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

        if (!e.getCodIdentificacao().equals(Constants.VAZIO)) {
            builder.append("Cód. identificação: ").append(e.getCodIdentificacao());
        } else {
            builder.append("Cód. identificação: ").append(NÃO_PREENCHIDO);
        }

        builder.append("\nIniciais do nome: ").append(e.getIniciaisNome());
        builder.append("\nIdade: ").append(e.getIdade()).append(" anos");
        builder.append("\nSexo: ").append(e.getSexo());
        builder.append("\nCor da pele: ").append(e.getCorPele());

        if (!e.getConstituicaoFamiliar().equals(Constants.VAZIO)) {
            builder.append("\nConstituição familiar: ").append(e.getConstituicaoFamiliar());
        } else {
            builder.append("\nConstituição familiar: ").append(NÃO_PREENCHIDO);
        }

        builder.append("\nProfissão: ").append(e.getProfissao());
        builder.append("\nEscolaridade: ").append(e.getEscolaridade());
        builder.append("\nAltura: ").append(e.getAltura()).append(" m");
        builder.append("\nPeso: ").append(e.getPeso()).append(" kg");

        if (e.getImc() != 0) {
            builder.append("\nIMC: ").append(e.getImc()).append(" kg/m²");
        } else {
            builder.append("\nIMC: ").append(NÃO_PREENCHIDO);
        }

        if (e.getCintura() != 0) {
            builder.append("\nCintura: ").append(e.getCintura()).append(" cm");
        } else {
            builder.append("\nCintura: ").append(NÃO_PREENCHIDO);
        }

        if (e.getQuadril() != 0) {
            builder.append("\nQuadril: ").append(e.getQuadril()).append(" cm");
        } else {
            builder.append("\nQuadril: ").append(NÃO_PREENCHIDO);
        }

        if (e.getCinturaQuadril() != 0) {
            builder.append("\nRelação cintura/quadril: ").append(e.getCinturaQuadril());
        } else {
            builder.append("\nRelação cintura/quadril: ").append(NÃO_PREENCHIDO);
        }

        if (e.getCinturaQuadril() != 0) {
            builder.append("\nRelação cintura/estatura: ").append(e.getCinturaEstatura());
        } else {
            builder.append("\nRelação cintura/estatura: ").append(NÃO_PREENCHIDO);
        }

        if (e.getPas() != 0) {
            builder.append("\nPAS: ").append(e.getPas()).append(" mm/hg");
        } else {
            builder.append("\nPAS: ").append(NÃO_PREENCHIDO);
        }

        if (e.getGlicemiaCapilar() != 0) {
            builder.append("\nGlicemia capilar: ").append(e.getGlicemiaCapilar()).append(" mg/dl");
        } else {
            builder.append("\nGlicemia capilar: ").append(NÃO_PREENCHIDO);
        }

        if (e.getEspirometria() != 0) {
            builder.append("\nEspirometria: ").append(e.getEspirometria()).append(" dl");
        } else {
            builder.append("\nEspirometria: ").append(NÃO_PREENCHIDO);
        }

        if (!e.getDoencas().equals(Constants.VAZIO)) {
            builder.append("\nDoenças referidas: ").append(e.getDoencas());
        } else {
            builder.append("\nDoenças referidas: ").append(NÃO_PREENCHIDO);
        }

        if (e.getTesteEsforcoAntes() != 0) {
            builder.append("\nTeste de esforço antes: ").append(e.getTesteEsforcoAntes()).append(" bpm");
        } else {
            builder.append("\nTeste de esforço antes: ").append(NÃO_PREENCHIDO);
        }

        if (e.getTesteEsforcoDepois() != 0) {
            builder.append("\nTeste de esforço depois: ").append(e.getTesteEsforcoDepois()).append(" bpm");
        } else {
            builder.append("\nTeste de esforço depois: ").append(NÃO_PREENCHIDO);
        }

        builder.append("\nReligião referidas: ").append(e.getReligiao());
        builder.append("\nHá quantos anos: ").append(e.getTempoReligiao()).append(" anos");
        builder.append("\nSaúde física: ").append(e.getSaudeFisica());
        builder.append("\nSaúde mental: ").append(e.getSaudeMental());

        if (!e.getQualidadeVida().equals(Constants.VAZIO)) {
            builder.append("\nQualidade de vida: ").append(e.getQualidadeVida());
        } else {
            builder.append("\nQualidade de vida: ").append(NÃO_PREENCHIDO);
        }

        if (!e.getoQueMelhorar().equals(Constants.VAZIO)) {
            builder.append("\nO que você deseja melhorar em sua saúde?: ").append(e.getoQueMelhorar());
        } else {
            builder.append("\nO que você deseja melhorar em sua saúde?: ").append(NÃO_PREENCHIDO);
        }

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
