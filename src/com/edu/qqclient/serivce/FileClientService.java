package com.edu.qqclient.serivce;

import com.edu.qqcommon.Message;
import com.edu.qqcommon.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileClientService {

    public void sendFileToOne(String src, String dest, String senderId, String getter) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MESS);
        message.setSender(senderId);
        message.setGetter(getter);
        message.setDest(dest);
        message.setContent(src);

        FileInputStream  fileInputStream = null;

        byte[] bytes = new byte[(int) new File(src).length()];

        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(bytes); // 将src文件读入到程序的字节数组

            //将文件对应的字节数组设置 message
            message.setFileBytes(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("\n"+ message.getSender()+"给"+ message.getGetter() + "发送文件");


        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId)
                    .getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
