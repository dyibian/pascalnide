package com.duy.pascal.frontend.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.duy.pascal.frontend.R;
import com.duy.pascal.frontend.activities.AbstractAppCompatActivity;

/**
 * Created by Duy on 27-Feb-17.
 */

public class DocumentActivity extends AbstractAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document);
    }
}