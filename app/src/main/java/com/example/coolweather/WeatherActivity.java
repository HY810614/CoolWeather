package com.example.coolweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.QwertyKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.gson.Gson;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";

    public DrawerLayout drawerLayout;
    private Button navButton;


    public SwipeRefreshLayout swipeRefresh;

    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView feeldegree_text;
    private LinearLayout forecastLayout;

    private TextView windDir_text;
    private TextView windScale_text;
    private TextView humidity_text;
    private TextView precip_text;
    private TextView pressuer_text;
    private TextView vis_text;
    private TextView temp_text;

    private List<WeatherDailyBean.DailyBean> forecasts;

    private ImageView bing_pic_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让系统顶部和背景融为一体
        //5.0以上才可以
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //设置活动布局在状态栏上面
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //设置为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_weather);
        // 初始化各控件
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        feeldegree_text = (TextView) findViewById(R.id.feeldegree_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);


        windDir_text = (TextView)findViewById(R.id.windDir_text);
        windScale_text = (TextView)findViewById(R.id.windScale_text);

        humidity_text = (TextView)findViewById(R.id.humidity_text);
        precip_text = (TextView)findViewById(R.id.precip_text);

        pressuer_text = (TextView)findViewById(R.id.pressure_text);
        vis_text = (TextView)findViewById(R.id.vis_text);

        bing_pic_img = (ImageView)findViewById(R.id.bing_pic_img);

        temp_text = (TextView)findViewById(R.id.temp_text);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String bingPc = prefs.getString("bing_pc",null);
            //去服务器查询天气

        String weatherId = getIntent().getStringExtra("weather_id");
        weatherLayout.setVisibility(View.INVISIBLE);
        requestWeather(weatherId);

        if(bingPc != null) {
            Glide.with(this).load(bingPc).into(bing_pic_img);
        } else {
            loadBingPic();
        }

        //下拉刷新
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.refresh);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //获取最新的天气
                requestWeather(weatherId);
            }
        });

        //侧边栏更新城市
        drawerLayout = (DrawerLayout) findViewById(R.id.drawe_layout);
        navButton = (Button) findViewById(R.id.nav_button);

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });



    }

    /**
     * 根据天气id请求城市天气信息
     */
    public void requestWeather(final String weatherId) {
        //在子线程中进行服务器访问
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取实时天气
                QWeather.getWeatherNow(WeatherActivity.this,weatherId,
                        new QWeather.OnResultWeatherNowListener() {

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(WeatherActivity.this,"获取实况天气失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(WeatherNowBean weatherNowBean) {
                        //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因

                        Weather weather = new Weather();
                        Log.d(TAG, "onSuccess: " + weatherNowBean.getCode().getCode() );
//                        Toast.makeText(WeatherActivity.this,"请求状态码为：" + weatherNowBean.getCode().getCode(),
//                                Toast.LENGTH_SHORT).show();
                        if (weatherNowBean.getCode().getCode() == "200"){
                            WeatherNowBean.NowBaseBean now = weatherNowBean.getNow();
                            //设置weather各种信息
                            weather.setTemp(now.getTemp());
                            weather.setText(now.getText());
                            weather.setUpdateTime(now.getObsTime());
                            weather.setWindDir(now.getWindDir());
                            weather.setWindScale(now.getWindScale());
                            weather.setHumidity(now.getHumidity());
                            weather.setFeelsLike(now.getFeelsLike());
                            weather.setCloud(now.getCloud());
                            weather.setVis(now.getVis());
                            weather.setPrecip(now.getPrecip());
                            weather.setPress(now.getPressure());

                        } else {
                            Toast.makeText(WeatherActivity.this, "服务器获取具体天气信息失败" + weatherNowBean.getNow().getTemp(), Toast.LENGTH_SHORT).show();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                showWeatherInfo(weather);

                            }
                        });
                        //刷新每日一图
                        loadBingPic();
                    }
                });
            }

        }).start();

        //获取预报天气
        new Thread(new Runnable() {
            @Override
            public void run() {
                QWeather.getWeather3D(WeatherActivity.this, weatherId, new QWeather.OnResultWeatherDailyListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d(TAG, "获取3天天气预报失败");
                    }

                    @Override
                    public void onSuccess(WeatherDailyBean weatherDailyBean) {
                        if (weatherDailyBean.getCode().getCode() == "200") {
                            //清除之前的数据
                            if(forecasts != null)
                                forecasts.clear();
                            //获得逐天天气
                            forecasts = weatherDailyBean.getDaily();
                        } else {
                            Toast.makeText(WeatherActivity.this,"3天天气预报接口状态错误 " + weatherDailyBean.getCode().getCode(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 处理并展示Weather实体类中的数据
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = getIntent().getStringExtra("countyName");
        String updateTime = weather.getUpdateTime();
        String degree = weather.getTemp() + "℃";
        Log.d(TAG, degree);
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        feeldegree_text.setText("体感温度为: " + weather.getFeelsLike() + "℃");
        windDir_text.setText("风向：" + weather.getWindDir());
        windScale_text.setText("风力：" + weather.getWindScale() +"级");
        humidity_text.setText("相对湿度：" + weather.getHumidity() + "%");
        precip_text.setText("降水量：" + weather.getPrecip() + "ml");
        pressuer_text.setText("大气压强：" + weather.getPress() + "Pa");
        vis_text.setText("能见度：" + weather.getVis() + "km");
        temp_text.setText(weather.getText());
        //清除预报天气
        forecastLayout.removeAllViews();
        if (forecasts != null)
            for (WeatherDailyBean.DailyBean forecast : forecasts) {
                //加载子布局
                View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
                TextView dateText = (TextView) view.findViewById(R.id.date_text);
                TextView infoText = (TextView) view.findViewById(R.id.info_text);
                TextView maxText = (TextView) view.findViewById(R.id.max_text);
                TextView minText = (TextView) view.findViewById(R.id.min_text);
                dateText.setText(forecast.getFxDate());
                infoText.setText(forecast.getTextDay());
                maxText.setText(forecast.getTempMax());
                minText.setText(forecast.getTempMin());
                //添加到父布局
                forecastLayout.addView(view);
            }
        //获取完新的信息后 关闭刷新
        swipeRefresh.setRefreshing(false);

        weatherLayout.setVisibility(View.VISIBLE);
        //启动服务
//        Intent intent = new Intent(this, AutoUpdateService.class);
//        startService(intent);

    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.
                        getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bing_pic_img);
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

}