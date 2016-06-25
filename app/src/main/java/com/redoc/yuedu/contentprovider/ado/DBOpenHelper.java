package com.redoc.yuedu.contentprovider.ado;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.redoc.yuedu.model.Category;
import com.redoc.yuedu.presenter.CategoriesManager;

/**
 * Created by limen on 2016/6/18.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private CategoriesManager categoriesManager;

    public DBOpenHelper(Context context, SQLiteDatabase.CursorFactory factory, CategoriesManager categoriesManager) {
        super(context, "YueduDatabase", factory, 1);
        this.categoriesManager = categoriesManager;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHANNELS_TABLE = "Create table channels(Id integer primary key AutoIncrement," +
                " ChannelId varchar(20), ChannelName varchar(20), CategoryId varchar(20)," +
                " LinkFormat varchar(200), Selected integer, CanCache integer, Weight integer," +
                " NeedCache integer)";
        db.execSQL(CREATE_CHANNELS_TABLE);
        for (Category category : categoriesManager.getCategories()) {
            for(String sql : category.getChannelsCreationSQL()) {
                db.execSQL(sql);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
