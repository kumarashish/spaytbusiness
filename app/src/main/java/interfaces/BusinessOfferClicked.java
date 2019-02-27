package interfaces;

import models.BusinessProductModel;
import models.Business_Offers;

public interface BusinessOfferClicked {
    public void onOfferSelected(Business_Offers model);
    public void onDeleteCLicked(Business_Offers model);
}
