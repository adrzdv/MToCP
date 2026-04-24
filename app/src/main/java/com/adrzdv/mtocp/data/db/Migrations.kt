package com.adrzdv.mtocp.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS depots")
        db.execSQL("DROP TABLE IF EXISTS violations")
        db.execSQL("DROP TABLE IF EXISTS branches")
        db.execSQL("DROP TABLE IF EXISTS violation_attributes")

        db.execSQL(
            """
            CREATE TABLE branches (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                short_name TEXT NOT NULL, 
                full_name TEXT NOT NULL, 
                is_active INTEGER NOT NULL
            )
        """
        )

        db.execSQL(
            """
            CREATE TABLE departments (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                short_name TEXT NOT NULL, 
                full_name TEXT NOT NULL, 
                is_active INTEGER NOT NULL
            )
        """
        )

        db.execSQL(
            """
            CREATE TABLE divisions (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                short_name TEXT NOT NULL, 
                name TEXT NOT NULL, 
                is_active INTEGER NOT NULL DEFAULT 1
            )
        """
        )

        db.execSQL("CREATE UNIQUE INDEX index_divisions_name ON divisions(name)")

        db.execSQL(
            """
            CREATE TABLE revision_types (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                name TEXT NOT NULL
            )
        """
        )

        db.execSQL(
            """
            CREATE TABLE violations (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                code INTEGER NOT NULL, 
                description TEXT NOT NULL, 
                criteria TEXT NOT NULL, 
                measure TEXT NOT NULL, 
                is_active INTEGER NOT NULL DEFAULT 1
            )
        """
        )

        db.execSQL("CREATE INDEX idx_violation_code ON violations(code)")

        db.execSQL(
            """
            CREATE TABLE depots (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                short_name TEXT NOT NULL, 
                full_name TEXT NOT NULL, 
                is_active INTEGER NOT NULL, 
                branch_id INTEGER NOT NULL, 
                phone_number TEXT NOT NULL, 
                FOREIGN KEY(branch_id) REFERENCES branches(id) ON DELETE RESTRICT
            )
        """
        )

        db.execSQL("CREATE INDEX index_depots_branch_id ON depots(branch_id)")

        db.execSQL(
            """
            CREATE TABLE violations_divisions (
                division_id INTEGER NOT NULL, 
                violation_id INTEGER NOT NULL, 
                PRIMARY KEY(division_id, violation_id), 
                FOREIGN KEY(division_id) REFERENCES divisions(id) ON DELETE CASCADE, 
                FOREIGN KEY(violation_id) REFERENCES violations(id) ON DELETE CASCADE
            )
        """
        )

        db.execSQL(
            """
            CREATE TABLE violations_departments (
                violation_id INTEGER NOT NULL, 
                department_id INTEGER NOT NULL, 
                PRIMARY KEY(violation_id, department_id), 
                FOREIGN KEY(department_id) REFERENCES departments(id) ON DELETE CASCADE, 
                FOREIGN KEY(violation_id) REFERENCES violations(id) ON DELETE CASCADE
            )
        """
        )

        db.execSQL(
            """
            CREATE TABLE violations_types (
                violation_id INTEGER NOT NULL, 
                type_id INTEGER NOT NULL, 
                PRIMARY KEY(violation_id, type_id), 
                FOREIGN KEY(type_id) REFERENCES revision_types(id) ON DELETE CASCADE, 
                FOREIGN KEY(violation_id) REFERENCES violations(id) ON DELETE CASCADE
            )
        """
        )

        db.execSQL("CREATE INDEX index_violations_departments_violation_id ON violations_departments(violation_id)")
        db.execSQL("CREATE INDEX index_violations_departments_department_id ON violations_departments(department_id)")
        db.execSQL("CREATE INDEX index_violations_divisions_division_id ON violations_divisions(division_id)")
        db.execSQL("CREATE INDEX index_violations_divisions_violation_id ON violations_divisions(violation_id)")
        db.execSQL("CREATE INDEX index_violations_types_violation_id ON violations_types(violation_id)")
        db.execSQL("CREATE INDEX index_violations_types_type_id ON violations_types(type_id)")
    }
}
