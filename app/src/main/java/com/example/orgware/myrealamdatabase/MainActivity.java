package com.example.orgware.myrealamdatabase;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements SampleAdapter.ClickManager {


    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.add)
    FloatingActionButton add;
    private Context context;

    private int id = 1;
    private Realm realm;
    private SampleAdapter sampleAdapter;
    private ArrayList<PojoItem> mListItem = new ArrayList<>();
    private static MainActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyApplication.getInstance().getBusInstance().register(this);
        realm = Realm.getInstance(MainActivity.this);
        instance = this;
        listView.setLayoutManager(new LinearLayoutManager(this));
        sampleAdapter = new SampleAdapter(this, this);
        listView.setAdapter(sampleAdapter);


        showList();

    }

    static MainActivity getInstance() {
        return instance;
    }


    private void showList() {
        mListItem.clear();
        getAllUser();
        sampleAdapter.setList(mListItem);
    }

    private void getAllUser() {
        RealmResults<PojoItem> results = realm.where(PojoItem.class).findAll();
        realm.beginTransaction();
        for (int i = 0; i < results.size(); i++) {
            mListItem.add(results.get(i));
        }
        if (results.size() > 0)
            id = realm.where(PojoItem.class).max("id").intValue() + 1;
        realm.commitTransaction();
    }

    @Override
    public void onItemClicked(PojoItem mList) {
        showDeleteDialog(mList.getId());
    }

    @Override
    public void onImageClicker(PojoItem mList) {

        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("id", String.valueOf(mList.getId()));
        intent.putExtra("name", mList.getName());
        intent.putExtra("mobile", mList.getMobile());
        intent.putExtra("address", mList.getAddress());
        intent.putExtra("age", String.valueOf(mList.getAge()));
        startActivity(intent);

    }

    public PojoItem searchPerson(int personId) {
        RealmResults<PojoItem> results = realm.where(PojoItem.class).equalTo("id", personId).findAll();
        realm.beginTransaction();
        realm.commitTransaction();
        return results.get(0);
    }

    @OnClick(R.id.add)
    public void onClick() {
        startActivity(new Intent(getApplicationContext(), PersonalDetailActivity.class));
    }

    public PojoItem searchList(int listId) {
        RealmResults<PojoItem> results = realm.where(PojoItem.class).equalTo("id", listId).findAll();
        realm.beginTransaction();
        realm.commitTransaction();
        return results.get(0);
    }

    public void deleteList(int personId) {
        RealmResults<PojoItem> result = realm.where(PojoItem.class).equalTo("id", personId).findAll();
        realm.beginTransaction();
        result.remove(0);
        realm.commitTransaction();
    }

    public void showDeleteDialog(final int personId) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sample);
        Button mNo = (Button) dialog.findViewById(R.id.no);
        Button mYes = (Button) dialog.findViewById(R.id.yes);
        mNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().deleteList(personId);
                showList();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Subscribe
    public void getAllDate(PojoItem item) {
        showList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().getBusInstance().unregister(this);
    }

}
