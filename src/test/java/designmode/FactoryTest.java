package designmode;

/**
 * @Author pengyd
 * @Date 2018/9/21 15:02
 * @function: 抽象工厂模式
 * 1.provider
 * 2.各自的Factory，实现provider中的product方法来创建各自的对象
 * 3.定义一个通用接口
 * 4.每个对象实现该接口，定义自己的实现方式
 */
public class FactoryTest {
    public static void main(String[] args) {
        Provider smsFactory = new SmsFactory();
        Sender smsSender = smsFactory.productSender();
        smsSender.send();

        Provider mailFactory = new MailFactory();
        Sender mailSender = mailFactory.productSender();
        mailSender.send();
    }
}

//定义一个通用的工具send
interface Sender{
    void send();
}
//mail发送方式
class MailSender implements Sender{
    @Override
    public void send() {
        System.out.println("this is MailSender!");
    }
}
//sms发送方式
class SmsSender implements Sender{
    @Override
    public void send() {
        System.out.println("this is SmsSender!");
    }
}

//抽象出来一个生产工厂
interface Provider{
     Sender productSender();
}
//生产Mail的接口的工厂
class MailFactory implements Provider{
    @Override
    public Sender productSender() {
        return new MailSender();
    }
}
//生产Sms的接口的工厂
class SmsFactory implements Provider{
    @Override
    public Sender productSender() {
        return new SmsSender();
    }
}


