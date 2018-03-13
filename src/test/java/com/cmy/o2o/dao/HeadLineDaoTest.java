package com.cmy.o2o.dao;

import com.cmy.o2o.entity.HeadLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Author : cmy
 * Date   : 2018-03-07 19:05.
 * desc   :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HeadLineDaoTest {

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void queryHeadLine() {
        List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
        assertEquals(2, headLineList.size());
    }
}