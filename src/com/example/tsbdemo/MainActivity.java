package com.example.tsbdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		intent = new Intent(MainActivity.this, WebActivity.class);
	}

	public void btn(View v) {
		switch (v.getId()) {

		case R.id.btn1:
			intent.putExtra("url", "https://www.huxiu.com/article/168405.html");
			startActivity(intent);
			break;
		case R.id.btn2:
			intent.putExtra("url", "http://feizaojilao.esy.es/history.html");
			startActivity(intent);
			break;
		case R.id.btn3:
			intent.putExtra("url", "http://v.qq.com/cover/z/z1x68ih8qwjbsen.html");
			startActivity(intent);
			break;
		case R.id.btn4:
			intent.putExtra("url", "http://v.youku.com/v_show/id_XMTc3NTIzNjY2NA==.html?spm=a2hww.20023042.m_223465.5~5~5~5!2~5~5~A&from=y1.3-idx-beta-1519-23042.223465.4-1");
			startActivity(intent);
			break;
		case R.id.btn5:
			intent.putExtra("url", "http://www.hao123.com/");
			startActivity(intent);
			break;
		case R.id.btn6:
			intent.putExtra("url", "http://www.apta.gov.cn/Information/ActivityDetail?aid=167");
			startActivity(intent);
			break;
		case R.id.btn7:
			intent.putExtra("url", "http://220.180.239.166/Information/ActivityDetail?aid=123");
			startActivity(intent);
			break;

		default:
			break;
		}

	}

}
