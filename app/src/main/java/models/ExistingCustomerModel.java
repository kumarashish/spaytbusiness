package models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ExistingCustomerModel {
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("existing_consumers")
    @Expose
    private List<ExistingConsumer> existingConsumers = null;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ExistingConsumer> getExistingConsumers() {
        return existingConsumers;
    }

    public void setExistingConsumers(List<ExistingConsumer> existingConsumers) {
        this.existingConsumers = existingConsumers;
    }
    public class ExistingConsumer {

        @SerializedName("consumer_id")
        @Expose
        private String consumerId;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private Object lastName;
        @SerializedName("total_orders")
        @Expose
        private String totalOrders;

        public String getConsumerId() {
            return consumerId;
        }

        public void setConsumerId(String consumerId) {
            this.consumerId = consumerId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public Object getLastName() {
            return lastName;
        }

        public void setLastName(Object lastName) {
            this.lastName = lastName;
        }

        public String getTotalOrders() {
            return totalOrders;
        }

        public void setTotalOrders(String totalOrders) {
            this.totalOrders = totalOrders;
        }

    }
}
