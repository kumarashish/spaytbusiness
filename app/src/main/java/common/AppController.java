package common;

import android.app.Application;

import com.google.gson.Gson;

import java.util.ArrayList;

import models.BusinessProductModel;
import models.BusinessProfile;
import models.UserProfile;
import utils.Validation;
import utils.WebApiCall;

public class    AppController extends Application {
    AppController controller;
    WebApiCall webApiCall;
    PrefManager manager;
    UserProfile profile;
    BusinessProfile businessProfile;
    ArrayList<BusinessProductModel>myCart=new ArrayList<>();
    @Override
    public void onCreate() {
        controller=this;
        webApiCall=new WebApiCall(getApplicationContext());
        manager=new PrefManager(getApplicationContext());
        if(manager.getUserProfile().length()>0)
        {
            if (manager.getUserProfile().length() > 0) {
                Gson gson = new Gson();
                profile = gson.fromJson(manager.getUserProfile(), UserProfile.class);
                businessProfile=gson.fromJson(manager.getBusinessProfile(),BusinessProfile.class);
            }
        }else {
            profile=new UserProfile();
            businessProfile=new BusinessProfile();
        }
        super.onCreate();
    }

    public int getIndexOfModel(BusinessProductModel model)
    {
        return myCart.indexOf(model);
    }
public void updateModel(BusinessProductModel model,int index)
{
    myCart.set(index,model);

}
    public ArrayList<BusinessProductModel> getGetMyCart() {
        return myCart;
    }

    public void addProduct(BusinessProductModel model)
    {
      myCart.add(model);
    }


    public int getQuantity()
    {int quantity=0;
        if(myCart.size()>0)
        {
            for(int i=0;i<myCart.size();i++)
            {
                quantity+=myCart.get(i).getQuantity();
            }
        }
        return quantity;
    }

    public Double getTotalPrice() {
        double price = 0;
        if (myCart.size() > 0) {
            for (int i = 0; i < myCart.size(); i++) {
                double val = Double.parseDouble(myCart.get(i).getPrice()) * myCart.get(i).getQuantity();
                price += val;
            }
        }
        return price;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
        Gson gson = new Gson();
        String userProfileString = gson.toJson(profile);
        manager.setUserProfile(userProfileString);

    }
    public void setBusinessProfile(BusinessProfile profile) {
        this.businessProfile = profile;
        Gson gson = new Gson();
        String userProfileString = gson.toJson(profile);
        manager.setBusinessProfile(userProfileString);

    }
    public AppController getController() {
        return controller;
    }

    public WebApiCall getWebApiCall() {
        return webApiCall;
    }

    public PrefManager getManager() {
        return manager;
    }

    public BusinessProfile getBusinessProfile() {
        return businessProfile;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void logout()
    {
        manager.setUserToken("");
        manager.setBusinessProfile("");
        manager.setUserProfile("");
        manager.setUserLoggedIn(false);
        profile=new UserProfile();
        businessProfile=new BusinessProfile();
    }
}
