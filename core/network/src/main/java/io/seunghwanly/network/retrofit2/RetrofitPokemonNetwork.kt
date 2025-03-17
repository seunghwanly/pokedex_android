package io.seunghwanly.network.retrofit2


import androidx.core.os.trace
import io.seunghwanly.model.Pokemon
import io.seunghwanly.model.PokemonName
import io.seunghwanly.network.PokemonNetworkDataSource
import io.seunghwanly.network.model.ApiResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.Call
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


// Retrofit API declaration for Pokemon API
private interface RetrofitPokemonNetworkApi {
    @GET(value = "pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
    ): ApiResponse<List<PokemonName>>

    @GET(value = "pokemon/{id}")
    suspend fun getPokemonById(id: Int): ApiResponse<Pokemon>
}

@Singleton
internal class RetrofitPokemonNetwork @Inject constructor(
    private val baseUrl: String,
    private val networkJson: Json,
    private val okHttpCallFactory: dagger.Lazy<Call.Factory>,
) : PokemonNetworkDataSource {

    private val api = trace("RetrofitPokemonNetwork") {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .callFactory { okHttpCallFactory.get().newCall(it) }
            .addConverterFactory(KotlinxSerializationConverterFactory(networkJson))
            .build()
            .create(RetrofitPokemonNetworkApi::class.java)
    }

    override suspend fun fetchPokemons(
        limit: Int?,
        offset: Int?,
    ): ApiResponse<List<PokemonName>> =
        api.getPokemons(limit = limit, offset = offset)

    override suspend fun fetchPokemonById(
        id: Int,
    ): ApiResponse<Pokemon> =
        api.getPokemonById(id)
}

// TODO(seunghwanly): Library for generic types?
private class KotlinxSerializationConverterFactory(
    private val json: Json,
) : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation?>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *>? {
        val serializer = json.serializersModule.serializer(type)
        return Converter<ResponseBody, Any> { body ->
            json.decodeFromString(
                serializer,
                body.string()
            )
        }
    }
}