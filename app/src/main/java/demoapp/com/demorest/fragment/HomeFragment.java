package demoapp.com.demorest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quickblox.sample.core.utils.Toaster;

import java.util.ArrayList;

import demoapp.com.demorest.R;
import demoapp.com.demorest.adapter.ItemAdapter;
import demoapp.com.demorest.model.Item;
import demoapp.com.demorest.utils.ItemClickSupport;

public class HomeFragment extends Fragment {
    //Defining Variables
    //Defining Variables
    ArrayList<Item> homeItem;
    RecyclerView mRecyclerView;
    ItemAdapter adapter;
    String mLocationName;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dress_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view initialize and functionality declare

        homeItem = new ArrayList<Item>();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.homeItemRecycler);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ItemAdapter(getActivity(), homeItem);
        mRecyclerView.setAdapter(adapter);

        // Select item on listclick
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Item data = homeItem.get(position);
                        mLocationName = data.getLocation();
                        Log.d("Location: ", mLocationName);

                        Toaster.longToast("Coming Soon...");
                            /*Intent intent = new Intent(getActivity(), contact_profile.class);
                            intent.putExtra("display_name", mDisplayName);
                            intent.putExtra("mobileNumber", mMobileNumber);
                            intent.putExtra("BitmapImage", bitMap_parse);
                            getActivity().startActivity(intent);*/
                    }
                }
        );


    }
}
