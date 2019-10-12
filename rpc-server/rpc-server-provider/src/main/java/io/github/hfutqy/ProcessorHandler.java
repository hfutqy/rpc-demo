package io.github.hfutqy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author qiyu
 * @date 2019-10-10
 */
public class ProcessorHandler implements Runnable {

    private Socket socket;
    private Object service;

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        // 当有IO数据进来的时候，会在这里执行
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            // inputStream应该存什么信息？
            // 1.客户端请求方法名称 + 参数
            // 2 请求的目标类
            // 3 请求的参数类型
            // 4 其他信息，比如dubbo group

            // 反序列化
            RpcRequest rpcRequest = (RpcRequest) inputStream.readObject();
            // 反射调用
            Object result = invoke(rpcRequest);
            // 获取output流
            outputStream =new ObjectOutputStream(socket.getOutputStream());
            // 写入output(序列化)
            outputStream.writeObject(result);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * 反射实现服务端功能处理
     * 然而反射性能较差，所以dubbo优化，使用动态代理，大大提升性能
     * @param request
     * @return
     */
    private Object invoke(RpcRequest request) {
        Object result = null;
        try {
            // 请求参数
            Object[] args = request.getParams();
            // 请求参数的类型
            Class<?>[] types = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                types[i] = args[i].getClass();
            }
            // 反射加载对应的class
            Class clazz = Class.forName(request.getClassName());
            // 通过反射找到对应class中的方法
            Method method = clazz.getMethod(request.getMethodName(), types);

            result = method.invoke(service, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
