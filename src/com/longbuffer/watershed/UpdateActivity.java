package com.longbuffer.watershed;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

import com.longbuffer.watershed.MyLocation.LocationResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateActivity extends Activity implements OnClickListener{
	
	private static final int CAMERA_PIC1_REQUEST = 101;
	private static final int CAMERA_PIC2_REQUEST = 102;
	private static final int CAMERA_PIC3_REQUEST = 103;
	private static final int CAMERA_PIC4_REQUEST = 104;
	
	private static final String appFolderName = "WaterShed" ;
	private static final String dataFolderName = "WaterShed/CapturedData" ;
	private static final String photoFolderName = "WaterShed/CapturedData/Photos" ;
	
	EditText mBeneficiaryName = null;
	EditText mSpouseName = null;
	ImageButton mImage1 = null;
	ImageButton mImage2 = null;
	ImageButton mImage3 = null;
	ImageButton mImage4 = null;
	Button mSaveBtn = null;
	boolean mIsImage1Captured = false ;
	boolean mIsImage2Captured = false ;
	boolean mIsImage3Captured = false ;
	boolean mIsImage4Captured = false ;
	MyLocation mMyLocation = null;
	LocationResult locationResult = null;
	DataManager mDataManager = null;
	String mLongitude = null;
	String mLatitude = null;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.update_activity); 
        
        mBeneficiaryName = (EditText)findViewById(R.id.beneficiary_entry);
        mSpouseName = (EditText)findViewById(R.id.spouse_entry);
        mImage1 = (ImageButton)findViewById(R.id.image_one_button);
        mImage1.setOnClickListener(this);
        mImage2 = (ImageButton)findViewById(R.id.image_two_button);
        mImage2.setOnClickListener(this);
        mImage3 = (ImageButton)findViewById(R.id.image_three_button);
        mImage3.setOnClickListener(this);
        mImage4 = (ImageButton)findViewById(R.id.image_four_button);
        mImage4.setOnClickListener(this);
        mSaveBtn = (Button)findViewById(R.id.save_btn);
        mSaveBtn.setEnabled(false);
        mSaveBtn.setOnClickListener(this);
        mDataManager  = DataManager.getDataManager();
        mMyLocation = new MyLocation();
        locationResult = new LocationResult(){

			@Override
			public void gotLocation(Location location) {
				// TODO Auto-generated method stub
				 if (location != null) {					    
					    mLongitude = new String(Double.toString(location.getLongitude()));
					    mLatitude = new String(Double.toString(location.getLatitude()));
				 }	
				 String data = mDataManager.getDistrict() +
					"~" + mDataManager.getTaluk() + "~" +
					mDataManager.getAppSiNo() + "~" +
					mDataManager.getAppSubSiNo() + "~" +
					mDataManager.getScheme() + "~" +mDataManager.getSeriesYear() + 
					"~" + mDataManager.getHobli() + "~" + mDataManager.getVillage() +
					"~" + mDataManager.getBeneficiary() + "~" + mDataManager.getSpouse()
					+"~"+mBeneficiaryName.getText().toString().trim()+"_1.jpg" +"~" +
					mBeneficiaryName.getText().toString().trim()+"_2.jpg" +"~"+
					mBeneficiaryName.getText().toString().trim()+"_3.jpg" +"~"+
					mBeneficiaryName.getText().toString().trim()+"_4.jpg" +"~" ;
				 
				 	if(mLongitude != null){
				 		data = data + "Longitude:"+mLongitude +"~";
				 	}
				 	if(mLatitude != null){
				 		data = data + "Latitude:"+mLatitude ;
				 	}
					//data = data +"\n" ;	
					writeDataToFile(data);
					mDataManager.resetData();
					finish();
				 
				Toast.makeText(getBaseContext(), "Data Saved", Toast.LENGTH_LONG).show();
			}        	
        };
        createDirectories();
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent cameraIntent = null;
		if(mBeneficiaryName.getText().toString().trim().length() == 0 || mSpouseName.getText().toString().trim().length() == 0){
			return;
		}
		
		switch (v.getId()){
		case R.id.image_one_button:
			cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getPhotoFile(mBeneficiaryName.getText().toString().trim()+"_1.jpg")) );
			startActivityForResult(cameraIntent, CAMERA_PIC1_REQUEST);
			
			break;
		case R.id.image_two_button:
			cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getPhotoFile(mBeneficiaryName.getText().toString().trim()+"_2.jpg")) );
			startActivityForResult(cameraIntent, CAMERA_PIC2_REQUEST);  
			
			break;
		case R.id.image_three_button:
			cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getPhotoFile(mBeneficiaryName.getText().toString().trim()+"_3.jpg")) );
			startActivityForResult(cameraIntent, CAMERA_PIC3_REQUEST);
			
			break;
		case R.id.image_four_button:
			cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getPhotoFile(mBeneficiaryName.getText().toString().trim()+"_4.jpg")) );
			startActivityForResult(cameraIntent, CAMERA_PIC4_REQUEST);
			
			break;
		case R.id.save_btn:
			saveData();
			break;
		default:				
		}		
	}
	
	private void createDirectories(){
		final File path1 = new File( Environment.getExternalStorageDirectory(), appFolderName );
		if(!path1.exists()){
			path1.mkdir();
		}
		final File path2 = new File( Environment.getExternalStorageDirectory(), dataFolderName );
		if(!path2.exists()){
			path2.mkdir();
		}
		final File path3 = new File( Environment.getExternalStorageDirectory(), photoFolderName );
		if(!path3.exists()){
			path3.mkdir();
		}
	}
	private File getPhotoFile(String filename){		  
		  final File path = new File( Environment.getExternalStorageDirectory(), photoFolderName );
		  if(!path.exists()){
		    path.mkdir();
		  }
		  return new File(path, filename);
		}
	
	
	protected void enableSaveButton(){
		if(mBeneficiaryName.getText().toString().trim().length() != 0 && mSpouseName.getText().toString().trim().length() != 0 && mIsImage1Captured && mIsImage2Captured && mIsImage3Captured && mIsImage4Captured){
			mSaveBtn.setEnabled(true);
		}
	}
	protected void saveData(){
		 mMyLocation.getLocation(this, locationResult);
		
		/*
		String data = mDataManager.getDistrict() +
		"~" + mDataManager.getTaluk() + "~" +
		mDataManager.getAppSiNo() + "~" +
		mDataManager.getAppSubSiNo() + "~" +
		mDataManager.getScheme() + "~" +mDataManager.getSeriesYear() + 
		"~" + mDataManager.getHobli() + "~" + mDataManager.getVillage() +
		"~" + mDataManager.getBeneficiary() + "~" + mDataManager.getSpouse()
		+"~"+mBeneficiaryName.getText().toString().trim()+"_1.jpg" +"~" +
		mBeneficiaryName.getText().toString().trim()+"_2.jpg" +"~"+
		mBeneficiaryName.getText().toString().trim()+"_3.jpg" +"~"+
		mBeneficiaryName.getText().toString().trim()+"_4.jpg" +"~" +"\n";
				
		writeDataToFile(data);
		mDataManager.resetData();
		finish();
		*/
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		Bitmap thumbnail = null;
		File file = null;
		if (resultCode != Activity.RESULT_OK){
			return;
		}
		switch(requestCode){
		case CAMERA_PIC1_REQUEST:
			file = getPhotoFile(mBeneficiaryName.getText().toString().trim()+"_1.jpg");
			try {
			thumbnail = Media.getBitmap(getContentResolver(), Uri.fromFile(file) );
			}catch (FileNotFoundException e) {
		          e.printStackTrace();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }			 
			mImage1.setImageBitmap(thumbnail);
			mIsImage1Captured = true ;
			enableSaveButton();
			mDataManager.setBitmap1(thumbnail);
			break;
		case CAMERA_PIC2_REQUEST:
			file = getPhotoFile(mBeneficiaryName.getText().toString().trim()+"_2.jpg");
			try {
				thumbnail = Media.getBitmap(getContentResolver(), Uri.fromFile(file) );
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			mImage2.setImageBitmap(thumbnail);
			mIsImage2Captured = true ;
			enableSaveButton();
			mDataManager.setBitmap2(thumbnail);
			break;
		case CAMERA_PIC3_REQUEST:
			file = getPhotoFile(mBeneficiaryName.getText().toString().trim()+"_3.jpg");
			try {
				thumbnail = Media.getBitmap(getContentResolver(), Uri.fromFile(file) );
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			mImage3.setImageBitmap(thumbnail);
			mIsImage3Captured = true ;
			enableSaveButton();
			mDataManager.setBitmap3(thumbnail);
			break;
		case CAMERA_PIC4_REQUEST:
			file = getPhotoFile(mBeneficiaryName.getText().toString().trim()+"_4.jpg");
			try {
				thumbnail = Media.getBitmap(getContentResolver(), Uri.fromFile(file) );
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			mImage4.setImageBitmap(thumbnail);
			mIsImage4Captured = true ;
			enableSaveButton();
			mDataManager.setBitmap4(thumbnail);
			break;
		default:
			break;
		}
	}  
	protected void onStop (){
		super.onStop();
		mDataManager.setBeneficiary(mBeneficiaryName.getText().toString().trim());
		mDataManager.setSpouse(mSpouseName.getText().toString().trim());
	}	
	@Override
	protected void onResume() {
	  super.onResume();	  
	  if(mDataManager.getBeneficiary() != null){
		  mBeneficiaryName.setText(mDataManager.getBeneficiary());
	  }else{
		  mBeneficiaryName.setText("");
	  }
	  if(mDataManager.getSpouse() != null){
		  mSpouseName.setText(mDataManager.getSpouse());
	  }else{
		  mSpouseName.setText("");
	  }
	  if(mDataManager.getBitmap1() != null){
		  mImage1.setImageBitmap(mDataManager.getBitmap1());
	  }
	  if(mDataManager.getBitmap2() != null){
		  mImage2.setImageBitmap(mDataManager.getBitmap2());
	  }
	  if(mDataManager.getBitmap3() != null){
		  mImage3.setImageBitmap(mDataManager.getBitmap3());
	  }
	  if(mDataManager.getBitmap4() != null){
		  mImage4.setImageBitmap(mDataManager.getBitmap4());
	  }
	}
	
	protected void writeDataToFile(String data){		
		try
	    {
			File root1 = new File(Environment.getExternalStorageDirectory(), "WaterShed");
	        if (!root1.exists()) {
	            root1.mkdirs();
	        }
	        File root2 = new File(root1, "CapturedData");
	        if (!root2.exists()) {
	            root2.mkdirs();
	        }
	        BufferedWriter bw = null;
/*
	        File gpxfile = new File(root2, "updata.txt");
	        FileWriter writer = new FileWriter(gpxfile,true);
	        writer.append(data);
	        writer.flush();
	        writer.close();
	        */
	        File gpxfile = new File(root2, "updata.txt");
	        FileWriter writer = new FileWriter(gpxfile,true);
	        bw = new BufferedWriter(writer);
	        bw.write(data);
	   	 	bw.newLine();
	   	 	bw.flush();
	   	 	bw.close();
	        //Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
	    }
	    catch(IOException e)
	    {
	         e.printStackTrace();	         
	    }
	}	
}
