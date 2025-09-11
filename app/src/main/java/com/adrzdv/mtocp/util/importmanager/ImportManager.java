package com.adrzdv.mtocp.util.importmanager;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.adrzdv.mtocp.MessageCodes;
import com.adrzdv.mtocp.data.importmodel.AdditionalParamImport;
import com.adrzdv.mtocp.data.importmodel.CompanyImport;
import com.adrzdv.mtocp.data.importmodel.DepotImport;
import com.adrzdv.mtocp.data.importmodel.TrainImport;
import com.adrzdv.mtocp.data.importmodel.ViolationImport;
import com.adrzdv.mtocp.util.importmanager.handlers.ImportHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class ImportManager {

    private final ImportHandlerRegistry registry;
    private final Executor executor;

    public ImportManager(ImportHandlerRegistry registry, Executor executor) {
        this.registry = registry;
        this.executor = executor;
    }

    public void importFromJson(Context context, Uri uri, Consumer<String> onResult) {
        try (InputStream input = context.getContentResolver().openInputStream(uri);
             InputStreamReader reader = new InputStreamReader(input)) {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, ctx) ->
                            LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE)
                    )
                    .create();
            ImportData data = gson.fromJson(reader, ImportData.class);

            executor.execute(() -> {
                applyHandler(data.getViolationList(), ViolationImport.class);
                applyHandler(data.getDepotsList(), DepotImport.class);
                applyHandler(data.getCompanyList(), CompanyImport.class);
                applyHandler(data.getTrainList(), TrainImport.class);
                applyHandler(data.getAdditionalParamList(), AdditionalParamImport.class);
            });
            onResult.accept(MessageCodes.SUCCESS.getErrorTitle());

        } catch (Exception e) {
            Log.d("IMPORT_MANAGER", e.getMessage());
            onResult.accept(MessageCodes.LOAD_ERROR.toString());
        }
    }

    private <T> void applyHandler(List<T> list, Class<T> clazz) {
        if (list != null && !list.isEmpty()) {
            ImportHandler<T> handler = registry.getHandler(clazz);
            if (handler != null) {
                handler.handle(list);
            }
        }
    }
}
