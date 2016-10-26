package com.example.tsbdemo;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

public class WebActivity extends Activity {

	WebView tbsWebView;
	ProgressBar web_bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		String url = getIntent().getStringExtra("url");

		web_bar = (ProgressBar) findViewById(R.id.web_bar);
		web_bar.getProgressDrawable().setColorFilter(Color.RED,
				android.graphics.PorterDuff.Mode.SRC_IN);

		tbsWebView = (WebView) findViewById(R.id.tbsWebView);
		tbsWebView.loadUrl(url);

		WebSettings settings = tbsWebView.getSettings();
		settings.setJavaScriptEnabled(true);

		// 设置加载进来的页面自适应手机屏幕
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);

		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setDisplayZoomControls(true);
		
		
		//settings.setUserAgent( );

		tbsWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.e("WebView ", "shouldOverrideUrlLoading " + url);
				view.loadUrl(url);
				return true;
			}

		});

		tbsWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				web_bar.setProgress(newProgress);

				if (newProgress == 100) {
					web_bar.setVisibility(View.GONE);
				} else {
					web_bar.setVisibility(View.VISIBLE);
				}
			}

		});
	}

	@Override
	protected void onDestroy() {
		if (tbsWebView != null)
			tbsWebView.destroy();
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && tbsWebView.canGoBack()) {
			tbsWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
