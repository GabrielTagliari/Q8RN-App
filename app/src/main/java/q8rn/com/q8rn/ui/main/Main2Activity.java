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

    private TextView mTextMessage;

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

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        /*// Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog); // layout of your dialog
        // Set dialog title
        dialog.setTitle("Detail");

        // set values for custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText(item.toString());
        // similar add statements for other details
        dialog.show();*/
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
