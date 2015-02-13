package dao;

import com.test.dao.RegistrationDao;
import com.test.domain.Registration;
import com.test.util.UniqueNumber;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by Kahfi on 2/13/2015.
 */
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml",
        "classpath:**/applicationContext*.xml"})
public class RegistrationDaoTest {

    @Test
    public void insert(){

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        RegistrationDao registrationDao = ctx.getBean("registrationDao", RegistrationDao.class);

        Registration r = new Registration();

        /*Create a Unique Number for ID*/
        Long id = UniqueNumber.getUniqueNumber();

        r.setId(id);
        r.setName("Kahfi Maulana");
        r.setEmail("kahfi.maulana.iskandar@gmail.com");
        r.setAddress("Indonesia");

        registrationDao.save(r);

        System.out.println("Insert Success");
    }
}
