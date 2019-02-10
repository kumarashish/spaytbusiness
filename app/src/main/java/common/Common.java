package common;

public class Common {
    public static String baseUrl="https://api.spayt.de";
    public static String isUserExists=baseUrl+"/business/check_username_exists";
    public static String  isBusinessExists=baseUrl+"/business/check_business_exists";
    public static String getCategories=baseUrl+"/business/categories";
    public static String registerUser=baseUrl+"/business/register";
    public static String login=baseUrl+"/business/login";
    public static String login_withPaypal=baseUrl+"/business/complete_signup_with_paypal?code=";
    public static String forgetPassword=baseUrl+"/business/forgotpass";
    public static String businessLocationUrl=baseUrl+"/business_locations";




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



//https://github.com/paypal/PayPal-Android-SDK/blob/master/SampleApp/src/main/java/com/paypal/example/paypalandroidsdkexample/SampleActivity.java

/******user na///////
 * email
 */

/******login////
 * email
 * password
 * device_id
 */
/******business esists????
 * company_name
 */


/********register
 * email
 * password
 * salutation
 * first_name
 * last_name
 * category_id
 * company_name
 * street_name
 * door_no
 * city
 * zip_code
 * business_email (optional) - if not specified, email will be used as business_email
 * paypal_email (optional)
 * vat_id (optional)
 */
/******forgetPassword
 * email
 */


}
