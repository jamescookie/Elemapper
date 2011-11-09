package org.elephant.mapper.resources;

import java.net.URL;

public class ResourceLoader {
    public static URL load(String path) {
        return ResourceLoader.class.getResource(path);
    }
}
