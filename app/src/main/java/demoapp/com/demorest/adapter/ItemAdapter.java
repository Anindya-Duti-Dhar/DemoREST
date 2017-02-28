package demoapp.com.demorest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import demoapp.com.demorest.R;
import demoapp.com.demorest.model.Item;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView home_image;
        public TextView home_item_title;

        public ViewHolder(View itemView) {
            super(itemView);
            home_item_title = (TextView) itemView.findViewById(R.id.home_item_title);
            home_image = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }

    private ArrayList<Item> _data;
    private Context mContext;

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    public ItemAdapter(Context context, ArrayList<Item> _data) {
        this._data = _data;

        _data.add(0, getListItem("Nikunja-2", "12000 Taka"));
        _data.add(1, getListItem("Dhanmondi-6A", "12000 Taka"));
        _data.add(2, getListItem("Mohakhali", "12000 Taka"));
        _data.add(3, getListItem("Mirpur-10", "12000 Taka"));
        _data.add(4,getListItem("Mirpur-2", "12000 Taka"));
        _data.add(5,getListItem("Kazipara", "12000 Taka"));
        _data.add(6, getListItem("Nikunja-1",  "12000 Taka"));
        _data.add(7, getListItem("Uttara, Sector-13",  "12000 Taka"));
        _data.add(8, getListItem("Kathalbagan","12000 Taka"));
        _data.add(9, getListItem("Kalabagan","12000 Taka"));
        _data.add(10, getListItem("Mirpur-10","12000 Taka"));
        _data.add(11, getListItem("Framgate, Tejkunipara", "12000 Taka"));

    }

    public Item getListItem(String location, String rentPrice) {
        Item StaticItem = new Item();
        StaticItem.setLocation(location);
        StaticItem.setRentPrice(rentPrice);
        return StaticItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.home_item_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Item data = _data.get(position);
        TextView ListTitle = viewHolder.home_item_title;
        ListTitle.setText(data.getLocation());

        ImageView ListImage = viewHolder.home_image;
        ListImage.setImageResource(R.drawable.demo_flat);
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

}