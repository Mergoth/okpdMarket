package ru.okpdmarket.util;

public class ComponentVersion {

    private static final String UNKNOWN = "unknown";

    /**
     * Returns version of the component from Manifest.mf file
     * It searches for 'Implementation-Version' attribute.
     * And returns 'unknown' in case when component wasn't packed into jar file,
     * so it's impossible to determine the version of component
     *
     * @param clazz Any class of the component
     * @return version of the component
     */
    public static String getComponentVersion(Class clazz) {
        String version = clazz.getPackage().getImplementationVersion();
        return version == null ? UNKNOWN : version;
    }

    public static String getComponentTitle(Class clazz) {
        String title = clazz.getPackage().getImplementationTitle();
        return title == null ? UNKNOWN : title;
    }
}
