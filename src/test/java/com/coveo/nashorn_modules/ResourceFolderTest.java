package com.coveo.nashorn_modules;

import org.junit.Test;
import org.openjdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.ScriptEngineManager;

import static org.junit.Assert.*;

public class ResourceFolderTest {
    private final ResourceFolder root = ResourceFolder.create(getClass().getClassLoader(), "com/coveo/nashorn_modules/test1", "UTF-8");

    @Test
    public void rootFolderHasTheExpectedProperties() {
        assertEquals("/", root.getPath());
        assertNull(root.getParent());
    }

    @Test
    public void getFileReturnsTheContentOfTheFileWhenItExists() {
        assertTrue(root.getFile("foo.js").contains("foo"));
    }

    @Test
    public void getFileReturnsNullWhenFileDoesNotExists() {
        assertNull(root.getFile("invalid"));
    }

    @Test
    public void getFolderReturnsAnObjectWithTheExpectedProperties() {
        Folder sub = root.getFolder("subdir");
        assertEquals("/subdir/", sub.getPath());
        assertSame(root, sub.getParent());
        Folder subsub = sub.getFolder("subsubdir");
        assertEquals("/subdir/subsubdir/", subsub.getPath());
        assertSame(sub, subsub.getParent());
    }

    @Test
    public void getFolderNeverReturnsNullBecauseItCannot() {
        assertNotNull(root.getFolder("subdir"));
        assertNotNull(root.getFolder("invalid"));
    }

    @Test
    public void getFileCanBeUsedOnSubFolderIfFileExist() {
        assertTrue(root.getFolder("subdir").getFile("bar.js").contains("bar"));
    }

    @Test
    public void resourceFolderWorksWhenUsedForReal() throws Throwable {
        NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
        Require.enable(engine, root);
        assertEquals("spam", engine.eval("require('./foo').bar.spam.spam"));
    }
}
