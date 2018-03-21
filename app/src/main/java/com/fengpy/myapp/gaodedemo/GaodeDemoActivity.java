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
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.TileOverlay;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.WeightedLatLng;
import com.fengpy.myapp.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
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
    private int data = 10;

    public ConfigManager configManager;
    private  List<BandSetting> bandSettings;
    private List<DataInfo> dataInfos;

    // 监测值对应图例色值列表
    private HashMap<String, List<Integer>> colorValues = new HashMap<String, List<Integer>>();

    private PreviewHandler mHandler = new PreviewHandler(this);

    private class PreviewHandler extends Handler {
        private WeakReference<GaodeDemoActivity> reference;

        public PreviewHandler(GaodeDemoActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
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
                    break;

                case 1://TODO=====演示实时添加数据，绘制热力图
                    WeightedLatLng weightedLatLng = (WeightedLatLng)msg.obj;

                    hotMapDatas.add(weightedLatLng);
                    createHeatMap3(hotMapDatas);

//                    createHeatMap4(weightedLatLng);
                    break;
            }
        }
    }

    private List<WeightedLatLng> hotMapDatas = new ArrayList<WeightedLatLng>();

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
//                initData();

                initHotData3();//生成热力图数据
            }
        });

//        displayHotmap();

    }

    /**
     * 展示热力图
     */
    private void displayHotmap() {
        //第一种：只有经纬度的热力图数据
        /*LatLng[] latlngs = initHotData1();
        createHeatMap1(latlngs);*/

        //第二种：添加了权值的热力图
        /*WeightedLatLng[] latlngs = initHotData2();
        createHeatMap2(latlngs);*/
    }


    /**
     * 1 生成热力图数据: 经纬度
     */
    private LatLng[] initHotData1() {
        LatLng[] latlngs = new LatLng[500];
        double x = 39.904979;
        double y = 116.40964;

        for (int i = 0; i < 500; i++) {
            double _x = 0;
            double _y = 0;
            _x = Math.random() * 0.5 - 0.25;
            _y = Math.random() * 0.5 - 0.25;

            latlngs[i] = new LatLng(x + _x, y + _y);
        }

        return latlngs;
    }

    /**
     * 2 绘制热力图
     * @param latlngs
     */
    private void createHeatMap1(LatLng[] latlngs) {
        //2 构建热力图
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(Arrays.asList(latlngs));//设置热力图绘制的数据
//        .gradient(ALT_HEATMAP_GRADIENT);
        //构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();

        // 3绘制热力图图层
        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
        // 向地图上添加 TileOverlayOptions 类对象
        mAMap.addTileOverlay(tileOverlayOptions);
    }

    /**
     * 第二种添加权值的热力图数据： 生成热力图数据: 经纬度+权值
     */
    private WeightedLatLng[] initHotData2() {

        WeightedLatLng[] weightedLatlngs = new WeightedLatLng[500];
        double x = 39.904979;
        double y = 116.40964;
        double val = 20.00;

        for (int i = 0; i < 500; i++) {
            double _x = 0;
            double _y = 0;
            double _val = 0;

            _x = Math.random() * 0.5 - 0.25;
            _y = Math.random() * 0.5 - 0.25;
            _val = Math.random() * 10;

            LatLng latlng = new LatLng(x + _x, y + _y);

            weightedLatlngs[i] = new WeightedLatLng(latlng, val + _val);

        }

        return weightedLatlngs;
    }

    /**
     * 2 绘制热力图
     * @param latlngs
     */
    private void createHeatMap2(WeightedLatLng[] latlngs) {
        //2 构建热力图
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.weightedData(Arrays.asList(latlngs));
//        .gradient(ALT_HEATMAP_GRADIENT);
        //构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();

        // 3绘制热力图图层
        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
        // 向地图上添加 TileOverlayOptions 类对象
        mAMap.addTileOverlay(tileOverlayOptions);
    }

    private void initHotData3(){

        Message msg = new Message();
        msg.what = 1;

        double x = 39.904979;
        double y = 116.40964;
        double val = 20.00;

        double _x = 0;
        double _y = 0;
        double _val = 0;

        _x = Math.random() * 0.5 - 0.25;
        _y = Math.random() * 0.5 - 0.25;
        _val = Math.random() * 10;

        LatLng latlng = new LatLng(x + _x, y + _y);

        WeightedLatLng hotData3 = new WeightedLatLng(latlng, val + _val);

       /* Bundle b = new Bundle();
        b.putSerializable();
        msg.setData(b);*/

        msg.obj = hotData3;
        mHandler.sendMessage(msg);
    }

    private List<TileOverlay> _o = new ArrayList<TileOverlay>();
    /**
     * 3 ArrayList 绘制热力图
     * @param hotMapDatas
     */
    private void createHeatMap3(List<WeightedLatLng> hotMapDatas) {
        //TODO====直接这样的会重复绘制，绘制前需要清除之前绘制在地图上的热力图点
        //mAMap.clear();
        //2 构建热力图
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.weightedData(hotMapDatas);
//        .gradient(ALT_HEATMAP_GRADIENT);
        //构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();

        // 3绘制热力图图层
        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
        // 向地图上添加 TileOverlayOptions 类对象
        TileOverlay o = mAMap.addTileOverlay(tileOverlayOptions);
        int size = _o.size();
        if(size > 1){//TODO=====size > 2当保留三层绘制图层的时候删除的闪现效果不是很明显
            TileOverlay to = _o.get(0);
            to.remove();
            _o.remove(0);
        }
        _o.add(o);
    }

    /**
     * 3 ArrayList 绘制热力图
     * @param hotMapDatas
     */
    private void createHeatMap4(WeightedLatLng hotMapDatas) {
        //TODO======这样每次单个绘制，所有的热力点在地图缩放的时候不会自动聚合
        //2 构建热力图
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.weightedData(Arrays.asList(hotMapDatas));
//        .gradient(ALT_HEATMAP_GRADIENT);
        //构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();

        // 3绘制热力图图层
        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
        // 向地图上添加 TileOverlayOptions 类对象
        mAMap.addTileOverlay(tileOverlayOptions);
    }

    /**
     * 根据实时生成的数据绘制路线
     */
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
        msg.what = 0;
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
