package test.com.zengzp.redcode.util; 

import com.zengzp.redcode.RedCodeApplication;
import com.zengzp.redcode.util.RedPacketUtil;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

/** 
* RedPacketUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 22, 2023</pre> 
* @version 1.0 
*/
@SpringBootTest(classes = RedCodeApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RedPacketUtilTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}

/** 
* 
* Method: divideRedPacket(Integer totalAmount, Integer totalPeopleNum) 
* 
*/ 
@Test
public void testDivideRedPacket() throws Exception { 
//TODO: Test goes here...
    Integer totalAmount=1000;
    Integer totalPeopleNum=10;

    Integer num =0;

    List<Integer> integers = RedPacketUtil.divideRedPacket(totalAmount, totalPeopleNum);

    for (int i=0;i<integers.size();i++) {

        System.out.println("第"+i+"红包金额："+new BigDecimal(integers.get(i).toString()).divide(new BigDecimal(100))+"元");
        num+=integers.get(i);

    }
    System.out.println("发红包的总金额："+new BigDecimal(num.toString()).divide(new BigDecimal(100))+"元");
} 


} 
