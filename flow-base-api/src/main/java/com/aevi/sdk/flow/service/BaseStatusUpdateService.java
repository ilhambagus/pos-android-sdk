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

package com.aevi.sdk.flow.service;

import android.support.annotation.NonNull;
import com.aevi.sdk.flow.FlowBaseConfig;
import com.aevi.sdk.flow.model.Request;
import com.aevi.sdk.flow.stage.StatusUpdateModel;

/**
 * Base service for handling status update requests defined by a request type and associated bespoke data.
 *
 * See documentation for examples and reference types and data.
 */
public abstract class BaseStatusUpdateService extends BaseApiService {

    public BaseStatusUpdateService() {
        super(FlowBaseConfig.VERSION);
    }

    @Override
    protected void processRequest(@NonNull ClientCommunicator clientCommunicator, @NonNull String request, @NonNull String flowStage) {
        StatusUpdateModel statusUpdateModel = StatusUpdateModel.fromService(clientCommunicator, Request.fromJson(request));
        processRequest(statusUpdateModel);
    }

    /**
     * Handle the status update request.
     *
     * @param stageModel The status update stage model
     */
    protected abstract void processRequest(StatusUpdateModel stageModel);
}
