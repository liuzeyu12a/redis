package rediscase.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import redis.test.utils.JedisPoolUtil;
import rediscase.dao.ProvinceDao;
import rediscase.dao.impl.ProvinceDaoImp;
import rediscase.domain.Province;
import rediscase.service.ProvinceService;

import java.util.List;

public class ProvinceServiceImpl  implements ProvinceService{

    private ProvinceDao dao = new ProvinceDaoImp();
    @Override
    public List<Province> findAll() {
        return dao.findAll();
    }

    @Override
    public String findAllJson() {
        //1.获取redis客户端连接
        Jedis jedis = JedisPoolUtil.getResource();
        //2.查询redis缓存中是否有province
        String province_json = jedis.get("province");
        //3.判断是否get到,第一次是查询数据库
        if(province_json == null || province_json.length() == 0){
            System.out.println("第一次查询，从数据库中获取");
            //3.1从数据库中获取
            List<Province> list = dao.findAll();
            //序列化为json
            ObjectMapper mapper = new ObjectMapper();
            try {
                province_json = mapper.writeValueAsString(list);
                jedis.set("province",province_json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("第二次查询，从缓存中获取");
        }

        return province_json;
    }
}
