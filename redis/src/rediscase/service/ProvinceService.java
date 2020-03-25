package rediscase.service;

import rediscase.domain.Province;

import java.util.List;

public interface ProvinceService {

    public List<Province> findAll();

    //省份从redis缓存中
    public String findAllJson();
}
