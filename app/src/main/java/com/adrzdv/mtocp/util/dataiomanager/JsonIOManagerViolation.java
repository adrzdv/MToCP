package com.adrzdv.mtocp.util.dataiomanager;

import android.content.Context;
import android.net.Uri;

import com.adrzdv.mtocp.ErrorCodes;
import com.adrzdv.mtocp.data.importmodel.ViolationImport;
import com.adrzdv.mtocp.util.ListViolationToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class JsonIOManagerViolation implements DataIOManager<ViolationImport> {

    @Override
    public boolean exportData(Context context, Object object) {
        throw new UnsupportedOperationException(ErrorCodes.UNHANDLED_METHOD.toString());
    }

    @Override
    public List<ViolationImport> importData(Context context, Uri uri) {

        List<ViolationImport> importedList;

        Gson gson = new GsonBuilder().create();

        try (InputStream input = context.getContentResolver().openInputStream(uri)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                StringBuilder jsonString = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonString.append(line);
                }
                String jsonData = jsonString.toString();
                importedList = gson.fromJson(jsonData, new ListViolationToken().getType());

            }
        } catch (IOException e) {
            return Collections.emptyList();
        }

        return importedList;

    }
}
