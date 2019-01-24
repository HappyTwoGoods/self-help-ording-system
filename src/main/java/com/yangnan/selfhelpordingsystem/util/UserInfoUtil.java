package com.yangnan.selfhelpordingsystem.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class UserInfoUtil {

    /**
     * 从请求中获取userId
     *
     * @return
     */
    public static int getUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String shopId = (String) request.getAttribute("userId");
            return NumberUtils.toInt(shopId);
        }
        return 0;
    }
}
