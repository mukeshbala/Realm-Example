package com.example.orgware.myrealamdatabase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Orgware on 8/1/2017.
 */

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {
    Context context;
    List<PojoItem> pojoItems ;
    ClickManager clickManager;
    int id;


    public SampleAdapter(Context context, ClickManager clickManager) {
        this.context = context;
        pojoItems=new ArrayList<>();
        this.clickManager = clickManager;
    }

    public void setList(List<PojoItem> list){
        if(list!=null){
            pojoItems.clear();
            pojoItems.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample, null);
        ViewHolder holder = new ViewHolder(view, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PojoItem item =pojoItems.get(position);
        holder.txtName.setText(item.getName());

    }

    @Override
    public int getItemCount() {
        return pojoItems.size();
    }

    public interface ClickManager {
        void onItemClicked(PojoItem mList);
        void onImageClicker(PojoItem mList);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtName;
        private ImageView imageEdit;
        private ImageView imageDelete;
        private RelativeLayout listLay;
        SampleAdapter sampleAdapter;

        public ViewHolder(View itemView, SampleAdapter sampleAdapter) {
            super(itemView);
            this.sampleAdapter = sampleAdapter;
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            imageEdit = (ImageView) itemView.findViewById(R.id.image_edit);
            listLay = (RelativeLayout) itemView.findViewById(R.id.list_lay);

            listLay.setOnClickListener(this);
            imageEdit.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.list_lay:
                    clickManager.onItemClicked(pojoItems.get(getAdapterPosition()));
                    break;
                case R.id.image_edit:
                    clickManager.onImageClicker(pojoItems.get(getAdapterPosition()));
                    break;

            }
        }
    }
}
