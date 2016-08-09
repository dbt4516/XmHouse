import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.entity.THouse;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import com.cnc.xmhouse.service.CrawlService;
import com.cnc.xmhouse.service.HouseService;
import com.cnc.xmhouse.service.StaticService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by hongzhan on 2016/7/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class ExamServiceTest {

    @Autowired
    private BaseDao<THouse>houseDao;
    @Autowired
    private HouseService houseService;
    @Autowired
    private CrawlService crawlService;
    @Autowired
    private StaticService staticService;

    @Test
    public void tesetDB(){
      staticService.getText(1400625067000l,1470625067000l);
    }
}
