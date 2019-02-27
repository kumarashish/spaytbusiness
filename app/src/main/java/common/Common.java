package common;

public class Common {
    public static String baseUrl="https://api.spayt.de";
    public static String isUserExists=baseUrl+"/business/check_username_exists";
    public static String  isBusinessExists=baseUrl+"/business/check_business_exists";
    public static String getCategories=baseUrl+"/business/categories";
    public static String registerUser=baseUrl+"/business/register";
    public static String login=baseUrl+"/business/login";
    public static String login_withPaypal=baseUrl+"/business_users/complete_signup_with_paypal?";
    public static String forgetPassword=baseUrl+"/business/forgotpass";
    public static String businessLocationUrl=baseUrl+"/business_locations";
    public static String businessUserUrl=baseUrl+"/business_users";
    public static String offersUrl=baseUrl+"/business_products_offers";
    public static String businessProducts=baseUrl+"/business_products";
    public static String businessProfile=baseUrl+"/business";
    public static String updateBusinessProfile=baseUrl+"/business/update";
    public static String addBusinessUser=baseUrl+"/business_users/add";
    public static String updateBusinessUser=baseUrl+"/business_users/update";
    public static String updateBusinessProducts=baseUrl+"/business_products/update";
    public static String addBusinessProducts=baseUrl+"/business_products/add";
    public static String deleteBusinessProduct=baseUrl+"/business_products/delete";
    public static String addBusinessLocation=baseUrl+"/business_locations/add";
    public static String updateBusinessLocation=baseUrl+"/business_locations/update";
    public static String deleteBusinessLocation=baseUrl+"/business_locations/delete";
    public static String addBusinessOffers=baseUrl+"/business_products_offers/add";
    public static String updateBusinessOffers=baseUrl+"/business_products_offers/update";
    public static String deleteBusinessOffers=baseUrl+"/business_products_offers/delete";

   public static String[] id={"id"};
    public static String[] updateBusinessKeys = {"business_email","category_id", "company_name", "street_name", "door_no", "city", "zip_code", "phone_number", "paypal_email", "vat_id"};
    public static String []updateUserKeys={"id","first_name","last_name","salutation","email","is_active","user_type"};
    public static String []addUserKeys={"first_name","last_name","salutation","email","is_active","user_type","password"};
    public static String[] addBusinessOffersKeys = {"product_id",
            "business_location_ids",
            "offer_name",
            "offer_description",
            "from_date",
            "to_date",
            "opening_hour_mode",
            "morning_from",
            "morning_to",
            "afternoon_from",
            "afternoon_to",
            "total_price",
            "price_per_liter",
            "parking_fee_per_hour",
            "minimum_parking_hours",
            "maximum_parking_fee_perday"
    };
    public static String[] updateBusinessOffersKeys = {"id ","product_id",
            "business_location_ids",
            "offer_name",
            "offer_description",
            "from_date",
            "to_date",
            "opening_hour_mode",
            "morning_from",
            "morning_to",
            "afternoon_from",
            "afternoon_to",
            "total_price",
            "price_per_liter",
            "parking_fee_per_hour",
            "minimum_parking_hours",
            "maximum_parking_fee_perday"
    };

