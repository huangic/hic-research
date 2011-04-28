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

/**
 * Default interface to use when receiving a response from server.
 * 
 * @author Makas Tzavellas (makas dot tzavellas at gmail dot com)
 *
 * @param <T>
 */
public interface DataResponseHandler<T> {
    /**
     * Notify the implementation that response is available.
     * @param status The status of the download. Whether it was successful or failed.
     * @param result The result of the download if any.
     */
    void resultAvailable(int status, T result);
}
