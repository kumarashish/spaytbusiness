package models;

public class RegistrationModel {

    String email="";
    String password="";
    String salutation="";
    String first_name="";
    String last_name="";
    String category_id="";
    String company_name="";
    String street_name="";
    String door_no="";
    String city="";
    String zip_code="";
    String business_email="";
    String paypal_email="";
    String vat_id="";
    String phoneNumber="";

    public RegistrationModel()
    {}

    public RegistrationModel(String fname,String lname,String salutation,String emailId,String password)
    {
        this.first_name=fname;
        this.last_name=lname;
        this.salutation=salutation;
        this.email=emailId;
        this.password=password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setBusiness_email(String business_email) {
        this.business_email = business_email;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDoor_no(String door_no) {
        this.door_no = door_no;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPaypal_email(String paypal_email) {
        this.paypal_email = paypal_email;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setVat_id(String vat_id) {
        this.vat_id = vat_id;
    }

    public String getPaypal_email() {
        return paypal_email;
    }

    public String getBusiness_email() {
        return business_email;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getPassword() {
        return password;
    }

    public String getStreet_name() {
        return street_name;
    }

    public String getDoor_no() {
        return door_no;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getZip_code() {
        return zip_code;
    }

    public String getVat_id() {
        return vat_id;
    }

    public String getCity() {
        return city;
    }

    public String getCategory_id() {
        return category_id;
    }

}
