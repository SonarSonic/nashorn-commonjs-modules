package com.coveo.nashorn_modules;

public interface Folder {

    Folder getParent();

    String getPath();

    String getFile(String name);

    Folder getFolder(String name);

}
