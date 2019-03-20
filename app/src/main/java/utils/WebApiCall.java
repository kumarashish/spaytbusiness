package utils;

import android.content.Context;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import interfaces.WebApiResponseCallback;
import models.RegistrationModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Ashish.Kumar on 23-01-2018.
 */

public class WebApiCall {
    OkHttpClient client;
    private  OkHttpClient.Builder client1;
    public static final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Context context;
    static OkHttpClient clientForMP;

    public WebApiCall(Context context) {
        client = new OkHttpClient();
        this.context = context;
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

    public void getData(String url,String token, final WebApiResponseCallback callback) {

        HttpUrl httpUrl = HttpUrl.parse(url).newBuilder()
                .addEncodedQueryParameter("code",token)

                .build();
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();
        final Request request = new Request.Builder().url(httpUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.fillInStackTrace().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        if (response.message() != null) {
                            callback.onError(response.message());
                        } else {
                            callback.onError("No data found!");
                        }

                    }
                }else{
                    callback.onError(response.body().string());
                }
            }
        });
    }


    public void getDataCommon(String url,String token, final WebApiResponseCallback callback) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
        final Request request = new Request.Builder().header("X-Businesstoken",token).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.fillInStackTrace().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        if (response.message() != null) {
                            callback.onError(response.message());
                        } else {
                            callback.onError("No data found!");
                        }
                    }
                }else{
                    callback.onError(response.body().string());
                }
            }
        });
    }

    public String getData(String url) {
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS).build();
        final Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();

            } else {
                return getErrorData();
            }

        } catch (Exception ex) {
            ex.fillInStackTrace();
            return getErrorData();
        }

    }
    public String getData(String url,String token) {
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS).build();
        final Request request = new Request.Builder().header("X-Businesstoken",token).url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();

            } else {
                return getErrorData();
            }

        } catch (Exception ex) {
            ex.fillInStackTrace();
            return getErrorData();
        }

    }

    public String getErrorData() {
        JSONObject object = new JSONObject();
        try {
            object.put("Status", false);
            object.put("Message", "Error occured");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return object.toString();
    }
    public void forgetPassword(String url,String Email ,final WebApiResponseCallback callback) {
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody formBody = null;

        formBody = new FormBody.Builder()
                .add("email", Email)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(formBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.fillInStackTrace().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        if (response.message() != null) {
                            callback.onError(response.message());
                        } else {
                            callback.onError("No data found!");
                        }

                    }
                }
            }
        });
    }


    public void postData(String url,String token,String [] key,String [] values,final WebApiResponseCallback callback)
    {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
        FormBody.Builder formBuilder = new FormBody.Builder();
        for(int i=0;i<key.length;i++)
        {

                formBuilder.add(key[i], values[i]);

        }
        RequestBody formBody= formBuilder.build();
        Request request = new Request.Builder().header("X-Businesstoken",token).url(url).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onError(e.fillInStackTrace().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        callback.onError(response.message());
                    }
                } else {
                    callback.onError(response.message());
                }
            }
        });
    }


    public void postData(String url, String token, String requestJson, final WebApiResponseCallback callback) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
        RequestBody body = RequestBody.create(JSON, requestJson);
        Request request = new Request.Builder().header("X-Businesstoken", token).url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onError(e.fillInStackTrace().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        callback.onError(response.message());
                    }
                } else {
                    callback.onError(response.message());
                }
            }
        });
    }


    public void loginWithFb(String url, String fb_Id, String email, String userName, String deviceId, String accessToken, final WebApiResponseCallback callback) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
        RequestBody formBody = null;
        formBody = new FormBody.Builder()
                .add("fb_id", fb_Id)
                .add("email", email)
                .add("username", userName)
                .add("device_id", deviceId)
                .add("access_token", accessToken)
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onError(e.fillInStackTrace().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        callback.onError(response.message());
                    }
                } else {
                    callback.onError(response.message());
                }
            }
        });
    }
    public void login(String url, String email, String password,String deviceId, final WebApiResponseCallback callback) {

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
        RequestBody formBody = null;
        if(deviceId==null)
        {
            deviceId="";
        }

        formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("device_id", deviceId)
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onError(e.fillInStackTrace().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        callback.onError(response.message());
                    }
                } else {
                    callback.onError(response.message());
                }
            }
        });
    }


public void postFlormData(String url,String key,String userId ,final WebApiResponseCallback callback)
{
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).build();
    RequestBody formBody = null;
    formBody = new FormBody.Builder()
            .add(key, userId)
            .build();
    Request request = new Request.Builder().url(url).post(formBody).build();
    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

            callback.onError(e.fillInStackTrace().toString());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.code() == 200 || response.code() == 201) {
                if (response != null) {
                    callback.onSucess(response.body().string());
                } else {
                    callback.onError(response.message());
                }
            } else {
                callback.onError(response.message());
            }
        }
    });
}

    public String postFlormData(String url,String userId)
    {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
        RequestBody formBody = null;
        formBody = new FormBody.Builder()
                .add("user_id", userId)
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();

            } else {
                return getErrorData();
            }

        } catch (Exception ex) {
            ex.fillInStackTrace();
            return getErrorData();
        }

    }
    public void register(String url, RegistrationModel model, final WebApiResponseCallback callback) {

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();
        RequestBody formBody = null;



        formBody = new FormBody.Builder()
                .add("email", model.getEmail())
                .add("password", model.getPassword())
                .add("first_name", model.getFirst_name())
                .add("last_name", model.getLast_name())
                .add("salutation", model.getSalutation())
                .add("category_id", model.getCategory_id())
                .add("company_name", model.getCompany_name())
                .add("street_name", model.getStreet_name())
                .add("door_no", model.getDoor_no())
                .add("city", model.getCity())

                .add("zip_code", model.getZip_code())
                .add("phone_number", model.getPhoneNumber())
                .add("business_email", model.getBusiness_email())
                .add("paypal_email", model.getPaypal_email())
                .add("vat_id", model.getVat_id())
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onError(e.fillInStackTrace().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        callback.onError(response.message());
                    }
                } else {
                    callback.onError(response.message());
                }
            }
        });
    }



    }

