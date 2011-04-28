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

import idv.hic.android.paged.DataLoaderHandler;
import idv.hic.android.paged.DataResponseHandler;

import java.util.ArrayList;

import android.os.AsyncTask;

/**
 * Sample implementation of the {@link DataLoaderHandler}
 * @author Makas Tzavellas (makas dot tzavellas at gmail dot com)
 *
 */
public class SampleDataLoaderHandler implements DataLoaderHandler<String>, DataResponseHandler<SampleDataResult<String>> {
    private static final int MAX_SIZE_PER_PAGE = 10;
    private static final int MAX_ITEMS = 51;

    private boolean mLoading;
    private ArrayList<String> mValues = new ArrayList<String>();
    private DataLoadedCallback<String> mCallback;
    private int mMaxItems;
    
    public int getMaxItems() {
        return mMaxItems;
    }

    public void getNext(DataLoadedCallback<String> callback) {
        mLoading = true;
        mCallback = callback;
        new SampleBackgroundTask(this).execute(mValues.size());
    }

    public void getValues(DataLoadedCallback<String> callback) {
        if (mValues.isEmpty()) {
            // No data has been loaded before.
            // Load the initial data which includes the max size.
            mLoading = true;
            mCallback = callback;
            new SampleFirstBackgroundTask(this).execute();
        } else {
            callback.dataLoaded(mValues);
        }
    }

    public boolean isLoading() {
        return mLoading;
    }

    public void resultAvailable(int status, SampleDataResult<String> result) {
        mLoading = false;
        if (mMaxItems == 0)
            mMaxItems = result.maxItems;
        
        mValues.addAll(result.values);
        
        mCallback.dataLoaded(result.values);
    }
    
    /**
     * Sample implementation when loading the data the first time.
     * @author Makas Tzavellas (makas dot tzavellas at gmail dot com)
     *
     */
    private static class SampleFirstBackgroundTask extends AsyncTask<Void, Void, SampleDataResult<String>> {
        private DataResponseHandler<SampleDataResult<String>> mResponseHandler;
        
        private SampleFirstBackgroundTask(DataResponseHandler<SampleDataResult<String>> responseHandler) {
            mResponseHandler = responseHandler;
        }
        
        @Override
        protected SampleDataResult<String> doInBackground(Void... params) {
            // Put whatever info
            ArrayList<String> values = new ArrayList<String>(MAX_SIZE_PER_PAGE);
            for (int i = 0; i < MAX_SIZE_PER_PAGE; i++) {
                values.add("" + i);
            }
            SampleDataResult<String> result = new SampleDataResult<String>();
            // Since this is loaded the first time, the max item should be made available.
            result.maxItems = MAX_ITEMS;
            result.values = values;
            try {
                // Pause for 5 seconds to simulate "busy" state.
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(SampleDataResult<String> result) {
            mResponseHandler.resultAvailable(0, result);
        }
        
    }

    /**
     * Sample implementation that "downloads" more data.
     * @author Makas Tzavellas (makas dot tzavellas at gmail dot com)
     *
     */
    private class SampleBackgroundTask extends AsyncTask<Integer, Void, SampleDataResult<String>> {
        private DataResponseHandler<SampleDataResult<String>> mResponseHandler;
        
        private SampleBackgroundTask(DataResponseHandler<SampleDataResult<String>> responseHandler) {
            mResponseHandler = responseHandler;
        }

        @Override
        protected SampleDataResult<String> doInBackground(Integer... params) {
            int currentSize = params[0];
            ArrayList<String> values = new ArrayList<String>(MAX_SIZE_PER_PAGE);
            
            int maxSize = currentSize + MAX_SIZE_PER_PAGE;
            if (maxSize > MAX_ITEMS)
                maxSize = MAX_ITEMS;
            
            for (int i = currentSize; i < maxSize; i++) {
                values.add("" + i);
            }
            SampleDataResult<String> result = new SampleDataResult<String>();
            result.values = values;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }
        
        @Override
        protected void onPostExecute(SampleDataResult<String> result) {
            mResponseHandler.resultAvailable(0, result);
        }
    }

}
