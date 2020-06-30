package com.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.other.WeatherDetail;
import com.android.database.CityDB;
import com.android.R;
import com.google.gson.Gson;

import java.util.List;

public class ManagerAP extends BaseAdapter{
    Context context;
    List<CityDB> mDatas;
    public ManagerAP(Context context, List<CityDB> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city_manager,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        CityDB bean = mDatas.get(position);
        holder.cityTv.setText(bean.getCity());
        WeatherDetail weatherBean = new Gson().fromJson(bean.getContent(), WeatherDetail.class);
        // 获取当天天气情况
        WeatherDetail.ResultsBean.WeatherDataBean dataBean = weatherBean.getResults().get(0).getWeather_data().get(0);
        holder.conTv.setText("天气:"+dataBean.getWeather());
        String[] split = dataBean.getDate().split("：");
        String todayTemp = split[1].replace(")", "");
        holder.currentTempTv.setText(todayTemp);
        holder.windTv.setText(dataBean.getWind());
        holder.tempRangeTv.setText(dataBean.getTemperature());
        return convertView;
    }

    class ViewHolder{
        TextView cityTv,conTv,currentTempTv,windTv,tempRangeTv;
        ImageView iv;
        public ViewHolder(View itemView){
            cityTv = itemView.findViewById(R.id.item_city_tv_city);
            conTv = itemView.findViewById(R.id.item_city_tv_condition);
            currentTempTv = itemView.findViewById(R.id.item_city_tv_temp);
            windTv = itemView.findViewById(R.id.item_city_wind);
            tempRangeTv = itemView.findViewById(R.id.item_city_temprange);
        }
    }
}
