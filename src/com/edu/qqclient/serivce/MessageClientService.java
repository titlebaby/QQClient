package com.edu.qqclient.serivce;

import com.edu.qqcommon.Message;
import com.edu.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 该类提供和消息相关的方法
 */
public class MessageClientService {
    public void sendMessageToOne(String content, String senderId, String getterId) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMMON_MES);
        message.setSender(senderId);
        message.setContent(content);
        message.setGetter(getterId);
        message.setSendTime(new Date().toString());
        System.out.println("发送前的打印："+senderId + "对" + getterId + "说" + content);

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            ManageClientConnectServerThread.getClientConnectServerThread(senderId).
                                    getSocket().getOutputStream()
                    );
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessageToMore(String content, String senderId) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_TO_ALL_MESS);
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println("发送前的打印："+senderId + "对所有人说" + content);

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            ManageClientConnectServerThread.getClientConnectServerThread(senderId).
                                    getSocket().getOutputStream()
                    );
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
