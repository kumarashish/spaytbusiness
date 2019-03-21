package models;

import org.json.JSONObject;

public class CategoryModel {
    String categoryName;
    String categoryId;
    String field_names;

    public CategoryModel(JSONObject jsonObject)
    {
        try{
            categoryName=jsonObject.isNull("category_name")?"":jsonObject.getString("category_name");
            categoryId=jsonObject.isNull("id")?"":jsonObject.getString("id");
            field_names=jsonObject.isNull("field_names")?"":jsonObject.getString("field_names");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getField_names() {
        return field_names;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }
}
