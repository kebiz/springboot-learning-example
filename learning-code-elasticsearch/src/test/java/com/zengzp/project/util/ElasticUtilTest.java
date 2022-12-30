package com.zengzp.project.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ElasticUtilTest {
    @InjectMocks
    ElasticUtil elasticUtil;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateIndex() throws Exception {
        elasticUtil.createIndex();
    }

    @Test
    public void testQurey() throws Exception {
        elasticUtil.qurey();
    }

    @Test
    public void testCreate() throws Exception {
        // Setup
        // Run the test
        elasticUtil.create();

        // Verify the results
    }

    @Test
    public void testGet() throws Exception {
        // Setup
        // Run the test
        elasticUtil.get();

        // Verify the results
    }

    @Test
    public void testUpdate() throws Exception {
        // Setup
        // Run the test
        elasticUtil.update();

        // Verify the results
    }

    @Test
    public void testSearch() throws Exception {
        // Setup
        // Run the test
        elasticUtil.search();

        // Verify the results
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme