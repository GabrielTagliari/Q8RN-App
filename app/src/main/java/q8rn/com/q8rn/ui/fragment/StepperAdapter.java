package q8rn.com.q8rn.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import q8rn.com.q8rn.R;

/** Created by gabriel on 09/04/17. */

public class StepperAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "position";

    public StepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        final StepDadosPessoaisFragment step = new StepDadosPessoaisFragment();
        Bundle b = new Bundle();
        b.putInt(CURRENT_STEP_POSITION_KEY, position);
        step.setArguments(b);
        return step;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        if (position == 0)
            return new StepViewModel.Builder(context)
                .setTitle(R.string.tab_title_dados_pessoais)
                .create();
        if (position == 1)
            return new StepViewModel.Builder(context)
                .setTitle(R.string.tab_title_medidas)
                .create();
        return new StepViewModel.Builder(context)
                .setTitle(R.string.tab_title_dados_pessoais)
                .create();
    }
}