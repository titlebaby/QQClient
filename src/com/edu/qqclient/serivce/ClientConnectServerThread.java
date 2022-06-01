package com.edu.qqclient.serivce;

import com.edu.qqcommon.Message;
import com.edu.qqcommon.MessageType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    // 该线程需要持有socket

    private Socket socket;


    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        // 因为thread 需要在后台和服务器通信，因此做成while循环
        while (true) {

            try {
                System.out.println("客服端线程，等待从读取从服务器端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();// 如果服务器没有发送message对象，阻塞在这里
                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_USER)) {
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("========当前在线用户表=====");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户："+ onlineUsers[i]);

                    }
//                    System.out.println("接收者:"+ message.getGetter());
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMMON_MES)) {
                    System.out.println("\n"+message.getSender() + "要和"+ message.getGetter()+"说"+ message.getContent());
                }else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MESS)){
                    System.out.println("\n"+message.getSender() + "要群发消息说："+ message.getContent());
                }else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MESS)){
                    // 保存发送过来的文件,文件字节数组，通过文件输出流写到磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("保存成功~");


                } else {
                    System.out.println("暂不处理~~");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    //
    public Socket getSocket() {
        return this.socket;
    }

}
