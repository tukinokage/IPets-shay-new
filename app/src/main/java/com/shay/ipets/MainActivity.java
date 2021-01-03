package com.shay.ipets;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.shay.ipets.ui.dialog.OperatorDialog;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {
    OperatorDialog dialog;
    @BindView(R.id.bottom_operate_btn)
    Button operaterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        initListener();
    }

    private void initListener(){
        operaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog == null){
                    dialog = new OperatorDialog(MainActivity.this);
                }
                if(dialog.isShowing()){
                    dialog.dismiss();
                }else {

                    dialog.show();
                }

            }
        });
    }

}
