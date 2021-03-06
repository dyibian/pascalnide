/*
 *  Copyright (c) 2017 Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duy.pascal.ui.code.sample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.commonsware.cwac.pager.PageDescriptor;
import com.commonsware.cwac.pager.SimplePageDescriptor;
import com.duy.pascal.ui.BaseActivity;
import com.duy.pascal.ui.R;
import com.duy.pascal.ui.code.CompileManager;
import com.duy.pascal.ui.code.sample.adapters.CodePagerAdapter;
import com.duy.pascal.ui.code.sample.adapters.CodeSampleAdapter;
import com.duy.pascal.ui.code.sample.fragments.CodeSampleFragment;
import com.duy.pascal.ui.editor.EditorActivity;
import com.duy.pascal.ui.file.FileManager;
import com.duy.pascal.ui.runnable.ExecuteActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.util.ArrayList;


public class CodeSampleActivity extends BaseActivity implements CodeSampleAdapter.OnCodeClickListener {

    private final String[] mCategories = new String[]{"Basic", "System", "Crt", "Dos", "Graph",
            "Math", "CompleteProgram", "Android", "AndroidDialog", "AndroidZxing", "AndroidLocation"};
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MaterialSearchView mSearchView;
    private Toolbar mToolbar;
    private FileManager mFileManager;
    private CodePagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFileManager = new FileManager(getApplicationContext());

        setContentView(R.layout.activity_code_sample);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.code_sample);

        mViewPager = findViewById(R.id.view_pager);
        mSearchView = findViewById(R.id.search_view);
        mTabLayout = findViewById(R.id.tab_layout);

        final ArrayList<PageDescriptor> pages = new ArrayList<>();
        for (String category : mCategories) {
            pages.add(new SimplePageDescriptor(category, category));
        }

        mPagerAdapter = new CodePagerAdapter(getSupportFragmentManager(), pages);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                CodeSampleFragment codeSampleFragment = mPagerAdapter.getCurrentFragment();
                if (codeSampleFragment != null) {
                    codeSampleFragment.query(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_code_sample, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickRun(String code) {
        //create file temp
        mFileManager.setContentFileTemp(code);

        //set file temp for generate
        Intent intent = new Intent(this, ExecuteActivity.class);

        //this code is verified, do not need compile
        intent.putExtra(CompileManager.EXTRA_FILE, mFileManager.getTempFile());
        startActivity(intent);
    }

    @Override
    public void onClickEdit(String code) {
        //create file temp
        String suffix = Integer.toHexString((int) System.currentTimeMillis());
        File file = mFileManager.createNewFileInMode("sample_" + suffix + ".pas");
        mFileManager.saveFile(file, code);

        //set file temp for generate
        Intent intent = new Intent(this, EditorActivity.class);

        //this code is verified, do not need compile
        intent.putExtra(CompileManager.EXTRA_FILE, file);
        startActivity(intent);
    }


}
