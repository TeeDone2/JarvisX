import com.android.build.api.dsl.ApkSigningConfig
val ksPath = System.getenv("ANDROID_KEYSTORE_PATH")
val ksPass = System.getenv("ANDROID_KEYSTORE_PASSWORD")
val keyAlias = System.getenv("ANDROID_KEY_ALIAS")
val keyPass = System.getenv("ANDROID_KEY_PASSWORD")

fun ApkSigningConfig.applyIfAvailable() {
  if (!ksPath.isNullOrEmpty() && !ksPass.isNullOrEmpty() && !keyAlias.isNullOrEmpty() && !keyPass.isNullOrEmpty()) {
    storeFile = file(ksPath)
    storePassword = ksPass
    keyAlias = keyAlias
    keyPassword = keyPass
  }
}
