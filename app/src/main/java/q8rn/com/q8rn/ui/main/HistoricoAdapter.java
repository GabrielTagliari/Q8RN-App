package q8rn.com.q8rn.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q8rn.com.q8rn.R;
import q8rn.com.q8rn.entity.Entrevistado;

/**
 * Created by Gabriel on 04/03/2018.
 */

public class HistoricoAdapter extends RecyclerView.Adapter {

    private final ArrayList<Entrevistado> entrevistados;
    private final Context context;

    HistoricoAdapter(ArrayList<Entrevistado> entrevistados, Context context) {
        this.entrevistados = entrevistados;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.historico_itens, parent, false);

        return new HistoricoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HistoricoViewHolder viewHolder = (HistoricoViewHolder) holder;

        Entrevistado entrevistado = entrevistados.get(position);

        viewHolder.mImageView.setImageDrawable(getDrawableImagem(position));
        viewHolder.mIniciaisNome.setText(entrevistado.getIniciaisNome());
    }

    @Override
    public int getItemCount() {
        return entrevistados.size();
    }

    private TextDrawable getDrawableImagem(int position) {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        return TextDrawable.builder()
                .buildRound(entrevistados.get(position).getIniciaisNome().substring(0,1), color);
    }

    public class HistoricoViewHolder extends RecyclerView.ViewHolder {

        final ImageView mImageView;
        final TextView mIniciaisNome;

        public HistoricoViewHolder(View view) {
            super(view);
            mIniciaisNome = (TextView) view.findViewById(R.id.item_lista_iniciais_nome);
            mImageView = (ImageView) view.findViewById(R.id.item_lista_imagem);
        }
    }
}
