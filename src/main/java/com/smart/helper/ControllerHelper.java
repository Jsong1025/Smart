package com.smart.helper;

import com.smart.annotation.Action;
import com.smart.bean.Handler;
import com.smart.bean.Request;
import com.smart.util.ArrayUtil;
import com.smart.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 *
 * @author jsong
 */
public final class ControllerHelper {

    /**
     * 用于存放请求与处理器的映射关系（Action Map）
     */
    private static final Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        // 获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {

                // 获取Controller类中的方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    for (Method method : methods) {

                        // 判断当前方法是否带有Action注解
                        if (method.isAnnotationPresent(Action.class)) {

                            // 从Action中获取URL映射规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();

                            // 验证URL映射规则
                            if (mapping.matches("\\w+/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {

                                    // 获取当前请求方法与请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod , requestPath);
                                    Handler handler = new Handler(controllerClass , method);

                                    // 初始化ActionMap
                                    ACTION_MAP.put(request , handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     */
    public static Handler getHandler(String requestMethod , String requestPath) {
        Request request = new Request(requestMethod , requestPath);
        return ACTION_MAP.get(request);
    }

}
