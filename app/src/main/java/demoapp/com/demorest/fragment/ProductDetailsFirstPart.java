package demoapp.com.demorest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import demoapp.com.demorest.R;
import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

public class ProductDetailsFirstPart extends Fragment {
    //Defining Variables
    private static final String TAG = ProductDetailsFirstPart.class.getSimpleName();
    String mParsedProductName;

    ImageView mAddToWishList, mProductDetailsImage;
    TextView mProductDetailsName, mProductDetailsPrice;
    public SmallBang mSmallBang;

    public static ProductDetailsFirstPart newInstance() {
        ProductDetailsFirstPart fragment = new ProductDetailsFirstPart();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public ProductDetailsFirstPart() {
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
        return inflater.inflate(R.layout.product_details_first_part, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view initialize and functionality declare

        Bundle bundle = getActivity().getIntent().getExtras();
        mParsedProductName = bundle.getString("product_name");

        mProductDetailsName = (TextView) view.findViewById(R.id.product_details_name);
        mProductDetailsName.setText(mParsedProductName);

        mProductDetailsPrice = (TextView) view.findViewById(R.id.product_details_price);
        mProductDetailsPrice.setText("16000 Taka");

        mProductDetailsImage = (ImageView) view.findViewById(R.id.product_details_image);

        mSmallBang = SmallBang.attach2Window(getActivity());
        mAddToWishList = (ImageView) view.findViewById(R.id.product_details_fav);
        mAddToWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddToWishList.setImageResource(R.drawable.heart_red);
                mSmallBang.bang(view);
                mSmallBang.setmListener(new SmallBangListener() {
                    @Override
                    public void onAnimationStart() {
                    }

                    @Override
                    public void onAnimationEnd() {
                        toast("Add to Wish List");
                    }
                });
            }
        });

    }

    private void toast(String text) {
        Toast.makeText(getActivity(),text, Toast.LENGTH_SHORT).show();
    }
}
