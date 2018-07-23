package com.example.user.finale.data;

import android.provider.BaseColumns;

public class Contract {
    public static final class diseaseEntry {
        public static final String TABLE_NAME = "diseases";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DISEASE_NAME = "name";
        public static final String COLUMN_DISEASE_DESC = "desc";
        public static final String COLUMN_DISEASE_SYMPTOMS = "symptoms";
        public static final String COLUMN_DISEASE_TREATMENT = "treatment";
    }
}
