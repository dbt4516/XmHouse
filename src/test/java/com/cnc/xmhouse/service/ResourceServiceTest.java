package com.cnc.xmhouse.service;

import com.cnc.xmhouse.lagacy_entity.Resource;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by linsy1 on 2016/7/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class ResourceServiceTest {

    @Autowired
    private ResourceService resourceService;
    @Test
    public void getResourcesByCourseId() throws Exception {
        List<Resource> list = resourceService.getResourcesByCourseId(27);

        if(null != list){
            for(Resource resource:list){System.out.println(resource.getSource());}}

    }
/*    @Test
    public void removeByCourseId() throws Exception {
        resourceService.removeByCourseId(2);
    }
   @Test
    public void remove() throws Exception {
         resourceService.remove(1);

    }*/


}
