package designmode;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author pengyd
 * @Date 2018/9/21 11:13
 * @function: cjlib动态代理  不需要实现任何接口，JDK动态代理则需要实现接口，静态代理同样也需要
 */
public class ProxyTest implements MethodInterceptor {

    private Object object;

    public  Object getInstance(Object object){
        this.object = object;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("代理开始");
        Object invoke = methodProxy.invokeSuper(o, objects);
        System.out.println("代理结束");
        return invoke;
    }
}

/**
 *  目标对象
 */
class GG{
    public void test(){
        System.out.println("开心就好");
    }

    public void hh(){
        System.out.println("这是第二个方法！");
    }
}

/**
 *  测试类     工厂
 */
class TT{
    public static void main(String[] args) {
        GG gg = new GG();
        ProxyTest proxyTest = new ProxyTest();
        GG proxy = (GG) proxyTest.getInstance(gg);
        proxy.test();
        proxy.hh();
    }
}
