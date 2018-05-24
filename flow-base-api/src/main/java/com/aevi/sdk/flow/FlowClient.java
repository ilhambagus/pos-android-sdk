package com.aevi.sdk.flow;


import com.aevi.sdk.flow.model.*;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Client for general functions not directly related to any particular domain (such as payments).
 */
public interface FlowClient {

    /**
     * Query for devices connected to the processing service.
     *
     * Returns a single that emits a list of currently connected devices.
     *
     * This should be queried each time a selection is required to ensure an up-to-date list.
     *
     * You can subscribe to {@link #subscribeToSystemEvents()} for updates on changes to the available devices.
     *
     * @return Single emitting a list of {@link Device} objects containing basic device info
     */
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
    Single<FlowServices> getFlowServices();

    /**
     * Initiate processing of the given {@link Request}.
     *
     * Returns a single that emits a {@link Response} after processing has completed.
     *
     * @param request The request
     * @return Single emitting a {@link Response} model
     */
    Single<Response> processRequest(Request request);

    /**
     * Subscribe to general system events.
     *
     * Examples are when there are changed to devices, applications or system settings.
     * See documentation for a comprehensive list.
     *
     * @return A stream that will emit {@link FlowEvent} items
     */
    Observable<FlowEvent> subscribeToSystemEvents();

    /**
     * Query for the current system settings, such as timeouts, flow configs, etc.
     *
     * This will return {@link AdditionalData} populated with key/value pair data as per the documentation.
     *
     * @return A single emitting {@link AdditionalData} populated with key/value pair data as per the documentation
     */
    Single<AdditionalData> getSystemSettings();
}
