package ru.bmstu.iu3.totodo.data.db;

import android.provider.BaseColumns;

/**
 * Created by Icetroid on 21.11.2017.
 */

public class TaskContract
{
    private TaskContract(){}

    public static final class TaskEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_FULL_TEXT = "fullText";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_PRIORITY = "priority";
    }

    public static final class PriorityEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "priority";
        public static final String COLUMN_PRIORITY = "priority";
    }
}
