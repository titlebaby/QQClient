package com.edu.qqcommon;

import java.io.PipedReader;
import java.io.Serializable;

/**
 * 表示客服端和服务器端的消息对象
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L; //序列化，写这个是为了增加他的兼容性
    private String sender; //发送者
    private String getter; //接受者
    private String content; //内容
    private String sendTime; // 发送时间
    private String mesType; //消息的类型[可以再消息接口种定义消息类型]

    private byte[] fileBytes;
    private int fileLen = 0;
    private String dest; // 将文件传输到哪里
    private String src; //源文件路径

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
