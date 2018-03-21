package com.fengpy.myapp.gaode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.fengpy.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ description:
 * @ time: 2018/2/8.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class GaodeActivity extends AppCompatActivity implements AMap.InfoWindowAdapter{

    private MapView mapView;
    private AMap aMap;
    List<MarkerInfo> markerInfos;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gaode);

        initData();

        mapView = (MapView) findViewById(R.id.gaode_map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();

        LatLng latLng = new LatLng(39.906901, 116.397972);
//        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));

        drawPoint(latLng);
        aMap.setOnMarkerClickListener(markerClickListener);
        aMap.setOnInfoWindowClickListener(infoWindowClickListener);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);
    }

    public void initData(){
        if(markerInfos == null) {
            markerInfos = new ArrayList<>();
        }

        for (int i = 0; i < 5; i++) {
            MarkerInfo info = new MarkerInfo();
            info.setName("第" + i + "个");
            info.setAge( (i+ 10) + "岁");
            info.setLatlng((30.123456 + i) + ";" +(110.654321 + i));
            info.setLh((0.56 + i) + "V/m");
            markerInfos.add(info);
        }
    }

    // 定义 设置点击infoWindow事件监听器
    AMap.OnInfoWindowClickListener infoWindowClickListener = new AMap.OnInfoWindowClickListener(){
        @Override
        public void onInfoWindowClick(Marker marker) {
            Toast.makeText(GaodeActivity.this, "监听点击infowindow窗口事件回调", Toast.LENGTH_SHORT).show();
        }
    };

    private void drawPoint(LatLng latLng) {
        Bitmap bitmap = Bitmap.createBitmap(30 * 2, 30 * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#000080"));
        paint.setAlpha(160);
        canvas.drawCircle(30, 30, 30 - 10, paint);
        BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(bitmap);
        final Marker marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).position(latLng).icon(bd));

        /*MarkerOptions options = new MarkerOptions();
        options.
        marker.setMarkerOptions();*/
        marker.setObject(markerInfos.get(0));
    }

    // 设置自定义的InfoWindow样式
   /* AMap.InfoWindowAdapter infoWindowAdapter = new AMap.InfoWindowAdapter(){
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    };*/



    // 定义 Marker 点击事件监听
    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
        // marker 对象被点击时回调的接口
        // 返回 true 则表示接口已响应事件，否则返回false
        @Override
        public boolean onMarkerClick(Marker marker) {
//            Toast.makeText(GaodeActivity.this, "marker对象被点击", Toast.LENGTH_SHORT).show();
            return false;
        }
    };

    @Override
    public View getInfoWindow(Marker marker) {

        /*if (radioOption.getCheckedRadioButtonId() != R.id.custom_info_window) {
            return null;
        }*/
        View infoWindow = getLayoutInflater().inflate(
                R.layout.custom_info_window, null);

        render(marker, infoWindow);
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        /*if (radioOption.getCheckedRadioButtonId() != R.id.custom_info_contents) {
            return null;
        }*/
        /*View infoContent = getLayoutInflater().inflate(
                R.layout.custom_info_contents, null);
        render(marker, infoContent);
        return infoContent;*/
        return null;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        /*if (radioOption.getCheckedRadioButtonId() == R.id.custom_info_contents) {
            ((ImageView) view.findViewById(R.id.badge))
                    .setImageResource(R.drawable.badge_sa);
        } else if (radioOption.getCheckedRadioButtonId() == R.id.custom_info_window) {
            ImageView imageView = (ImageView) view.findViewById(R.id.badge);
            imageView.setImageResource(R.drawable.badge_wa);
        }*/

        MarkerInfo info = (MarkerInfo) marker.getObject();
        TextView tv_time = (TextView)view.findViewById(R.id.tv_time);
        TextView tv_lng_lat = (TextView)view.findViewById(R.id.tv_lng_lat);
        TextView tv_data_1111 = (TextView)view.findViewById(R.id.tv_data_1111);
        TextView tv_data_2222 = (TextView)view.findViewById(R.id.tv_data_2222);

        String time = info.getName();
        String latlng = info.getAge();
        String data111 = info.getLh();
        String data222 = info.getLatlng();

        if(time != null) {
            tv_time.setText(time);
        } else {
            tv_time.setText("");
        }

        if(latlng != null) {
            tv_lng_lat.setText(latlng);
        } else {
            tv_lng_lat.setText("");
        }

        if(data111 != null) {
            tv_data_1111.setText(data111);
        } else {
            tv_data_1111.setText("");
        }

        if(data222 != null) {
            tv_data_2222.setText(data222);
        } else {
            tv_data_2222.setText("");
        }

        /*String title = info.toString();

        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.RED), 0,
                    titleText.length(), 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
                    snippetText.length(), 0);
            snippetUi.setTextSize(20);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }*/
    }

}
