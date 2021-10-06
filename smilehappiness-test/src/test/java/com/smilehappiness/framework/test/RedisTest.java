package com.smilehappiness.framework.test;

import com.smilehappiness.cache.util.RedisUtil;
import com.smilehappiness.cache.util.RedissonLockRedisUtil;
import com.smilehappiness.exception.exceptions.BusinessException;
import com.smilehappiness.exception.exceptions.SystemInternalException;
import com.smilehappiness.framework.SmileHappinessApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * Redis工具类测试
 * <p/>
 *
 * @author smilehappiness
 * @Date 2021/10/3 14:18
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmileHappinessApplication.class)
public class RedisTest {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedissonLockRedisUtil redissonLockRedisUtil;


    /**
     * <p>
     * Redis工具类基础使用测试
     * <p/>
     *
     * @param
     * @return void
     * @Date 2021/10/4 16:50
     */
    @Test
    public void testRedisUtil() {
        //赋值
        redisUtil.set("test1", "你好");
        //该工具类，默认过期单位为秒
        redisUtil.set("test2", "测试一下过期时间", 30);

        //取值
        System.out.println(redisUtil.get("test1"));
        System.out.println(redisUtil.get("test2"));

        //删除值
        redisUtil.del("test1");
    }

    /**
     * <p>
     * 测试分布式锁的使用，基于Redisson客户端实现（该方法的实现推荐使用）
     * <p/>
     *
     * @param
     * @return void
     * @Date 2021/10/4 16:51
     */
    @Test
    public void testDistributeLock() {
        String bizLockKey = "smilehappiness:trialOrder:orderNumberxxxxxxx";

        //支持过期解锁功能，10秒钟以后自动解锁, 无需调用unlock方法手动解锁，当然，为了不占用资源，使用锁处理完业务，一般还是建议手动释放锁
        RLock lock = redissonLockRedisUtil.lock(bizLockKey, 60L);
        if (lock.tryLock()) {
            try {
                //处理业务方法。。。
            } catch (Exception e) {
                log.error("获取分布式锁失败，失败原因：{}", e);
                throw new SystemInternalException("获取分布式锁失败，失败原因：" + e.getMessage());
            } finally {
                lock.unlock();
            }
        } else {
            log.error("系统繁忙，请稍后再试！");
            throw new BusinessException("系统繁忙，请稍后再试！");
        }
    }

    /**
     * <p>
     * 测试分布式锁的使用，基于Redisson客户端实现（该方法的实现推荐使用）
     * <p/>
     *
     * @param
     * @return void
     * @Date 2021/10/4 16:51
     */
    @Test
    public void testDistributeLockTwo() {
        String bizLockKey = "smilehappiness:trialOrder:orderNumberxxxxxxx";

        //尝试加锁，最多等待30秒，上锁以后120秒自动解锁
        boolean lockFlag = redissonLockRedisUtil.tryLock(bizLockKey, 30L, 2 * 60L);
        if (lockFlag) {
            try {
                //处理业务方法。。。
            } catch (Exception e) {
                log.error("获取分布式锁失败，失败原因：{}", e);
                throw new SystemInternalException("获取分布式锁失败，失败原因：" + e.getMessage());
            } finally {
                redissonLockRedisUtil.unlock(bizLockKey);
            }
        } else {
            log.error("系统繁忙，请稍后再试！");
            throw new BusinessException("系统繁忙，请稍后再试！");
        }
    }

    /**
     * <p>
     * 测试分布式锁的使用，基于Redisson客户端实现方式
     * <p/>
     *
     * @param
     * @return void
     * @Date 2021/10/4 16:51
     */
    @Test
    public void testDistributeLockOriginal() {
        String bizLockKey = "smilehappiness:trialOrder:orderNumberxxxxxxx";
        RLock lock = redissonClient.getLock(bizLockKey);

        if (lock.tryLock()) {
            try {
                //处理业务方法。。。
            } catch (Exception e) {
                log.error("获取分布式锁失败，失败原因：{}", e);
                throw new SystemInternalException("获取分布式锁失败，失败原因：" + e.getMessage());
            } finally {
                lock.unlock();
            }
        } else {
            log.error("系统繁忙，请稍后再试！");
            throw new BusinessException("系统繁忙，请稍后再试！");
        }
    }

}
