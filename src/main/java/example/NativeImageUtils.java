package example;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.graalvm.nativeimage.hosted.Feature.FeatureAccess;
import org.graalvm.nativeimage.hosted.RuntimeReflection;

/** Internal class offering helper methods for registering methods/classes for reflection. */
public class NativeImageUtils {

    private static final Logger LOGGER = Logger.getLogger(NativeImageUtils.class.getName());
    private static final String CLASS_REFLECTION_ERROR_MESSAGE =
            "Failed to find {0} on the classpath for reflection.";

    private NativeImageUtils() {}

    /** Registers an entire class for reflection use. */
    public static void registerClassForReflection(FeatureAccess access, String name) {
        Class<?> clazz = access.findClassByName(name);
        if (clazz != null) {
            RuntimeReflection.register(clazz);
            RuntimeReflection.register(clazz.getDeclaredConstructors());
            RuntimeReflection.register(clazz.getDeclaredFields());
            RuntimeReflection.register(clazz.getDeclaredMethods());
        } else {
            LOGGER.log(Level.WARNING, CLASS_REFLECTION_ERROR_MESSAGE, name);
        }
    }
}
