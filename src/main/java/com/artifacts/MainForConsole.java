package com.artifacts;

import java.net.http.HttpResponse;

import static com.artifacts.api.serverdetails.ServerDetails.getServerDetails;

public class MainForConsole {
    public static void main(String[] args) throws Exception {
        HttpResponse<String> getServerDetailsResult = getServerDetails();
        if (getServerDetailsResult.statusCode() == 200) {
            //getToken();
        } else {
            System.exit(0);
        }
    }
}
