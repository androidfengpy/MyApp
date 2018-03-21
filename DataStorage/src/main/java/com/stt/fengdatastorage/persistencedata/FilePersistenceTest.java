package com.stt.fengdatastorage.persistencedata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.stt.fengdatastorage.R;
import com.stt.fengdatastorage.utils.FileTools;

/**
 * @ description:
 * @ time: 2017/11/22.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class FilePersistenceTest extends AppCompatActivity {

    private EditText etContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_file_persistence);

        etContent = (EditText) findViewById(R.id.et_content);

        String inputText = FileTools.load(this, "data");
        if(!TextUtils.isEmpty(inputText)) {
            etContent.setText(inputText);
            etContent.setSelection(inputText.length());
            Toast.makeText(this, "数据恢复成功！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "没有数据，或者数据恢复失败！", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inptuText = etContent.getText().toString();

        if(TextUtils.isEmpty(inptuText)) {
            Toast.makeText(this, "输入的内容不能为空！", Toast.LENGTH_LONG).show();
        } else {
            boolean isSave = FileTools.save(this, "data", inptuText);
            if(isSave) {
                Toast.makeText(this, "数据保存成功！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "数据保存失败！", Toast.LENGTH_LONG).show();
            }
        }

    }
}
