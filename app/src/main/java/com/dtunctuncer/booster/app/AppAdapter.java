package com.dtunctuncer.booster.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dtunctuncer.booster.R;
import com.dtunctuncer.booster.core.events.AppClickEvent;
import com.dtunctuncer.booster.model.AppInfo;
import com.dtunctuncer.booster.utils.RxBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private List<AppInfo> appInfoList;
    private Context context;
    private RxBus rxBus;

    AppAdapter(List<AppInfo> appInfoList, Context context, RxBus rxBus) {
        this.appInfoList = appInfoList;
        this.context = context;
        this.rxBus = rxBus;
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
            rxBus.send(new AppClickEvent(appInfoList.get(getAdapterPosition())));
        }
    }
}