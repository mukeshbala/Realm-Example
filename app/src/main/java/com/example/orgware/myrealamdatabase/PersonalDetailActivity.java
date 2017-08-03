package com.example.orgware.myrealamdatabase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
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
import io.realm.RealmResults;

/**
 * Created by Orgware on 8/1/2017.
 */

public class PersonalDetailActivity extends AppCompatActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.age)
    EditText age;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.save)
    Button save;
    private int id = 1;
    private PojoItem pojoItem;
    private Realm realm;
    public static PersonalDetailActivity instance;
    private ArrayList<PojoItem> mPojoList = new ArrayList<>();
    private String stName;
    private String stMobile;
    private String stAddress;
    private String stAge;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);
        ButterKnife.bind(this);
        realm = Realm.getInstance(PersonalDetailActivity.this);
        instance = this;
        MyApplication.getInstance().getBusInstance().register(this);
        getAllUser();
    }

    public static PersonalDetailActivity getInstance() {
        return instance;
    }


    @OnClick({R.id.cancel, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.save:
                getValue(null, -1);
                MyApplication.getInstance().getBusInstance().post(new PojoItem());
                break;
        }
    }

    public void getValue(final PojoItem model, final int position) {

        stName = name.getText().toString();
        stMobile = mobile.getText().toString();
        stAddress = address.getText().toString();
        stAge = age.getText().toString();

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
            if (model == null) {
                addDataValue(item);
                finish();

            } else {
                upDateDetails(item, position, model.getId());
                finish();
            }
        } else {
            Toast.makeText(instance, "error", Toast.LENGTH_SHORT).show();
        }
    }

    private void addDataValue(PojoItem model) {
        realm.beginTransaction();
        PojoItem pojoItem = realm.createObject(PojoItem.class);
        pojoItem.setId(id);
        pojoItem.setName(model.getName());
        pojoItem.setMobile(model.getMobile());
        pojoItem.setAddress(model.getAddress());
        pojoItem.setAge(model.getAge());

        mPojoList.add(pojoItem);

        realm.commitTransaction();
        id++;
    }

    public void upDateDetails(PojoItem model, int position, int personId) {
        PojoItem editDetails = realm.where(PojoItem.class).equalTo("id", personId).findFirst();
        realm.beginTransaction();
        editDetails.setName(model.getName());
        editDetails.setMobile(model.getMobile());
        editDetails.setAddress(model.getMobile());
        editDetails.setAge(model.getAge());
        realm.commitTransaction();
        mPojoList.set(personId, editDetails);
    }

    private void getAllUser() {
        RealmResults<PojoItem> results = realm.where(PojoItem.class).findAll();
        realm.beginTransaction();
        for (int i = 0; i < results.size(); i++) {
            mPojoList.add(results.get(i));
        }
        if (results.size() > 0)
            id = realm.where(PojoItem.class).max("id").intValue() + 1;
        realm.commitTransaction();
    }



    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getInstance().getBusInstance().unregister(this);
    }

}
