package redis.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.test.utils.JedisPoolUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisTest {

    /**
     * Redis的快速入门
     */
    @Test
    public void test1(){
        //1.获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        //2.操作
        jedis.set("username","liuzeyu");
        //3.关闭连接
        jedis.close();
    }

    /**
     * string 数据结构操作
     */
    @Test
    public void test2(){
        //1.获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        //2.操作
        jedis.set("username","liuzeyu");
        //3.获取
        String username = jedis.get("username");
        System.out.println(username);

        //2.1特殊的存储：可以设置存储到redis服务器的失效时间
        jedis.setex("activecode",20,"1234");
        String activecode = jedis.get("activecode");
        System.out.println(activecode);
        //3.关闭连接
        jedis.close();
    }

    /**
     * hash 数据结构操作
     */
    @Test
    public void test3(){
        //1.获取连接
        Jedis jedis = new Jedis();  //无参默认："localhost", 6379
        //2.操作
        jedis.hset("user","username","zhangsan");
        jedis.hset("user","age","33");
        jedis.hset("user","gender","male");
        //获取user的age
        String age = jedis.hget("user", "age");
        System.out.println(age);

        //获取所有
        Map<String, String> users = jedis.hgetAll("user");
        Set<String> keySet = users.keySet();
        for (String key : keySet) {
            System.out.println(key +":" + users.get(key));
        }
        //关闭连接
        jedis.close();
    }

    /**
     * list 数据结构操作
     */
    @Test
    public void test4(){
        //1.获取连接
        Jedis jedis = new Jedis();  //无参默认："localhost", 6379
        //2.操作
        jedis.lpush("name","a","b");
        jedis.rpush("name","x","y");
        System.out.println(jedis.lpop("name")); //b
        System.out.println(jedis.rpop("name"));  //y
        //期待输出 ： a x
        List<String> names = jedis.lrange("name", 0, -1);
        System.out.println(names);
        //关闭连接
        jedis.close();
    }
    /**
     * set 数据结构操作
     */
    @Test
    public void test5(){
        //1.获取连接
        Jedis jedis = new Jedis();  //无参默认："localhost", 6379
        //2.操作
        jedis.sadd("nickname","zhangsan","lisi");

        //3.获取
        Set<String> user = jedis.smembers("nickname");
        System.out.println(user);
        //关闭连接
        jedis.close();
    }

    /**
     * sortedset 数据结构操作
     */
    @Test
    public void test6(){
        //1.获取连接
        Jedis jedis = new Jedis();  //无参默认："localhost", 6379
        //2.操作
        jedis.zadd("stu",10,"xiaoming");
        jedis.zadd("stu",31,"xiaohua");
        jedis.zadd("stu",55,"xiaomiss");

        //3.获取
        Set<String> stus = jedis.zrange("stu", 0, -1);
        System.out.println(stus);
        //关闭连接
        jedis.close();
    }

    /**
     * Jedis 连接池的基本使用
     */
    @Test
    public void test7(){
        //0.创建一个配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(50);

        //1.创建jedis连接池
        JedisPool pool = new JedisPool(config,"localhost",6379);

        //2.获取服务器连接，返回客户端对象
        Jedis jedis = pool.getResource();

        //操作
        jedis.set("haha","heihei");
        String haha = jedis.get("haha");
        System.out.println(haha);

        //关闭：归还连接至连接池
        jedis.close();
    }


    /**
     * Jedis 连接池工具类的基本使用
     */
    @Test
    public void test8(){
        //获取Jedis连接
        Jedis jedis = JedisPoolUtil.getResource();

        //操作
        jedis.set("heihei","utils");

        //归还连接
        jedis.close();
    }
}
