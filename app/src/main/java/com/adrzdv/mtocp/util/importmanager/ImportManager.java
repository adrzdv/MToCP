package com.adrzdv.mtocp.util.importmanager;

import android.content.Context;
import android.net.Uri;

import com.adrzdv.mtocp.MessageCodes;
import com.adrzdv.mtocp.data.importmodel.DepotImport;
import com.adrzdv.mtocp.data.importmodel.ViolationImport;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
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

            Gson gson = new Gson();
            ImportData data = gson.fromJson(reader, ImportData.class);

            executor.execute(() -> {
                applyHandler(data.getViolationList(), ViolationImport.class);
                applyHandler(data.getDepotsList(), DepotImport.class);
            });
            onResult.accept(MessageCodes.SUCCESS.getErrorTitle());

        } catch (Exception e) {
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
