package redis.test.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JedisPoolUtil {
    //连接池对象
    private static JedisPool pool;

    //工具类加载的时候加载配置文件
    static {
        //1.类加载器加载到内存中
        InputStream is = JedisPoolUtil.class.getClassLoader().getResourceAsStream("jedis.properties");
        //2.准备Properties对象
        Properties pro = new Properties();
        try {
            //3.加载到内存
            pro.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //4.准备配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));

        pool = new JedisPool(config,pro.getProperty("host"),Integer.parseInt(pro.getProperty("port")));
    }


    //获取连接
    public static Jedis getResource(){
        return pool.getResource();
    }
}
