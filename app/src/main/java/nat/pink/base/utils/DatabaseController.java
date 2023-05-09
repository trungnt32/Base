package nat.pink.base.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nat.pink.base.R;
import nat.pink.base.dao.AppDatabase;
import nat.pink.base.model.ObjectSpin;
import nat.pink.base.model.ObjectsContentSpin;

public class DatabaseController {

    private static DatabaseController databaseController;
    private AppDatabase appDatabase;
    private Context context;
    private List<ObjectsContentSpin> objectsContentSpins = new ArrayList<>();

    public static DatabaseController getInstance(Context context) {
        if (databaseController == null) {
            databaseController = new DatabaseController(context);
        }
        return databaseController;
    }

    public DatabaseController(Context context) {
        appDatabase = AppDatabase.getInstance(context);
        this.context = context;
    }

    public List<ObjectsContentSpin> getContentSpinByTime(long id) {
        if (appDatabase != null) {
            return appDatabase.getContentSpinDao().getContentSpinByTime(id);
        }
        return new ArrayList<>();
    }

    public List<ObjectsContentSpin> getAllContentSpin() {
        if (appDatabase != null) {
            return appDatabase.getContentSpinDao().getAll();
        }
        return new ArrayList<>();
    }

    public List<ObjectSpin> getAllSpin() {
        if (appDatabase != null) {
            return appDatabase.getImageDao().getAll();
        }
        return new ArrayList<>();
    }

    public ObjectSpin getDefaultSpin() {
        if (appDatabase != null) {
            return appDatabase.getImageDao().getSpinDefault();
        }
        return new ObjectSpin();
    }

    public void initDefaultDatabase() {
        initDataStep1();
        initDataStep2();
        initDataStep3();
        initDataStep4();
        initDataStep5();
        initDataStep6();
    }

