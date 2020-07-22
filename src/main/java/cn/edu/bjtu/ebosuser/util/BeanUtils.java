package cn.edu.bjtu.ebosuser.util;

import cn.edu.bjtu.ebosuser.exception.BeanUtilsException;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class BeanUtils {
    private BeanUtils() {
    }

    public static void updateProperties(@NonNull Object source, @NonNull Object target) {
        Assert.notNull(source, "source object must not be null");
        Assert.notNull(target, "target object must not be null");

        try {
            org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        } catch (BeansException e) {
            throw new BeanUtilsException("Failed to copy properties", e);
        }
    }

    @NonNull
    private static String[] getNullPropertyNames(@NonNull Object source) {
        return getNullPropertyNameSet(source).toArray(new String[0]);
    }

    @NonNull
    private static Set<String> getNullPropertyNameSet(@NonNull Object source) {
        Assert.notNull(source, "source object must not be null");
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            if (propertyValue == null) {
                emptyNames.add(propertyName);
            }
        }
        return emptyNames;
    }
}
