package bootstrap.controller;

import bootstrap.domain.People;
import bootstrap.mapper.PeopleMapper;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
public class HelloController {
    
    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private DataSource dataSource;
    @Autowired
    @Qualifier("p3")
    private People p;



    @GetMapping("/fff")
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
