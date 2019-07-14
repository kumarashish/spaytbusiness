package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BusinessProductModel {
    ArrayList<Business_locations>list=new ArrayList<>();
    String id;
    String type="product";
    String business_id;
    String product_category_id;
    String name;
    String description;
    String total_price;
    String price_per_liter;
    String parking_fee_per_hour;
    String minimum_parking_hours;
    String maximum_parking_fee_perday;
    String price;
    public int quantity=0;
    public BusinessProductModel(JSONObject jsonObject)

    {try {
        id = jsonObject.isNull("id") ? "" : jsonObject.getString("id");
        business_id= jsonObject.isNull("business_id") ? "" : jsonObject.getString("business_id");
        product_category_id= jsonObject.isNull("product_category_id") ? "" : jsonObject.getString("product_category_id");
        name= jsonObject.isNull("name") ? jsonObject.getString("offer_name"): jsonObject.getString("name");
        description= jsonObject.isNull("description") ? jsonObject.getString("offer_description") : jsonObject.getString("description");
        total_price= jsonObject.isNull("total_price") ? "" : jsonObject.getString("total_price");
        price_per_liter= jsonObject.isNull("price_per_liter") ? "" : jsonObject.getString("price_per_liter");
        parking_fee_per_hour= jsonObject.isNull("parking_fee_per_hour") ? "" : jsonObject.getString("parking_fee_per_hour");
        minimum_parking_hours= jsonObject.isNull("minimum_parking_hours") ? "" : jsonObject.getString("minimum_parking_hours");
        maximum_parking_fee_perday= jsonObject.isNull("maximum_parking_fee_perday") ? "" : jsonObject.getString("maximum_parking_fee_perday");
        JSONArray jsonArray=jsonObject.getJSONArray("business_locations");
        if((jsonArray!=null)&&(jsonArray.length()>0))
        {for(int i=0;i<jsonArray.length();i++) {
            list.add(new Business_locations(jsonArray.getJSONObject(i)));
        }
        }
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
    }



    public BusinessProductModel(String type,String id,String name,String description,String quantiy,String price){

        this.type=type;
        this.id=id;
        this.name=name;
        this.description=description;
        this.quantity=Integer.parseInt(quantiy);
        this.price=price;


    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setQuantity(int getQuantity) {
        this.quantity = getQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Business_locations> getList() {
        return list;
    }

    public String getName() {
        return name;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public String getDescription() {
        return description;
    }

    public String getMaximum_parking_fee_perday() {
        return maximum_parking_fee_perday;
    }

    public String getMinimum_parking_hours() {
        return minimum_parking_hours;
    }

    public String getProduct_category_id() {
        return product_category_id;
    }

    public String getParking_fee_per_hour() {
        return parking_fee_per_hour;
    }

    public String getPrice_per_liter() {
        return price_per_liter;
    }

    public String getTotal_price() {
        return total_price;
    }



}
