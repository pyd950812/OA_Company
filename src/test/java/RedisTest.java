import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author pengyd
 * @Date 2018/4/23 17:23
 * @function:
 */
public class RedisTest {


    public static void main(String[] args) {
//        Jedis jedis = new Jedis("r-bp1b56b16131a0d4.redis.rds.aliyuncs.com",6379);
//        jedis.auth("Wearehero2016");
//
//        Jedis jedis = new Jedis("192.168.21.27",6379);
//        jedis.auth("We-are-hero-2015");
//
//
////        String s = jedis.get("commit");
////        System.out.println(s);
//
//        jedis.set("pdop:test","123");
//        System.out.println(jedis.get("pdop:test"));
//
////        jedis.del("pdop:test");


        Boolean b = true;
        String s = b.toString();
        System.out.println(s);

    }
}
