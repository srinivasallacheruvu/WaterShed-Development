package com.longbuffer.watershed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends Activity implements OnClickListener{
	
	
	EditText mDistrict = null;
	EditText mTaluk = null;
	EditText mAppSiNo = null;
	EditText mAppSubSiNo = null;
	EditText mScheme = null;
	EditText mSeriesYear = null;
	EditText mHobli = null;
	EditText mVillage = null;
	
	Button mViewBtn = null;
	Button mUpdateBtn = null;
	DataManager mDataManager = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.input_activity);    
        
        mDistrict = (EditText)findViewById(R.id.district_entry);
        mTaluk = (EditText)findViewById(R.id.taluk_entry);
        mAppSiNo = (EditText)findViewById(R.id.app_si_no_entry);
        mAppSubSiNo = (EditText)findViewById(R.id.app_sub_si_no_entry);
        mScheme = (EditText)findViewById(R.id.scheme_entry);
        mSeriesYear = (EditText)findViewById(R.id.series_entry);
        mHobli = (EditText)findViewById(R.id.hobli_entry);
        mVillage = (EditText)findViewById(R.id.village_entry);
        mViewBtn = (Button)findViewById(R.id.view_btn);
        mViewBtn.setOnClickListener(this);
        mUpdateBtn = (Button)findViewById(R.id.update_btn);
        mUpdateBtn.setOnClickListener(this);
        mDataManager  = DataManager.getDataManager();
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub		
		switch (v.getId()){
		case R.id.view_btn:
			loadData();
			break;
		case R.id.update_btn:
			if(isUpdateEnabled()){
				
				mDataManager.setDistrict(mDistrict.getText().toString().trim());
				mDataManager.setTaluk(mTaluk.getText().toString().trim());
				mDataManager.setAppSiNo(mAppSiNo.getText().toString().trim());
				mDataManager.setAppSubSiNo(mAppSubSiNo.getText().toString().trim());
				mDataManager.setScheme(mScheme.getText().toString().trim());
				mDataManager.setSeriesYear(mSeriesYear.getText().toString().trim());
				mDataManager.setHobli(mHobli.getText().toString().trim());
				mDataManager.setVillage(mVillage.getText().toString().trim());
				
				Intent updateIntent = new Intent(this, UpdateActivity.class);
				startActivity(updateIntent);
			}else{
				Toast.makeText(this, "Fill data", Toast.LENGTH_LONG).show();
			}			
			break;
		default:				
		}		
	}
	protected void loadData(){		
		if(mAppSiNo.getText().toString().trim().length() != 0 || mAppSubSiNo.getText().toString().trim().length() != 0){
			mScheme.setText("New Scheme");
			mSeriesYear.setText("2011");
			mHobli.setText("New Hobli");
			mVillage.setText("Gandhi Nagar");
		}		
	}
	protected boolean isUpdateEnabled(){		
		if(mDistrict.getText().toString().trim().length() != 0 || mTaluk.getText().toString().trim().length() != 0 || 
		   mAppSiNo.getText().toString().trim().length() != 0 || mAppSubSiNo.getText().toString().trim().length() != 0 ||
		   mScheme.getText().toString().trim().length() != 0 || mSeriesYear.getText().toString().trim().length() != 0 ||
		   mHobli.getText().toString().trim().length() != 0 || mVillage.getText().toString().trim().length() != 0   ){
			return true ;
		}
		return false;
	}
	@Override
	protected void onResume() {
		super.onResume();

		if(mDataManager.getDistrict() != null){
			mDistrict.setText(mDataManager.getDistrict());
		}else{
			mDistrict.setText("");
			mDistrict.requestFocus();
		}

		if(mDataManager.getTaluk() != null){
			mTaluk.setText(mDataManager.getTaluk());
		}else{
			mTaluk.setText("");
		}

		if(mDataManager.getAppSiNo() != null){
			mAppSiNo.setText(mDataManager.getAppSiNo());
		}else{
			mAppSiNo.setText("");
		}

		if(mDataManager.getAppSubSiNo() != null){
			mAppSubSiNo.setText(mDataManager.getAppSubSiNo());
		}else{
			mAppSubSiNo.setText("");
		}

		if(mDataManager.getScheme() != null){
			mScheme.setText(mDataManager.getScheme());
		}else{
			mScheme.setText("");
		}

		if(mDataManager.getSeriesYear() != null){
			mSeriesYear.setText(mDataManager.getSeriesYear());
		}else{
			mSeriesYear.setText("");
		}

		if(mDataManager.getHobli() != null){
			mHobli.setText(mDataManager.getHobli());
		}else{
			mHobli.setText("");
		}

		if(mDataManager.getVillage() != null){
			mVillage.setText(mDataManager.getVillage());
		}else{
			mVillage.setText("");
		}
	}
}