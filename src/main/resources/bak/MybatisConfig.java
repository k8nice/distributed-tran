import com.power.datasource.database.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ningh
 */
@Configuration
@MapperScan(basePackages = MybatisConfig.BASE_PACKAGE,sqlSessionFactoryRef = "sqlSessionTemplate")
public class MybatisConfig extends AbstractDataSourceConfig {

    /**
     * mapper模式下的接口层
     */
    static final String BASE_PACKAGE = "com.nice.mapper";

    /**
     * 对接数据库的实体层
     */
    private static final String ALTASES_PACKAGE = "com.nice.domain";

    private static final String MAPPER_LOCATION = "classpath:com/nice/mapper/*.xml";


    @Primary
    @Bean(name = "ds1")
    public DataSource dataSourceOne(Environment env){
        String prefix = "custom.datasource.ds1.";
        return getDataSource(env,prefix,"ds1");
    }

    @Bean(name = "ds2")
    public DataSource dataSourceTwo(Environment env){
        String prefix = "custom.datasource.ds2.";
        return getDataSource(env,prefix,"ds2");
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource(@Qualifier("ds1")DataSource ds1, @Qualifier("ds2")DataSource ds2){
        Map<Object,Object> targetDataSources = new HashMap<>();
        targetDataSources.put("ds1",ds1);
        targetDataSources.put("ds2",ds2);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(ds1);
        return dataSource;
    }

    @Bean(name = "ds1")
    public SqlSessionFactory ds1(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        try {
            return createSqlSessionFactory(dataSource);
        }catch (Exception e){
            throw  new Exception("sqlSessionFactoryOne方法"+"此处有异常");
        }
    }


    @Bean(name = "ds2")
    public SqlSessionFactory ds2(@Qualifier("ds2") DataSource dataSource) throws Exception {
        return createSqlSessionFactory(dataSource);
    }

    //-----
//    @Primary
//    @Bean(name = "sqlSessionTemplate")
//    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory){
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
    //---------

    @Bean(name = "sqlSessionTemplate")
    public CustomSqlSessionTemplate sqlSessionTemplate(@Qualifier("ds1")SqlSessionFactory ds1,
                                                       @Qualifier("ds2") SqlSessionFactory ds2){
            Map<Object,SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();
            sqlSessionFactoryMap.put("ds1",ds1);
            sqlSessionFactoryMap.put("ds2",ds2);
            CustomSqlSessionTemplate customSqlSessionTemplate = new CustomSqlSessionTemplate(ds1);
            customSqlSessionTemplate.setDefaultTargetSqlSessionFactory(ds2);
            customSqlSessionTemplate.setTargetSqlSessionFactorys(sqlSessionFactoryMap);
            return customSqlSessionTemplate;
    }




    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage(ALTASES_PACKAGE);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }


}
