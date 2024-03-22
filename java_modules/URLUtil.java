package com.keti.iam.idthub.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLUtil {

    public static HttpURLConnection createConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        return (HttpURLConnection) url.openConnection();
    }
}
