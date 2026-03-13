package com.example.apiscript.api

/* Per tal de poder importar BuildConfig, serà necessari fer:
    1. Editar app/build.gradle.kts i afegir:
        1.1. secretsFile = project.rootProject.file("app/secrets.properties") ...
        1.2. buildConfigFields... dins de defaultConfig
        1.3. buildFeatures{ buildConfig = true }
    2. File -> Sync Project with Gradle Files
    3. Build -> Clean Project
    4. Build -> Assemble Module '...'
 */

import com.example.apiscript.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object RetrofitInstance {
    // Agafa la BASE_URL de les peticions del servei web de secrets.properties
    private val BASE_URL = BuildConfig.BASE_URL

    val api: AssistenciaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AssistenciaApiService::class.java)
    }
}
