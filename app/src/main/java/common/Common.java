package common;

public class Common {
    public static String baseUrl="https://api.spayt.de";
    public static String isUserExists=baseUrl+"/business/check_username_exists";
    public static String  isBusinessExists=baseUrl+"/business/check_business_exists";
    public static String getCategories=baseUrl+"/business/categories";
    public static String registerUser=baseUrl+"/business/register";
    public static String login=baseUrl+"/business/login";
    public static String login_withPaypal=baseUrl+"/business/login_with_paypal";
    public static String forgetPassword=baseUrl+"/business/forgotpass";


    public static String paypalClientId="Aeotj4cpU2wDvlExN1qZciDC8KP-cG4xR4ZwbJIK7Ec2UGfNzn6_LRIMLz8c0oAY8gTfXaxdUhyJbaup";
    public static String paypalClientSecret="EJTAzxAEzeoIrTTfgZkDyQHsZseHNVjUx-RlQco_XETkm6KxLvpPkRF-lViyWjhplXNdXp1d0DeCYBfG";


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
