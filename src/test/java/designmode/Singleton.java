package designmode;

/**
 * @Author pengyd
 * @Date 2018/3/8 15:18
 * @function:  单例模式
 */
public class Singleton {
    /**
     *   饿汉式     类加载的时候就创建好对象  线程安全的
     *
     */
    //1.首先将构造方法私有化  private
    private Singleton(){

    }

    //2.饿汉式  提供私有的静态的实例
    private static Singleton instance =  new Singleton();

    //3.向外部提供获取实例的方法
    public static Singleton getInstance(){
        return  instance;
    }

}


class Singleton2{
    /**
     *   懒汉式    需要获取实例的时候再去创建实例对象  线程不安全的
     *
     */
    //1.首先将构造方法私有化  private
    private Singleton2(){}

    //2.懒汉式
    private static Singleton2 instance;

    //3.
    public static Singleton2 getInstance(){
        if(instance==null){
            return instance = new Singleton2();
        }else {
            return instance;
        }
    }

}


class S {
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        if(s1==s2){
            System.out.println("111");
        }else {
            System.out.println("22");
        }


        Singleton2 s3 = Singleton2.getInstance();
        Singleton2 s4 = Singleton2.getInstance();
        if(s3==s4){
            System.out.println("111");
        }else {
            System.out.println("22");
        }
    }
}
