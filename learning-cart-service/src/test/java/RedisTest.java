import com.zengzp.cart.CartApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/30 11:32
 * @description：redis测试类
 * @modified By：
 * @version: 1$
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CartApplication.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() throws Exception {
        redisTemplate.opsForValue().set("k1","value1");

        System.out.println(redisTemplate.opsForValue().get("k1"));
    }
}
