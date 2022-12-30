package com.zengzp.rabbit.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchServiceTest {
    @Autowired
    ElasticsearchService elasticsearchService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendElasticsearchAddMessage() throws Exception {
        elasticsearchService.sendElasticsearchAddMessage();
    }

    @Test
    public void testSendElasticsearchDelMessage() throws Exception {
        elasticsearchService.sendElasticsearchDelMessage();
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme