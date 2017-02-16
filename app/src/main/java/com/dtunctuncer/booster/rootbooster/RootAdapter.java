package com.dtunctuncer.booster.rootbooster;

import android.content.Context;
import android.content.Intent;
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
import com.dtunctuncer.booster.model.RootMode;
import com.dtunctuncer.booster.notification.ModeNotification;
import com.dtunctuncer.booster.utils.SpUtils;
import com.dtunctuncer.booster.utils.analytics.AnalyticsUtils;
import com.stericson.RootTools.RootTools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class RootAdapter extends RecyclerView.Adapter<RootAdapter.ViewHolder> {
    private List<RootMode> rootModes;
    private Context context;
    private SpUtils spUtils;
    private RootAdapterListener listener;
    private BoosterModeManager boosterModeManager;
    private ModeNotification modeNotification;


    RootAdapter(List<RootMode> rootModes, Context context) {
        this.rootModes = rootModes;
        this.context = context;
        this.spUtils = new SpUtils(context);
        this.boosterModeManager = new BoosterModeManager();
        this.modeNotification = new ModeNotification(context.getApplicationContext());
    }


    void setListener(RootAdapterListener listener) {
        this.listener = listener;
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
                    if (checked) {
                        for (RootMode mode : rootModes) {
                            if (mode.getIsActive()) {
                                mode.setIsActive(false);
                            }

                            if (rootMode.getBootMode() == mode.getBootMode()) {
                                mode.setIsActive(true);
                            }
                        }
                    } else {

                        for (RootMode mode : rootModes) {
                            if (rootMode.getBootMode() == mode.getBootMode()) {
                                mode.setIsActive(false);
                            }
                        }

                    }

                    if (hasActive(rootModes)) {
                        for (RootMode mode : rootModes) {
                            if (mode.getIsActive()) {
                                listener.startBoost();
                                spUtils.setCurrentMode(mode.getBootMode());
                                boosterModeManager.setMode(mode.getBootMode());
                                context.startService(new Intent(context.getApplicationContext(), BoosterService.class));
                                modeNotification.startNotification(mode.getBootMode());
                            }
                        }
                    } else {
                        spUtils.setCurrentMode(BoosterModes.NO_MODE);
                        modeNotification.startNotification(BoosterModes.NO_MODE);
                    }

                    notifyDataSetChanged();
                }
            };

            handler.post(r);

        }
    }
}