/*
 * Copyright (c) 2015 Zelory.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package id.zelory.codepolitan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.adapter.BenihRecyclerAdapter;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.util.KeyboardUtil;
import id.zelory.benih.view.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.ArticleController;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.data.model.Article;
import id.zelory.codepolitan.data.LocalDataManager;
import id.zelory.codepolitan.ui.ReadActivity;
import id.zelory.codepolitan.ui.adapter.GeneralArticleAdapter;

/**
 * Created on : August 30, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public abstract class ListArticleFragment<Data extends Parcelable> extends
        BenihFragment<Data> implements SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener, ArticleController.Presenter,
        BenihRecyclerAdapter.OnItemClickListener
{
    protected ArticleController articleController;
    @Bind(R.id.swipe_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view) BenihRecyclerView recyclerView;
    protected SearchView searchView;
    protected GeneralArticleAdapter adapter;
    protected int currentPage = 1;
    protected boolean searching = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_list_article;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        currentPage = bundle != null ? bundle.getInt("currentPage") : 1;
        setUpSwipeLayout();
        setUpAdapter();
        setUpRecyclerView();
        setupController(bundle);
    }

    protected abstract void setUpRecyclerView();


    protected void setUpAdapter()
    {
        if (adapter == null)
        {
            adapter = new GeneralArticleAdapter(getActivity());
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
        }
    }

    protected void setUpSwipeLayout()
    {
        swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        searchView = (SearchView) menu.getItem(0).getActionView();
        searchView.setOnQueryTextListener(this);
    }

    protected void setupController(Bundle bundle)
    {
        if (articleController == null)
        {
            articleController = new ArticleController(this);
        }

        if (bundle != null)
        {
            articleController.loadState(bundle);
        } else
        {
            new Handler().postDelayed(this::onRefresh, 800);
        }
    }

    @Override
    public void onRefresh()
    {
        currentPage = 1;
        adapter.clear();
        setUpRecyclerView();
    }

    @Override
    public void showArticle(Article article)
    {

    }

    @Override
    public void showArticles(List<Article> articles)
    {
        if (!searching && adapter != null)
        {
            adapter.add(articles);
        }
    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {
        adapter.clear();
        adapter.add(articles);
    }

    @Override
    public void showLoading()
    {
        if (!searching)
        {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void dismissLoading()
    {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(Throwable throwable)
    {
        switch (throwable.getMessage())
        {
            case ErrorEvent.LOAD_STATE_LIST_ARTICLE:
                onRefresh();
                break;
            case ErrorEvent.LOAD_LIST_ARTICLE_BY_PAGE:
                Snackbar.make(recyclerView, R.string.error_message, Snackbar.LENGTH_LONG)
                        .setAction(R.string.retry, v -> articleController.loadArticles(currentPage))
                        .show();
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        articleController.filter(query);
        KeyboardUtil.hideKeyboard(getActivity(), searchView);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        searching = !"".equals(newText);
        articleController.filter(newText);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        articleController.saveState(outState);
        outState.putInt("currentPage", currentPage);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        LocalDataManager.saveArticles(adapter.getData());
        LocalDataManager.savePosition(position);
        startActivity(new Intent(getActivity(), ReadActivity.class));
    }
}
