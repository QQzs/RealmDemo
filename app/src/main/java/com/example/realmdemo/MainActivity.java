package com.example.realmdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.realmdemo.bean.Cat;
import com.example.realmdemo.bean.Dog;
import com.example.realmdemo.bean.User;
import com.example.realmdemo.data.RealmHelper;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;

public class MainActivity extends AppCompatActivity {

    private TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result = findViewById(R.id.tv_result);

    }

    private Realm getRealm(){

        RealmConfiguration config = new RealmConfiguration.Builder()
                // 文件名
                .name("test.realm")
                // 版本号
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        return Realm.getInstance(config);

    }

    public void add(View view){

//        User user = new User(2,"zhang");

        RealmList<Dog> list = new RealmList<>();
        list.add(new Dog(1 , "111"));
        list.add(new Dog(2 , "222"));
        list.add(new Dog(3 , "333"));
        list.add(new Dog(4 , "444"));
        list.add(new Dog(5 , "555"));
        list.add(new Dog(6 , "666"));
        list.add(new Dog(7 , "777"));
        list.add(new Dog(8 , "888"));

        RealmHelper.getInstance().addorUpdate(list);
    }

    public void update(View view){

        Dog dog = new Dog(1,"11111");
        RealmHelper.getInstance().addorUpdate(dog);

    }

    public void query(View view){

//        List<User> users = (List<User>) RealmHelper.getInstance().queryAll(User.class);

//        List<Dog> dogs = (List<Dog>) RealmHelper.getInstance().queryAll(Dog.class);

        List<Dog> dogs = (List<Dog>) RealmHelper.getInstance().queryLimit(Dog.class , "id" , 3);

        tv_result.setText(dogs.toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RealmHelper.getInstance().close();
    }
}
