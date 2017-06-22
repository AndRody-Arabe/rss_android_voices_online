package nasser_alqtami.andrody.com.plus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

public class ListAdapterr extends BaseAdapter {

    LayoutInflater inflater;
    private List<Data> Datalist = null;
    private ArrayList<Data> arraylist;
    Context context;

    public ListAdapterr(Activity context, List<Data> openSite) {
        this.context=context;
        this.Datalist = openSite;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(openSite);

    }

    @Override
    public int getCount() {
        return Datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return Datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        View Item =inflater.inflate(R.layout.listview_item, parent,false);

        LinearLayout LL = (LinearLayout) Item.findViewById(R.id.Ll_);

        LL.setBackgroundColor(Color.parseColor("#099806"));

        TextView txtTitle = (TextView) Item.findViewById(R.id.titleid);

        txtTitle.setTextColor(Color.parseColor("#099806"));

        txtTitle.setText(Datalist.get(position).getTitle());

        if (position % 2 == 0){
            LL.setBackgroundColor(Color.parseColor("#038001"));
            txtTitle.setTextColor(Color.parseColor("#038001"));
        }

        return Item;



    };
}