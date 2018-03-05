package q8rn.com.q8rn.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import q8rn.com.q8rn.R;
import q8rn.com.q8rn.decoration.SimpleDividerItemDecoration;
import q8rn.com.q8rn.entity.Entrevistado;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.home_historico_lista)
    RecyclerView mListaHistorico;

    @BindView(R.id.toolbar_historico)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        toolbar.setTitleTextColor(Color.BLACK);

        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Q8RN");

        ArrayList<Entrevistado> entrevistados = new ArrayList<>();

        Entrevistado entrevistado1 = new Entrevistado();
        entrevistado1.setIniciaisNome("OI");
        entrevistados.add(entrevistado1);

        Entrevistado entrevistado2 = new Entrevistado();
        entrevistado2.setIniciaisNome("TESTE");
        entrevistados.add(entrevistado2);

        Entrevistado entrevistado3 = new Entrevistado();
        entrevistado3.setIniciaisNome("GTR");
        entrevistados.add(entrevistado3);

        Entrevistado entrevistado4 = new Entrevistado();
        entrevistado4.setIniciaisNome("TESTE");
        entrevistados.add(entrevistado4);

        Entrevistado entrevistado5 = new Entrevistado();
        entrevistado5.setIniciaisNome("OI");
        entrevistados.add(entrevistado5);

        Entrevistado entrevistado6 = new Entrevistado();
        entrevistado6.setIniciaisNome("TESTE");
        entrevistados.add(entrevistado6);

        Entrevistado entrevistado7 = new Entrevistado();
        entrevistado7.setIniciaisNome("GTR");
        entrevistados.add(entrevistado7);

        Entrevistado entrevistado8 = new Entrevistado();
        entrevistado8.setIniciaisNome("TESTE");
        entrevistados.add(entrevistado8);

        Entrevistado entrevistado9 = new Entrevistado();
        entrevistado9.setIniciaisNome("OI");
        entrevistados.add(entrevistado9);

        Entrevistado entrevistado10 = new Entrevistado();
        entrevistado10.setIniciaisNome("TESTE");
        entrevistados.add(entrevistado10);

        Entrevistado entrevistado11 = new Entrevistado();
        entrevistado11.setIniciaisNome("GTR");
        entrevistados.add(entrevistado11);

        Entrevistado entrevistado12 = new Entrevistado();
        entrevistado12.setIniciaisNome("TESTE");
        entrevistados.add(entrevistado12);

        Entrevistado entrevistado13 = new Entrevistado();
        entrevistado13.setIniciaisNome("OI");
        entrevistados.add(entrevistado13);

        Entrevistado entrevistado14 = new Entrevistado();
        entrevistado14.setIniciaisNome("TESTE");
        entrevistados.add(entrevistado14);

        Entrevistado entrevistado15 = new Entrevistado();
        entrevistado15.setIniciaisNome("GTR");
        entrevistados.add(entrevistado15);

        Entrevistado entrevistado16 = new Entrevistado();
        entrevistado16.setIniciaisNome("TESTE");
        entrevistados.add(entrevistado16);

        mListaHistorico.setAdapter(new HistoricoAdapter(entrevistados, this));
        mListaHistorico.addItemDecoration(new SimpleDividerItemDecoration(this));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mListaHistorico.setLayoutManager(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_questionario:
                Intent intent = new Intent(HomeActivity.this, FormStepperActivity.class);
                startActivity(intent);
                break;
            case R.id.item_menu_exportar:
                break;
            case R.id.item_menu_creditos:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
