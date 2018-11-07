package q8rn.com.q8rn.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.helper.PermissaoHelper;
import q8rn.com.q8rn.manager.QuestaoEntrevistadoManager;
import q8rn.com.q8rn.ui.main.EntrevistadoDetailActivity;
import q8rn.com.q8rn.ui.main.dummy.EntrevistadoContent;

/**
 * An activity representing a list of Entrevistados. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link EntrevistadoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class EntrevistadoListFragment extends Fragment {

    private static final String TAG = "questao";
    public static final String NÃO_EXISTEM_DADOS = "Não existem dados a serem exportados";
    public static final String Q8RN_TRACO = "Q8RN - ";
    public static final String CORPO_EMAIL = "Banco de dados do questionário dos oito remédios naturais gerado em: ";
    public static final String TEXT_HTML = "text/html";
    public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_entrevistado_list, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        EntrevistadoContent.populaHistorico(getContext());

        TextView nenhumHistorico = (TextView) v.findViewById(R.id.nenhum_historico);

        if (EntrevistadoContent.ITEMS.isEmpty()) {
            nenhumHistorico.setVisibility(View.VISIBLE);
        }

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestaoEntrevistadoManager qeManager = new QuestaoEntrevistadoManager(getContext());

                PermissaoHelper permissaoHelper = new PermissaoHelper(getContext());

                if (permissaoHelper.temPermissaoArmazenamento()) {
                    try {
                        File file = qeManager.gerarExcel(getContext());
                        Uri u1;
                        u1 = Uri.fromFile(file);

                        Date dataSemformato = Calendar.getInstance().getTime();

                        SimpleDateFormat spf = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
                        String dataFormatada = spf.format(dataSemformato);

                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, Q8RN_TRACO + dataFormatada);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, CORPO_EMAIL + dataFormatada + "\n\n");
                        sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
                        sendIntent.setType(TEXT_HTML);
                        startActivity(sendIntent);

                    } catch (RuntimeException e) {
                        Log.i(TAG, e.getMessage());
                        Toast.makeText(getActivity(), NÃO_EXISTEM_DADOS, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        View recyclerView = v.findViewById(R.id.entrevistado_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (v.findViewById(R.id.entrevistado_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        return v;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(EntrevistadoContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<EntrevistadoContent.EntrevistadoItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<EntrevistadoContent.EntrevistadoItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.entrevistado_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(EntrevistadoDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        EntrevistadoDetailFragment fragment = new EntrevistadoDetailFragment();
                        fragment.setArguments(arguments);
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.entrevistado_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, EntrevistadoDetailActivity.class);
                        intent.putExtra(EntrevistadoDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public EntrevistadoContent.EntrevistadoItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
