import com.sappadev.simplewebangular.conf.ApplicationConfig
import com.sappadev.simplewebangular.confservlet.ServletConfig
import com.sappadev.simplewebangular.data.dto.CustomerDTO
import com.sappadev.simplewebangular.services.CustomerService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@WebAppConfiguration
@Rollback
@Transactional
@ContextHierarchy([
        @ContextConfiguration(classes = ApplicationConfig),
        @ContextConfiguration(classes = ServletConfig)
])
@Slf4j
/**
 * @author ( < a href = " mailto : sergei.ledvanov @ gmail.com " > Sergei Ledvanov < / a > ) - 14.12.2015.
 */
class MySpec extends Specification {

    def numList = (1..10).toList()

    @Autowired
    WebApplicationContext wac

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build()
    }

    def "first"() {
        def cd = new CustomerDTO()
        CustomerService cs = Mock()
        cs.createCustomer(_) >> cd

        when:
        numList << 11
        numList.each {
            println it
        }.collect {}

        then:
        11 in numList
        cs.createCustomer(new CustomerDTO()) == cd

    }


}
