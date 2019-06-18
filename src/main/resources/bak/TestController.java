import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ningh
 */
@RestController
public class TestController {

    /**
     * 没有指定为主数据源
     */
//    @Primary
    @Autowired
//    @Qualifier("dataSource")
    private DataSource dataSource;


    @Autowired
    @Qualifier("ds1")
    private DataSource dataSource1;


    @Autowired
    @Qualifier("ds2")
    private DataSource dataSource2;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        System.out.println("TestController.setJdbcTemplate()");
        jdbcTemplate.setDataSource(dataSource1);
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping("/get")
    public String get(){
        //观察控制台的打印信息。
        System.out.println(dataSource);
        return "ok";
    }

    @RequestMapping("/get1")
    public String get1(){
        //观察控制台的打印信息。
        System.out.println(dataSource1);
        return "ok.1";
    }

    @RequestMapping("/get2")
    public String get2(){
        //观察控制台的打印信息。
        System.out.println(dataSource2);
        return "ok.2";
    }

    @RequestMapping("/get3")
    public String get3(){
        //观察控制台的打印信息
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource1);
        System.out.println(jdbcTemplate.getDataSource());
        System.out.println(jdbcTemplate);
        /*
         *Demo1只再test1中存在，test1并没有此数据库;需要自己进行复制，不然会报错:Table `test1.demo1`
         * doesn't exist
         */
        String sql = "select * from sys_user";
        jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println(rs.getLong("id") + "---" + rs.getString("name"));
                return "";
            }
        });

        return "ok.3";
    }

    @RequestMapping("/get4")

    public String get4() {

        // 观察控制台的打印信息.

        System.out.println(jdbcTemplate.getDataSource());

        System.out.println(jdbcTemplate);



        /*
         * Demo1只在test1中存在，test并没有此数据库; 需要自己自己进行复制，不然会报错：Table 'test1.demo1'
         * doesn't exist
         */

        String sql = "select  * from  sys_user";

        jdbcTemplate.query(sql, new RowMapper<String>() {



            @Override

            public String mapRow(ResultSet rs, int rowNum) throws SQLException {

                System.out.println(rs.getLong("id") + "---" + rs.getString("name"));

                return "";

            }



        });



        return "ok.4";

    }

    @Transactional
    @GetMapping("/set5")
    public String addUser(){
        JdbcTemplate jdbcTemplate1 = new JdbcTemplate(dataSource1);
        JdbcTemplate jdbcTemplate2 = new JdbcTemplate(dataSource2);
        jdbcTemplate1.update("insert into  sys_user values(1,'nice')");
        int i = 1/0;
        jdbcTemplate2.update("insert into  sys_user values(2,'nice')");
        return "";
    }

}
