package com.artifacts.api.http;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Send {
    public static HttpResponse<String> get(String endpoint, boolean useToken) throws Exception {
        return Response.buildResponse(Request.buildGetRequest(endpoint, useToken));
    }

    public static HttpResponse<String> post(String endpoint, String body, boolean useToken) throws Exception {
        return Response.buildResponse(Request.buildPostRequest(endpoint, body, useToken));
    }
}
