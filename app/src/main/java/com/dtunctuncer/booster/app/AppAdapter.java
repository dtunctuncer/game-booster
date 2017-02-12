package com.dtunctuncer.booster.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtunctuncer.booster.R;
import com.dtunctuncer.booster.core.EventCategories;
import com.dtunctuncer.booster.model.AppInfo;
import com.dtunctuncer.booster.utils.AnalyticsUtils;
import com.dtunctuncer.booster.utils.RAMBooster;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private RAMBooster ramBooster;
    private List<AppInfo> appInfoList;
    private Context context;

    AppAdapter(List<AppInfo> appInfoList, Context context) {
        this.ramBooster = new RAMBooster(context.getApplicationContext());
        this.appInfoList = appInfoList;
        this.context = context;
    }

    @Override
    public AppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppAdapter.ViewHolder holder, int position) {
        final AppInfo appInfo = appInfoList.get(position);

        holder.appName.setText(appInfo.getName());
        holder.appImage.setImageDrawable(appInfo.getIcon());
    }

    @Override
    public int getItemCount() {
        return appInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.appImage)
        ImageView appImage;
        @BindView(R.id.appName)
        TextView appName;
        @BindView(R.id.appItem)
        RelativeLayout appItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            appItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ramBooster.clearRAM();
            AnalyticsUtils.trackEvent(EventCategories.CLICK_EVENT,"Click App Booster","Rootsuz app boostlama tıklandı");
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(appInfoList.get(getAdapterPosition()).getPackageName());
            if (intent == null) {
                Toast.makeText(context, R.string.app_boost_error, Toast.LENGTH_SHORT).show();
                return;
            }

            intent.addCategory("android.intent.category.LAUNCHER");
            context.startActivity(intent);
            Toast.makeText(context, R.string.app_boosted, Toast.LENGTH_SHORT).show();
            ((Activity) context).finish();
        }
    }
}
