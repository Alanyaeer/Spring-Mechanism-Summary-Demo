package com.learn.spring.custom.spelresolver;

import com.learn.spring.custom.spelresolver.annotation.CustomFieldKey;
import com.learn.spring.custom.spelresolver.annotation.CustomKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CustomProvider {
    private final ParameterNameDiscoverer parameterNameDiscoverer =  new DefaultParameterNameDiscoverer();

    private final StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();

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

    public List<String> handleCustomFieldKey(CustomFieldKey customFieldKey, ProceedingJoinPoint joinPoint, Integer index) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        Parameter[] parameters = method.getParameters();
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        for(int i = 0; i < parameterNames.length;++i){
            if(parameterNames[i] != null){
                standardEvaluationContext.setVariable(parameterNames[i], args[i]);
            }
        }
        String[] keys = customFieldKey.keys();
        List<String> resultList = new ArrayList<>();
        for(int i = 0; i < parameterNames.length; ++i){
            if(parameters[i].getAnnotation(CustomFieldKey.class) != null){
                for (String key : keys) {
                    String evaluateStr = expression.parseExpression(key).getValue(standardEvaluationContext, String.class);
                    resultList.add(evaluateStr);
                }
            }
        }
        return resultList;
    }

    /**
     * 查找索引
     *
     * @param parameterAnnotations 参数注释
     * @param annotationClass      注释类
     * @return int
     */
    private int findAnnotationIndex(Annotation[][] parameterAnnotations, Class<? extends Annotation> annotationClass) {
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if(annotation.annotationType() == (annotationClass)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public String getParameterKey(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] parameters = method.getParameters();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < parameters.length; ++i) {
            Parameter parameter = parameters[i];
            if (parameter.getAnnotation(CustomFieldKey.class) != null) {
                CustomFieldKey annotation = parameter.getAnnotation(CustomFieldKey.class);
                list.addAll(handleCustomFieldKey(annotation, joinPoint, i));
            }
        }
        return StringUtils.collectionToDelimitedString(list, "", "-", "");
    }
}
