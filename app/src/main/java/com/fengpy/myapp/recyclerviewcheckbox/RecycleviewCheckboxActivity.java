package com.fengpy.myapp.recyclerviewcheckbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.fengpy.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ description:
 * @ time: 2018/1/10.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class RecycleviewCheckboxActivity extends AppCompatActivity {

    private List<Fruit> fruitInfoList = new ArrayList<>();
    private List<FruitMsg> fruitMsgList = new ArrayList<>();

    private RecyclerView recyclerView1,recyclerView2;
    private Button btnDelete,btnAll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycle_checkbox);

        initFruit();

        initView();
    }

    private void initView() {

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_1);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view_2);

        recyclerView1.setLayoutManager(layoutManager);
        FruitAdapter adapter1 = new FruitAdapter(fruitInfoList);
        recyclerView1.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new FruitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final LinearLayoutManager layoutManager2 = new LinearLayoutManager(RecycleviewCheckboxActivity.this);
                String fruitName =  fruitInfoList.get(position).getName();
                initAppleMsg(fruitName);

                recyclerView2.setLayoutManager(layoutManager2);
                FruitMsgAdapter adapter2 = new FruitMsgAdapter(fruitMsgList);
                recyclerView2.setAdapter(adapter2);
            }
        });



        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnAll = (Button) findViewById(R.id.btn_all);


    }

    private void initFruit(){
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("Apple", 1.99, "河北", "20170809", "20180102");
            fruitInfoList.add(apple);

            Fruit banana = new Fruit("Banana", 2.99, "海南", "20180101", "20180110");
            fruitInfoList.add(banana);

            Fruit orange = new Fruit("Orange", 3.99, "北京", "20170909", "20180103");
            fruitInfoList.add(orange);

            Fruit pear = new Fruit("Pear", 4.99, "长沙", "20171009", "20180107");
            fruitInfoList.add(pear);

            Fruit grape = new Fruit("Grape", 5.99, "邢台", "20171109", "20180105");
            fruitInfoList.add(grape);

            Fruit mango = new Fruit("Mango", 6.99, "邯郸", "20171309", "20180104");
            fruitInfoList.add(mango);
        }
    }

    private void initAppleMsg(String fruitName) {
        if(fruitMsgList.size() > 0) {
            fruitMsgList.clear();
        }

        FruitMsg apple1 = new FruitMsg(fruitName, 1.90, "河北", "20170801", "20180102");
        fruitMsgList.add(apple1);

        FruitMsg apple2 = new FruitMsg(fruitName, 1.91, "河北", "20170802", "20180102");
        fruitMsgList.add(apple2);

        FruitMsg apple3 = new FruitMsg(fruitName, 1.92, "河北", "20170803", "20180102");
        fruitMsgList.add(apple3);

        FruitMsg apple4 = new FruitMsg(fruitName, 1.93, "河北", "20170804", "20180102");
        fruitMsgList.add(apple4);

        FruitMsg apple5 = new FruitMsg(fruitName, 1.94, "河北", "20170805", "20180102");
        fruitMsgList.add(apple5);

        FruitMsg apple6 = new FruitMsg(fruitName, 1.95, "河北", "20170806", "20180102");
        fruitMsgList.add(apple6);

    }
}
