package com.edu.qqclient.view;

import com.edu.qqclient.serivce.FileClientService;
import com.edu.qqclient.serivce.MessageClientService;
import com.edu.qqclient.serivce.UserClientService;
import com.edu.qqclient.utils.Utility;
import com.edu.qqcommon.Message;
import com.edu.qqcommon.MessageType;

public class QQView {


    private boolean loop = true; // 控制是否显示菜单

    private String key = "";

    private UserClientService userClientService = new UserClientService();
    private MessageClientService messageClientService = new MessageClientService();
    private FileClientService fileClientService = new FileClientService();

    public static void main(String[] args) {
        new QQView().mainMenu();
    }

    private void mainMenu() {
        while (loop) {
            System.out.println("============欢迎登录网络通信系统=========");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.println(" 请输入你的选择");

            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.println(" 请输入用户号：");
                    String userId = Utility.readString(50);
                    System.out.println("请输入密码：");
                    String pwd = Utility.readString(50);
                    //需要去服务器端验证
                    if (userClientService.checkUser(userId,pwd)) {
                        System.out.println("========欢迎(用户" + userId + ")登录成功======");
                        while (loop) {
                            System.out.println("\n========网络通信二级菜单(用户" + userId + ")========");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入你的选择：");
                            String key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    System.out.println("显示在线用户列表");
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("群聊消息");
                                    System.out.println("请输入想说的话");
                                    String content1 = Utility.readString(100);
                                    messageClientService.sendMessageToMore(content1,userId);
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    System.out.println("请输入在线的用户id");
                                    String getterId = Utility.readString(50);
                                    System.out.println("请输入想说的话");
                                    String content = Utility.readString(100);
                                    messageClientService.sendMessageToOne(content,userId,getterId);
                                    break;
                                case "4":
                                    System.out.println("请输入要发送的用户id");
                                    getterId = Utility.readString(50);
                                    fileClientService.sendFileToOne("e:\\a.txt","e:\\aaa.txt",userId,getterId);
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    loop = false;
                                    //调用服务器，发送关闭连接的方法
                                    userClientService.logout();
                                    System.out.println("退出系统");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("登录失败~");
                    }

                    break;
                case "9":
                    loop = false;
                    System.out.println(" 退出系统");
                    break;
            }
        }

    }
}
