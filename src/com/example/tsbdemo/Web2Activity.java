package com.example.tsbdemo;



import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Web2Activity extends Activity {
	
	private EditText et_text;
	private EditText et_num;
	private WebView webView;
	private String text;
	private String numstr;
	private int num;

	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String str=(String) msg.obj;
				et_text.setText(str);
				break;
			case 0:
				int i=  (Integer) msg.obj;
				et_num.setText(i+"");
				
				break;
//			case 3:
//				int j= (Integer) msg.obj;
//				et_num.setText(j+"");
//				break;

			default:
				break;
			}
			
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web2);
		
		initView();
	}

	private void initView() {
		et_text = (EditText) findViewById(R.id.et_text);
		et_num = (EditText) findViewById(R.id.et_num);
		webView = (WebView) findViewById(R.id.tbsWebView);
		WebSettings settings=webView.getSettings();
		settings.setJavaScriptEnabled(true);
		
		webView.loadUrl("file:///android_asset/webview.html");
		
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				new AlertDialog.Builder(Web2Activity.this).setTitle("Alert")
					.setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							result.confirm();
						}
					}).create().show();
				
				return true;
			}
			
			@Override
			public boolean onJsConfirm(WebView view, String url, String message,
					final JsResult result) {
				new AlertDialog.Builder(Web2Activity.this).setTitle("Confirm").setMessage(message)
				   .setPositiveButton("ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}
				}).setNegativeButton("no", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}
				}).create().show();
				return true;
			}
			
			
			@Override
			public boolean onJsPrompt(WebView view, String url, String message, 
					String defaultValue, final JsPromptResult result) {
				
				final View v = View.inflate(Web2Activity.this, R.layout.prompt_dialog, null);  
                TextView tv=(TextView) v.findViewById(R.id.prompt_message_text);
                tv.setText(message);
                ((EditText) v.findViewById(R.id.prompt_input_field)).setText(defaultValue);  

                new AlertDialog.Builder(Web2Activity.this).setTitle("Prompt").setView(v).
                 	setPositiveButton("ok", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							String value = ((EditText) v.findViewById(R.id.prompt_input_field)).getText().toString();  
	                        result.confirm(value);  
						}
					}).setNegativeButton("no", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							result.cancel();  
						}
					}).create().show();
				
				return true;
			}
			
			
		});
		
		
		
		
		webView.addJavascriptInterface(new WebViewJavaScriptFunction() {
			
			@Override
			public void onJsFunctionCalled(String tag) {
				
			}
			@JavascriptInterface
			public void setText(String str) {
				Toast.makeText(Web2Activity.this, "web传入的Text "+str, 0).show();
				 
				Message message=new Message();
				message.what=1;
				message.obj=str;
				handler.sendMessage(message);
				
			}
			
			@JavascriptInterface
			public void setNum(int i) {
				Toast.makeText(Web2Activity.this, "web传入的Num "+i, 0).show();
				Message message=new Message();
				message.what=0;
				message.obj=i;
				handler.sendMessage(message);
			}
			
			
			
			@JavascriptInterface
			public String getText(){
				return text;
			}
			
			@JavascriptInterface
			public int getNum(){
				return num;
			}
			
			@JavascriptInterface
			public void finish(){
				Log.e("finish", "finish");
				Web2Activity.this.finish();
			}
			
			@JavascriptInterface
			public void setPrompt(String s){
				((Button)findViewById(R.id.btn_send)).setText(s);
			}
			
		}, "android");
	}
	
	
	public void btn(View v) { 
		switch (v.getId()) {
		case R.id.btn_sub:
			text = et_text.getText().toString();
			webView.loadUrl("javascript:sendText()");
			
			break;
			
		case R.id.btn_add:
			//安卓端 +
			numstr=et_num.getText().toString();
			num=Integer.parseInt(numstr);
			num++;
			et_num.setText(num+"");
			webView.loadUrl("javascript:sendNum()");
			
			break;
			
		case R.id.btn_reduce:
			//安卓端 -
			numstr=et_num.getText().toString();
			num=Integer.parseInt(numstr);
			num--;
			et_num.setText(num+"");
			webView.loadUrl("javascript:sendNum()");
			break;
			
		case R.id.btn_close:
			//安卓端 finish
			webView.loadUrl("javascript:sendClose()");
			break;

		case R.id.btn_send:
			//安卓端 send
			webView.loadUrl("javascript:sendPrompt()");
			break;
			
		default:
			break;
		}
		
		
	}
	
	@Override
	protected void onDestroy() {
		if (webView != null)
			webView.destroy();
		super.onDestroy();
	}

}
