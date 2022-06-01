package com.edu.qqclient.serivce;

import java.util.HashMap;

/**
 * 该类管理客服端链接到服务器端的线程的类
 */
public class ManageClientConnectServerThread {
    // 把多个线程放入一个hashMap集合，key就是userid， value就是线程
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();


    // 将某个线程加入到集合中


    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);

    }

    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }
}
