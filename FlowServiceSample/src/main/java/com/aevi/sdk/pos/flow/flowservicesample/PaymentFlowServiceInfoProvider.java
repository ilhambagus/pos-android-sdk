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

package com.aevi.sdk.pos.flow.flowservicesample;


import com.aevi.sdk.pos.flow.model.PaymentFlowServiceInfo;
import com.aevi.sdk.pos.flow.model.PaymentFlowServiceInfoBuilder;
import com.aevi.sdk.pos.flow.provider.BasePaymentFlowServiceInfoProvider;

import static com.aevi.sdk.flow.constants.FlowTypes.FLOW_TYPE_RECEIPT_DELIVERY;
import static com.aevi.sdk.flow.constants.FlowTypes.FLOW_TYPE_SALE;
import static com.aevi.sdk.flow.constants.PaymentMethods.*;
import static com.aevi.sdk.pos.flow.flowservicesample.service.GenericRequestService.SHOW_LOYALTY_POINTS_REQUEST;

public class PaymentFlowServiceInfoProvider extends BasePaymentFlowServiceInfoProvider {

    @Override
    protected PaymentFlowServiceInfo getPaymentFlowServiceInfo() {
        return new PaymentFlowServiceInfoBuilder()
                .withVendor("AEVI")
                .withDisplayName("Flow Service Sample")
                .withCanAdjustAmounts(true)
                .withCanPayAmounts(true, PAYMENT_METHOD_LOYALTY_POINTS, PAYMENT_METHOD_GIFT_CARD, PAYMENT_METHOD_CASH)
                .withSupportedFlowTypes(FLOW_TYPE_SALE, FLOW_TYPE_RECEIPT_DELIVERY)
                .withCustomRequestTypes(SHOW_LOYALTY_POINTS_REQUEST)
                .build(getContext());
    }
}
