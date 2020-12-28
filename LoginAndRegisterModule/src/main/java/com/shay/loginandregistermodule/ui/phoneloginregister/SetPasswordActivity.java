package com.shay.loginandregistermodule.ui.phoneloginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.shay.loginandregistermodule.R;
import com.shay.loginandregistermodule.viewmodel.SetPasswordFactory;
import com.shay.loginandregistermodule.viewmodel.SetPasswordViewModel;

public class SetPasswordActivity extends AppCompatActivity {

    private SetPasswordViewModel setPasswordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        setPasswordViewModel = new ViewModelProvider(this, new SetPasswordFactory())
                .get(SetPasswordViewModel.class);
    }


    private void init(){
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("userId");

    }

    public void initObserver(){

    }

    public void initListener(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
