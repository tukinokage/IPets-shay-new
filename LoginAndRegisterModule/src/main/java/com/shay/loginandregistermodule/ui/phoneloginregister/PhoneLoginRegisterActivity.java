package com.shay.loginandregistermodule.ui.phoneloginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.shay.loginandregistermodule.R;
import com.shay.loginandregistermodule.data.datasource.PhoneSmsDataSource;
import com.shay.loginandregistermodule.data.model.SmsResultStauts;
import com.shay.loginandregistermodule.data.repository.PhoneSmsRepository;
import com.shay.loginandregistermodule.viewmodel.PhoneSmsViewModel;
import com.shay.loginandregistermodule.viewmodel.PhoneSmsViewModelFactory;

public class PhoneLoginRegisterActivity extends AppCompatActivity {
private PhoneSmsViewModel phoneSmsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login_register);
        phoneSmsViewModel = new ViewModelProvider(this, new PhoneSmsViewModelFactory())
                .get(PhoneSmsViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        phoneSmsViewModel.getSmsResultLiveData().observe(this, new Observer<SmsResultStauts>() {
            @Override
            public void onChanged(SmsResultStauts smsResultStauts) {

            }
        });

    }

    public void confirm(View v){

    }

    @Override
    protected void onDestroy() {
        phoneSmsViewModel.cancelAsyncTask();
        super.onDestroy();
    }
}
