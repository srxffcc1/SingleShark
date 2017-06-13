package com.businessframehelp.utils;

import android.database.sqlite.SQLiteQueryBuilder;

import com.businessframehelp.db.IDao;
import com.kymjs.common.StringUtils;

/**
 * Created by Administrator on 2017/5/14.
 */

public class DbUtil {

    /**
     * 交集
     *
     * @param daos
     * @return
     */
    public String justgetSqlINTERSECT(IDao... daos) {
        String sqls = "";
        for (int i = 0; i < daos.length; i++) {
            String sqlObject = justgetSql(daos[i]);
            sqls = sqls + sqlObject + " INTERSECT ";
        }
        if (sqls.length() > 10) {
            sqls = sqls.substring(0, sqls.length() - " INTERSECT ".length());
        }
        return "" + sqls + "";
    }
    /**
     * 并集
     *
     * @param daos
     * @return
     */
    public String justgetSqlUNION(IDao... daos) {
        String sqls = "";
        for (int i = 0; i < daos.length; i++) {
            String sqlObject = justgetSql(daos[i]);
            sqls = sqls + sqlObject + " UNION ";
        }
        if (sqls.length() > 10) {
            sqls = sqls.substring(0, sqls.length() - " UNION ".length());
        }
        return "" + sqls + "";
    }

    public String justgetSql(IDao dao) {
        String sql = "";
        sql = SQLiteQueryBuilder.buildQueryString(false, dao.getTable(), null, dao.getSelection(), null, null, null, null);
        String[] selectionArgs = dao.getSelectionArgs();
        if (selectionArgs != null) {
            for (int i = 0; i < selectionArgs.length; i++) {
                sql = sql.replaceFirst("\\?", "'" + StringUtils.washString(selectionArgs[i]) + "'");
            }
        } else {

        }
        return sql;
    }
    public String justgetSqlNeedCount(IDao dao) {
        String sql = "";
        sql = SQLiteQueryBuilder.buildQueryString(false, dao.getTable(), new String[]{"COUNT(*)"}, dao.getSelection(), null, null, null, null);
        String[] selectionArgs = dao.getSelectionArgs();
        if (selectionArgs != null) {
            for (int i = 0; i < selectionArgs.length; i++) {
                sql = sql.replaceFirst("\\?", "'" + StringUtils.washString(selectionArgs[i]) + "'");
            }
        } else {

        }
        return sql;
    }
}
