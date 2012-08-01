package uk.co.vurt.hakken.activities;

import uk.co.vurt.hakken.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectTask extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setListAdapter(new ArrayAdapter<String>(this, R.layout.selecttask_list_item, TASKS));
        
        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        
        listView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//When clicked, show a toast indicating which item was clicked
				Toast.makeText(getApplicationContext(), ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
				
			}
        	
        });
 
    }
    
    //TODO: make this list dynamic, based on tasks loaded from server
    static final String[] TASKS = new String[] {
    	"Record Activity",
    	"Task 2",
    	"Task 3",
    	"Task 4",
    	"Task 5",
    	"Task 6",
    	"Task 7",
    	"Task 8",
    	"Task 9",
    	"Task 10"
    };
}