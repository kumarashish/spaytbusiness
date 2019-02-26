package models;

import org.json.JSONObject;

public class Business_locations {
    String id;
    String business_id;
    String location_name;
    String street_name;
    String door_no;
    String city;
    String zip_code;
    String latitude;
    String longitude;
    String phone_number;
    String image_url;
    String description;
    String monday_mode;
    String tuesday_mode;
    String wednesday_mode;
    String thursday_mode;
    String friday_mode;
    String saturday_mode;
    String sunday_mode;
    String monday_morning_from;
    String monday_morning_to;
    String monday_afternoon_from;
    String monday_afternoon_to;
    String tuesday_morning_from;
    String tuesday_morning_to;
    String tuesday_afternoon_from;
    String tuesday_afternoon_to;
    String wednesday_morning_from;
    String wednesday_morning_to;
    String wednesday_afternoon_from;
    String wednesday_afternoon_to;
    String thursday_morning_from;
    String thursday_morning_to;
    String thursday_afternoon_from;
    String thursday_afternoon_to;
    String friday_morning_from;
    String friday_morning_to;
    String friday_afternoon_from;
    String friday_afternoon_to;
    String saturday_morning_from;
    String saturday_morning_to;
    String saturday_afternoon_from;
    String saturday_afternoon_to;
    String sunday_morning_from;
    String sunday_morning_to;
    String sunday_afternoon_from;
    String sunday_afternoon_to;


