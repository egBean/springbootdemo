package bootstrap.controller;

import bootstrap.annotations.ApiNeedLogin;
import bootstrap.domain.People;
import bootstrap.eventpubandlisten.DemoEvent;
import bootstrap.mapper.PeopleMapper;
import bootstrap.util.JedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;

@Controller
@Api("hello类测试")
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    
    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private DataSource dataSource;
    @Autowired
    @Qualifier("p3")
    private People p;
    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private Environment environment;
    @Autowired
    private ApplicationEventPublisher publisher;

    private static final Logger LOGGER = LoggerFactory.getLogger("testLogger");


    @GetMapping("/publish")
    @ResponseBody
    public String publish(){
        System.out.println(Thread.currentThread().getName());
        DemoEvent event = new DemoEvent(p);
        publisher.publishEvent(event);
        return "succ";
    }


    @GetMapping("/a")
    @ResponseBody
    @ApiNeedLogin("abc")
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

    /**
     * transactional 注解的timeout属性，是指最后一个sql语句开始的时间距离方法开始的时间+sql本身执行的时间(待测试)
     * @return
     */
    @GetMapping("/index")
    @ResponseBody
    public String log(){
        logger.info("test elk");
        logger.warn("warn elk ....");
        logger.error("error elk ...........");
        return "index";
    }
    @GetMapping("/user")
    @ResponseBody
    public String user(){
        return "user";
    }
    @GetMapping("/leaf")
    public ModelAndView leaf(){
        People p = new People("老王",19);
        ModelAndView mv = new ModelAndView();
        mv.addObject("people",p);
        mv.setViewName("people");
        return mv;
    }



    @GetMapping("/fff")
    @ResponseBody
    @ApiOperation(value = "方法描述", notes = "测试使用")
    @ApiImplicitParam(name = "type",value = "类型")
    public String home(String type){
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
