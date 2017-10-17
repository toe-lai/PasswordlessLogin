package com.udacity.toelie.passwordlesslogin.template;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.udacity.toelie.passwordlesslogin.R;


/**
 * Created by toe-lie on 3/6/2017.
 */

public class TemplateFragment extends Fragment {

    public static TemplateFragment newInstance() {
        return new TemplateFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.template_frag, container, false);
        initViews(rootView);
        initViewsListeners();

        return rootView;
    }

    private void initViews(View rootView) {

    }

    private void initViewsListeners() {

    }

}
