package com.abhishek.architecturepattern_livedata_pagging_di.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abhishek.architecturepattern_livedata_pagging_di.MyApplication;
import com.abhishek.architecturepattern_livedata_pagging_di.R;
import com.abhishek.architecturepattern_livedata_pagging_di.databinding.PagingDemoLayoutBinding;
import com.abhishek.architecturepattern_livedata_pagging_di.utils.Constant;
import com.abhishek.architecturepattern_livedata_pagging_di.utils.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import javax.inject.Inject;

public class PagingDemoAct extends AppCompatActivity {

    private SearchView searchView;
    @Inject
    ViewModelFactory viewModelFactory;
    PagingLibViewModel viewModel;
    PagingDemoLayoutBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(PagingDemoAct.this, R.layout.paging_demo_layout);


        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ((MyApplication) getApplication()).getAppComponent().doInjection(this);

        viewModel = ViewModelProviders.of(PagingDemoAct.this, viewModelFactory).get(PagingLibViewModel.class);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                searchView = (SearchView) item.getActionView();
                searchView.setSubmitButtonEnabled(true);
                searchView.setOnQueryTextListener(onQueryTextListener);
                break;
            case R.id.action_filter:
                Toast.makeText(this, "Working ", Toast.LENGTH_SHORT).show();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private SearchView.OnQueryTextListener onQueryTextListener =
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    getDealsFromDb(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    getDealsFromDb(newText);
                    return true;
                }

                private void getDealsFromDb(String searchText) {
                    searchText = "%" + searchText + "%";
                    Toast.makeText(PagingDemoAct.this, "" + searchText, Toast.LENGTH_SHORT).show();

          /*          localRepository.getDealsListInfo(DealsSearchActivity.this, searchText)
                            .observe(DealsSearchActivity.this, new Observer<List<DealInfo>>() {
                                @Override
                                public void onChanged(@Nullable List<DealInfo> deals) {
                                    if (deals == null) {
                                        return;
                                    }
                                    DealsListViewAdapter adapter = new DealsListViewAdapter(
                                            DealsSearchActivity.this,
                                            R.layout.deal_item_layout, deals);
                                    listView.setAdapter(adapter);

                                }
                            });

           */
                }
            };

    private void init() {

        binding.list.setLayoutManager(new LinearLayoutManager(PagingDemoAct.this));
        MyPageListAdapter adapter = new MyPageListAdapter();
        binding.list.setAdapter(adapter);

        if (!Constant.checkInternetConnection(this)) {
            Snackbar.make(findViewById(android.R.id.content), Constant.CHECK_NETWORK_ERROR, Snackbar.LENGTH_SHORT)
                    .show();
        }

        viewModel.getListLiveData().observe(this, adapter::submitList);

        viewModel.getProgressLoadStatus().observe(this, status -> {
            if (Objects.requireNonNull(status).equalsIgnoreCase(Constant.LOADING)) {
                binding.progress.setVisibility(View.VISIBLE);
            } else if (status.equalsIgnoreCase(Constant.LOADED)) {
                binding.progress.setVisibility(View.GONE);
            }
        });

    }
}
