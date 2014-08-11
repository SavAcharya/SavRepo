package com.ridenow.utils;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Path;
import android.view.Gravity;
import android.widget.Toast;

import com.ridenow.adapter.CreateGroupModel;
import com.ridenow.adapter.ListModel;

public class Utils {

	public static Collection<? extends ListModel> getAllDummyData() {
	     ArrayList<ListModel> data  = new ArrayList<ListModel>();
	     ListModel model = new ListModel(1, "Rambo", "http", true, false);
	     data.add(model);
	     model = new ListModel(1, "Alizabeth", "http", false, false);
	     data.add(model);
	     model = new ListModel(1, "Manoj", "http", true, false);
	     data.add(model);
	     model = new ListModel(1, "Sourav", "http", true, false);
	     data.add(model);
	     model = new ListModel(1, "Amit Makwana", "http", false, false);
	     data.add(model);
	     model = new ListModel(1, "Shreya Rawat", "http", true, false);
	     data.add(model);
	     model = new ListModel(1, "Raju Srivastava", "http", false, false);
	     data.add(model);
	     model = new ListModel(1, "Kundan Roy", "http", true, false);
	     data.add(model);
	 	return data;
	}
	
	public static Collection<? extends CreateGroupModel> getAllDummyDataNetwork() {
	     ArrayList<CreateGroupModel> data  = new ArrayList<CreateGroupModel>();
	    
	     CreateGroupModel model = new CreateGroupModel("All", false,null);
	     data.add(model);
	     model = new CreateGroupModel("Office", false,null);
	     data.add(model);
	     model = new CreateGroupModel("Friends", false,null);
	     data.add(model);
	     model = new CreateGroupModel("Community", false,null);
	     data.add(model);
	  
	 	return data;
	}
	public static Bitmap getCircularBitmap(Bitmap bitmap) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        final Bitmap outputBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);

        final Path path = new Path();
        path.addCircle(
                  (float)(width / 2)
                , (float)(height / 2)
                , (float) Math.min(width, (height / 2))
                , Path.Direction.CCW);

        final Canvas canvas = new Canvas(outputBitmap);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, null);
        return outputBitmap;}
	public static void showToast(Context context, String message)
	{
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
	    toast.show();
		toast.setGravity(Gravity.CLIP_HORIZONTAL | Gravity.CENTER_HORIZONTAL, 0, 0);

	}
}
