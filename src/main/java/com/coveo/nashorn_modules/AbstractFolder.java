package com.coveo.nashorn_modules;

public abstract class AbstractFolder implements Folder {

    public Folder parent;
    public String path;

    protected AbstractFolder(Folder parent, String path) {
        this.parent = parent;
        this.path = path;
    }

    public Folder getParent() {
        return parent;
    }

    public String getPath() {
        return path;
    }

}
