package com.artifacts.api.http;

import java.net.http.HttpResponse;

public class Send {
    public static HttpResponse<String> get(String endpoint) throws Exception {
        return Response.buildResponse(Request.buildGetRequest(endpoint));
    }

    public static HttpResponse<String> post(String endpoint, String body) throws Exception {
        return Response.buildResponse(Request.buildPostRequest(endpoint, body));
    }
}
