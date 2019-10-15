# rpc-demo
RPC DEMO  

学习rpc工作原理，自己写了个demo，没有额外的、复杂的组件，很纯粹的一个rpcdemo。  

## 服务端
1. 提供一个对外暴露的接口IService，并实现对外接口的实现类Service  
2. 约定一个RpcRequest对象结构体，暴露出去 (className、methodName、[]params) 
3. 发布服务方法(指定监听端口、new Service())，socket.accept()监听请求,等待连接  
4. 从socket请求获取inputStream,并反序列化为rpcRequest，反射得到Service的实现结果result  
5. 从socket请求获取outputStream，写入result,并关闭流  

## 客户端  
1. 为服务端暴露接口实现动态代理类(Proxy.newProxyInstance(,,))，需要传递指定接口、host、port
2. 实现动态代理InvocationHandler()的invoke()方法,组装RpcRequest对象待建立sockect请求
3. 创建socket请求实现类RpcNetTransport,实现socket请求方法Object send(RpcRequest request)
4. send()方法内，建立socket通信，outputStream把request写入服务端，拿到inputStream，拿到result
5. InvocationHandler()的invoke()方法内调用socket请求send()方法,拿到result
