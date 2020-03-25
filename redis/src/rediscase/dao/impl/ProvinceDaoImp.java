package rediscase.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import rediscase.dao.ProvinceDao;
import rediscase.domain.Province;
import rediscase.utils.JDBCUtils;

import java.util.List;

public class ProvinceDaoImp implements ProvinceDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Province> findAll() {
        String sql = "select * from province";
        List<Province> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Province>(Province.class));
        return list;
    }
}
