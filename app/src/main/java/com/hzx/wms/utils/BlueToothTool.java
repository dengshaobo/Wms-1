package com.hzx.wms.utils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * Created by linhu on 2019/6/20.
 */

public class BlueToothTool {
    private BluetoothDevice device;
    private Handler mhandler;
    private BluetoothSocket socket;
    public static final int CONNECT_FAILED = 1;
    public static final int READ_FAILED = 2;
    public static final int WRITE_FAILED = 3;
    public static final int DATA = 4;
    public static final int CONNECT_SUCCESS = 5;
    private boolean isConnect = false;

    public BlueToothTool(BluetoothDevice device, Handler handler) {
        this.device = device;
        this.mhandler = handler;
    }

    /**
     * 开辟连接线程任务
     */
    public void connect() {
        Thread thread = new Thread(() -> {
            BluetoothSocket tmp = null;
            Method method;
            try {
                method = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                tmp = (BluetoothSocket) method.invoke(device, 1);
            } catch (Exception e) {
                setState(CONNECT_FAILED);
                Log.e("TAG", e.toString());
            }
            socket = tmp;
            try {
                socket.connect();
                isConnect = true;
                setState(CONNECT_SUCCESS);
                //连接成功后开启读取数据的线程
                Readtask readtask = new Readtask();
                readtask.start();
            } catch (Exception e) {
                setState(CONNECT_FAILED);
                Log.e("TAG", e.toString());
            }
        });
        new Thread(thread).start();
    }

    /**
     * 开辟线程读任务
     */
    public class Readtask extends Thread {
        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            InputStream inputStream;   //建立输入流读取数据
            while (true) {
                try {
                    inputStream = socket.getInputStream();
                    if ((bytes = inputStream.read(buffer)) > 0) {
                        byte[] buf_data = new byte[bytes];
                        for (int i = 0; i < bytes; i++) {
                            buf_data[i] = buffer[i];
                        }
                        String s = new String(buf_data);
                        Message msg = mhandler.obtainMessage();
                        msg.what = DATA;
                        msg.obj = s;
                        mhandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    setState(READ_FAILED);
                    Log.e("TAG", e.toString());
                    break;
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    Log.e("TAG", e.toString());
                }
            }
        }
    }

    public void close() {
        if (socket != null) {
            try {
                socket.close();
              //  setState(CONNECT_FAILED);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setState(int mes) {
        Message message = new Message();
        message.what = mes;
        mhandler.sendMessage(message);
    }
}
