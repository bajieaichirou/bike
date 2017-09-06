package com.qianying.bike.util;

/**
 *
 */

import android.content.Context;

import com.qianying.bike.BuildConfig;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


/**
 *
 */
public class CacheUtil {

    public static final String ADDRESS_ONE = "ADDRESS_ONE";
    public static final String ADDRESS_TWO = "ADDRESS_TWO";
    public static final String ADDRESS_HISTORY = "ADDRESS_HISTORY";

    public static String getCacheFileName(String name) {

        return DigestUtils.md5(name + BuildConfig.VERSION_NAME + BuildConfig.VERSION_CODE + UserHelper.getInstance().getUser().getUsername());
    }


    public static <T> void saveList(Context ctx, List<T> data, String filename) {
        if (ctx == null || filename == null) {
            return;
        }


        File file = new File(ctx.getFilesDir(), getCacheFileName(filename));
        if (file.exists()) {
            file.delete();
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ctx.openFileOutput(
                    getCacheFileName(filename), Context.MODE_PRIVATE));
            oos.writeObject(data);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> loadList(Context ctx, String filename) {

        if (ctx == null) {
            return null;
        }
        List<T> data = null;
        File file = new File(ctx.getFilesDir(), getCacheFileName(filename));
        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(
                        ctx.openFileInput(getCacheFileName(filename)));
                data = (List<T>) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    public static <T> void saveObject(Context ctx, T data, String filename) {
        if (ctx == null || filename == null) {
            return;
        }


        File file = new File(ctx.getFilesDir(), getCacheFileName(filename));
        if (file.exists()) {
            file.delete();
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ctx.openFileOutput(
                    getCacheFileName(filename), Context.MODE_PRIVATE));
            oos.writeObject(data);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T loadObject(Context ctx, String filename) {

        if (ctx == null) {
            return null;
        }
        T data = null;
        File file = new File(ctx.getFilesDir(), getCacheFileName(filename));
        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(
                        ctx.openFileInput(getCacheFileName(filename)));
                data = (T) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }


    public static <T> void saveList(Context ctx, List<T> data, Class<T> clazz) {
        if (ctx == null) {
            return;
        }
//        T t=(T) ((ParameterizedType) ctx.getClass()
//                .getGenericSuperclass()).getActualTypeArguments()[0];

//        Class<T>  clazz=(Class<T>)getSuperClassGenricType(ctx.getClass(), 0);
//
        String filename = clazz.getSimpleName();
        File file = new File(ctx.getFilesDir(), getCacheFileName(filename));
        if (file.exists()) {
            file.delete();
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ctx.openFileOutput(
                    getCacheFileName(filename), Context.MODE_PRIVATE));
            oos.writeObject(data);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> loadList(Context ctx, Class<T> clazz) {
        List<T> data = null;


//        Class clazz=(Class<T>) ((ParameterizedType) ctx.getClass()
//                .getGenericSuperclass()).getActualTypeArguments()[0];

//        Class<T> clazz=(Class<T>)getSuperClassGenricType(ctx.getClass(), 0);
        String filename = clazz.getSimpleName();
//        L.i(filename);
        File file = new File(ctx.getFilesDir(), getCacheFileName(filename));
        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(
                        ctx.openFileInput(getCacheFileName(filename)));
                data = (List<T>) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }


    public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {

        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }


}

