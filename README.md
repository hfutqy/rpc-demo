# rpc-demo
RPC DEMO  

学习rpc工作原理，自己写了个demo，没有额外的、复杂的组件，很纯粹的一个rpcdemo。  

## 服务端
1. 提供一个对外暴露的接口IService，并实现对外接口的实现类Service  
2. 约定一个RpcRequest对象，暴露出去  
3. 实现socket.accept()监听请求(指定监听端口、指定监听的Service),等待连接  
4. 从socket请求获取inputStream,并反序列化为rpcRequest，反射得到Service的实现结果result  
5. 从socket请求获取outputStream，写入result,并关闭流  

## 客户端  
1. 
