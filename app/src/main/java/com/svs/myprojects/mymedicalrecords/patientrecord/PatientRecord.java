package com.svs.myprojects.mymedicalrecords.patientrecord;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.gson.Gson;
import com.svs.myprojects.mymedicalrecords.Constants;
import com.svs.myprojects.mymedicalrecords.R;
import com.svs.myprojects.mymedicalrecords.patientrecord.home.FragmentHome;
import com.svs.myprojects.mymedicalrecords.patientrecord.makereservation.DocSpecItems;
import com.svs.myprojects.mymedicalrecords.patientrecord.makereservation.FragmentDocSpecInterface;
import com.svs.myprojects.mymedicalrecords.patientrecord.makereservation.FragmentDocSpeciality;
import com.svs.myprojects.mymedicalrecords.patientrecord.makereservation.FragmentMakeAppointment;
import com.svs.myprojects.mymedicalrecords.patientrecord.makereservation.FragmentViewDoctors;
import com.svs.myprojects.mymedicalrecords.patientrecord.makereservation.GroupHeaderItems;
import com.svs.myprojects.mymedicalrecords.patientrecord.medicinereminder.FragmentAddMedicineReminder;
import com.svs.myprojects.mymedicalrecords.patientrecord.medicinereminder.FragmentMedicineReminder;
import com.svs.myprojects.mymedicalrecords.patientrecord.medicinereminder.PatientRecordToAddMedInterface;
import com.svs.myprojects.mymedicalrecords.patientrecord.pharmacist.FragmentRequestMed;
import com.svs.myprojects.mymedicalrecords.patientrecord.pharmacist.FragmentRequestMedLoc;
import com.svs.myprojects.mymedicalrecords.patientrecord.pharmacist.RequestMedInterface;
import com.svs.myprojects.mymedicalrecords.patientrecord.scribblepad.FragmentScribble;
import com.svs.myprojects.mymedicalrecords.patientrecord.uploadimage.FragmentUploadImage;

public class PatientRecord extends AppCompatActivity implements PatientRecordToAddMedInterface,
        FragmentDocSpecInterface,RequestMedInterface {

    public String FRAGMENT_ADD_MED_TAG = "add_med";
    public String FRAGMENT_MAKE_APPOINTMENT = "make_appointment";
    public String FRAGMENT_VIEW_DOC = "view_doc";
    public String FRAGMENT_REQ_MED = "request_med";
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_pill);
        fab.setVisibility(View.GONE);
        replaceFragment(new FragmentHome());

    }

    private void addDrawerItems() {
        final String[] nav_drawer_title = getResources().getStringArray(R.array.nav_drawer_title);//{ "Android", "Medicine Reminder", "Windows", "OS X", "Linux" };
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nav_drawer_title);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        replaceFragment(new FragmentDocSpeciality());
                        break;
                    case 1:
                        replaceFragment(new FragmentHome());
                        break;
                    case 2:
                        replaceFragment(new FragmentMedicineReminder());
                        break;
                    case 3:
                        replaceFragment(new FragmentScribble());
                        break;
                    case 4:
                        replaceFragment(new FragmentUploadImage());
                        break;
                    case 5:
                        replaceFragment(new FragmentRequestMedLoc());
                        break;
                    case 6:
                        finish();
                        break;
                    default:
                        replaceFragment(new FragmentHome());
                        break;
                }

                mDrawerLayout.closeDrawer(mDrawerList);
                mActivityTitle = nav_drawer_title[position];
