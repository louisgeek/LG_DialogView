package com.louisgeek.dialogview.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Classichu on 2017/5/24.
 */
public class ReflectUtil {


    /**
     * 反射获得对象的值
     *
     * @param fieldName
     * @return
     */
    public static <T, E> T getFieldValue(E eObj, String fieldName) {
        return getFieldValueFromSuperClass(eObj.getClass(), eObj, fieldName);
    }

    public static <E> void setFieldValue(E eObj, String fieldName, Object value) {
        setFieldValueForSuperClass(eObj.getClass(), eObj, fieldName, value);
    }

    /**
     * 反射获得对象的值
     * 常用于  子类继承父类调用
     *
     * @param fieldName
     * @return
     */
    public static <T> T getFieldValueFromSuperClass(Class tClass, Object obj, String fieldName) {
        try {
            Field field = tClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void setFieldValueForSuperClass(Class tClass, Object obj, String fieldName, Object value) {
        try {
            Field field = tClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static Object invokeMethod(Object obj, String methodName, Class<?> parameterType, Object arg) {
        return invokeMethodForSuperClass(obj.getClass(), obj, methodName, parameterType, arg);
    }


    public static Object invokeMethodForSuperClass(Class tClass, Object obj, String methodName, Class<?> parameterType, Object arg) {
        try {
            Method method = tClass.getMethod(methodName, parameterType);
            method.setAccessible(true);
            Object result = method.invoke(obj, arg);
            return result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object invokeMethodForSuperClass(Class tClass, Object obj, String methodName, Class<?> parameterType, Object arg,
                                                   Class<?> parameterTypes2, Object arg2) {
        try {
            Method method = tClass.getMethod(methodName, parameterType, parameterTypes2);
            method.setAccessible(true);
            Object result = method.invoke(obj, arg, arg2);
            return result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
