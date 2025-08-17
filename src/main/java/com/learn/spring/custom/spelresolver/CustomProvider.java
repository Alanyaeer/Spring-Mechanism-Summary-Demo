package com.learn.spring.custom.spelresolver;

import com.learn.spring.custom.spelresolver.annotation.CustomFieldKey;
import com.learn.spring.custom.spelresolver.annotation.CustomKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CustomProvider{
    private final ParameterNameDiscoverer parameterNameDiscoverer =  new DefaultParameterNameDiscoverer();

    private final StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();

    private final TemplateParserContext templateParserContext = new TemplateParserContext();

    private final ExpressionParser expression = new SpelExpressionParser();

    @Resource
    private BeanFactory beanFactory;
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

    public List<String> handleCustomFieldKey(CustomFieldKey annotation, ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        for(int i = 0; i < parameterNames.length;++i){
            if(parameterNames[i] != null){
                standardEvaluationContext.setVariable(parameterNames[i], args[i]);
            }
        }
        List<String> resultList = new ArrayList<>();
        if(annotation != null){
            String[] keys = annotation.keys();
            for (String key : keys) {
                String evaluateStr = expression.parseExpression(key).getValue(standardEvaluationContext, String.class);
                resultList.add(evaluateStr);
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
                list.addAll(handleCustomFieldKey(annotation, joinPoint));
            }
        }
        return StringUtils.collectionToDelimitedString(list, "", "-", "");
    }

    public String handleSpelExpression(CustomKey customKey) {
        String keyExpression = customKey.keyExpression();
        if(!StringUtils.hasText(keyExpression)){
            return "";
        }
        Object value = expression.parseExpression(resolveSpelExpr(keyExpression), templateParserContext).getValue(String.class);
        return ObjectUtils.nullSafeToString(value);
    }

    private String resolveSpelExpr(String spelExpr){
        String resolveStr =  ((ConfigurableBeanFactory) beanFactory).resolveEmbeddedValue(spelExpr);
        log.info("resolveStr {}", resolveStr);
        return resolveStr;
    }
}
