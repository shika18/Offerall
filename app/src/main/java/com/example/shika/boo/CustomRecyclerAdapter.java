package com.example.shika.boo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;


public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<PersonUtils> personUtils;

    public CustomRecyclerAdapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(personUtils.get(position));

        PersonUtils pu = personUtils.get(position);

        Glide.with(context).load(pu.getProductimage() )
                //pu.getProductimage()
                .into(holder.imageView);

        holder.pName.setText(pu.getPersonFirstName()+" "+pu.getPersonLastName());
        holder.pJobProfile.setText(pu.getJobProfile());

    }

    @Override
    public int getItemCount() {
        return personUtils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pName;
        public TextView pJobProfile;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);

            pName = (TextView) itemView.findViewById(R.id.pNametxt);
            pJobProfile = (TextView) itemView.findViewById(R.id.pJobProfiletxt);
            imageView = (ImageView) itemView.findViewById(R.id.ig);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PersonUtils cpu = (PersonUtils) view.getTag();

                    Toast.makeText(view.getContext(), cpu.getPersonFirstName()+" "+cpu.getPersonLastName()+" is "+ cpu.getJobProfile(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

}