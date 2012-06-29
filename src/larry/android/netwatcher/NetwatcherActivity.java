package larry.android.netwatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ListActivity;
import android.content.Context;
import android.net.TrafficStats;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class NetwatcherActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button all = (Button)findViewById(R.id.all);
        all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				watch();
				
			}
		});
        Button mobile = (Button)findViewById(R.id.mobile);
        mobile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				watchMobile();
				
			}
		});
        
        setListAdapter(new SimpleAdapter(this, getData(),
                android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
        getListView().setTextFilterEnabled(true);
    }
    
    protected List<Map<String, Object>> getData() {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> list = mActivityManager.getRunningAppProcesses();
		for(RunningAppProcessInfo rap:list){
			Map<String, Object> temp = new HashMap<String, Object>();
	        temp.put("title", rap.uid+":"+rap.pid+":"+rap.processName+":"+TrafficStats.getUidRxBytes(rap.uid));
	        result.add(temp);
		}
		
		return result;
        
    }
    
    private void watch() {
    	long r = TrafficStats.getTotalRxBytes();
    	long s = TrafficStats.getTotalTxBytes();
    	TextView log = (TextView)findViewById(R.id.log);
    	StringBuffer sb = new StringBuffer().append("all  r:").append(r).append("  s").append(s);
    	log.setText(log.getText()+"  \n"+sb.toString());
    	log.invalidate();
	}
    private void watchMobile() {
    	long r = TrafficStats.getMobileRxBytes();
    	long s = TrafficStats.getMobileTxBytes();
    	TextView log = (TextView)findViewById(R.id.log);
    	StringBuffer sb = new StringBuffer().append("mobil r:").append(r).append("  s").append(s);
    	log.setText(log.getText()+"  \n"+sb.toString());
    	log.invalidate();
	}
    

    private void getProcess() {
		ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> list = mActivityManager.getRunningAppProcesses();
		list.get(0);

	}
    
    
}