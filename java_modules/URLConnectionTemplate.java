package com.keti.iam.idthub.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Slf4j
public abstract class URLConnectionTemplate {

    public String urlExecute(String urlString , boolean isReturnString , String formData , boolean isDoOutput) throws IOException {

        HttpURLConnection conn = createConnection(urlString);
        // timeout 추가
        conn.setConnectTimeout(1500);
        conn.setReadTimeout(2500);
        
        call(conn , formData);

        // if( isDoOutput ) {
        //     try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
        //         dos.writeBytes(formData);
        //     }
        // }
       if (isDoOutput) {
            // 바이트 단위로 변경
            byte[] payload = formData.getBytes(StandardCharsets.UTF_8);
            conn.setRequestProperty("Content-Length", Integer.toString(payload.length));

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload);
                os.flush();
            }
        }

        // try (BufferedReader br = new BufferedReader(new InputStreamReader(
        //         conn.getInputStream()))) {
        //     if (isReturnString) {
        //         String line;
        //         StringBuilder input = new StringBuilder();
        //         while ((line = br.readLine()) != null) {
        //             input.append(line);
        //         }
        //         return input.toString();
        //     } else {
        //         String line;
        //         StringBuilder input = new StringBuilder();
        //         while ((line = br.readLine()) != null) {
        //             input.append(line);
        //         }
        //         log.info("input = {}" , input);
        //         return null;
        //     }
        // } finally {
        //     conn.disconnect();
        // }

        int code = conn.getResponseCode();
        InputStream is = (code >= 200 && code < 400) ? conn.getInputStream() : conn.getErrorStream();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            if (!isReturnString) return null;

            String line;
            StringBuilder input = new StringBuilder();
            while ((line = br.readLine()) != null) {
                input.append(line);
            }
            return input.toString();

        } finally {
            conn.disconnect();
        }
    }

    protected abstract void call(HttpURLConnection httpURLConnection , String formData) throws ProtocolException;
    protected HttpURLConnection createConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        return (HttpURLConnection) url.openConnection();
    }
}



