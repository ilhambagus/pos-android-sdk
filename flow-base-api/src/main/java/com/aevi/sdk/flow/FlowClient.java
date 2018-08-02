/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.aevi.sdk.flow;


import android.support.annotation.NonNull;

import com.aevi.sdk.flow.model.*;
import com.aevi.sdk.flow.model.config.SystemSettings;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Client for general functions not directly related to any particular domain (such as payments).
 */
public interface FlowClient {

    /**
     * Query for devices connected to the processing service, if multi-device is enabled.
     *
     * It is up to the flow processing service configuration if multi-device is enabled or not. Use {@link FlowClient#getSystemSettings()} to check
     * whether it is allowed. See documentation/samples for how to retrieve the value.
     *
     * Returns a single that emits a list of currently connected devices.
     *
     * This should be queried each time a selection is required to ensure an up-to-date list.
     *
     * You can subscribe to {@link #subscribeToSystemEvents()} for updates on changes to the available devices.
     *
     * @return Single emitting a list of {@link Device} objects containing basic device info
     */
    @NonNull
    Single<List<Device>> getDevices();

    /**
     * Query for installed flow services on this and connected devices.
     *
     * Returns a single that emits a {@link FlowServices} model containing the available flow services.
     *
     * This should be queried each time an up-to-date list is required.
     *
     * You can subscribe to {@link #subscribeToSystemEvents()} for updates on changes to the available flow services.
     *
     * @return Single emitting a {@link FlowServices} model
     */
    @NonNull
    Single<FlowServices> getFlowServices();

    /**
     * Query for a list of all supported request types that can be used when creating a {@link Request}.
     *
     * Unlike the request types returned via {@link FlowServices#getAllSupportedRequestTypes()} which purely reflects what the services reports, this list
     * will also take into account any restrictions or limitations imposed by the acquirer/merchant configuration.
     *
     * @return The list of supported request types
     */
    @NonNull
    Single<List<String>> getSupportedRequestTypes();

    /**
     * Initiate processing of the given {@link Request}.
     *
     * Returns a single that emits a {@link Response} after processing has completed.
     *
     * @param request The request
     * @return Single emitting a {@link Response} model
     */
    @NonNull
    Single<Response> processRequest(Request request);

    /**
     * Subscribe to general system events.
     *
     * Examples are when there are changed to devices, applications or system settings.
     * See documentation for a comprehensive list.
     *
     * @return A stream that will emit {@link FlowEvent} items
     */
    @NonNull
    Observable<FlowEvent> subscribeToSystemEvents();

    /**
     * Query for the current system settings, such as timeouts, flow configs, etc.
     *
     * @return A single emitting {@link SystemSettings}
     */
    @NonNull
    Single<SystemSettings> getSystemSettings();
}
