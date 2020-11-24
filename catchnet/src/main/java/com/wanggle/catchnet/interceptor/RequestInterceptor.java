package com.wanggle.catchnet.interceptor;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wanggle.catchnet.costants.NetConstants;
import com.wanggle.catchnet.provider.DBOpenHelper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 网络请求拦截器
 */
public class RequestInterceptor implements Interceptor {
    private String responseCodeKey = "errcode";//默认errorcode
    public static int sCustomSuccessCode = 0;//自定义请求成功Code，默认为0
    private Context context;

    public RequestInterceptor(Context context) {
        this.context = context;
    }

    public RequestInterceptor(Context context, String responseCodeKey, int customSuccessCode) {
        this.context = context;
        this.responseCodeKey = responseCodeKey;
        sCustomSuccessCode = customSuccessCode;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String url = original.url().toString();
        Response response = chain.proceed(original);
        saveRequestInfo(url, toPrettyFormat(getRequestInfo(original))
                , toPrettyFormat(getResponseInfo(response))
                , getHeaders(original), original.method(), getResponseCode(response));
        Log.e("tag", String.format("...\n请求链接：%s\n请求参数：%s\n请求响应%s", url, getRequestInfo(original), getResponseInfo(response)));
        return response;
    }

    /**
     * 获取请求头并格式化返回
     */
    private String getHeaders(Request request) {
        Headers headers = request.headers();
        Set<String> names = headers.names();
        StringBuilder stringBuilder = new StringBuilder();
        for (String name : names) {
            stringBuilder.append(name);
            stringBuilder.append(" : ");
            stringBuilder.append(headers.get(name));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * 打印请求消息
     *
     * @param request 请求的对象
     */
    private String getRequestInfo(Request request) {
        String str = "";
        if (request == null) {
            return str;
        }
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return str;
        }
        try {
            Buffer bufferedSink = new Buffer();
            requestBody.writeTo(bufferedSink);
            Charset charset = StandardCharsets.UTF_8;
            str = bufferedSink.readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 打印返回消息
     *
     * @param response 返回的对象
     */
    private String getResponseInfo(Response response) {
        String str = "";
        if (response == null || !response.isSuccessful()) {
            return str;
        }
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            try {
                source.request(Long.MAX_VALUE); // Buffer the entire body.
            } catch (IOException e) {
                e.printStackTrace();
            }
            Buffer buffer = source.buffer();
            Charset charset = StandardCharsets.UTF_8;
            if (contentLength != 0) {
                str = buffer.clone().readString(charset);
            }
        }
        return str;
    }

    /**
     * 格式化输出JSON字符串
     * @return 格式化后的JSON字符串
     */
    private String toPrettyFormat(String json) {
        if (!TextUtils.isEmpty(json)) {
            JsonObject jsonObject = getJsonObject(json);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(jsonObject);
        }
        return json;
    }

    /**
     * 字符串转JsonObject
     */
    private JsonObject getJsonObject(String json) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(json).getAsJsonObject();
    }

    /**
     * 获取请求结果Code
     */
    private int getResponseCode(Response response) {
        int responseCode = -1;
        String responseStr = getResponseInfo(response);
        JsonObject responseJson = getJsonObject(responseStr);
        int commonCode = response.code();
        if (commonCode == 200) {//请求成功
            Set<String> keySet = responseJson.keySet();
            for (String key : keySet) {
                if (responseCodeKey.equals(key)) {
                    responseCode = responseJson.get(key).getAsInt();
                }
            }
        } else {//请求失败
            responseCode = commonCode;
        }
        return responseCode;
    }

    /**
     * 保存请求信息到ContentProvider
     */
    private void saveRequestInfo(String url, String request, String response, String headers, String requestMethod, int responseCode) {
        Uri requestUri = Uri.parse("content://com.wanggle.catchnet.provider.CustomContentProvider/" + DBOpenHelper.requestTable);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.requestMethod, requestMethod);
        contentValues.put(DBOpenHelper.url, url);
        contentValues.put(DBOpenHelper.header, headers);
        contentValues.put(DBOpenHelper.request, request);
        contentValues.put(DBOpenHelper.response, response);
        contentValues.put(DBOpenHelper.responseCode, responseCode);
        int start = url.indexOf("com") + 3;
        int end = url.lastIndexOf("?") == -1 ? url.length() : url.lastIndexOf("?");
        String subUrl = url.substring(start, end);
        contentValues.put(DBOpenHelper.interfaceName, NetConstants.netMap.get(subUrl) == null ? subUrl : NetConstants.netMap.get(subUrl));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        contentValues.put(DBOpenHelper.requestTime, df.format(date));
        context.getContentResolver().insert(requestUri, contentValues);
    }
}
