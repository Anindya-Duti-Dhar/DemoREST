package demoapp.com.demorest.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import demoapp.com.demorest.R;

public class ProductDetailsSecondPart extends Fragment {
    //Defining Variables
    private static final String TAG = ProductDetailsSecondPart.class.getSimpleName();
    Button mMoreSameProductDetailsBtn;
    SmileRating mSmileRating;
    TextView mDescriptionTab, mConditionsTab, mReviewTab;
    View mDescriptionTabIndicator, mConditionsTabIndicator, mReviewTabIndicator;
    LinearLayout mDescriptionTabLayout, mConditionsTabLayout, mReviewTabLayout;

    public static ProductDetailsSecondPart newInstance() {
        ProductDetailsSecondPart fragment = new ProductDetailsSecondPart();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public ProductDetailsSecondPart() {
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
        return inflater.inflate(R.layout.product_details_second_part, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view initialize and functionality declare
        mMoreSameProductDetailsBtn = (Button) view.findViewById(R.id.more_same_product_details_btn);
        mMoreSameProductDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set action logic
                toast("Coming Soon...");
            }
        });


        mSmileRating = (SmileRating) view.findViewById(R.id.product_review_smile_rating);
        mSmileRating.setSelectedSmile(BaseRating.OKAY, true);

        mDescriptionTab = (TextView) view.findViewById(R.id.product_description_tab);
        mConditionsTab = (TextView) view.findViewById(R.id.product_conditions_tab);
        mReviewTab= (TextView) view.findViewById(R.id.product_review_tab);
        mDescriptionTabIndicator = view.findViewById(R.id.product_description_tab_indicator);
        mConditionsTabIndicator = view.findViewById(R.id.product_conditions_tab_indicator);
        mReviewTabIndicator = view.findViewById(R.id.product_review_tab_indicator);
        mDescriptionTabLayout = (LinearLayout) view.findViewById(R.id.product_description_layout);
        mConditionsTabLayout = (LinearLayout) view.findViewById(R.id.product_conditions_layout);
        mReviewTabLayout = (LinearLayout) view.findViewById(R.id.product_review_layout);

        mDescriptionTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDescriptionTabIndicator.setVisibility(View.VISIBLE);
                mConditionsTabIndicator.setVisibility(View.GONE);
                mReviewTabIndicator.setVisibility(View.GONE);
                mDescriptionTabLayout.setVisibility(View.VISIBLE);
                mConditionsTabLayout.setVisibility(View.GONE);
                mReviewTabLayout.setVisibility(View.GONE);
            }
        });

        mConditionsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDescriptionTabIndicator.setVisibility(View.GONE);
                mConditionsTabIndicator.setVisibility(View.VISIBLE);
                mReviewTabIndicator.setVisibility(View.GONE);
                mDescriptionTabLayout.setVisibility(View.GONE );
                mConditionsTabLayout.setVisibility(View.VISIBLE);
                mReviewTabLayout.setVisibility(View.GONE);
            }
        });

        mReviewTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDescriptionTabIndicator.setVisibility(View.GONE);
                mConditionsTabIndicator.setVisibility(View.GONE);
                mReviewTabIndicator.setVisibility(View.VISIBLE);
                mDescriptionTabLayout.setVisibility(View.GONE);
                mConditionsTabLayout.setVisibility(View.GONE);
                mReviewTabLayout.setVisibility(View.VISIBLE);
            }
        });

        mSmileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley) {
                    case SmileRating.BAD:
                        Log.i(TAG, "Bad");
                        break;
                    case SmileRating.GOOD:
                        Log.i(TAG, "Good");
                        break;
                    case SmileRating.GREAT:
                        Log.i(TAG, "Great");
                        break;
                    case SmileRating.OKAY:
                        Log.i(TAG, "Okay");
                        break;
                    case SmileRating.TERRIBLE:
                        Log.i(TAG, "Terrible");
                        break;
                }
            }
        });

        // Initializing Internet Check
        if (hasConnection(getActivity())) {
            // ToDo
        } else {

        }


    }

    private void toast(String text) {
        Toast.makeText(getActivity(),text, Toast.LENGTH_SHORT).show();
    }

    // Internet check method
    public boolean hasConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }
}
