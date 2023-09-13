package com.kangkimleekojangcho.akgimi.user.adapter.out;

import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorException;
import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryInternetPort;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class QueryInternetAdapter implements QueryInternetPort {

    public String get(String url) {
        try {
            URL urlObj;
            HttpURLConnection conn;
            urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content_Type", "application/x-ww-form-urlencoded;charset=utf-8");
            if (conn.getResponseCode() != 200) {
                throw new IOException();
            }
            return getBody(conn);
        } catch (IOException e) {
            throw new ServerErrorException(ServerErrorExceptionCode.NETWORK_ERROR);
        }
    }



    public String post(String rawUrl, HashMap<String, String> info){
        try {
            URL url = new URL(rawUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true);
            try (BufferedWriter bw = new BufferedWriter((new OutputStreamWriter(conn.getOutputStream())))) {
                List<Map.Entry<String, String>> entryList = new ArrayList<>(info.entrySet());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < info.size(); i++) {
                    Map.Entry<String, String> entry = entryList.get(i);
                    String key = entry.getKey();
                    String value = entry.getValue();
                    sb.append(key).append('=').append(value);
                    if (i != info.size() - 1) {
                        sb.append('&');
                    }
                }
                bw.write(sb.toString());
                bw.flush();
            }
//            if (conn.getResponseCode() != 200) {
//                throw new IOException();
//            }
            return getBody(conn);
        }catch(IOException e){
            throw new ServerErrorException(ServerErrorExceptionCode.NETWORK_ERROR);
        }
    }

    private String getBody(HttpURLConnection conn) throws IOException {
        String responseData = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            while ((responseData = br.readLine()) != null) {
                sb.append(responseData);
            }
            responseData = sb.toString();
        }
        return responseData;
    }
}
