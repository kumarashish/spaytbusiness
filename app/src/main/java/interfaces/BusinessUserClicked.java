package interfaces;

import models.BusinessProfile;
import models.Business_locations;
import models.UserProfile;

/**
 * Created by ashish.kumar on 26-02-2019.
 */

public interface BusinessUserClicked {
    public void onUserSelected(UserProfile model);
    public void onDeleteCLicked(UserProfile model);
}
