package example;

import static example.NativeImageUtils.registerClassForReflection;

import org.graalvm.nativeimage.hosted.Feature;

/** Configures Native Image settings for the grpc-netty-shaded dependency. */
final class GrpcNettyFeature implements Feature {

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        loadMiscClasses(access);
    }

    /** Miscellaneous classes that need to be registered coming from various JARs. */
    private static void loadMiscClasses(BeforeAnalysisAccess access) {
        // This commented out line is the root cause of the problem
        // registerClassForReflection(access, "java.lang.management.ManagementFactory");

        registerClassForReflection(access, "java.lang.management.RuntimeMXBean");
    }
}
