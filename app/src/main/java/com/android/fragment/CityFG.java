package com.android.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.R;
import com.android.other.DateUtil;
import com.android.other.WeatherDetail;
import com.android.database.DBManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CityFG extends BaseFG {
    // TODO: Rename parameter arguments, choose names that match
    TextView test1,test11,test2,test22,test3,test33,test4,test44,test5,test55,tempTv,cityTv,conditionTv,windTv,tempRangeTv,dateTv;
    ImageView dayIv;
    LinearLayout futureLayout;
    String url1 = "http://api.map.baidu.com/telematics/v3/weather?location=";
    String url2 = "&output=json&ak=FkPhtMBK0HTIQNh7gG4cNUttSTyr0nzo";
    private List<WeatherDetail.ResultsBean.IndexBean> indexList;
    String city;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String ii;
    public CityFG() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CityWeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityFG newInstance(String param1, String param2) {
        CityFG fragment = new CityFG();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        initView(view);
        Bundle bundle = getArguments();
        city = bundle.getString("city");
        String url = url1+city+url2;
        loadData(url);

        return view;
    }

    @Override
    public void onSuccess(String result) {
        parseShowData(result);
        int i = DBManager.updateInfoByCity(city, result);
        if (i<=0) {
            DBManager.addCityInfo(city,result);
        }

    }

    private void parseShowData(String result) {
        //使用gson解析数据
        WeatherDetail weatherBean = new Gson().fromJson(result, WeatherDetail.class);
        WeatherDetail.ResultsBean resultsBean = weatherBean.getResults().get(0);
        //获取生活指数集合列表
        indexList = resultsBean.getIndex();
        dateTv.setText("天气更新于   "+DateUtil.getNowDateTime() );
        cityTv.setText(resultsBean.getCurrentCity());
        // 获取当天天气情况
        WeatherDetail.ResultsBean.WeatherDataBean todayDataBean = resultsBean.getWeather_data().get(0);
        windTv.setText(todayDataBean.getWind());
        tempRangeTv.setText(todayDataBean.getTemperature());
        conditionTv.setText(todayDataBean.getWeather());
        String[] split = todayDataBean.getDate().split("：");
        String todayTemp = split[1].replace(")", "");
        tempTv.setText(todayTemp);
        //根据天气情况选取合适的天气图片
        ii=todayDataBean.getDayPictureUrl().split("/")[6];
        if(ii.equals("duoyun.png"))
            dayIv.setImageResource(R.mipmap.duoyun);
        else if(ii.equals("zhenyu.png"))
            dayIv.setImageResource(R.mipmap.zhenyu);
        else if(ii.equals("xiaoyu.png"))
            dayIv.setImageResource(R.mipmap.xiaoyu);
        else if(ii.equals("zhongyu.png"))
            dayIv.setImageResource(R.mipmap.zhongyu);
        else if(ii.equals("dayu.png"))
            dayIv.setImageResource(R.mipmap.zhongyu);
        else if(ii.equals("baoyu.png"))
            dayIv.setImageResource(R.mipmap.baoyu);
        else if(ii.equals("qing.png"))
            dayIv.setImageResource(R.mipmap.qing);
        else if(ii.equals("yin.png"))
            dayIv.setImageResource(R.mipmap.yin);
        else if(ii.equals("leizhenyu.png"))
            dayIv.setImageResource(R.mipmap.leizhenyu);
        //获取未来三天的天气情况
        List<WeatherDetail.ResultsBean.WeatherDataBean> futureList = resultsBean.getWeather_data();
        futureList.remove(0);
        for (int i = 0; i < futureList.size(); i++) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_main_center, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            futureLayout.addView(itemView);
            TextView idateTv = itemView.findViewById(R.id.item_center_tv_date);
            TextView iconTv = itemView.findViewById(R.id.item_center_tv_con);
            TextView itemprangeTv = itemView.findViewById(R.id.item_center_tv_temp);
            ImageView iIv = itemView.findViewById(R.id.item_center_iv);
            WeatherDetail.ResultsBean.WeatherDataBean dataBean = futureList.get(i);
            idateTv.setText(dataBean.getDate());
            iconTv.setText(dataBean.getWeather());
            itemprangeTv.setText(dataBean.getTemperature());
            Picasso.with(getActivity()).load(dataBean.getDayPictureUrl()).into(iIv);
        }

        WeatherDetail.ResultsBean.IndexBean indexBean = indexList.get(0);
        String msg = indexBean.getZs()+"\n"+indexBean.getDes();
        test11.setText(indexBean.getZs());
        test1.setText(indexBean.getDes());

        indexBean = indexList.get(1);
        test22.setText(indexBean.getZs());
        test2.setText(indexBean.getDes());

        indexBean = indexList.get(2);
        test33.setText(indexBean.getZs());
        test3.setText(indexBean.getDes());

        indexBean = indexList.get(3);
        test44.setText(indexBean.getZs());
        test4.setText(indexBean.getDes());

        indexBean = indexList.get(4);
        test55.setText(indexBean.getZs());
        test5.setText(indexBean.getDes());
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        String s = DBManager.queryInfoByCity(city);
        if (!TextUtils.isEmpty(s)) {
            parseShowData(s);
        }
    }

    private void initView(View view) {
        test1 = view.findViewById(R.id.dress);
        test11 = view.findViewById(R.id.dress0);
        test2 = view.findViewById(R.id.washcar);
        test22 = view.findViewById(R.id.washcar0);
        test3 = view.findViewById(R.id.cold);
        test33 = view.findViewById(R.id.cold0);
        test4 = view.findViewById(R.id.sport);
        test44 = view.findViewById(R.id.sport0);
        test5 = view.findViewById(R.id.rays);
        test55 = view.findViewById(R.id.rays0);
        tempTv = view.findViewById(R.id.frag_tv_currenttemp);
        cityTv = view.findViewById(R.id.frag_tv_city);
        conditionTv = view.findViewById(R.id.frag_tv_condition);
        windTv = view.findViewById(R.id.frag_tv_wind);
        tempRangeTv = view.findViewById(R.id.frag_tv_temprange);
        dateTv = view.findViewById(R.id.frag_tv_date);
        dayIv = view.findViewById(R.id.frag_iv_today);
        futureLayout = view.findViewById(R.id.frag_center_layout);
    }

}
