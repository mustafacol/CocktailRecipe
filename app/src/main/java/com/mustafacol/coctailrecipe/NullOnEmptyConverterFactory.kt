package com.mustafacol.coctailrecipe

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
       val delegate = retrofit.nextResponseBodyConverter<ResponseBody>(this,type,annotations)
        return (label@ Converter<ResponseBody, Any?> { body: ResponseBody ->
            if (body.source().exhausted()) return@Converter null
            delegate.convert(body)
        })
    }
}