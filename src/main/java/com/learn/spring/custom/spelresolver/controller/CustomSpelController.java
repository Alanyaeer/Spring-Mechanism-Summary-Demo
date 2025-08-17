package com.learn.spring.custom.spelresolver.controller;

import com.learn.spring.custom.spelresolver.annotation.CustomFieldKey;
import com.learn.spring.custom.spelresolver.annotation.CustomKey;
import com.learn.spring.custom.spelresolver.entity.Child;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 自定义SPEL解析器
 *
 * @author wps
 * @date 2025/08/13
 */
@Controller
@RequestMapping("/custom/spel/resolve")
@Slf4j
public class CustomSpelController {
    /**
     * 测试
     * 测试 {@link Child} 这个对象
     * @param child 小孩
     * @return {@link String }
     */
    @GetMapping("/test")
    @CustomKey(keys = {"#child.name", "#child.age"})
    public String test(@CustomFieldKey(keys = {"#child.name", "#child.age"}, fieldIndex = 1) String key,  @RequestBody Child child) {
        log.info("key: {}", key);
        return key;
    }
}