    public static String[] addBusinessProduct = {"product_category_id",
            "business_location_ids",
            "name",
            "description",
            "total_price",
            "price_per_liter",
            "parking_fee_per_hour",
            "minimum_parking_hours",
            "maximum_parking_fee_perday"
    };
    public static String[] updateBusinessProduct = {"id", "product_category_id",
            "business_location_ids",
            "name",
            "description",
            "total_price",
            "price_per_liter",
            "parking_fee_per_hour",
            "minimum_parking_hours",
            "maximum_parking_fee_perday"
    };
    public static String[] updateBusinessLocationKeys = {"id", "location_name", "street_name",
            "door_no",
            "city",
            "zip_code",
            "phone_number",
            "monday_mode",
            "tuesday_mode",
            "wednesday_mode",
            "thursday_mode",
            "friday_mode",
            "saturday_mode",
            "sunday_mode",
            "monday_morning_from",
            "monday_morning_to",
            "monday_afternoon_from",
            "monday_afternoon_to",
            "tuesday_morning_from",
            "tuesday_morning_to",
            "tuesday_afternoon_from",
            "tuesday_afternoon_to",
            "wednesday_morning_from",
            "wednesday_morning_to",
            "wednesday_afternoon_from",
            "wednesday_afternoon_to",
            "thursday_morning_from",
            "thursday_morning_to",
            "thursday_afternoon_from",
            "thursday_afternoon_to",
            "friday_morning_from",
            "friday_morning_to",
            "friday_afternoon_from",
            "friday_afternoon_to",
            "saturday_morning_from",
            "saturday_morning_to",
            "saturday_afternoon_from",
            "saturday_afternoon_to",
            "sunday_morning_from",
            "sunday_morning_to",
            "sunday_afternoon_from",
            "sunday_afternoon_to"};
public static String[]addBusinessLocationKeys={"location_name","street_name",
        "door_no",
        "city",
        "zip_code",
        "phone_number",
        "monday_mode",
        "tuesday_mode",
        "wednesday_mode",
        "thursday_mode",
        "friday_mode",
        "saturday_mode",
        "sunday_mode",
        "monday_morning_from",
        "monday_morning_to",
        "monday_afternoon_from",
        "monday_afternoon_to",
        "tuesday_morning_from",
        "tuesday_morning_to",
        "tuesday_afternoon_from",
        "tuesday_afternoon_to",
        "wednesday_morning_from",
        "wednesday_morning_to",
        "wednesday_afternoon_from",
        "wednesday_afternoon_to",
        "thursday_morning_from",
        "thursday_morning_to",
        "thursday_afternoon_from",
        "thursday_afternoon_to",
        "friday_morning_from",
        "friday_morning_to",
        "friday_afternoon_from",
        "friday_afternoon_to",
        "saturday_morning_from",
        "saturday_morning_to",
        "saturday_afternoon_from",
        "saturday_afternoon_to",
        "sunday_morning_from",
        "sunday_morning_to",
        "sunday_afternoon_from",
        "sunday_afternoon_to"};












    //"
//        /business_products_offers/add
///business_products_offers/update
///business_products_offers/delete
///business_products_offers/details
///business_products_offers/search"

   //   public static String paypalClientId="Aeotj4cpU2wDvlExN1qZciDC8KP-cG4xR4ZwbJIK7Ec2UGfNzn6_LRIMLz8c0oAY8gTfXaxdUhyJbaup";
//    public static String paypalClientSecret="EJTAzxAEzeoIrTTfgZkDyQHsZseHNVjUx-RlQco_XETkm6KxLvpPkRF-lViyWjhplXNdXp1d0DeCYBfG";
   public static String paypalClientId="ATEDn8H26GtM_VpLMrIWrTwvJCxc6d_xWNa0W34PL9FqLwEg41FRrjlLZppbhR_ShSG50ztPZ6bpPBXz";
  //  public static String paypalClientId="AWjXaMC3FMmr70GbAEHKVpCXQQ5XyZ745RQ-yFq_2Vu7E-AEog9xi_XokV0De0CJr2Q_QlWzannKEQzp";
    public static String paypalClientSecret="EF0a3sqSFCDwlYmpiTGhJNrEapL_qi2Gdktsh5lOhJTtMoxuDdgkjK-BcoKJeznwYrEegLRpHwh5fTgB";


 //      /business_users/complete_signup_with_paypal

//    "/business_locations
//            /business_locations/add
///business_locations/update
///business_locations/delete
///business_locations/search
///business_locations/details"



//    "/business_products/categories
//            /business_products
///business_products/add
///business_products/update
///business_products/delete
///business_products/details
///business_products/search"






}
