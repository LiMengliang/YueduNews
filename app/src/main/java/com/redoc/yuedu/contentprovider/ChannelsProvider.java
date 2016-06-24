package com.redoc.yuedu.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.redoc.yuedu.YueduApplication;
import com.redoc.yuedu.contentprovider.ado.DatabaseUtils;

public class ChannelsProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ChannelsCode = 1;
    private static final int CacheableChannelsCode = 2;

    public static final String CacheableChannelsPath = "cacheableChannels";
    public static final String ChannelProviderAuthority = "com.redoc.yuedu.channelsprovider";
    public static final String ChannelsPath = "channels";
    public static final Uri ChannelsUri = Uri.parse("content://com.redoc.yuedu.channelsprovider/channels");
    public static final Uri CacheableChannelsUri = Uri.parse("content://com.redoc.yuedu.channelsprovider/cacheableChannels");

    static {
        uriMatcher.addURI(ChannelProviderAuthority, ChannelsPath, ChannelsCode);
        uriMatcher.addURI(ChannelProviderAuthority, CacheableChannelsPath, CacheableChannelsCode);
    }

    public ChannelsProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return "com.yuedu.yuedu.channels";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = YueduApplication.getDbOpenHelper().getWritableDatabase();
        Uri resultUri = null;
        switch (uriMatcher.match(uri)) {
            case ChannelsCode:
                if(values != null && values.size() > 0) {
                    long rowId = db.insert(DatabaseUtils.CHANNELS_TABLE_NAME, null, values);
                    resultUri = ContentUris.withAppendedId(ChannelsUri, rowId);
                }
                break;
            default:
                throw new IllegalArgumentException("未知URI：" + uri);
        }
        return resultUri;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = YueduApplication.getDbOpenHelper().getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case ChannelsCode:
                cursor = db.query(DatabaseUtils.CHANNELS_TABLE_NAME, projection, selection,
                        selectionArgs, null, null, null);
                break;
            case CacheableChannelsCode:
                selection += (" AND " + DatabaseUtils.CAN_CACHE + "=1");
                cursor = db.query(DatabaseUtils.CHANNELS_TABLE_NAME, projection, selection,
                        selectionArgs, null, null, null);
                break;
        }
        if(cursor == null) {
            throw new IllegalArgumentException("未知URI：" + uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = YueduApplication.getDbOpenHelper().getWritableDatabase();
        int result = 0;
        switch (uriMatcher.match(uri)) {
            case ChannelsCode:
                result = db.update(DatabaseUtils.CHANNELS_TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知URI：" + uri);
        }
        return result;
    }
}
