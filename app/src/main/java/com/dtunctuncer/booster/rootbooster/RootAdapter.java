package com.dtunctuncer.booster.rootbooster;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dtunctuncer.booster.R;
import com.dtunctuncer.booster.core.BoosterModes;
import com.dtunctuncer.booster.core.EventCategories;
import com.dtunctuncer.booster.core.events.ChangeRootModeEvent;
import com.dtunctuncer.booster.model.RootMode;
import com.dtunctuncer.booster.utils.RxBus;
import com.dtunctuncer.booster.utils.analytics.AnalyticsUtils;
import com.stericson.RootTools.RootTools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class RootAdapter extends RecyclerView.Adapter<RootAdapter.ViewHolder> {
    private List<RootMode> rootModes;
    private Context context;
    private RxBus rxBus;

    RootAdapter(List<RootMode> rootModes, Context context, RxBus rxBus) {
        this.rxBus = rxBus;
        this.rootModes = rootModes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_root_mode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RootMode rootMode = rootModes.get(position);

        if (rootMode.getBootMode() == BoosterModes.ULTRA_MODE) {
            holder.rootModeName.setText(context.getString(R.string.ultra_mode));
            holder.rootModeDetail.setText(context.getString(R.string.ultra_mode_help));
        } else if (rootMode.getBootMode() == BoosterModes.HIGH_MODE) {
            holder.rootModeName.setText(context.getString(R.string.high_mode));
            holder.rootModeDetail.setText(context.getString(R.string.high_mode_help));
        }


        holder.rootModeSwitch.setChecked(rootMode.getIsActive());
    }

    private boolean hasActive(List<RootMode> rootModes) {
        for (RootMode rootMode : rootModes) {
            if (rootMode.getIsActive()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return rootModes.size();
    }

    void clearModes() {
        for (RootMode rootMode : rootModes) {
            rootMode.setIsActive(false);
        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        @BindView(R.id.rootModeName)
        TextView rootModeName;
        @BindView(R.id.rootModeSwitch)
        SwitchCompat rootModeSwitch;
        @BindView(R.id.rootModeDetail)
        TextView rootModeDetail;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            rootModeSwitch.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (!RootTools.isRootAvailable()) {
                Toast.makeText(context, R.string.no_root_permission, Toast.LENGTH_SHORT).show();
                buttonView.setChecked(false);
                return;
            }

            final boolean checked = isChecked;
            final RootMode rootMode = rootModes.get(getAdapterPosition());

            String mode = rootMode.getBootMode() == BoosterModes.ULTRA_MODE ? "ULTRA MODE" : "HIGH_MODE";
            AnalyticsUtils.trackEvent(EventCategories.CLICK_EVENT, "Click Switch", "Switch tıklandı : " + mode);

            Handler handler = new Handler();
            final Runnable r = new Runnable() {
                public void run() {

                    for (RootMode mode : rootModes) {
                        if (checked) {
                            if (mode.getIsActive()) {
                                mode.setIsActive(false);
                            }

                            if (rootMode.getBootMode() == mode.getBootMode()) {
                                mode.setIsActive(true);
                            }
                        } else {
                            if (rootMode.getBootMode() == mode.getBootMode()) {
                                mode.setIsActive(false);
                            }
                        }
                    }

                    if (hasActive(rootModes)) {
                        for (RootMode mode : rootModes) {
                            if (mode.getIsActive()) {
                                rxBus.send(new ChangeRootModeEvent(mode.getBootMode()));
                            }
                        }
                    } else {
                        rxBus.send(new ChangeRootModeEvent(BoosterModes.NO_MODE));
                    }

                    notifyDataSetChanged();
                }
            };

            handler.post(r);

        }
    }
}