package com.artifacts.api.http;

import java.net.http.HttpResponse;

public class Send {
    public static HttpResponse<String> get(String uri) throws Exception {
        return Response.buildResponse(Request.buildGetRequest(uri));
    }

    public static HttpResponse<String> post(String uri, String body) throws Exception {
        return Response.buildResponse(Request.buildPostRequest(uri, body));
    }
}
