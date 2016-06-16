package com.robinwu.diangetai.controller;

import com.robinwu.diangetai.controller.IndexController;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Robin on 16/6/14.
 */
public class IndexControllerTest {

    @Test
    public void testIndexGet() {
        IndexController indexController = new IndexController();
        String returnStr = indexController.index();
        assertEquals(returnStr,"index");
    }
}
