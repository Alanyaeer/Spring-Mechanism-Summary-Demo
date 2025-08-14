package com.learn.spring.custom.spelresolver;

import com.learn.spring.custom.spelresolver.annotation.CustomKey;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

@Component
public class CustomProvider {
    private final ParameterNameDiscoverer parameterNameDiscoverer =  new DefaultParameterNameDiscoverer();

    private final ExpressionParser expression = new SpelExpressionParser();
    public String handleCustomKey(CustomKey customKey, Method method, Object[] parameterValues) {
        String[] keys = customKey.keys();
        ArrayList<String> stringArrayList = new ArrayList<String>();
        for (String key : keys) {
            String evaluateStr = evaluateStr(key, method, parameterValues);
            stringArrayList.add(evaluateStr);
        }
        return StringUtils.collectionToDelimitedString(stringArrayList, ",", "{", "}");
    }
    private String evaluateStr(String key, Method method, Object[] parameterValues){
        MethodBasedEvaluationContext methodBasedEvaluationContext = new MethodBasedEvaluationContext(null, method, parameterValues, parameterNameDiscoverer);
        Object value = expression.parseExpression(key).getValue(methodBasedEvaluationContext);
        return ObjectUtils.nullSafeToString(value);
    }
}
