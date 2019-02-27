package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Business_Offers {
    String id;
    String business_id;
    String offer_name;
    String offer_description;
    String image_url;
    String from_date;
    String to_date;
    String opening_hour_mode;
    String morning_from;
    String morning_to;
    String afternoon_from;
    String afternoon_to;
    String total_price;
    String price_per_liter;
    String parking_fee_per_hour;
    String minimum_parking_hours;
    String maximum_parking_fee_perday;
    String product_id;
    String product_name;
    ArrayList<Business_locations> locations=new ArrayList<>();

    public Business_Offers(JSONObject jsonObject)
    {try {
        id=jsonObject.isNull("id")?"":jsonObject.getString("id");
        business_id=jsonObject.isNull("business_id")?"":jsonObject.getString("business_id");
        offer_name=jsonObject.isNull("offer_name")?"":jsonObject.getString("offer_name");
                offer_description=jsonObject.isNull("offer_description")?"":jsonObject.getString("offer_description");
                image_url=jsonObject.isNull("image_url")?"":jsonObject.getString("image_url");
                from_date=jsonObject.isNull("from_date")?"":jsonObject.getString("from_date");
                to_date=jsonObject.isNull("to_date")?"":jsonObject.getString("to_date");
                opening_hour_mode=jsonObject.isNull("opening_hour_mode")?"":jsonObject.getString("opening_hour_mode");
                morning_from=jsonObject.isNull("morning_from")?"":jsonObject.getString("morning_from");
               morning_to=jsonObject.isNull("morning_to")?"":jsonObject.getString("morning_to");
               afternoon_from=jsonObject.isNull("afternoon_from")?"":jsonObject.getString("afternoon_from");
               afternoon_to=jsonObject.isNull("afternoon_to")?"":jsonObject.getString("afternoon_to");
               total_price=jsonObject.isNull("total_price")?"":jsonObject.getString("total_price");
               price_per_liter=jsonObject.isNull("price_per_liter")?"":jsonObject.getString("price_per_liter");
               parking_fee_per_hour=jsonObject.isNull("parking_fee_per_hour")?"":jsonObject.getString("parking_fee_per_hour");
               minimum_parking_hours=jsonObject.isNull("minimum_parking_hours")?"":jsonObject.getString("minimum_parking_hours");
                maximum_parking_fee_perday=jsonObject.isNull("maximum_parking_fee_perday")?"":jsonObject.getString("maximum_parking_fee_perday");
               product_id=jsonObject.isNull("product_id")?"":jsonObject.getString("product_id");
               product_name=jsonObject.isNull("product_name")?"":jsonObject.getString("product_name");
        JSONArray businessLocation=jsonObject.getJSONArray("business_locations");
        for(int i=0;i<businessLocation.length();i++)
        {
            locations.add(new Business_locations(businessLocation.getJSONObject(i)));
        }
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
    }

    public String getImage_url() {
        return image_url;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public String getId() {
        return id;
    }

    public String getFrom_date() {
        return from_date;
    }

    public String getOffer_description() {
        return offer_description;
    }

    public String getOffer_name() {
        return offer_name;
    }

    public String getMorning_from() {
        return morning_from;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getPrice_per_liter() {
        return price_per_liter;
    }

    public String getParking_fee_per_hour() {
        return parking_fee_per_hour;
    }

    public String getMinimum_parking_hours() {
        return minimum_parking_hours;
    }

    public String getMaximum_parking_fee_perday() {
        return maximum_parking_fee_perday;
    }

    public ArrayList<Business_locations> getLocations() {
        return locations;
    }

    public String getAfternoon_from() {
        return afternoon_from;
    }

    public String getAfternoon_to() {
        return afternoon_to;
    }

    public String getMorning_to() {
        return morning_to;
    }

    public String getOpening_hour_mode() {
        return opening_hour_mode;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getTo_date() {
        return to_date;
    }
}
