package models;

import org.json.JSONObject;

public class UserProfile {
    String user_id;
    String businessId;
    String salutation;
    String email;
    String first_name;
    String last_name;
    String role;
    String role_id;
   String  is_active;
    public UserProfile(){}
    public UserProfile(String value) {
        try {
            JSONObject jsonObj=new JSONObject(value);
            JSONObject jsonObject=jsonObj.getJSONObject("user_details");
            user_id = jsonObject.isNull("user_id") ? "" : jsonObject.getString("user_id");
            salutation = jsonObject.isNull("salutation") ? "" : jsonObject.getString("salutation");
            email = jsonObject.isNull("email") ? "" : jsonObject.getString("email");
            first_name = jsonObject.isNull("first_name") ? "" : jsonObject.getString("first_name");
            last_name = jsonObject.isNull("last_name") ? "" : jsonObject.getString("last_name");
            role = jsonObject.isNull("role") ? "" : jsonObject.getString("role");
            role_id = jsonObject.isNull("role_id") ? "" : jsonObject.getString("role_id");
            is_active= jsonObject.isNull("is_active") ? "" : jsonObject.getString("is_active");
        } catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }



    public UserProfile(JSONObject jsonObject){
        try {


            user_id = jsonObject.isNull("id") ? "" : jsonObject.getString("id");
            businessId = jsonObject.isNull("business_id") ? "" : jsonObject.getString("business_id");
            salutation = jsonObject.isNull("salutation") ? "" : jsonObject.getString("salutation");
            email = jsonObject.isNull("email") ? "" : jsonObject.getString("email");
            first_name = jsonObject.isNull("first_name") ? "" : jsonObject.getString("first_name");
            last_name = jsonObject.isNull("last_name") ? "" : jsonObject.getString("last_name");
            role = jsonObject.isNull("user_type_name") ? "" : jsonObject.getString("user_type_name");
            role_id = jsonObject.isNull("user_type") ? "" : jsonObject.getString("user_type");
            is_active= jsonObject.isNull("is_active") ? "" : jsonObject.getString("is_active");
        } catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getIs_active() {
        return is_active;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getRole() {
        return role;
    }

    public String getRole_id() {
        return role_id;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getUser_id() {
        return user_id;
    }
}
