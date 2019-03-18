package com.mmall.controller.portal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lkmc2 on 2018/2/19.
 */

@Controller
@RequestMapping("/test/")
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class); //日志记录器

    @RequestMapping("test")
    @ResponseBody
    public String saySomething(String string) {
        logger.info("test info");
        logger.warn("test warn");
        logger.error("test error");
        return "value=" + string;
    }

}
