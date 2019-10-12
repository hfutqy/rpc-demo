package io.github.hfutqy;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qiyu
 * @date 2019-10-10
 * 传输对象
 */
@Data
public class RpcRequest implements Serializable {
    private String className;
    private String methodName;
    private Object[] params;


}
