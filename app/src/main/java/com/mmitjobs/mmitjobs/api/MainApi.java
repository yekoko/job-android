package com.mmitjobs.mmitjobs.api;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mmitjobs.mmitjobs.model.RealmInt;
import com.mmitjobs.mmitjobs.model.RealmString;
import com.mmitjobs.mmitjobs.util.Constant;

import java.io.IOException;
import java.lang.reflect.Type;

import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pyaehein on 7/9/16.
 */
public class MainApi {

    //static Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<RealmList<RealmString>>() {}.getType(), new StringRealmListConverter()).create();

    static Type token = new TypeToken<RealmList<RealmInt>>(){}.getType();
    static Gson gsonInt = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .registerTypeAdapter(token, new TypeAdapter<RealmList<RealmInt>>() {

                @Override
                public void write(JsonWriter out, RealmList<RealmInt> value) throws IOException {
                    // Ignore
                }

                @Override
                public RealmList<RealmInt> read(JsonReader in) throws IOException {
                    RealmList<RealmInt> list = new RealmList<RealmInt>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(new RealmInt(in.nextInt()));
                    }
                    in.endArray();
                    return list;
                }
            })
            .create();

    static Gson gsonString = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .registerTypeAdapter(token, new TypeAdapter<RealmList<RealmString>>() {

                @Override
                public void write(JsonWriter out, RealmList<RealmString> value) throws IOException {
                    // Ignore
                }

                @Override
                public RealmList<RealmString> read(JsonReader in) throws IOException {
                    RealmList<RealmString> list = new RealmList<RealmString>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(new RealmString(in.nextString()));
                    }
                    in.endArray();
                    return list;
                }
            })
            .create();

    static Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constant.Base_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(new OkHttpClient());

    public MainApi() {
    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.build();
        Log.d("PH:", "Main API");
        return retrofit.create(serviceClass);
    }
}
