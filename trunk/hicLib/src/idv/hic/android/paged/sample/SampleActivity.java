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
package idv.hic.android.paged.sample;

import idv.hic.android.R;
import idv.hic.android.paged.AbstractPageableAdapter;
import idv.hic.android.paged.DataLoaderHandler;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Sample Activity that contains the Pageable list view.
 * @author Makas Tzavellas (makas dot tzavellas at gmail dot com)
 *
 */
public class SampleActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListView listView = (ListView)findViewById(R.id.list);
        SampleDataLoaderHandler handler = new SampleDataLoaderHandler();
        PageableAdapter adapter = new PageableAdapter(listView, this, R.layout.loading, handler);
        listView.setAdapter(adapter);
        // Set the adapter as the on scroll listener.
        // It is done here just incase the activity or other class needs to listen to onScroll event too.
        listView.setOnScrollListener(adapter);
    }
    
    /**
     * Sample Pageable adapter that extends {@link AbstractPageableAdapter}
     * @author Makas Tzavellas (makas dot tzavellas at gmail dot com)
     *
     */
    private class PageableAdapter extends AbstractPageableAdapter<String> {

        public PageableAdapter(ListView listView, Context context, int loadingViewResourceId, DataLoaderHandler<String> handler) {
            super(listView, context, loadingViewResourceId, handler);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            ((TextView)convertView).setText(getItem(position).toString());
            return convertView;
        }
        
    }
}