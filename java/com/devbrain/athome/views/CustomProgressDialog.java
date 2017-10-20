package com.devbrain.athome.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.devbrain.athome.R;


/**
 * Class used to show custom progress dialog.
 */
public class CustomProgressDialog {

	Context ctx;
	private Dialog dialog;

	public CustomProgressDialog(Context ctx){
		this.ctx=ctx;
		
		dialog = new Dialog(ctx);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = dialog.getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dialog.setContentView(R.layout.custom_loading);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);	
	}

	public void show(){
		try {
			dialog.show();
		}catch(Exception ex) {
			
		}
	}

	public void dismiss(){
		try{
		if (dialog!=null) {
            if (dialog.isShowing()) {
            	dialog.dismiss();     
            }
        }		
		}catch(Exception e){
			
		}
	}
	
	public boolean isShowing(){
		return dialog.isShowing();
	}
}