    public Business_locations(JSONObject jsonObject) {
        try {
            id = jsonObject.isNull("id") ? "" : jsonObject.getString("id");
            location_name = jsonObject.isNull("location_name") ? "" : jsonObject.getString("location_name");
            street_name = jsonObject.isNull("street_name") ? "" : jsonObject.getString("street_name");
            door_no = jsonObject.isNull("door_no") ? "" : jsonObject.getString("door_no");
            city = jsonObject.isNull("city") ? "" : jsonObject.getString("city");
            zip_code = jsonObject.isNull("zip_code") ? "" : jsonObject.getString("zip_code");
            latitude = jsonObject.isNull("latitude") ? "" : jsonObject.getString("latitude");
            longitude = jsonObject.isNull("longitude") ? "" : jsonObject.getString("longitude");
            phone_number = jsonObject.isNull("phone_number") ? "" : jsonObject.getString("phone_number");
            image_url = jsonObject.isNull("image_url") ? "" : jsonObject.getString("image_url");
            description = jsonObject.isNull("description") ? "" : jsonObject.getString("description");
            business_id = jsonObject.isNull("business_id") ? "" : jsonObject.getString("business_id");
            monday_mode = jsonObject.isNull("monday_mode") ? "" : jsonObject.getString("monday_mode");
            tuesday_mode = jsonObject.isNull("tuesday_mode") ? "" : jsonObject.getString("tuesday_mode");
            wednesday_mode = jsonObject.isNull("wednesday_mode") ? "" : jsonObject.getString("wednesday_mode");
            thursday_mode = jsonObject.isNull("thursday_mode") ? "" : jsonObject.getString("thursday_mode");
            friday_mode = jsonObject.isNull("friday_mode") ? "" : jsonObject.getString("friday_mode");
            saturday_mode = jsonObject.isNull("saturday_mode") ? "" : jsonObject.getString("saturday_mode");
            sunday_mode = jsonObject.isNull("sunday_mode") ? "" : jsonObject.getString("sunday_mode");
            monday_morning_from = jsonObject.isNull("monday_morning_from") ? "" : jsonObject.getString("monday_morning_from");
            monday_morning_to = jsonObject.isNull("monday_morning_to") ? "" : jsonObject.getString("monday_morning_to");
            monday_afternoon_from = jsonObject.isNull("monday_afternoon_from") ? "" : jsonObject.getString("monday_afternoon_from");
            monday_afternoon_to = jsonObject.isNull("monday_afternoon_to") ? "" : jsonObject.getString("monday_afternoon_to");
            tuesday_morning_from = jsonObject.isNull("tuesday_morning_from") ? "" : jsonObject.getString("tuesday_morning_from");
            tuesday_morning_to = jsonObject.isNull("tuesday_morning_to") ? "" : jsonObject.getString("tuesday_morning_to");
            tuesday_afternoon_from = jsonObject.isNull("tuesday_afternoon_from") ? "" : jsonObject.getString("tuesday_afternoon_from");
            tuesday_afternoon_to = jsonObject.isNull("tuesday_afternoon_to") ? "" : jsonObject.getString("tuesday_afternoon_to");
            wednesday_morning_from = jsonObject.isNull("wednesday_morning_from") ? "" : jsonObject.getString("wednesday_morning_from");
            wednesday_morning_to = jsonObject.isNull("wednesday_morning_to") ? "" : jsonObject.getString("wednesday_morning_to");
            wednesday_afternoon_from = jsonObject.isNull("wednesday_afternoon_from") ? "" : jsonObject.getString("wednesday_afternoon_from");
            wednesday_afternoon_to = jsonObject.isNull("wednesday_afternoon_to") ? "" : jsonObject.getString("wednesday_afternoon_to");
            thursday_morning_from = jsonObject.isNull("thursday_morning_from") ? "" : jsonObject.getString("thursday_morning_from");
            thursday_morning_to = jsonObject.isNull("thursday_morning_to") ? "" : jsonObject.getString("thursday_morning_to");
            thursday_afternoon_from = jsonObject.isNull("thursday_afternoon_from") ? "" : jsonObject.getString("thursday_afternoon_from");
            thursday_afternoon_to = jsonObject.isNull("thursday_afternoon_to") ? "" : jsonObject.getString("thursday_afternoon_to");
            friday_morning_from = jsonObject.isNull("friday_morning_from") ? "" : jsonObject.getString("friday_morning_from");
            friday_morning_to = jsonObject.isNull("friday_morning_to") ? "" : jsonObject.getString("friday_morning_to");
            friday_afternoon_from = jsonObject.isNull("friday_afternoon_from") ? "" : jsonObject.getString("friday_afternoon_from");
            friday_afternoon_to = jsonObject.isNull("friday_afternoon_to") ? "" : jsonObject.getString("friday_afternoon_to");
            saturday_morning_from = jsonObject.isNull("saturday_morning_from") ? "" : jsonObject.getString("saturday_morning_from");
            saturday_morning_to = jsonObject.isNull("saturday_morning_to") ? "" : jsonObject.getString("saturday_morning_to");
            saturday_afternoon_from = jsonObject.isNull("saturday_afternoon_from") ? "" : jsonObject.getString("saturday_afternoon_from");
            saturday_afternoon_to = jsonObject.isNull("saturday_afternoon_to") ? "" : jsonObject.getString("saturday_afternoon_to");
            sunday_morning_from = jsonObject.isNull("sunday_morning_from") ? "" : jsonObject.getString("sunday_morning_from");
            sunday_morning_to = jsonObject.isNull("sunday_morning_to") ? "" : jsonObject.getString("sunday_morning_to");
            sunday_afternoon_from = jsonObject.isNull("sunday_afternoon_from") ? "" : jsonObject.getString("sunday_afternoon_from");
            sunday_afternoon_to = jsonObject.isNull("sunday_afternoon_to") ? "" : jsonObject.getString("sunday_afternoon_to");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public String getBusiness_id() {
        return business_id;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getDoor_no() {
        return door_no;
    }

    public String getZip_code() {
        return zip_code;
    }

    public String getStreet_name() {
        return street_name;
    }

    public String getLocation_name() {
        return location_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getFriday_afternoon_from() {
        return friday_afternoon_from;
    }

    public String getFriday_afternoon_to() {
        return friday_afternoon_to;
    }

    public String getMonday_mode() {
        return monday_mode;
    }

    public String getFriday_mode() {
        return friday_mode;
    }

    public String getFriday_morning_from() {
        return friday_morning_from;
    }

    public String getFriday_morning_to() {
        return friday_morning_to;
    }

    public String getMonday_afternoon_from() {
        return monday_afternoon_from;
    }

    public String getMonday_afternoon_to() {
        return monday_afternoon_to;
    }

    public String getThursday_mode() {
        return thursday_mode;
    }

    public String getSaturday_mode() {
        return saturday_mode;
    }

    public String getMonday_morning_from() {
        return monday_morning_from;
    }

    public String getMonday_morning_to() {
        return monday_morning_to;
    }

    public String getSaturday_afternoon_from() {
        return saturday_afternoon_from;
    }

    public String getSaturday_afternoon_to() {
        return saturday_afternoon_to;
    }

    public String getSaturday_morning_from() {
        return saturday_morning_from;
    }

    public String getSaturday_morning_to() {
        return saturday_morning_to;
    }

    public String getSunday_afternoon_from() {
        return sunday_afternoon_from;
    }

    public String getSunday_mode() {
        return sunday_mode;
    }

    public String getSunday_afternoon_to() {
        return sunday_afternoon_to;
    }

    public String getSunday_morning_from() {
        return sunday_morning_from;
    }

    public String getSunday_morning_to() {
        return sunday_morning_to;
    }

    public String getTuesday_mode() {
        return tuesday_mode;
    }

    public String getThursday_afternoon_from() {
        return thursday_afternoon_from;
    }

    public String getThursday_afternoon_to() {
        return thursday_afternoon_to;
    }

    public String getThursday_morning_from() {
        return thursday_morning_from;
    }

    public String getThursday_morning_to() {
        return thursday_morning_to;
    }

    public String getTuesday_afternoon_from() {
        return tuesday_afternoon_from;
    }

    public String getTuesday_afternoon_to() {
        return tuesday_afternoon_to;
    }

    public String getWednesday_mode() {
        return wednesday_mode;
    }

    public String getTuesday_morning_from() {
        return tuesday_morning_from;
    }

    public String getTuesday_morning_to() {
        return tuesday_morning_to;
    }

    public String getWednesday_afternoon_from() {
        return wednesday_afternoon_from;
    }

    public String getWednesday_afternoon_to() {
        return wednesday_afternoon_to;
    }

    public String getWednesday_morning_from() {
        return wednesday_morning_from;
    }

    public String getWednesday_morning_to() {
        return wednesday_morning_to;
    }
}


