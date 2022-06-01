package com.edu.qqclient.serivce;

import com.edu.qqcommon.Message;
import com.edu.qqcommon.MessageType;
import com.edu.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserClientService {

    private User u = new User(); //因为我们可能再其他敌方使用user信息，因此做成成员属性

    //因为socket 在其他地方也要使用 所以做成属性
    private Socket socket;

    // 向服务器端请求在线用户列表
    public void onlineFriendList(){
        //构建要发送的消息
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_USER);
        message.setSender(u.getUserId());

        // 发送给服务器

        try {
            // 应该得到当前线程的Socket 对应的 ObjectOutPutStream 对象
            //得到userId对应的线程对象
            ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId());
            // 通过这个线程得到关联的socket
            Socket socket = clientConnectServerThread.getSocket();
            // 得到当前线程socket 对应的 ObjectOutputStream 对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean checkUser(String userId , String pwd) {
        boolean b = false;
        u.setUserId(userId);
        u.setPassword(pwd);


        try {
            //192.168.111.115
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u); //发送user对象

            //从服务器端获取message对象

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message)ois.readObject();
            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {

                // 登录成功,启动新的线程，一直在run的状态（循环），使线程持有这个socket
                // 创建一个和服务器端保持通信的线程 -> ClientConnectServerThread
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start(); // 建立了一个链接

                // 这里为了后面客服端扩展，我们将线程放入到集合管理
                ManageClientConnectServerThread.addClientConnectServerThread(userId,ccst);

                b = true;

            } else {
                //登录失败，就不能启动启动和服务器通信的线程，关闭socket
//                ois.close();

                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }



    // 编写方法，退出客服端，并给服务器端，发送退出系统的消息message 对象

    public void logout(){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId()); // 指定是那个客服端

        try {
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId());
            Socket socket = clientConnectServerThread.getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getUserId()+ "退出进程");
            System.exit(0); //正常退出，第一个进程退出(这个是run的时候开始一个main线程和其他通讯线程)
            //将整个虚拟机里的内容都停掉了 ??
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
