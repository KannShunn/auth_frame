package com.cesgroup.auth.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * spring test 测试的基类
 * 
 * @author niklaus
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-mvc.xml", "file:src/main/resources/applicationContext-project.xml","file:src/main/resources/applicationContext.xml"})
@ActiveProfiles(value = "development")
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class AbstractContextControllerTest
{

}