    private void initDataStep1() {
        ObjectSpin objectSpin = new ObjectSpin();
        long date = System.currentTimeMillis();
        objectSpin.setDate(date);
        objectSpin.setDefault(true);
        objectSpin.setTypespin(Const.TYPE_SPIN_ALL);
        objectSpin.setSuggest(true);
        objectSpin.setTitle(context.getString(R.string.title_default_1));
        appDatabase.getImageDao().insert(objectSpin);

        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.content_default_1));
        strings.add(context.getString(R.string.content_default_2));
        strings.add(context.getString(R.string.content_default_3));
        strings.add(context.getString(R.string.content_default_4));
        strings.add(context.getString(R.string.content_default_5));
        strings.add(context.getString(R.string.content_default_6));
        strings.add(context.getString(R.string.content_default_7));
        strings.add(context.getString(R.string.content_default_8));
        for (int i = 0; i < strings.size(); i++) {
            ObjectsContentSpin objectsContentSpin = new ObjectsContentSpin();
            objectsContentSpin.setId(date + i);
            objectsContentSpin.setContent(strings.get(i));
            objectsContentSpin.setDate(date);
            objectsContentSpins.add(objectsContentSpin);
        }
        appDatabase.getContentSpinDao().insertAll(objectsContentSpins);
    }

    private void initDataStep2() {
        ObjectSpin objectSpin = new ObjectSpin();
        long date = System.currentTimeMillis();
        objectSpin.setTypespin(Const.TYPE_SPIN_ALL);
        objectSpin.setSuggest(true);
        objectSpin.setDate(date);
        objectSpin.setDefault(false);
        objectSpin.setTitle(context.getString(R.string.title_default_2));
        appDatabase.getImageDao().insert(objectSpin);

        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.content_default_1_1));
        strings.add(context.getString(R.string.content_default_2_1));
        strings.add(context.getString(R.string.content_default_3_1));
        strings.add(context.getString(R.string.content_default_4_1));
        strings.add(context.getString(R.string.content_default_5_1));
        strings.add(context.getString(R.string.content_default_6_1));
        for (int i = 0; i < strings.size(); i++) {
            ObjectsContentSpin objectsContentSpin = new ObjectsContentSpin();
            objectsContentSpin.setId(date + i);
            objectsContentSpin.setContent(strings.get(i));
            objectsContentSpin.setDate(date);
            objectsContentSpins.add(objectsContentSpin);
        }
        appDatabase.getContentSpinDao().insertAll(objectsContentSpins);
    }


    private void initDataStep3() {
        ObjectSpin objectSpin = new ObjectSpin();
        long date = System.currentTimeMillis();
        objectSpin.setTypespin(Const.TYPE_SPIN_ALL);
        objectSpin.setSuggest(true);
        objectSpin.setDate(date);
        objectSpin.setDefault(false);
        objectSpin.setTitle(context.getString(R.string.title_default_3));
        appDatabase.getImageDao().insert(objectSpin);

        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.content_default_1_2));
        strings.add(context.getString(R.string.content_default_2_2));
        strings.add(context.getString(R.string.content_default_3_2));
        strings.add(context.getString(R.string.content_default_4_2));
        strings.add(context.getString(R.string.content_default_5_2));
        for (int i = 0; i < strings.size(); i++) {
            ObjectsContentSpin objectsContentSpin = new ObjectsContentSpin();
            objectsContentSpin.setId(date + i);
            objectsContentSpin.setContent(strings.get(i));
            objectsContentSpin.setDate(date);
            objectsContentSpins.add(objectsContentSpin);
        }
        appDatabase.getContentSpinDao().insertAll(objectsContentSpins);
    }


    private void initDataStep4() {
        ObjectSpin objectSpin = new ObjectSpin();
        long date = System.currentTimeMillis();
        objectSpin.setTypespin(Const.TYPE_SPIN_ALL);
        objectSpin.setDate(date);
        objectSpin.setSuggest(true);
        objectSpin.setDefault(false);
        objectSpin.setTitle(context.getString(R.string.title_default_4));
        appDatabase.getImageDao().insert(objectSpin);

        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.content_default_1_3));
        strings.add(context.getString(R.string.content_default_2_3));
        strings.add(context.getString(R.string.content_default_3_3));
        strings.add(context.getString(R.string.content_default_4_3));
        strings.add(context.getString(R.string.content_default_5_3));
        strings.add(context.getString(R.string.content_default_6_3));
        strings.add(context.getString(R.string.content_default_7_3));

        for (int i = 0; i < strings.size(); i++) {
            ObjectsContentSpin objectsContentSpin = new ObjectsContentSpin();
            objectsContentSpin.setId(date + i);
            objectsContentSpin.setContent(strings.get(i));
            objectsContentSpin.setDate(date);
            objectsContentSpins.add(objectsContentSpin);
        }
        appDatabase.getContentSpinDao().insertAll(objectsContentSpins);
    }


    private void initDataStep5() {
        ObjectSpin objectSpin = new ObjectSpin();
        long date = System.currentTimeMillis();
        objectSpin.setTypespin(Const.TYPE_SPIN_ALL);
        objectSpin.setDate(date);
        objectSpin.setDefault(false);
        objectSpin.setSuggest(true);
        objectSpin.setTitle(context.getString(R.string.title_default_5));
        appDatabase.getImageDao().insert(objectSpin);

        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.content_default_1_4));
        strings.add(context.getString(R.string.content_default_2_4));
        strings.add(context.getString(R.string.content_default_3_4));
        strings.add(context.getString(R.string.content_default_4_4));
        strings.add(context.getString(R.string.content_default_5_4));
        strings.add(context.getString(R.string.content_default_6_4));
        strings.add(context.getString(R.string.content_default_7_4));
        for (int i = 0; i < strings.size(); i++) {
            ObjectsContentSpin objectsContentSpin = new ObjectsContentSpin();
            objectsContentSpin.setId(date + i);
            objectsContentSpin.setContent(strings.get(i));
            objectsContentSpin.setDate(date);
            objectsContentSpins.add(objectsContentSpin);
        }
        appDatabase.getContentSpinDao().insertAll(objectsContentSpins);
    }

    private void initDataStep6() {
        ObjectSpin objectSpin = new ObjectSpin();
        long date = System.currentTimeMillis();
        objectSpin.setTypespin(Const.TYPE_SPIN_ALL);
        objectSpin.setDate(date);
        objectSpin.setDefault(false);
        objectSpin.setSuggest(true);
        objectSpin.setTitle(context.getString(R.string.title_default_6));
        appDatabase.getImageDao().insert(objectSpin);

        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.content_default_1_5));
        strings.add(context.getString(R.string.content_default_2_5));
        strings.add(context.getString(R.string.content_default_3_5));
        strings.add(context.getString(R.string.content_default_4_5));
        strings.add(context.getString(R.string.content_default_5_5));

        for (int i = 0; i < strings.size(); i++) {
            ObjectsContentSpin objectsContentSpin = new ObjectsContentSpin();
            objectsContentSpin.setId(date + i);
            objectsContentSpin.setContent(strings.get(i));
            objectsContentSpin.setDate(date);
            objectsContentSpins.add(objectsContentSpin);
        }
        appDatabase.getContentSpinDao().insertAll(objectsContentSpins);
    }

    public void updateIsDefault(long date) {
        if (appDatabase != null) {
            appDatabase.getImageDao().updateIsDefaultFull();
            appDatabase.getImageDao().updateIsDefault(date);
        }
    }

    public void updateContentSpins(ObjectSpin objectSpin, List<ObjectsContentSpin> objectsContentSpins) {
        if (appDatabase != null) {
            appDatabase.getContentSpinDao().deleteContents(objectSpin.getDate());
            appDatabase.getContentSpinDao().insertAll(objectsContentSpins);
            appDatabase.getImageDao().insert(objectSpin);
        }
    }

    public void deleteContentSpins(ObjectSpin objectSpin){
        if (appDatabase != null) {
            appDatabase.getContentSpinDao().deleteContents(objectSpin.getDate());
            appDatabase.getImageDao().deleteByTime(objectSpin.getDate());
        }
    }

}
