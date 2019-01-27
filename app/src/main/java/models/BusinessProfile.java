package models;

import org.json.JSONObject;

public class BusinessProfile {
    String id;
    String category_name;
    String company_name;
    String street_name;
    String door_no;
    String city;
    String zip_code;
    String latitude;
    String longitude;
    String paypal_email;
    String vat_id;
    public BusinessProfile(){}
    public BusinessProfile(String value)
    {
        try{
            JSONObject jsonObj=new JSONObject(value);
            JSONObject jsonObject=jsonObj.getJSONObject("business_details");
            id=jsonObject.isNull("id")?"":jsonObject.getString("id");
            category_name=jsonObject.isNull("category_name")?"":jsonObject.getString("category_name");;
            company_name=jsonObject.isNull("company_name")?"":jsonObject.getString("company_name");;
            street_name=jsonObject.isNull("street_name")?"":jsonObject.getString("street_name");;
           door_no=jsonObject.isNull("door_no")?"":jsonObject.getString("door_no");;
            city=jsonObject.isNull("city")?"":jsonObject.getString("city");;
            zip_code=jsonObject.isNull("zip_code")?"":jsonObject.getString("zip_code");;
            latitude=jsonObject.isNull("latitude")?"":jsonObject.getString("latitude");;
            longitude=jsonObject.isNull("longitude")?"":jsonObject.getString("longitude");;
            paypal_email=jsonObject.isNull("paypal_email")?"":jsonObject.getString("paypal_email");
            vat_id=jsonObject.isNull("vat_id")?"":jsonObject.getString("vat_id");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getCity() {
        return city;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getDoor_no() {
        return door_no;
    }

    public String getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPaypal_email() {
        return paypal_email;
    }

    public String getStreet_name() {
        return street_name;
    }

    public String getVat_id() {
        return vat_id;
    }

    public String getZip_code() {
        return zip_code;
    }
}