package jp.co.comp.site.dictionary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

public class ItemDetailActivity extends Activity {

	private TextView mTitle;
	private TextView mDescr;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail);
		
		Intent intent = getIntent();
		
		String title = intent.getStringExtra("TITLE");
		mTitle = (TextView)findViewById(R.id.detail_title);
		mTitle.setText(title);
		
		String descr = intent.getStringExtra("DESCRIPTION");
		mDescr = (TextView)findViewById(R.id.detail_descr);
		mDescr.setText(descr);
	}
}
