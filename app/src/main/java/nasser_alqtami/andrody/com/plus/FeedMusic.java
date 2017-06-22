package nasser_alqtami.andrody.com.plus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import nasser_alqtami.andrody.com.PlayerActivity;
import nasser_alqtami.andrody.com.R;

/**
 * Created by Abboudi_Aliwi on 23.06.2017.
 * Website : http://andrody.com/
 * our channel on YouTube : https://www.youtube.com/c/Andrody2015
 * our page on Facebook : https://www.facebook.com/andrody2015/
 * our group on Facebook : https://www.facebook.com/groups/Programming.Android.apps/
 * our group on Whatsapp : https://chat.whatsapp.com/56JaImwTTMnCbQL6raHh7A
 * our group on Telegram : https://t.me/joinchat/AAAAAAm387zgezDhwkbuOA
 */

public class FeedMusic extends Fragment implements OnItemClickListener{
	private GridView rssFeedListView;
	public static List<Data> feedList;
	private static String SOURCE_URL;
	private ListAdapterr adapter;
	public static final String ACTION = "feed_list";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.initialiseVariables();
		LoadRssFeedThread thread = new LoadRssFeedThread(getActivity());
		thread.execute(SOURCE_URL);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.list, container, false);
		rssFeedListView = (GridView) view.findViewById(R.id.listviewid);
		rssFeedListView.setNumColumns(Utilities.getInstance().getNoOfColumns(getActivity()));
		if(feedList!=null){
			updateUi();
		}
		return view;
	}
	public void initialiseVariables() {
		feedList = new ArrayList<Data>();
		SOURCE_URL = this.getResources().getString(R.string.source_url);
	}	
	

	
	public class LoadRssFeedThread extends AsyncTask<String, Data, Void> {

		private ProgressDialog dialog;
		public LoadRssFeedThread(Activity activity) {
			dialog=new ProgressDialog(activity);
			dialog.setMessage("جاري التحميل ...");
			dialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			RssFeed parser=new RssFeed();
			NodeList nodelist=parser.getRSSFeedItems(params[0]);
			if(nodelist!=null){
				int length=nodelist.getLength();
				for(int i=0;i<length;i++){
					Data detail=parser.getResult(nodelist, i);
					if(detail!=null){						
						FeedMusic.feedList.add(detail);
						publishProgress(detail);
					}					
				}
			}
			return null;
		}	
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			updateUi();
			if(dialog.isShowing())
			dialog.dismiss();	
			super.onPostExecute(result);
		}	

	}
	
	private void updateUi(){
		adapter = new ListAdapterr(getActivity(), feedList);
		rssFeedListView.setAdapter(adapter);
		rssFeedListView.setOnItemClickListener(FeedMusic.this);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Intent i = new Intent(getActivity(), PlayerActivity.class);
			i.setAction(ACTION);			
			i.putExtra("position", arg2);
			startActivity(i);

	}


}
