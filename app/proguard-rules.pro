# VYRA ProGuard/R8 rules.
# Minification is disabled until Phase 10; keep-rules will be added per
# subsystem (AI runtime, ARCore, serialization models) as those land.

# Keep Kotlinx Serialization generated serializers.
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.**
-keepclassmembers class **$$serializer { *; }
