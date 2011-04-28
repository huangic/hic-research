/*
 * Copyright (C) 2010 Makas Tzavellas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package idv.hic.android.paged;

import idv.hic.android.paged.DataLoaderHandler.DataLoadedCallback;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Abstract adapter which will display the "busy" view as footer when a background download occurs.
 * Also calls the {@link DataLoaderHandler} for its initial values as well as subsequent items as 
 * the user scrolls to the end.
 * 
 * @author Makas Tzavellas (makas dot tzavellas at gmail dot com)
 *
 * @param <T>
 */
public abstract class AbstractPageableAdapter<T> extends BaseAdapter implements AbsListView.OnScrollListener, DataLoadedCallback<T> {

    private DataLoaderHandler<T> mDataLoaderHandler;
    private ListView mListView;
    private View mLoadingView;
    private Context mContext;
    private ArrayList<T> mList = new ArrayList<T>();

    /**
     * 
     * @param listView The list view that this adapter will be added to.
     * @param context The activity context.
     * @param loadingViewResourceId The layout to use when displaying the "busy" status
     * @param handler The handler to use when loading more data to the list view.
     */
    public AbstractPageableAdapter(ListView listView, Context context, int loadingViewResourceId, DataLoaderHandler<T> handler) {
        mContext = context;
        mDataLoaderHandler = handler;
        mListView = listView;
        mLoadingView = LayoutInflater.from(context).inflate(loadingViewResourceId, null);
        showLoading(true);
        mDataLoaderHandler.getValues(this);
    }
    
    /**
     * The {@link Context} passed in the constructor.
     * @return
     */
    public final Context getContext() {
        return mContext;
    }
    
    public final int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {
        // Nothing to do I suppose...
        
    }

    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE: {
                final int maxItems = mDataLoaderHandler.getMaxItems();
                final int first = mListView.getFirstVisiblePosition();
                final int count = mListView.getChildCount();
                final int total = getCount();
                
                if (first + count < total || mDataLoaderHandler.isLoading())
                    return;
                
                if (total < maxItems) {
                    showLoading(true);
                    mDataLoaderHandler.getNext(this);
                }
                break;
            }
        }
    }
    
    private void showLoading(boolean show) {
        if (show)
            mListView.addFooterView(mLoadingView);
        else
            mListView.removeFooterView(mLoadingView);
    }
    
    public void dataLoaded(ArrayList<T> values) {
        ArrayList<T> list = mList;
        for (T value : values) {
            list.add(value);
        }
        showLoading(false);
        notifyDataSetChanged();
    }
    
}