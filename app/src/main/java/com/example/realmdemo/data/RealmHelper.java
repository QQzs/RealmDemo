package com.example.realmdemo.data;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @Author: zs
 * @Date: 2019-05-08 10:15
 * @Description:
 */
public class RealmHelper {

    public static final String DB_NAME = "chat.realm";

    private static volatile RealmHelper mHelper;
    private volatile Realm mRealm;

    private RealmHelper(){

    }

    public static RealmHelper getInstance(){
        if (mHelper == null){
            synchronized (RealmHelper.class){
                if (mHelper == null){
                    mHelper = new RealmHelper();
                }
            }
        }
        return mHelper;
    }

    private Realm getRealm(){
        if (mRealm == null || mRealm.isClosed()){
            synchronized (this){
                if (mRealm == null || mRealm.isClosed()){
                    mRealm = Realm.getDefaultInstance();
                }
            }
        }
        return mRealm;
    }

    public void close(){
        if (mRealm != null){
            mRealm.close();
        }
    }

    /**
     * 增加单条数据到数据库中
     *
     * @param bean 数据对象
     */
    public void addorUpdate(final RealmObject bean) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bean);
            }
        });

    }

    /**
     * 增加单条数据到数据库中
     *
     * @param bean 数据对象
     */
    public RealmAsyncTask addorUpdateAsync(final RealmObject bean) {
        return getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bean);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }

    /**
     * 增加多条数据到数据库中
     *
     * @param beans 数据集合，其中元素必须继承了RealmObject
     */
    public void addorUpdate(final List<? extends RealmObject> beans) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(beans);

            }
        });

    }
    /**
     * 增加多条数据到数据库中
     *
     * @param beans 数据集合，其中元素必须继承了RealmObject
     */
    public RealmAsyncTask addorUpdateAsync(final List<? extends RealmObject> beans) {
        return getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(beans);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });

    }

    /**
     * 删除数据库中clazz类所属所有元素
     *
     * @param clazz
     */
    public void deleteAll(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = mRealm.where(clazz).findAll();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                beans.deleteAllFromRealm();

            }
        });

    }
    /**
     * 删除数据库中clazz类所属所有元素
     *
     * @param clazz
     */
    public RealmAsyncTask deleteAllAsync(Class<? extends RealmObject> clazz) {

        final RealmResults<? extends RealmObject> beans = getRealm().where(clazz).findAll();
        return getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                beans.deleteAllFromRealm();

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });

    }

    /**
     * 删除数据库中clazz类所属第一个元素
     *
     * @param clazz
     */
    public void deleteFirst(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = getRealm().where(clazz).findAll();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                beans.deleteFirstFromRealm();

            }
        });

    }

    /**
     * 删除数据库中clazz类所属最后一个元素
     *
     * @param clazz
     */
    public void deleteLast(Class<? extends RealmObject> clazz) {
        final RealmResults<? extends RealmObject> beans = getRealm().where(clazz).findAll();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                beans.deleteLastFromRealm();

            }
        });

    }

    /**
     * 删除数据库中的某个元素
     * @param clazz
     * @param field
     * @param value
     */
    public void deleteByField(Class<? extends RealmObject> clazz, String field , String value) {

        try {
            final RealmObject bean = getRealm().where(clazz).equalTo(field, value).findFirst();
            getRealm().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    bean.deleteFromRealm();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除数据库中clazz类所属数据中某一位置的元素
     *
     * @param clazz
     * @param position
     */
    public void deleteByPosition(Class<? extends RealmObject> clazz, final int position) {
        final RealmResults<? extends RealmObject> beans = getRealm().where(clazz).findAll();
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                beans.deleteFromRealm(position);
            }
        });

    }

    /**
     * 查询数据库中clazz类所属所有数据
     *
     * @param clazz
     * @return
     */
    public List<? extends RealmObject> queryAll(Class<? extends RealmObject> clazz) {

        RealmResults<? extends RealmObject> beans = getRealm().where(clazz).findAll();
        return getRealm().copyFromRealm(beans);
    }

    /**
     * 查询数据库中clazz类所属所有数据
     *
     * @param clazz
     * @return
     */
    public void queryAllAsync(Class<? extends RealmObject> clazz , Consumer<List<? extends RealmObject>> consumer) {

        try {
            getRealm().where(clazz).findAllAsync().asFlowable()
                    .filter(new Predicate<RealmResults<? extends RealmObject>>() {
                        @Override
                        public boolean test(RealmResults<? extends RealmObject> realmObjects){
                            // 是否查询完毕
                            return realmObjects.isLoaded();
                        }
                    })
                    .map(new Function<RealmResults<? extends RealmObject>, List<? extends RealmObject>>() {
                        @Override
                        public List<? extends RealmObject> apply(RealmResults<? extends RealmObject> realmObjects) throws Exception {
                            return getRealm().copyFromRealm(realmObjects);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询满足条件的第一个数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     */
    public RealmObject queryByFieldFirst(Class<? extends RealmObject> clazz, String fieldName, String value) {
        try {
            return getRealm().where(clazz).equalTo(fieldName, value).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询数据库中clazz类所属所有数据
     *
     * @param clazz
     * @return
     */
    public List<? extends RealmObject> queryLimit(Class<? extends RealmObject> clazz , String fieldName ,int value) {

        RealmResults<? extends RealmObject> beans = getRealm().where(clazz).lessThan(fieldName , value).findAll();
        return getRealm().copyFromRealm(beans);
    }


    /**
     * 查询满足条件的所有数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     */
    public List<? extends RealmObject> queryByFieldAll(Class<? extends RealmObject> clazz, String fieldName, String value) {

        try {
            final RealmResults<? extends RealmObject> beans = getRealm().where(clazz).equalTo(fieldName, value).findAll();
            return getRealm().copyFromRealm(beans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询满足条件的所有数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     */
    public void queryByFieldAllAsync(Class<? extends RealmObject> clazz, String fieldName, String value , Consumer<List<? extends RealmObject>> consumer) {
        try {
            getRealm().where(clazz).equalTo(fieldName, value).findAllAsync().asFlowable()
                    .filter(new Predicate<RealmResults<? extends RealmObject>>() {
                        @Override
                        public boolean test(RealmResults<? extends RealmObject> realmObjects){
                            return realmObjects.isLoaded();
                        }
                    })
                    .map(new Function<RealmResults<? extends RealmObject>, List<? extends RealmObject>>() {
                        @Override
                        public List<? extends RealmObject> apply(RealmResults<? extends RealmObject> realmObjects) throws Exception {
                            return getRealm().copyFromRealm(realmObjects);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询满足条件的第一个数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     */
    public RealmObject queryByFieldFirst(Class<? extends RealmObject> clazz, String fieldName, int value) {

        return getRealm().where(clazz).equalTo(fieldName, value).findFirst();
    }

    /**
     * 查询满足条件的所有数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     */
    public List<? extends RealmObject> queryByFieldAll(Class<? extends RealmObject> clazz, String fieldName, int value) {
        try {
            final RealmResults<? extends RealmObject> beans = getRealm().where(clazz).equalTo(fieldName, value).findAll();
            return getRealm().copyFromRealm(beans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 查询满足条件的所有数据
     *
     * @param clazz
     * @param fieldName
     * @param value
     * @return
     */
    public void queryByFieldAllAsync(Class<? extends RealmObject> clazz, String fieldName, int value , Consumer<List<? extends RealmObject>> consumer){

        try {
            getRealm().where(clazz).equalTo(fieldName, value).findAllAsync().asFlowable()
                    .filter(new Predicate<RealmResults<? extends RealmObject>>() {
                        @Override
                        public boolean test(RealmResults<? extends RealmObject> realmObjects){
                            return realmObjects.isLoaded();
                        }
                    })
                    .map(new Function<RealmResults<? extends RealmObject>, List<? extends RealmObject>>() {
                        @Override
                        public List<? extends RealmObject> apply(RealmResults<? extends RealmObject> realmObjects) throws Exception {
                            return getRealm().copyFromRealm(realmObjects);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询数据，按增量排序
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public List<? extends RealmObject> queryAllByAscending(Class<? extends RealmObject> clazz, String fieldName) {
        RealmResults<? extends RealmObject> beans = getRealm().where(clazz).findAll();
        RealmResults<? extends RealmObject> results = beans.sort(fieldName, Sort.ASCENDING);
        return getRealm().copyFromRealm(results);
    }


    /**
     * 查询数据，按降量排序
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public List<? extends RealmObject> queryAllByDescending(Class<? extends RealmObject> clazz, String fieldName) {
        RealmResults<? extends RealmObject> beans = getRealm().where(clazz).findAll();
        RealmResults<? extends RealmObject> results = beans.sort(fieldName, Sort.DESCENDING);
        return getRealm().copyFromRealm(results);
    }

}
