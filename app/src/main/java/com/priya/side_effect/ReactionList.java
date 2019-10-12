package com.priya.side_effect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.priya.side_effect.model.Medicine;
import com.priya.side_effect.model.Reaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReactionList extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.107:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    // create an instance of the ApiService
    ApiService apiService = retrofit.create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openrxn);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("Medicine:");
       // Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
//        final TextView textView = findViewById(R.id.textView2);
        final ListView listView = findViewById(R.id.reactionList);
        try {
            Single<List<Medicine>> medicines = apiService.getMedicineData(message);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.simple_text_item);
            medicines.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<Medicine>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onSuccess(List<Medicine> medicines) {
//                            StringBuilder meds = new StringBuilder();

//                            TreeSet<Map.Entry<String,Integer>> reactions = new TreeSet<>((r1, r2) -> 0);
//                                    new TreeSet<>(
//                                    (Map.Entry r1, Map.Entry r2)-> {
//                                        if (r1.getValue() != r2.getValue()) {
//                                            return r2.getValue().compareTo(r1.getValue());
//                                        } else {
//                                            return r1.getName().compareTo(r2.getName());
//                                        }
//                                    });
                            HashMap<String,Integer> freqMap = new HashMap<>();
                            medicines.forEach(med->{
                                med.getReaction().forEach(r->{
                                    if(freqMap.containsKey(r.getName())) {
                                        freqMap.put(r.getName(),freqMap.get(r.getName())+r.getCount());
                                    } else {
                                        freqMap.put(r.getName(), r.getCount());
                                    }
                                });
                            });
                            List<Map.Entry<String,Integer>> reactions = new ArrayList<>(freqMap.entrySet());
                            Collections.sort(reactions,(r1, r2) -> r2.getValue().compareTo(r1.getValue()));
                            reactions.stream().limit(10).collect(Collectors.toList())
                            .forEach(react->adapter.add(react.getKey() + " " + react.getValue()));
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}