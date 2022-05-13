# GraalVM native image build failure with gax-grpc

```
~/pubsub-sample-graal22.1-failure $ java -version                                                            git[branch:main]
openjdk version "11.0.15" 2022-04-19
OpenJDK Runtime Environment GraalVM CE 22.1.0 (build 11.0.15+10-jvmci-22.1-b06)
OpenJDK 64-Bit Server VM GraalVM CE 22.1.0 (build 11.0.15+10-jvmci-22.1-b06, mixed mode, sharing)
~/pubsub-sample-graal22.1-failure $ mvn package -P native -DskipTests                                        git[branch:main]
[INFO] Scanning for projects...
...
========================================================================================================================
GraalVM Native Image: Generating 'native-image-sample' (executable)...
========================================================================================================================
[1/7] Initializing...                                                                                    (6.9s @ 0.42GB)
 Version info: 'GraalVM 22.1.0 Java 11 CE'
 C compiler: cc (apple, x86_64, 13.1.6)
 Garbage collector: Serial GC
 5 user-provided feature(s)
  - com.google.api.gax.grpc.nativeimage.GrpcNettyFeature
  - com.google.api.gax.grpc.nativeimage.ProtobufMessageFeature
  - com.google.api.gax.nativeimage.GoogleJsonClientFeature
  - com.google.api.gax.nativeimage.OpenCensusFeature
  - com.oracle.svm.thirdparty.gson.GsonFeature
[2/7] Performing analysis...  [*]                                                                       (14.1s @ 1.66GB)
   4,888 (81.93%) of  5,966 classes reachable
   7,175 (60.14%) of 11,931 fields reachable
  31,073 (60.23%) of 51,593 methods reachable
     320 classes,   945 fields, and 5,976 methods registered for reflection

Fatal error: org.graalvm.compiler.debug.GraalError: com.oracle.graal.pointsto.constraints.UnsupportedFeatureException: Detected a PlatformManagedObject (a MXBean defined by the virtual machine) in the image heap. This bean is introspecting the VM that runs the image builder, i.e., a VM instance that is no longer available at image runtime. Class of disallowed object: com.sun.management.internal.HotSpotDiagnostic  To see how this object got instantiated use --trace-object-instantiation=com.sun.management.internal.HotSpotDiagnostic. The object was probably created by a class initializer and is reachable from a static field. You can request class initialization at image runtime by using the option --initialize-at-run-time=<class-name>. Or you can write your own initialization methods and call them explicitly from your main entry point.
	at com.oracle.graal.pointsto.util.AnalysisFuture.setException(AnalysisFuture.java:49)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:269)
	at com.oracle.graal.pointsto.util.AnalysisFuture.ensureDone(AnalysisFuture.java:63)
	at com.oracle.graal.pointsto.heap.ImageHeapScanner.lambda$postTask$9(ImageHeapScanner.java:611)
	at com.oracle.graal.pointsto.util.CompletionExecutor.executeCommand(CompletionExecutor.java:193)
	at com.oracle.graal.pointsto.util.CompletionExecutor.lambda$executeService$0(CompletionExecutor.java:177)
	at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.exec(ForkJoinTask.java:1426)
	at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
	at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
	at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1656)
	at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)
	at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:183)
Caused by: com.oracle.graal.pointsto.constraints.UnsupportedFeatureException: Detected a PlatformManagedObject (a MXBean defined by the virtual machine) in the image heap. This bean is introspecting the VM that runs the image builder, i.e., a VM instance that is no longer available at image runtime. Class of disallowed object: com.sun.management.internal.HotSpotDiagnostic  To see how this object got instantiated use --trace-object-instantiation=com.sun.management.internal.HotSpotDiagnostic. The object was probably created by a class initializer and is reachable from a static field. You can request class initialization at image runtime by using the option --initialize-at-run-time=<class-name>. Or you can write your own initialization methods and call them explicitly from your main entry point.
	at com.oracle.svm.hosted.image.DisallowedImageHeapObjectFeature.error(DisallowedImageHeapObjectFeature.java:173)
	at com.oracle.svm.hosted.image.DisallowedImageHeapObjectFeature.checkDisallowedMBeanObjects(DisallowedImageHeapObjectFeature.java:162)
	at com.oracle.svm.hosted.image.DisallowedImageHeapObjectFeature.replacer(DisallowedImageHeapObjectFeature.java:119)
	at com.oracle.graal.pointsto.meta.AnalysisUniverse.replaceObject(AnalysisUniverse.java:582)
	at com.oracle.svm.hosted.ameta.AnalysisConstantReflectionProvider.replaceObject(AnalysisConstantReflectionProvider.java:257)
	at com.oracle.svm.hosted.ameta.AnalysisConstantReflectionProvider.interceptValue(AnalysisConstantReflectionProvider.java:228)
	at com.oracle.svm.hosted.heap.SVMImageHeapScanner.transformFieldValue(SVMImageHeapScanner.java:126)
	at com.oracle.graal.pointsto.heap.ImageHeapScanner.onFieldValueReachable(ImageHeapScanner.java:331)
	at com.oracle.graal.pointsto.heap.ImageHeapScanner.onFieldValueReachable(ImageHeapScanner.java:310)
	at com.oracle.graal.pointsto.heap.ImageHeapScanner.lambda$computeTypeData$1(ImageHeapScanner.java:153)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	... 10 more
------------------------------------------------------------------------------------------------------------------------
                        1.1s (4.9% of total time) in 19 GCs | Peak RSS: 2.71GB | CPU load: 5.89
========================================================================================================================
Failed generating 'native-image-sample' after 21.4s.
Error: Image build request failed with exit status 1
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  23.871 s
[INFO] Finished at: 2022-05-12T22:18:12-04:00
[INFO] ------------------------------------------------------------------------
...
```