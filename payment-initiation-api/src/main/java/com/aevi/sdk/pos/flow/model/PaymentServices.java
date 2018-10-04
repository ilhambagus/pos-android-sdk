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

package com.aevi.sdk.pos.flow.model;


import com.aevi.sdk.flow.util.ComparisonUtil;
import com.aevi.sdk.pos.flow.PaymentClient;

import java.util.*;

/**
 * Represents the available payment services and exposes a range of helper functions to retrieve relevant information from the payment services
 * as a whole.
 */
public class PaymentServices {

    private final List<PaymentServiceInfo> paymentServiceInfoList;

    public PaymentServices(List<PaymentServiceInfo> paymentServiceInfoList) {
        this.paymentServiceInfoList = paymentServiceInfoList;
    }

    /**
     * Retrieve the full list of available payment services.
     *
     * @return The full list of available payment services
     */
    public List<PaymentServiceInfo> getAllPaymentServices() {
        return paymentServiceInfoList;
    }

    /**
     * Check whether a particular currency is supported by at least one of the payment services.
     *
     * @param currency The currency to check if supported
     * @return True if at least one payment service support it, false otherwise
     */
    public boolean isCurrencySupported(String currency) {
        return ComparisonUtil.stringCollectionContainsIgnoreCase(getAllSupportedCurrencies(), currency);
    }

    /**
     * Retrieve a consolidated set of supported currencies across all the payment services.
     *
     * @return A consolidated set of supported currencies across all the payment services
     */
    public Set<String> getAllSupportedCurrencies() {
        Set<String> currencies = new HashSet<>();
        for (PaymentServiceInfo serviceInfo : paymentServiceInfoList) {
            currencies.addAll(Arrays.asList(serviceInfo.getSupportedCurrencies()));
        }
        return currencies;
    }

    /**
     * Check whether a particular request type is supported by at least one of the payment services.
     *
     * @param requestType The request type to check if supported
     * @return True if at least one payment service support it, false otherwise
     */
    public boolean isRequestTypeSupported(String requestType) {
        return ComparisonUtil.stringCollectionContainsIgnoreCase(getAllSupportedRequestTypes(), requestType);
    }

    /**
     * Retrieve a consolidated set of all the supported request types across all the payment services.
     *
     * @return A consolidated set of all the supported request types across all the payment services
     */
    public Set<String> getAllSupportedRequestTypes() {
        Set<String> requestTypes = new HashSet<>();
        for (PaymentServiceInfo serviceInfo : paymentServiceInfoList) {
            requestTypes.addAll(Arrays.asList(serviceInfo.getSupportedRequestTypes()));
        }
        return requestTypes;
    }

    /**
     * Check whether a particular transaction type is supported by at least one of the payment services.
     *
     * @param transactionType The transaction type to check if supported
     * @return True if at least one payment service support it, false otherwise
     */
    public boolean isTransactionTypeSupported(String transactionType) {
        return ComparisonUtil.stringCollectionContainsIgnoreCase(getAllSupportedTransactionTypes(), transactionType);
    }

    /**
     * Retrieve a consolidated set of supported transaction types across all the payment services.
     *
     * Note that not all of these types may be allowed for use - see {@link PaymentClient#getSupportedTransactionTypes()} ()} for a filtered list.
     *
     * @return A consolidated set of supported transaction types across all the payment services
     */
    public Set<String> getAllSupportedTransactionTypes() {
        Set<String> txnTypes = new HashSet<>();
        for (PaymentServiceInfo serviceInfo : paymentServiceInfoList) {
            txnTypes.addAll(Arrays.asList(serviceInfo.getSupportedTransactionTypes()));
        }
        return txnTypes;
    }

    /**
     * Retrieve a consolidated set of supported payment methods across all the payment services.
     *
     * @return A consolidated set of supported payment methods across all the payment services
     */
    public Set<String> getAllSupportedPaymentMethods() {
        Set<String> paymentMethods = new HashSet<>();
        for (PaymentServiceInfo serviceInfo : paymentServiceInfoList) {
            paymentMethods.addAll(Arrays.asList(serviceInfo.getPaymentMethods()));
        }
        return paymentMethods;
    }

    /**
     * Check whether a particular data key (as used with {@link com.aevi.sdk.flow.model.AdditionalData}) is supported by at least one of the payment services.
     *
     * @param dataKey The data key to check if supported
     * @return True if at least one payment service support it, false otherwise
     */
    public boolean isDataKeySupported(String dataKey) {
        return ComparisonUtil.stringCollectionContainsIgnoreCase(getAllSupportedDataKeys(), dataKey);
    }

    /**
     * Retrieve a consolidated set of supported data keys ((as used with {@link com.aevi.sdk.flow.model.AdditionalData}) across all the payment services.
     *
     * @return A consolidated set of supported data keys across all the payment services
     */
    public Set<String> getAllSupportedDataKeys() {
        Set<String> dataKeys = new HashSet<>();
        for (PaymentServiceInfo serviceInfo : paymentServiceInfoList) {
            dataKeys.addAll(Arrays.asList(serviceInfo.getSupportedDataKeys()));
        }
        return dataKeys;
    }

    /**
     * Retrieve a list of all the payment services that support the separate card reading flow step.
     *
     * @return A list of all payment services that supports the separate card reading flow step
     */
    public List<PaymentServiceInfo> getPaymentServicesSupportingCardReadingStep() {
        List<PaymentServiceInfo> paymentServices = new ArrayList<>();
        for (PaymentServiceInfo serviceInfo : paymentServiceInfoList) {
            if (serviceInfo.supportsFlowCardReading()) {
                paymentServices.add(serviceInfo);
            }
        }
        return paymentServices;
    }

    /**
     * Retrieve a list of all the payment services that support accessibility mode for visually impaired users.
     *
     * @return A list of all payment services that supports accessibility mode for visually impaired users
     */
    public List<PaymentServiceInfo> getPaymentServicesSupportingAccessibilityMode() {
        List<PaymentServiceInfo> paymentServices = new ArrayList<>();
        for (PaymentServiceInfo serviceInfo : paymentServiceInfoList) {
            if (serviceInfo.supportsAccessibilityMode()) {
                paymentServices.add(serviceInfo);
            }
        }
        return paymentServices;
    }

}