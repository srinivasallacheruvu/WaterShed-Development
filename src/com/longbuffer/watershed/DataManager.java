package com.longbuffer.watershed;

import android.graphics.Bitmap;

public class DataManager {
	protected String mDistrict = null;
	protected String mTaluk = null;
	protected String mAppSiNo = null;
	protected String mAppSubSiNo = null;
	protected String mScheme = null;
	protected String mSeriesYear = null;
	protected String mHobli = null;
	protected String mVillage = null;
	protected String mBeneficiary = null;
	protected String mSpouse = null;
	protected Bitmap mImage1 = null;
	protected Bitmap mImage2 = null;
	protected Bitmap mImage3 = null;
	protected Bitmap mImage4 = null;
	
	protected static DataManager mDataManager = null;
	
	public static DataManager getDataManager(){
		if(mDataManager == null){
			mDataManager = new DataManager();
		}
		return mDataManager;
	}
	
	public void resetData(){
		mDistrict = null;
		mTaluk = null;
		mAppSiNo = null;
		mAppSubSiNo = null;
		mScheme = null;
		mSeriesYear = null;
		mHobli = null;
		mVillage = null;
		mBeneficiary = null;
		mSpouse = null;
		mImage1 = null;
		mImage2 = null;
		mImage3 = null;
		mImage4 = null;
	}
	
	public String getDistrict(){ 
		return mDistrict;
	}
	public String getTaluk(){
		return mTaluk;
	}
	public String getAppSiNo(){
		return mAppSiNo;
	}
	public String getAppSubSiNo(){
		return mAppSubSiNo;
	}
	public String getScheme(){
		return mScheme;
	}
	public String getSeriesYear(){
		return mSeriesYear;
	}
	public String getHobli(){
		return mHobli;
	}
	public String getVillage(){
		return mVillage;
	}
	public String getBeneficiary(){
		return mBeneficiary;
	}
	public String getSpouse(){
		return mSpouse;	
	}
	
	
	public void setDistrict(String data){ 
		mDistrict = data ;
	}
	public void setTaluk(String data){
		mTaluk = data ;
	}
	public void setAppSiNo(String data){
		mAppSiNo = data ;
	}
	public void setAppSubSiNo(String data){
		mAppSubSiNo = data ;
	}
	public void setScheme(String data){
		mScheme = data ;
	}
	public void setSeriesYear(String data){
		mSeriesYear = data ;
	}
	public void setHobli(String data){
		mHobli = data ;
	}
	public void setVillage(String data){
		mVillage = data ;
	}
	public void setBeneficiary(String data){
		mBeneficiary = data ;
	}
	public void setSpouse(String data){
		mSpouse = data ;	
	}
	
	public void setBitmap1(Bitmap image){
		mImage1 = image ;
	}
	public Bitmap getBitmap1(){
		return mImage1;
	}
	public void setBitmap2(Bitmap image){
		mImage2 = image ;
	}
	public Bitmap getBitmap2(){
		return mImage2;
	}
	public void setBitmap3(Bitmap image){
		mImage3 = image ;
	}
	public Bitmap getBitmap3(){
		return mImage3;
	}
	public void setBitmap4(Bitmap image){
		mImage4 = image ;
	}
	public Bitmap getBitmap4(){
		return mImage4;
	}
	
	
}
