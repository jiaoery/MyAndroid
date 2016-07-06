package jixiang.com.myandroid.adapterview;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import jixiang.com.myandroid.R;


public class TestAuctoCompletedTextView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_autocomplte_textview);
		AutoCompleteTextView completeTextView = (AutoCompleteTextView) findViewById(R.id.auto_completedtextview);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.only_textview, getResources().getStringArray(
						R.array.main_titles));
		completeTextView.setAdapter(adapter);
		completeTextView
				.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						switch (actionId) {
						case EditorInfo.IME_ACTION_GO:
							Toast.makeText(TestAuctoCompletedTextView.this,
									"You selected =" + v.getText(),
									Toast.LENGTH_SHORT).show();
							break;
						}
						return true;
					}
				});

		completeTextView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(
						TestAuctoCompletedTextView.this,
						"You clicked ="
								+ parent.getItemAtPosition(position).toString(),
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}
