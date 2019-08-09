package id.xaxxis.moviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import id.xaxxis.moviecatalogue.mvp.model.Data;

import static id.xaxxis.moviecatalogue.db.DatabaseContract.FavoriteColumns.DATE_RELEASE;
import static id.xaxxis.moviecatalogue.db.DatabaseContract.FavoriteColumns.ITEM_ID;
import static id.xaxxis.moviecatalogue.db.DatabaseContract.FavoriteColumns.TITLE;
import static id.xaxxis.moviecatalogue.db.DatabaseContract.TABLE_FAVORITE;

public class FavoriteHelpler {
    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DatabaseHelpler databaseHelpler;
    private static FavoriteHelpler INSTANCE;

    private static SQLiteDatabase database;

    private FavoriteHelpler(Context context) {
        databaseHelpler = new DatabaseHelpler(context);
    }

    public static FavoriteHelpler getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelpler(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelpler.getWritableDatabase();
    }

    public void close() {
        databaseHelpler.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<Data> getAllFavorites() {
        ArrayList<Data> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null,
                ITEM_ID, null);
        cursor.moveToFirst();
        Data data;
        if (cursor.getCount() > 0) {
            do {
                data = new Data();
                data.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_ID)));
                data.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                data.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE_RELEASE)));

                arrayList.add(data);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFavorite(Data data) {
        ContentValues args = new ContentValues();
        args.put(ITEM_ID, data.getId());
        args.put(TITLE, data.getTitle());
        args.put(DATE_RELEASE, data.getReleaseDate());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteFavorite(int id) {
        return database.delete(TABLE_FAVORITE, ITEM_ID + " = '" + id + "'", null);
    }
}
