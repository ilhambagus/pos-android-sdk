package com.aevi.sdk.flow.service;

import android.support.annotation.NonNull;
import android.util.Log;
import com.aevi.android.rxmessenger.ChannelServer;
import com.aevi.sdk.flow.model.AppMessage;
import com.aevi.sdk.flow.model.FlowException;
import com.aevi.sdk.flow.model.InternalData;
import io.reactivex.Observable;

import java.util.HashSet;
import java.util.Set;

import static com.aevi.sdk.flow.constants.AppMessageTypes.*;
import static com.aevi.sdk.flow.model.AppMessage.EMPTY_DATA;
import static com.aevi.sdk.flow.service.BaseApiService.BACKGROUND_PROCESSING;


/**
 * This class can be used to communicate with clients.
 *
 * A communicator instance will be created for your Flow Service on connection from a client
 */
public class ClientCommunicator {

    private static final String TAG = ClientCommunicator.class.getSimpleName();

    private final ChannelServer channelServer;
    private final InternalData internalData;
    private final Set<ActivityHelper> activityHelpers;

    ClientCommunicator(ChannelServer channelServer, InternalData internalData) {
        this.channelServer = channelServer;
        this.internalData = internalData;
        this.activityHelpers = new HashSet<>();
    }

    void sendAck() {
        Log.d(TAG, "Sending ack");
        AppMessage appMessage = new AppMessage(REQUEST_ACK_MESSAGE, internalData);
        channelServer.send(appMessage.toJson());
    }

    /**
     * Send a message to the client and end the communication stream
     *
     * @param response The response to send
     */
    public void sendResponseAndEnd(@NonNull String response) {
        if (channelServer != null) {
            AppMessage appMessage = new AppMessage(RESPONSE_MESSAGE, response, internalData);
            channelServer.send(appMessage.toJson());
            channelServer.sendEndStream();
        }
    }

    /**
     * Send an error message to the client
     *
     * @param flowError The {@link FlowException} to send
     */
    public void send(FlowException flowError) {
        if (channelServer != null) {
            channelServer.send(flowError.toJson());
        }
    }

    /**
     * Finish your flow service with no response
     */
    public void finishWithNoResponse() {
        if (channelServer != null) {
            AppMessage appMessage = new AppMessage(RESPONSE_MESSAGE, EMPTY_DATA, internalData);
            channelServer.send(appMessage.toJson());
            channelServer.sendEndStream();
        }
    }

    /**
     * Finish your flow service with an exception.
     *
     * Can be used to send an errorCode back to the initiating application
     *
     * @param errorCode The errorCode to send back
     * @param message   A human readable message to explain the error
     */
    public void sendResponseAsErrorAndEnd(@NonNull String errorCode, @NonNull String message) {
        if (channelServer != null) {
            FlowException flowServiceException = new FlowException(errorCode, message);
            String msg = flowServiceException.toJson();
            Log.d(TAG, "Sending error message: " + msg);
            AppMessage errorMessage = new AppMessage(FAILURE_MESSAGE, msg, internalData);
            channelServer.send(errorMessage.toJson());
            channelServer.sendEndStream();
        }
    }

    /**
     * Send notification that this service will process in the background and won't send back any response.
     *
     * Note that you should NOT show any UI after calling this, nor call any of the "finish...Response" methods.
     *
     * This is typically useful for post-transaction / post-flow services that processes the transaction information with no need
     * to show any user interface or augment the transaction.
     */
    public void notifyBackgroundProcessing() {
        Log.d(TAG, "notifyBackgroundProcessing");
        internalData.addAdditionalData(BACKGROUND_PROCESSING, "true");
        sendResponseAndEnd(EMPTY_DATA);
    }

    Observable<String> subscribeToMessages() {
        return channelServer.subscribeToMessages();
    }

    void finishStartedActivities() {
        for (ActivityHelper activityHelper : activityHelpers) {
            activityHelper.finishLaunchedActivity();
        }
    }

    /**
     * Add any {@link ActivityHelper} classes used to start activities for this client connection
     *
     * @param activityHelper The Activity helper class to add
     */
    public void addActivityHelper(ActivityHelper activityHelper) {
        activityHelpers.add(activityHelper);
    }
}
