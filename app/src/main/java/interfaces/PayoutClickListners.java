package interfaces;


import java.util.List;

import models.PayoutModel.PayoutConsumerOrder;

public interface PayoutClickListners {
    public void onDeleteClick(String orderId);
    public void onDetailsClick(List<PayoutConsumerOrder> model);
}
