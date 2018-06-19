package com.example.peter.timerjava.TrainingList;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.timerjava.Data.DatabaseManager;
import com.example.peter.timerjava.Data.Training;
import com.example.peter.timerjava.R;
import com.example.peter.timerjava.Settings.SettingActivity;
import com.example.peter.timerjava.Timer.TimerActivity;
import com.example.peter.timerjava.Utils.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.CustomViewHolder>  {

    private RealmResults<Training> mTrainings;
    private final Context mContext;

    public TrainingAdapter(Context context){
        mContext = context;
        mTrainings = DatabaseManager.getInstance().getTrainings();
    }

    @NonNull
    @Override
    public TrainingAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.workout_item, viewGroup, false);
        return new CustomViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingAdapter.CustomViewHolder customViewHolder, int i) {
        Training curTraining = mTrainings.get(i);
        customViewHolder.mNameText.setText(curTraining.getName());
        customViewHolder.mWorkoutTimeText.setText(Util.getInstance().getStringTimeFormat(curTraining.getWorkoutTime()));
        customViewHolder.mRestTimeText.setText(Util.getInstance().getStringTimeFormat(curTraining.getRestTime()));
    }

    @Override
    public int getItemCount() {
        return mTrainings.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.item_text_name)
        TextView mNameText;
        @BindView(R.id.item_delete_layout)
        FrameLayout mDeleteLayout;
        @BindView(R.id.item_setting_layout)
        FrameLayout mChangeLayout;
        @BindView(R.id.select_item_body)
        ConstraintLayout mainBody;
        @BindView(R.id.workout_time_text)
        TextView mWorkoutTimeText;
        @BindView(R.id.item_rest_time_text)
        TextView mRestTimeText;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mainBody.setOnClickListener(this);
            mDeleteLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseManager.getInstance().deleteTraining(mTrainings.get(getAdapterPosition()).getId());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

            mChangeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Training training = DatabaseManager.getInstance().getTraining(mTrainings.get(getAdapterPosition()).getId());
                    Intent intent = new Intent(mContext, SettingActivity.class);
                    intent.putExtra("TRAINING_ID",training.getId());
                    intent.putExtra("NEW_DATA",false);
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext,TimerActivity.class);
            intent.putExtra("CHOSEN_TRAINING_ID",mTrainings.get(getAdapterPosition()).getId());
            mContext.startActivity(intent);
        }
    }
}
