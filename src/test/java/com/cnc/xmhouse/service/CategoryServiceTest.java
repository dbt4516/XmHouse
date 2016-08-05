package com.cnc.xmhouse.service;

import com.cnc.xmhouse.lagacy_entity.Category;
import com.cnc.xmhouse.framework.config.AppsApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by linsy1 on 2016/7/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppsApplicationConfig.class)
@WebAppConfiguration
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    public void save()throws Exception{
        System.out.println(categoryService.save(new Category("tttt")));
    }
    @Test
    public void  sameName()throws Exception{
        System.out.println(categoryService.sameName("tttt"));
    }
    @Test
    public void listAll()throws Exception{
        for(Category category:categoryService.listAll())
            System.out.println(category.getName());
    }
    @Test
    public void remove()throws Exception{
        categoryService.remove(4);
    }

}
