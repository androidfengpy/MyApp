package com.fengpy.myapp.gaodedemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.fengpy.myapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @ description: 高德地图绘制路线和图例设置
 * @ time: 2017/8/28.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class GaodeDemoActivity extends AppCompatActivity {

    private MapView mMapView;
    private AMap mAMap;
    private PolylineOptions mPolyoptions;
    private MarkerOptions markerOption;
    private Polyline mPolyline;
    private Button mBtnStart;
    private String color;
    int data = 10;

    public ConfigManager configManager;
    private  List<BandSetting> bandSettings;
    private List<DataInfo> dataInfos;

    // 监测值对应图例色值列表
    private HashMap<String, List<Integer>> colorValues = new HashMap<String, List<Integer>>();


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            DataInfo dataInfo = (DataInfo) b.get("data_info");
            double lat = Double.valueOf(dataInfo.getLat());
            double lng = Double.valueOf(dataInfo.getLng());
            LatLng point = new LatLng(lat, lng);
            Double le = Double.valueOf(dataInfo.getLe());
            List<Integer> colorValue;
            try{
                BandSetting bandSetting = ConfigManager.getInstance(GaodeDemoActivity.this).getBandSetting("le");
               /* if(colorValues.containsKey("le")) {
                    colorValue = colorValues.get("le");
                } else {
                    colorValue = new ArrayList<Integer>();
                    colorValues.put("le", colorValue);
                }*/

                if(bandSetting != null) {
                    List<BandRangeSetting> rangs = bandSetting.getRange();
                    for (int i = 0; i <rangs.size(); i++){
                        BandRangeSetting range = rangs.get(i);
                        if(range.getMin() <= le && le < range.getMax()) {
                            color = range.getColor();
                            /*String c = range.getColor().replace("#", "");
                            if (c.length() < 8) c ="#" + "FF" + c;
                            color = Long.parseLong(c, 16) + "";*/
//                            colorValue.add(color.intValue());
//                            break;
                        }
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
                return;
            }

            redrawpoint(point, color);
            mPolyoptions.add(point);
            redrawline();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gaode_demo);
        configManager = ConfigManager.getInstance(this);
        bandSettings = configManager.getBandSettings();// 获取系统默认的图例设置

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        mAMap = mMapView.getMap();

        initpolyline();

        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });

    }


    private void initData() {
        double lat = 39.965;
        double lng = 116.644;

        Random r = new Random();
        double offsetX = r.nextDouble() * 0.01;
        double offsetY = r.nextDouble() * 0.01;

        lat += offsetX;
        lng += offsetY;

        data += 5;
        DataInfo dataInfo = new DataInfo(data +"", "20170829", lat+"", lng+"");
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putSerializable("data_info", dataInfo);
        msg.setData(b);
        mHandler.sendMessage(msg);

    }

    private void redrawpoint(LatLng point, String colorValue) {
        BitmapDescriptor bd = getBitmapDes(30, colorValue);
        final Marker marker = mAMap.addMarker(new MarkerOptions().anchor(0.5f,0.5f).position(point).icon(bd).snippet("DefaultMarker"));
//        final Marker marker = mAMap.addMarker(new MarkerOptions().position(point).snippet("DefaultMarker"));
    }

    private BitmapDescriptor getBitmapDes(int num, String colorValue) {
        return BitmapDescriptorFactory.fromBitmap(drawPoint(num, colorValue));
    }

    /*private Drawable getDrawAble() {
        int radius = 20;//DensityUtils.dp2px(context, 50);
        Drawable bitmapDrawable = new BitmapDrawable(null, drawPoint(radius));
        return bitmapDrawable;
    }*/

    /**
     * 传递进去点的色值
     * @return
     */
    private Bitmap drawPoint(int radius, String colorValue) {
        Bitmap bitmap = Bitmap.createBitmap(radius * 2, radius * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        //TODO====设置颜色
//        String color = "#" + colorValue;
//        paint.setColor(Color.parseColor(color));
//        String color = "#2dbdff";
        paint.setColor(Color.parseColor(colorValue));
//        paint.setColor(Color.parseColor("#2dbdff"));
        paint.setAlpha(160);
        canvas.drawCircle(radius,radius,radius-10,paint);

        return  bitmap;
    }

    private void initpolyline() {
        mPolyoptions = new PolylineOptions();
        mPolyoptions.width(10f);
        mPolyoptions.color(Color.GRAY);
    }

    /**
     * 实时轨迹画线
     */
    private void redrawline() {
        if (mPolyoptions.getPoints().size() > 1) {
            if (mPolyline != null) {
                mPolyline.setPoints(mPolyoptions.getPoints());
            } else {
                mPolyline = mAMap.addPolyline(mPolyoptions);
            }
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

}
