package id.xaxxis.moviecatalogue.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_FAVORITE = "favorite";

    static final class FavoriteColumns implements BaseColumns {
        static String ITEM_ID = "itemId";
        static String TITLE = "title";
        static String DATE_RELEASE = "date_release";
    }
}
