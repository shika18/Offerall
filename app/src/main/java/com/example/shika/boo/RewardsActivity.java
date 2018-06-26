package com.example.shika.boo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.k.recyclerview.KRecyclerView;

public class RewardsActivity extends AppCompatActivity {
    KRecyclerView kRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        kRecyclerView = (KRecyclerView) findViewById(R.id.krv);
        kRecyclerView.setAdapter(new SimpleAdapter(kRecyclerView));
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        float lastScale = 0;
        TextView textView;
        ImageView imageView;

        SimpleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_center);
            imageView = (ImageView) itemView.findViewById(R.id.iv_background);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kRecyclerView.setFocusPosition(getAdapterPosition());
                }
            });
        }

        void onScaled(float f) {
            if (Math.abs(f - lastScale) > 0.05f) {
                lastScale = f;
                textView.setScaleX(f + 1);
                textView.setScaleY(f + 1);
            }
        }
    }

    class SimpleAdapter extends KRecyclerView.EndlessAdapter<SimpleViewHolder> {
        LayoutInflater layoutInflater;
        Picasso picasso;

        SimpleAdapter(KRecyclerView kRecyclerView) {
            super(kRecyclerView);
            layoutInflater = LayoutInflater.from(RewardsActivity.this);
            picasso = Picasso.get();
        }

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleViewHolder(layoutInflater.inflate(R.layout.rv_item, parent, false));
        }

        @Override
        public int getItemViewType_(int position) {
            return 0;
        }

        @Override
        public int getItemCount_() {
            return 5;
        }

        @Override
        public void onBindViewHolder_(SimpleViewHolder holder, int position) {
            switch (position) {
                case 0:
                    holder.textView.setText("30% off on 3 pieces");
                    holder.imageView.setImageResource(R.drawable.clocc);
                    break;
                case 1:
                    holder.textView.setText("new one on large quantity");
                    holder.imageView.setImageResource(R.drawable.ehc);
                    break;
                case 2:
                    holder.textView.setText("40% off for 3 ");
                    holder.imageView.setImageResource(R.drawable.maccccc);
                    break;
                case 3:
                    holder.textView.setText("get 12 points");
                    holder.imageView.setImageResource(R.drawable.piz);
                    break;
                case 4:
                    holder.textView.setText("Extra prizes");
                    holder.imageView.setImageResource(R.drawable.codf);
                    break;
            }
        }

        @Override
        public void onScaled(SimpleViewHolder holder, int position, float f) {
            holder.onScaled(f);
        }
    }
}