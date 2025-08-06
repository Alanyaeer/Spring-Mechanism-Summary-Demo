package com.learn.spring.aware.eventpublisher.generic;

import com.learn.spring.aware.eventpublisher.common.BizEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Component
@Slf4j
public class Test {
    @PostConstruct
    public void init() {
        BizEntity bizEntity = new BizEntity();
        EntityGenericEvent<BizEntity> bizEntityEntityGenericEvent = new EntityGenericEvent<BizEntity>(bizEntity);
        EntityCreatedEvent<BizEntity> bizEntityEntityCreatedEvent = new EntityCreatedEvent<BizEntity>(bizEntity);

        ResolvableType resolvableType = bizEntityEntityCreatedEvent.getResolvableType();
        log.info("type: {}", resolvableType.getGeneric(0).resolve().getSimpleName());
    }

    public static String getGenericTypeName(Class<?> clazz) {
        ResolvableType resolvableType = ResolvableType.forClass(clazz);
        if (!resolvableType.hasGenerics()) {
            return "No Generic";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resolvableType.getGenerics().length; i++) {
            Class<?> genericClass = resolvableType.getGeneric(i).resolve();
            if (genericClass != null) {
                sb.append(genericClass.getSimpleName());
                if (i < resolvableType.getGenerics().length - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }
    public static String getSuperClassGenericTypeName(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return "No Generic";
        }

        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArgs = parameterizedType.getActualTypeArguments();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < actualTypeArgs.length; i++) {
            if (actualTypeArgs[i] instanceof Class) {
                sb.append(((Class<?>) actualTypeArgs[i]).getSimpleName());
                if (i < actualTypeArgs.length - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }
}
