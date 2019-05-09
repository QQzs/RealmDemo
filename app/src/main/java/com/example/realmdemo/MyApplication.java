package com.example.realmdemo;

import android.app.Application;
import android.util.Log;

import com.example.realmdemo.data.RealmHelper;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration configuration=new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .schemaVersion(3)
//                .deleteRealmIfMigrationNeeded()
                .migration(new CustomMigration())
                .build();
        String realmFileName = configuration.getPath();
        Log.d("My_Log","path = " + realmFileName);
        Realm.setDefaultConfiguration(configuration);

    }

    class CustomMigration implements RealmMigration {

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            RealmSchema schema = realm.getSchema();
            // 添加一个字段
            if (oldVersion == 1){
                RealmObjectSchema objectSchema = schema.get("Dog");
                objectSchema.addField("color",  String.class)
                .transform(new RealmObjectSchema.Function() {
                    @Override
                    public void apply(DynamicRealmObject obj) {
                        obj.set("color","red");
                    }
                });
                oldVersion++;
            }
            // 修改一个字段
            if (oldVersion == 2){
                RealmObjectSchema objectSchema = schema.get("Dog");
                objectSchema.renameField("color","colorName");
                oldVersion++;
            }

        }
    }

}
