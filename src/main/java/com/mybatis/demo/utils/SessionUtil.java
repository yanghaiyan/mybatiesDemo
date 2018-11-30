package com.mybatis.demo.utils;

import com.mybatis.demo.constant.SessionConstant;

import java.lang.reflect.Method;

public class SessionUtil {

    private static Object getSessionInThread() {
        Object session = AppContext.getContext().getObject(SessionConstant.APP_CONTEXT_SESSION);
        return session;
    }

    public static void addSession(String key, Object object) {
        Object session = getSessionInThread();
        if (session == null) {
            return;
        }
        try {
            Class<?>[] param = new Class[2];
            param[0] = String.class;
            param[1] = Object.class;

            Method method = session.getClass().getMethod("setAttribute", param);

            Object[] objects = new Object[2];
            objects[0] = key;
            objects[1] = object;

            method.invoke(session, objects);
        } catch (Exception ex) {
            new RuntimeException(ex);
        }
    }

    public static Object getSession(String key) {
        Object session = getSessionInThread();
        if (session == null) {
            return null;
        }
        try {
            Class<?>[] param = new Class[1];
            param[0] = String.class;

            Method method = session.getClass().getMethod("getAttribute", param);

            Object[] objects = new Object[1];
            objects[0] = key;

            Object returnValue = method.invoke(session, objects);
            return returnValue;
        } catch (Exception ex) {
            new RuntimeException(ex);
        }
        return null;
    }


    public static void removeSession(String key) {
        Object session = getSessionInThread();
        if (session == null) {
            return;
        }
        try {
            Class<?>[] param = new Class[2];
            param[0] = String.class;

            Method method = session.getClass().getMethod("removeAttribute", param);

            Object[] objects = new Object[2];
            objects[0] = key;

            method.invoke(session, objects);

        } catch (Exception ex) {
            new RuntimeException(ex);
        }
    }

    public static void invalidate() {
        Object session = getSessionInThread();
        if (session == null) {
            return;
        }
        try {
            Method method = session.getClass().getMethod("invalidate");
            method.invoke(session);
        } catch (Exception ex) {
            new RuntimeException(ex);
        }
    }
}
