package com.example.orgware.myrealamdatabase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by Orgware on 8/1/2017.
 */

public class DetailActivity extends AppCompatActivity {


//    @BindView(R.id.ide)
//    EditText id;
    @BindView(R.id.txtname)
    EditText txtname;
    @BindView(R.id.txtmobile)
    EditText txtmobile;
    @BindView(R.id.txtaddress)
    EditText txtaddress;
    @BindView(R.id.txtage)
    EditText txtage;
    @BindView(R.id.txtcancel)
    Button txtcancel;
    @BindView(R.id.txtsave)
    Button txtsave;

    private ArrayList<PojoItem> mPojoList = new ArrayList<>();
    private String stName;
    private String stMobile;
    private String stAddress;
    private String stAge;
    private PojoItem pojoItem;

    Realm realm;
    public static DetailActivity instance;
    String ids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        instance =this;
        MyApplication.getInstance().getBusInstance().register(this);
        realm = Realm.getInstance(DetailActivity.this);
         ids = getIntent().getStringExtra("id");

        txtname.setText(getIntent().getStringExtra("name"));
        txtmobile.setText(getIntent().getStringExtra("mobile"));
        txtaddress.setText(getIntent().getStringExtra("address"));
        txtage.setText(getIntent().getStringExtra("age"));
    }
    public DetailActivity getInstance() {
        return instance;
    }
    @OnClick({R.id.txtcancel, R.id.txtsave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtcancel:
                finish();
                break;
            case R.id.txtsave:
                getValue(Integer.parseInt(ids) );
                MyApplication.getInstance().getBusInstance().post(new PojoItem());

                break;
        }
    }

    public void updateDetails(PojoItem model,int personId){
        PojoItem item = realm.where(PojoItem.class).equalTo("id",personId).findFirst();
        realm.beginTransaction();
        item.setName(model.getName());
        item.setMobile(model.getMobile());
        item.setAddress(model.getAddress());
        item.setAge(model.getAge());
        realm.commitTransaction();
    }

    public void getValue( final int position) {

        stName = txtname.getText().toString();
        stMobile = txtmobile.getText().toString();
        stAddress = txtaddress.getText().toString();
        stAge = txtage.getText().toString();

        if (stName.equals("")) {
            Toast.makeText(this, "fill all details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (stMobile.equals("")) {
            Toast.makeText(this, "fill all details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (stAddress.equals("")) {
            Toast.makeText(this, "fill all details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (stAge.equals("")) {
            Toast.makeText(this, "fill all details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!stName.equals("") && !stMobile.equals("") && !stAddress.equals("") && !stAge.equals("")) {
            PojoItem item = new PojoItem();
            item.setName(stName);
            item.setMobile(stMobile);
            item.setAddress(stAddress);
            item.setAge(Integer.parseInt(stAge));
                updateDetails(item, position);
                finish();
            return;
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getInstance().getBusInstance().unregister(this);
    }



}
