package com.adrzdv.mtocp.util.importmanager.handlers;

import java.util.List;

public interface ImportHandler<T> {
    void handle(List<T> items);
}
