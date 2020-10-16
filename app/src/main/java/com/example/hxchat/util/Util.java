package com.example.hxchat.util;

import android.content.Context;
import android.widget.Toast;

public class Util {
    public static final String ws = "http://10.0.3.2:8080/chat";//websocket测试地址

    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
}
