package com.redoc.yuedu.utilities.File;

import android.os.Environment;

/**
 * Created by limen on 2016/5/30.
 */
public class FileSystemUtilities {

    public static boolean hasExternalStorage() {
        return Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED);
    }
}
