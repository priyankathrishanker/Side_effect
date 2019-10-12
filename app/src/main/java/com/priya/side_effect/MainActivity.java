package com.priya.side_effect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button button;
    EditText e;
    TextView t;
    /*Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.107:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    // create an instance of the ApiService
    ApiService apiService = retrofit.create(ApiService.class);
    // make a request by calling the corresponding method
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        e=(EditText)findViewById(R.id.editText);
        final String text = e.getText().toString();
        //t=(TextView)findViewById(R.id.textView);


        button =(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (e.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter the medicine name", Toast.LENGTH_SHORT).show();

                }
                else {
                openrxn();
                }

                /*try {
                    Single<List<Medicine>> medicines = apiService.getMedicineData(
                            e.getText().toString());
                    medicines.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<List<Medicine>>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    // we'll come back to this in a moment
                                }

                                @Override
                                public void onSuccess(List<Medicine> medicines) {
                                    StringBuilder meds = new StringBuilder();
                    for(Medicine med : medicines) {
//                        meds.append(med.getName());
//
                        for(Reaction r : med.getReaction()) {
                            meds.append(r.getName());
                            meds.append(" ");
                        }

                    }
                    t.setText(meds.toString());
                                    ReactionList();
                                }
                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }
                            });


                } catch(Exception e) {
                    e.printStackTrace();
                }

            */
            }
        });

    }
    public void openrxn()
    {
        Intent intent = new Intent(this, ReactionList.class);
        String message = e.getText().toString();
        intent.putExtra("Medicine:",message);
        startActivity(intent);
    }
}
