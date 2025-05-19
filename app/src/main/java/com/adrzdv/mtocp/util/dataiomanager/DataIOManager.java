package com.adrzdv.mtocp.util.dataiomanager;

import android.content.Context;
import android.net.Uri;

import java.util.List;

public interface DataIOManager<T> {

    boolean exportData(Context context, Object object);

    List<T> importData(Context context, Uri uri);

}
