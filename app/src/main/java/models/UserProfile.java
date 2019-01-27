package models;

import org.json.JSONObject;

public class UserProfile {
    String user_id;
    String salutation;
    String email;
    String first_name;
    String last_name;
    String role;
    String role_id;
    public UserProfile(){}
    public UserProfile(String value) {
        try {
            JSONObject jsonObj=new JSONObject(value);
            JSONObject jsonObject=jsonObj.getJSONObject("user_details");
            user_id = jsonObject.isNull("user_id") ? "" : jsonObject.getString("user_id");
            salutation = jsonObject.isNull("salutation") ? "" : jsonObject.getString("salutationd");
            email = jsonObject.isNull("email") ? "" : jsonObject.getString("email");
            first_name = jsonObject.isNull("first_name") ? "" : jsonObject.getString("first_name");
            last_name = jsonObject.isNull("last_name") ? "" : jsonObject.getString("last_name");
            role = jsonObject.isNull("role") ? "" : jsonObject.getString("role");
            role_id = jsonObject.isNull("role_id") ? "" : jsonObject.getString("role_id");
        } catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
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
