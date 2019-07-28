package models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

public class PayoutModel {
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("payouts_details")
    @Expose
    private List<PayoutsDetail> payoutsDetails = null;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<PayoutsDetail> getPayoutsDetails() {
        return payoutsDetails;
    }

    public void setPayoutsDetails(List<PayoutsDetail> payoutsDetails) {
        this.payoutsDetails = payoutsDetails;
    }

    public void updatePayoutsDetails(PayoutsDetail payoutsDetails) {
        if (this.payoutsDetails == null) {
            this.payoutsDetails = new ArrayList<>();
        }
        this.payoutsDetails.add(payoutsDetails);
    }

    public static class PayoutConsumerOrder {

        @SerializedName("business_id")
        @Expose
        private String businessId;
        @SerializedName("consumer_order_id")
        @Expose
        private String consumerOrderId;
        @SerializedName("total_amount")
        @Expose
        private String totalAmount;
        @SerializedName("paypal_fee")
        @Expose
        private String paypalFee;
        @SerializedName("commission_fee")
        @Expose
        private String commissionFee;
        @SerializedName("created_on")
        @Expose
        private String createdOn;

        public PayoutConsumerOrder(JSONObject jsonObject) {
            try {
                businessId = jsonObject.isNull("business_id") ? "" : jsonObject.getString("business_id");
                consumerOrderId = jsonObject.isNull("consumer_order_id") ? "" : jsonObject.getString("consumer_order_id");
                totalAmount = jsonObject.isNull("total_amount") ? "" : jsonObject.getString("total_amount");
                paypalFee = jsonObject.isNull("paypal_fee") ? "" : jsonObject.getString("paypal_fee");
                commissionFee = jsonObject.isNull("commission_fee") ? "" : jsonObject.getString("commission_fee");
                createdOn = jsonObject.isNull("created_on") ? "" : jsonObject.getString("created_on");

            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }
        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getConsumerOrderId() {
            return consumerOrderId;
        }

        public void setConsumerOrderId(String consumerOrderId) {
            this.consumerOrderId = consumerOrderId;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getPaypalFee() {
            return paypalFee;
        }

        public void setPaypalFee(String paypalFee) {
            this.paypalFee = paypalFee;
        }

        public String getCommissionFee() {
            return commissionFee;
        }

        public void setCommissionFee(String commissionFee) {
            this.commissionFee = commissionFee;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }
    }

    public static class PayoutsDetail {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("business_id")
        @Expose
        private String businessId;
        @SerializedName("total_amount")
        @Expose
        private String totalAmount;
        @SerializedName("paypal_fee")
        @Expose
        private String paypalFee;
        @SerializedName("commission_fee")
        @Expose
        private String commissionFee;
        @SerializedName("payout_total_amount")
        @Expose
        private String payoutTotalAmount;
        @SerializedName("paypal_transaction_id")
        @Expose
        private Object paypalTransactionId;
        @SerializedName("payout_status")
        @Expose
        private String payoutStatus;
        @SerializedName("paypal_transaction_on")
        @Expose
        private Object paypalTransactionOn;
        @SerializedName("completed_on")
        @Expose
        private Object completedOn;
        @SerializedName("created_on")
        @Expose
        private String createdOn;
        @SerializedName("payout_consumer_orders")
        @Expose
        public List<PayoutConsumerOrder> payoutConsumerOrders = null;

        public PayoutsDetail(JSONObject jsonObject) {
            try {

                id = jsonObject.isNull("id") ? "" : jsonObject.getString("id");
                businessId = jsonObject.isNull("business_id") ? "" : jsonObject.getString("business_id");
                totalAmount = jsonObject.isNull("total_amount") ? "" : jsonObject.getString("total_amount");
                paypalFee = jsonObject.isNull("paypal_fee") ? "" : jsonObject.getString("paypal_fee");
                commissionFee = jsonObject.isNull("commission_fee") ? "" : jsonObject.getString("commission_fee");
                payoutTotalAmount = jsonObject.isNull("payout_total_amount") ? "" : jsonObject.getString("payout_total_amount");
                paypalTransactionId = jsonObject.isNull("paypal_transaction_id") ? "" : jsonObject.getString("paypal_transaction_id");
                payoutStatus= jsonObject.isNull("payout_status") ? "" : jsonObject.getString("payout_status");
                paypalTransactionOn = jsonObject.isNull("paypal_transaction_on") ? "" : jsonObject.getString("paypal_transaction_on");
                completedOn = jsonObject.isNull("completed_on") ? "" : jsonObject.getString("completed_on");
                createdOn = jsonObject.isNull("created_on") ? "" : jsonObject.getString("created_on");
                JSONArray jsonArray = jsonObject.isNull("payout_consumer_orders") ? null : jsonObject.getJSONArray("payout_consumer_orders");
                payoutConsumerOrders = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    payoutConsumerOrders.add(new PayoutConsumerOrder(jsonArray.getJSONObject(i)));
                }
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getPaypalFee() {
            return paypalFee;
        }

        public void setPaypalFee(String paypalFee) {
            this.paypalFee = paypalFee;
        }

        public String getCommissionFee() {
            return commissionFee;
        }

        public void setCommissionFee(String commissionFee) {
            this.commissionFee = commissionFee;
        }

        public String getPayoutTotalAmount() {
            return payoutTotalAmount;
        }

        public void setPayoutTotalAmount(String payoutTotalAmount) {
            this.payoutTotalAmount = payoutTotalAmount;
        }

        public Object getPaypalTransactionId() {
            return paypalTransactionId;
        }

        public void setPaypalTransactionId(Object paypalTransactionId) {
            this.paypalTransactionId = paypalTransactionId;
        }

        public String getPayoutStatus() {
            return payoutStatus;
        }

        public void setPayoutStatus(String payoutStatus) {
            this.payoutStatus = payoutStatus;
        }

        public Object getPaypalTransactionOn() {
            return paypalTransactionOn;
        }

        public void setPaypalTransactionOn(Object paypalTransactionOn) {
            this.paypalTransactionOn = paypalTransactionOn;
        }

        public Object getCompletedOn() {
            return completedOn;
        }

        public void setCompletedOn(Object completedOn) {
            this.completedOn = completedOn;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public List<PayoutConsumerOrder> getPayoutConsumerOrders() {
            return payoutConsumerOrders;
        }

        public void setPayoutConsumerOrders(List<PayoutConsumerOrder> payoutConsumerOrders) {
            this.payoutConsumerOrders = payoutConsumerOrders;
        }

    }
}
