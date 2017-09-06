package com.qianying.bike.util;

/**
 *
 */

import android.content.Context;

import com.qianying.bike.BuildConfig;
import com.qianying.bike.model.MapLocation;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 *
 */
public class LocationHelper {

    public static final String LOCATION = "LOCATION";          //
    private static MapLocation maploaction;          //


    public static String getCacheFileName(String name) {
        return DigestUtils.md5(name + BuildConfig.VERSION_NAME + BuildConfig.VERSION_CODE + "BBDC");
    }


    public static void saveMapLocation(Context ctx, MapLocation data) {
        maploaction = data;
        if (ctx == null) {
            return;
        }
        File file = new File(ctx.getFilesDir(), getCacheFileName(LOCATION));
        if (file.exists()) {
            file.delete();
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ctx.openFileOutput(
                    getCacheFileName(LOCATION), Context.MODE_PRIVATE));
            oos.writeObject(maploaction);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MapLocation loadMapLocation(Context ctx) {
        if (maploaction != null) {
            return maploaction;
        }
        if (ctx == null) {
            return null;
        }
        File file = new File(ctx.getFilesDir(), getCacheFileName(LOCATION));
        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(
                        ctx.openFileInput(getCacheFileName(LOCATION)));
                maploaction = (MapLocation) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (maploaction == null) {
            maploaction = new MapLocation();
        }
        return maploaction;
    }

}

