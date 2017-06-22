package nasser_alqtami.andrody.com;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import nasser_alqtami.andrody.com.plus.FeedMusic;


/**
 * Created by Abboudi_Aliwi on 23.06.2017.
 * Website : http://andrody.com/
 * our channel on YouTube : https://www.youtube.com/c/Andrody2015
 * our page on Facebook : https://www.facebook.com/andrody2015/
 * our group on Facebook : https://www.facebook.com/groups/Programming.Android.apps/
 * our group on Whatsapp : https://chat.whatsapp.com/56JaImwTTMnCbQL6raHh7A
 * our group on Telegram : https://t.me/joinchat/AAAAAAm387zgezDhwkbuOA
 */

public class List extends FragmentActivity implements TabHost.OnTabChangeListener {

    private FragmentTabHost mTabHost;
    private static final String RSS_TAB="rss_tab";


    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host_layout);



        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec(RSS_TAB).setIndicator("STREAMING"),
                FeedMusic.class, null);


        mTabHost.setOnTabChangedListener(this);


        TabWidget widget = mTabHost.getTabWidget();
        for(int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);


            TextView tv = (TextView)v.findViewById(android.R.id.title);
            if(tv == null) {
                continue;
            }
        }

    }

    @Override
    public void onTabChanged(String tabId) {

        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    }

}
