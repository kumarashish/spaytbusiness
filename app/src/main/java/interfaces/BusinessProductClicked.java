package interfaces;

import models.BusinessProductModel;
import models.BusinessProfile;

/**
 * Created by ashish.kumar on 26-02-2019.
 */

public interface BusinessProductClicked {
    public void onProductSelected(BusinessProductModel model);
    public void onDeleteCLicked(BusinessProductModel model);
}
