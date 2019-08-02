package bootstrap.controller;

import bootstrap.domain.People;
import bootstrap.mapper.PeopleMapper;
import bootstrap.util.JedisUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HelloController {
    
    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private DataSource dataSource;
    @Autowired
    @Qualifier("p3")
    private People p;
    @Autowired
    private JedisUtil jedisUtil;


    @GetMapping("/")
    public String index(String type){
        /*jedisUtil.set("boot","tttt");
        System.out.println(jedisUtil.get("boot"));
        jedisUtil.expire("boot",5);
        Map<String,Object> map = new HashMap<>();
        map.put("name","laowang");
        map.put("age",18);

        jedisUtil.hmset("people",map);*/
        return "index";
    }



    @GetMapping("/fff")
    @ResponseBody
    public String home(){
        System.out.println("fff");
        /*try {
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from people");
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        /*List<People> peoples = peopleMapper.selectAll();
        peoples.forEach(p -> {
            System.out.println(JSON.toJSONString(p));
        });*/
        return "hello boot!!!";
    }
}
