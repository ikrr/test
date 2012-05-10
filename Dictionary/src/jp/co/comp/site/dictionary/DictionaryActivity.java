package jp.co.comp.site.dictionary;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.View;
import android.view.KeyEvent;
import android.content.Intent;


public class DictionaryActivity extends ListActivity implements OnEditorActionListener{
	
	private ArrayList<Item> mItems;
	private RssListAdapter mAdapter;
	private EditText mEditText;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mItems = new ArrayList<Item>();
        mAdapter = new RssListAdapter(this, mItems);
        
        mEditText = (EditText)findViewById(R.id.word);
        mEditText.setOnEditorActionListener(this);
        
        setListAdapter(mAdapter);
    }
    
    public boolean onEditorAction(TextView view, int id, KeyEvent keyEvent){
    	
    	mAdapter.clear();
    	
    	HtmlParser parser = null;
    	
    	// this is temporary. should add a control to select a dictionary.
    	// And alcParser is not working yet!!
    	int dictionaryMode = ParserFactory.WEBLIO;
    	
    	parser = ParserFactory.create(dictionaryMode);
    	
    	String[] wordArray = parser.getRetTextArray(mEditText.getText().toString());
    	
    	// add to the list.
    	for(String word : wordArray){
    		
    		String simpleText = parser.simplifyText(word);
	    	String[] definitions = parser.getTitleAndDescription(simpleText);
	    		
	    	if(!definitions[0].equals("")){
		    	Item currentItem = new Item();
		    	currentItem.setTitle(definitions[0]);
		    	currentItem.setDescription(definitions[1]);
		    	mAdapter.add(currentItem);
	    	}
    	}

    	return true;
    }
    
    @Override
    protected void onListItemClick(ListView lView, View view, int position, long id){
    	Item item = mItems.get(position);
    	Intent intent = new Intent(this, ItemDetailActivity.class);
    	intent.putExtra("TITLE", item.getTitle());
    	intent.putExtra("DESCRIPTION", item.getDescription());
    	startActivity(intent);
    }
}