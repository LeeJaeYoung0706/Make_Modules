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

        call(conn , formData);

        if( isDoOutput ) {
            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                dos.writeBytes(formData);
            }
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                conn.getInputStream()))) {
            if (isReturnString) {
                String line;
                StringBuilder input = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    input.append(line);
                }
                return input.toString();
            } else {
                String line;
                StringBuilder input = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    input.append(line);
                }
                log.info("input = {}" , input);
                return null;
            }
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


