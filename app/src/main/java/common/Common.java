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



    public static String[] updateBusinessKeys = {"business_email","category_id", "company_name", "street_name", "door_no", "city", "zip_code", "phone_number", "paypal_email", "vat_id"};
    public static String []updateUserKeys={"id","first_name","last_name","salutation","email","is_active","user_type"};
    public static String []addUserKeys={"first_name","last_name","salutation","email","is_active","user_type"};










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
