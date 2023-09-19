package com.kangkimleekojangcho.akgimi.user.application.port;

import java.util.HashMap;

public interface QueryInternetPort {

    String get(String url);

    String post(String rawUrl, HashMap<String, String> info);
}