//                setTitle(nav_drawer_title[position]);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.navigation_drawer_container, fragment);
        ft.commit();
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerList.setItemChecked(1, true);
                mDrawerList.setSelection(1);

                getSupportActionBar().setTitle("Choose your option");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void patientRecToAddMedCommunication(String mJsonArray) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTERFACE_BUNDLE_STR, mJsonArray);
        bundle.putInt(Constants.INTERFACE_ITEM_POSITION, -1);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentAddMedicineReminder fragment = new FragmentAddMedicineReminder();
        fragment.setArguments(bundle);
        ft.replace(R.id.navigation_drawer_container, fragment)
                .addToBackStack(FRAGMENT_ADD_MED_TAG)
                .commit();
    }

    @Override
    public void modifyExistingAlarm(String mJsonArray, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTERFACE_BUNDLE_STR, mJsonArray);
        bundle.putInt(Constants.INTERFACE_ITEM_POSITION, position);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentAddMedicineReminder fragment = new FragmentAddMedicineReminder();
        fragment.setArguments(bundle);
        ft.replace(R.id.navigation_drawer_container, fragment)
                .addToBackStack(FRAGMENT_ADD_MED_TAG)
            .commit();
    }

    @Override
    public void callNextFragment(DocSpecItems docSpecItems) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentMakeAppointment fragment = new FragmentMakeAppointment();
        ft.replace(R.id.navigation_drawer_container, fragment)
                .addToBackStack(FRAGMENT_MAKE_APPOINTMENT)
                .commit();
    }
    @Override
    public void callMakeAppointment(GroupHeaderItems groupHeaderItems) {
        getSupportActionBar().setTitle("Book Appointment");

        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String json = gson.toJson(groupHeaderItems);
        bundle.putString(Constants.GROUP_HEADER_ITEMS,json);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentMakeAppointment fragment = new FragmentMakeAppointment();
        fragment.setArguments(bundle);
        ft.replace(R.id.navigation_drawer_container, fragment)
                .addToBackStack(FRAGMENT_MAKE_APPOINTMENT)
                .commit();
    }

    @Override
    public void callViewDoctorList() {
        getSupportActionBar().setTitle("All Available Doctors..");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentViewDoctors fragment = new FragmentViewDoctors();
        ft.replace(R.id.navigation_drawer_container, fragment)
                .addToBackStack(FRAGMENT_VIEW_DOC)
                .commit();
    }

    @Override
    public void callRequestMedFrag() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentRequestMed fragment = new FragmentRequestMed();
        ft.replace(R.id.navigation_drawer_container, fragment)
                .addToBackStack(FRAGMENT_REQ_MED)
                .commit();
    }

    /*
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data)
    {
        if ( requestCode == Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

            if ( resultCode == RESULT_OK) {

                */
/*********** Load Captured Image And Data Start ****************//*


                String imageId = convertImageUriToFile( imageUri,CameraActivity);


                //  Create and excecute AsyncTask to load capture image

                new LoadImagesFromSDCard().execute(""+imageId);

                */
/*********** Load Captured Image And Data End ****************//*



            } else if ( resultCode == RESULT_CANCELED) {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    */
/************ Convert Image Uri path to physical path **************//*


    public static String convertImageUriToFile ( Uri imageUri, Activity activity )  {

        Cursor cursor = null;
        int imageID = 0;

        try {

            */
/*********** Which columns values want to get *******//*

            String [] proj={
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            cursor = activity.managedQuery(

                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)

            );

            //  Get Query Data

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            //int orientation_ColumnIndex = cursor.
            //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);

            int size = cursor.getCount();

            */
/*******  If size is 0, there are no images on the SD Card. *****//*


            if (size == 0) {
//                imageDetails.setText("No Image");
            }
            else
            {

                int thumbID = 0;
                if (cursor.moveToFirst()) {

                    */
/**************** Captured image details ************//*


                    */
/*****  Used to show image on view in LoadImagesFromSDCard class ******//*

                    imageID     = cursor.getInt(columnIndex);

                    thumbID     = cursor.getInt(columnIndexThumb);

                    String Path = cursor.getString(file_ColumnIndex);

                    //String orientation =  cursor.getString(orientation_ColumnIndex);

                    String CapturedImageDetails = " CapturedImageDetails : \n\n"
                            +" ImageID :"+imageID+"\n"
                            +" ThumbID :"+thumbID+"\n"
                            +" Path :"+Path+"\n";

                    // Show Captured Image detail on activity
//                    imageDetails.setText( CapturedImageDetails );
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )

        return ""+imageID;
    }
*/



}
