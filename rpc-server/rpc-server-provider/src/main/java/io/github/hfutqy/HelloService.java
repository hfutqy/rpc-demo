package io.github.hfutqy;

/**
 * @author qiyu
 * @date 2019-10-09
 */
public class HelloService implements IHelloService {
    @Override
    public String hello(String text) {
        return "Hello " + text;
    }
}
