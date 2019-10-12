package com.priya.side_effect;

import com.priya.side_effect.model.Medicine;

import java.util.List;
import java.util.jar.Attributes;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("all_medicines/{name}")
    Single<List<Medicine>> getMedicineData(@Path("name") String name);
}
