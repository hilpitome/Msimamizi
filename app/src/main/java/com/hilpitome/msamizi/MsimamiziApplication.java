package com.hilpitome.msamizi;

import android.app.Application;

import androidx.room.Room;

import com.hilpitome.msamizi.data.local.MsimamiziDatabase;

public class MsimamiziApplication extends Application {
    private MsimamiziDatabase msimamiziDb;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public synchronized MsimamiziDatabase getMsimamiziDb(){
        if(msimamiziDb== null){
            msimamiziDb = Room.databaseBuilder(getApplicationContext(),
                    MsimamiziDatabase.class, "msimamizi-database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return msimamiziDb;
    }
}
