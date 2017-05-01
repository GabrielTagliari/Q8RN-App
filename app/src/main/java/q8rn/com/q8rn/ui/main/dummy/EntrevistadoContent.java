package q8rn.com.q8rn.ui.main.dummy;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import q8rn.com.q8rn.entity.Entrevistado;
import q8rn.com.q8rn.manager.EntrevistadoManager;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class EntrevistadoContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<EntrevistadoItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, EntrevistadoItem> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    public static void populaHistorico(Context context) {
        EntrevistadoManager em = new EntrevistadoManager(context);

        List<Entrevistado> mEntrevistados = em.findAllEntrevistados();

        for (Entrevistado e : mEntrevistados) {
            addItem(createDummyItem(e));
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
        builder.append("Dados pessoais: ");
        builder.append("\nIdade: ").append(e.getIdade());
        builder.append("\nCor da pele: ").append(e.getCorPele());
        builder.append("\nProfissão: ").append(e.getProfissao());
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
