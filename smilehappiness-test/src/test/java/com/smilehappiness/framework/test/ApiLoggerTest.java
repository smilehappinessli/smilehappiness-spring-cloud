package com.smilehappiness.framework.test;

import com.smilehappiness.exception.exceptions.BusinessException;
import com.smilehappiness.framework.SmileHappinessApplication;
import com.smilehappiness.framework.entity.ApiLogger;
import com.smilehappiness.framework.enums.FrameworkBusinessExceptionEnum;
import com.smilehappiness.framework.service.ApiLoggerService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * <p>
 * ApiLoggerTest
 * <p/>
 *
 * @author smilehappiness
 * @Date 2021/10/3 14:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmileHappinessApplication.class)
public class ApiLoggerTest {

    @Autowired
    private ApiLoggerService apiLoggerService;

    @Test
    public void testGetApiLogger() {
        List<ApiLogger> apiLoggerList = apiLoggerService.getApiLoggerInfoByRequestUrlAndMethodName(null, "getApiLoggerInfoByRequestUrlAndMethodName");
        if (CollectionUtils.isEmpty(apiLoggerList)) {
            throw new BusinessException(FrameworkBusinessExceptionEnum.API_LOGGER_INFO_NULL);
        }
    }
}
