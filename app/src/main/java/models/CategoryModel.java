package models;

import org.json.JSONObject;

public class CategoryModel {
    String categoryName;
    String categoryId;

    public CategoryModel(JSONObject jsonObject)
    {
        try{
            categoryName=jsonObject.isNull("category_name")?"":jsonObject.getString("category_name");
            categoryId=jsonObject.isNull("id")?"":jsonObject.getString("id");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }
}
