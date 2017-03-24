package demoapp.com.demorest.fragment;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quickblox.core.Consts;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBProgressCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.QBCustomObjectsFiles;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.customobjects.model.QBCustomObjectFileField;
import com.quickblox.sample.core.utils.Toaster;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import demoapp.com.demorest.R;
import demoapp.com.demorest.activity.ProductDetails;
import demoapp.com.demorest.adapter.ItemAdapter;
import demoapp.com.demorest.model.Item;
import demoapp.com.demorest.utils.ItemClickSupport;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    //Defining Variables
    private static final String TAG = "HomeFragment";
    //Defining Variables
    ArrayList<Item> homeItem;
    RecyclerView mRecyclerView;
    ItemAdapter adapter;
    String mLocationName;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public FloatingActionButton content_add_fab;
    QBCustomObject mObject;

    File f;
    String mCameraImagePath;

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


        // initializing floating action button
        content_add_fab = (FloatingActionButton) view.findViewById(R.id.content_add_fab);
        content_add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toaster.longToast("Coming Soon...");
                //new Thread(new Task()).start();
                //startDilog();
                //new Thread(new Task2()).start();
            }
        });

        // Select item on listclick
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Item data = homeItem.get(position);
                        mLocationName = data.getLocation();
                        Log.d("Location: ", mLocationName);

                        Toaster.longToast("Coming Soon...");
                            Intent intent = new Intent(getActivity(), ProductDetails.class);
                            intent.putExtra("product_name", "ShelTec Builders");
                            getActivity().startActivity(intent);
                    }
                }
        );
    }

    class Task implements Runnable {
        @Override
        public void run() {
            addData();
        }
    }

    class Task2 implements Runnable {
        @Override
        public void run() {
            getData();
        }
    }

    public void addData(){
/*        QBCustomObject newRecord = new QBCustomObject("Movie");
        newRecord.put("name", "Ramayan");
        newRecord.put("comment", "fantastic movie");
        newRecord.put("genre", "Religious");
        newRecord.put("image", mCameraImagePath);


        QBCustomObject createdObject = null;
        try {
            createdObject = QBCustomObjects.createObject(newRecord).perform();
        } catch (QBResponseException e) {
            Toaster.longToast("Error to Add data");
        }
        if (createdObject != null) {
            Log.i("HomeFragment", ">>> created object: " + createdObject);
        }*/

        QBCustomObject qbCustomObject = new QBCustomObject("Movie");

        QBCustomObjectFileField uploadFileResult = null;
        try {
            uploadFileResult = QBCustomObjectsFiles.uploadFile(f, qbCustomObject, "image", new QBProgressCallback() {
                @Override
                public void onProgressUpdate(int progress) {
                    Log.i("HomeFragment", "progress: " + progress);
                }
            }).perform();
        } catch (QBResponseException e) {
            Log.d("HomeFragment","Error to Add data");
        }
        if(uploadFileResult != null){
            Log.i("HomeFragment", ">>>upload response:" + uploadFileResult.getFileName() + " " + uploadFileResult.getFileId() + " " +
                    uploadFileResult.getContentType());
        }
    }

    private void startDilog() {
        AlertDialog.Builder myAlertDilog = new AlertDialog.Builder(getActivity());
        myAlertDilog.setTitle("Upload picture option..");
        myAlertDilog.setMessage("Where to upload picture????");
        myAlertDilog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Intent picIntent = new Intent(Intent.ACTION_GET_CONTENT,null);
                //picIntent.setType("image/*");
                //picIntent.putExtra("return_data",true);
                //startActivityForResult(picIntent, 1);
                openGallery();
            }
        });
        myAlertDilog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(picIntent, 2);
                openCamera();
            }
        });
        myAlertDilog.show();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        f = new File(android.os.Environment.getExternalStorageDirectory(), currentTimeStamp);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, 1);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                try {
                    mCameraImagePath = f.getAbsolutePath();
                    new Thread(new Task()).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                //String[] filePath = { MediaStore.Images.Media.DATA };
                f = new File(getRealPathFromUri(selectedImage));
                new Thread(new Task()).start();
                //Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                //c.moveToFirst();
                //int columnIndex = c.getColumnIndex(filePath[0]);
                //String picturePath = c.getString(columnIndex);
                //if (picturePath != null && picturePath.length() > 1) {
                //    ContentValues va = new ContentValues();
                //} else {
                //}
               // mCameraImagePath = picturePath;
               // Log.w("path of image:", picturePath + "");
            }
        }
    }

    private String getRealPathFromUri(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void getData(){
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setPagesLimit(10);
        //requestBuilder.gt("rating", 1);

        Bundle params = new Bundle();
        ArrayList<QBCustomObject> objects = null;
        try {
            objects = QBCustomObjects.getObjects("Movie", requestBuilder, params).perform();
        } catch (QBResponseException e) {
            Log.d("HomeFragment","Error to get data");
        }
        if (objects != null) {
            int skip = params.getInt(Consts.SKIP);
            int limit = params.getInt(Consts.LIMIT);
            Log.i(TAG, "limit=" + limit + " skip=" + skip);
            Log.i(TAG, ">>> custom objects: " + objects);
        }
    }

}
