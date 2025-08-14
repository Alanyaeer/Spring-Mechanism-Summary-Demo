package com.learn.spring.custom.spelresolver.controller;

import com.learn.spring.custom.spelresolver.annotation.CustomKey;
import com.learn.spring.custom.spelresolver.entity.Child;
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
public class CustomSpelController {
    /**
     * 测试
     *
     * @param child 小孩
     * @return {@link String }
     */
    @GetMapping("/test")
    public String test(@RequestBody @CustomKey(keys = {"#child.name", "#child.age"}) Child child) {
        return child.toString();
    }
}
