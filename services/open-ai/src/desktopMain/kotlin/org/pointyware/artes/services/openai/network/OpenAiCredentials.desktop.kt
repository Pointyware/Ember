package org.pointyware.artes.services.openai.network

import java.io.File
import java.io.FileInputStream
import java.util.Properties


/**
 * First tries [environmentOpenAiCredentials], then [gradleOpenAiCredentials].
 */
fun openAiCredentials(): Result<OpenAiCredentials> {
    return environmentOpenAiCredentials()
        .onFailure { environmentThrowable ->
            return gradleOpenAiCredentials()
                .onFailure { gradleThrowable ->
                    return Result.failure(IllegalStateException("Environment and gradle both failed: ${environmentThrowable.message} | ${gradleThrowable.message}"))
                }
        }
}

/**
 * Retrieves [OpenAiCredentials] from environment variables.
 * Must be defined as [API_KEY] and [ORG_ID].
 */
fun environmentOpenAiCredentials(): Result<OpenAiCredentials> {
    val key = System.getProperty(API_KEY) ?: return Result.failure(IllegalStateException("Environment variable $API_KEY undefined."))
    val id = System.getProperty(ORG_ID) ?: return Result.failure(IllegalStateException("Environment variable $ORG_ID undefined."))
    return Result.success(OpenAiCredentials(key, id))
}

/**
 * Retrieves [OpenAiCredentials] from a module's ".gradle/local.properties".
 * Must be defined as [API_KEY] and [ORG_ID].
 */
fun gradleOpenAiCredentials(): Result<OpenAiCredentials> {
    return fileOpenAiCredentials(".gradle/local.properties")
}

/**
 * Reads the [Properties] file at [path] and gets [OpenAiCredentials].
 * @see fromProperties
 */
fun fileOpenAiCredentials(path: String): Result<OpenAiCredentials> {
    return FileInputStream(File(path)).use { fis ->
        Properties().apply { load(fis) }
    }.let { properties ->
        fromProperties(properties)
    }
}

/**
 * Retrieves [OpenAiCredentials] from a [Properties] object.
 * Must be defined as [API_KEY] and [ORG_ID].
 */
fun fromProperties(properties: Properties): Result<OpenAiCredentials> {
    val key = properties.getProperty(API_KEY) ?: return Result.failure(IllegalStateException("Property $API_KEY undefined."))
    val id = properties.getProperty(ORG_ID) ?: return Result.failure(IllegalStateException("Property $ORG_ID undefined."))
    return Result.success(OpenAiCredentials(apiKey = key, orgId = id))
}
