package com.adrzdv.mtocp.util.importmanager;

import java.util.List;

public interface ImportHandler<T> {
    void handle(List<T> items);
}
