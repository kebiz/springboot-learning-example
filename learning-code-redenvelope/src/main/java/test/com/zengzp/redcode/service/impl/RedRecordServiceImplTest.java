package test.com.zengzp.redcode.service.impl; 

import com.zengzp.redcode.RedCodeApplication;
import com.zengzp.redcode.dto.RedPacketDto;
import com.zengzp.redcode.service.RedRecordService;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/** 
* RedRecordServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>ÎåÔÂ 1, 2023</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedCodeApplication.class)
public class RedRecordServiceImplTest {
    @Autowired
    private RedRecordService redRecordService;
    @Autowired
    private RedisTemplate redisTemplate;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: handOut(RedPacketDto redPacketDto) 
* 
*/ 
@Test
public void testHandOut() throws Exception { 
//TODO: Test goes here...
    RedPacketDto redPacketDto=new RedPacketDto();
    redPacketDto.setUserId(1111);
    redPacketDto.setAmount(1000);
    redPacketDto.setTotal(10);
    String redId = redRecordService.handOut(redPacketDto);
    System.out.println("redId:"+redId);
}
    @Test
    public void testRod() throws Exception {
//TODO: Test goes here...
        BigDecimal red = (BigDecimal)redRecordService.rod(1111,"1653313337158565888");
        System.out.println("red:"+red.doubleValue());
    }
    @Test
    public void testRedisRed() throws Exception {
    for(int i=0;i<100;i++){
        Integer red=(Integer)redisTemplate.opsForList().rightPop("1653059192128372736");
        System.out.println("red:::"+red);
    }

    }

}
