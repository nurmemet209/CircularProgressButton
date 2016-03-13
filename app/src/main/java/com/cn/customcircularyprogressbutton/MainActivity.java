package com.cn.customcircularyprogressbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cn.library.CircularProgressButton;

public class MainActivity extends AppCompatActivity {

    private Button drigger;
    private CircularProgressButton progressButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         drigger=(Button)findViewById(R.id.drigger);
        progressButton=(CircularProgressButton)findViewById(R.id.circle_progress_btn);

        drigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressButton.getState()== CircularProgressButton.State.IDLE){
                    progressButton.set2Progress();
                }
                else if(progressButton.getState()== CircularProgressButton.State.PROGRESS){
                   //progressButton.set2Success();
                    progressButton.set2Error();
                }
                else if(progressButton.getState()== CircularProgressButton.State.ERROR){
                    progressButton.set2Idle();
                }

               // progressButton.setPressed(true);

            }
        });

        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
