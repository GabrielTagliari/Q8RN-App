package q8rn.com.q8rn.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import q8rn.com.q8rn.R;
import q8rn.com.q8rn.ui.fragment.AbaQuestionarioFragment;
import q8rn.com.q8rn.ui.fragment.EntrevistadoListFragment;
import q8rn.com.q8rn.ui.fragment.ItemFragment;
import q8rn.com.q8rn.ui.fragment.dummy.DummyContent;

public class Main2Activity extends AppCompatActivity
        implements ItemFragment.OnListFragmentInteractionListener,
                   AbaQuestionarioFragment.OnFragmentInteractionListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment mFragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mFragment = new AbaQuestionarioFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    mFragment = new EntrevistadoListFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();
                    return true;
                case R.id.navigation_notifications:
                    mFragment = new AbaQuestionarioFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Fragment mFragment = new AbaQuestionarioFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
