package com.aevi.sdk.flow;


import android.content.Context;

import com.aevi.android.rxmessenger.client.ObservableMessengerClient;
import com.aevi.sdk.flow.model.*;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;

public class FlowClientImpl extends ApiBase implements FlowClient {

    FlowClientImpl(Context context) {
        super(FlowBaseConfig.VERSION, context);
    }

    @Override
    public Single<List<Device>> getDevices() {
        final ObservableMessengerClient deviceMessenger = getMessengerClient(DEVICE_LIST_SERVICE_COMPONENT);
        AppMessage appMessage = new AppMessage(AppMessageTypes.REQUEST_MESSAGE, getInternalData());
        return deviceMessenger
                .sendMessage(appMessage.toJson())
                .map(new Function<String, Device>() {
                    @Override
                    public Device apply(String json) throws Exception {
                        return Device.fromJson(json);
                    }
                })
                .toList()
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        deviceMessenger.closeConnection();
                    }
                });
    }

    @Override
    public Single<FlowServices> getFlowServices() {
        final ObservableMessengerClient flowInfoMessenger = getMessengerClient(FLOW_SERVICE_INFO_COMPONENT);
        AppMessage appMessage = new AppMessage(AppMessageTypes.REQUEST_MESSAGE, getInternalData());
        return flowInfoMessenger
                .sendMessage(appMessage.toJson())
                .map(new Function<String, FlowServiceInfo>() {
                    @Override
                    public FlowServiceInfo apply(String json) throws Exception {
                        return FlowServiceInfo.fromJson(json);
                    }
                })
                .toList()
                .map(new Function<List<FlowServiceInfo>, FlowServices>() {
                    @Override
                    public FlowServices apply(List<FlowServiceInfo> flowServiceInfos) throws Exception {
                        return new FlowServices(flowServiceInfos);
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        flowInfoMessenger.closeConnection();
                    }
                });
    }

    @Override
    public Single<Response> processRequest(Request request) {
        return sendGenericRequest(request);
    }

    @Override
    public Observable<FlowEvent> subscribeToEventStream() {
        return Observable.error(new UnsupportedOperationException("Not yet implemented"));
    }
}
