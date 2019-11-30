package com.diabin.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;


public class DatabaseManager {

    private UserProfileDao mDao;

    //---------------单例--------------------
    private DatabaseManager(){

    }

    public static DatabaseManager getInstance(){
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }
    //---------------单例--------------------


    public final void init(Context context){
        initDao(context);
    }

    private final void initDao(Context context){
        ReleaseOpenHelper helper = new ReleaseOpenHelper(context,"fast_ec.db");//new ReleaseOpenHelper
        final Database db = helper.getWritableDb();//getWritableDb:greenDao中的方法
        DaoSession daoSession = new DaoMaster(db).newSession();
        mDao = daoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao(){
        return mDao;
    }
}
