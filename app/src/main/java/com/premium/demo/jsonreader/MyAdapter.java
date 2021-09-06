package com.premium.demo.jsonreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


public class MyAdapter extends ArrayAdapter<Tutorial> {

    private List<Tutorial> tutorialList;
    private Bitmap bitmap;
    private Context mCtx;

    public MyAdapter(List<Tutorial> tutorialList, Context mCtx) {
        super(mCtx, R.layout.list_item, tutorialList);
        this.tutorialList = tutorialList;
        this.mCtx = mCtx;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        convertView = inflater.inflate(R.layout.list_item, null, true);
        holder = new ViewHolder();

        Tutorial tutorial = tutorialList.get(position);
        String imageUrl = tutorial.getImageUrl();
        String tutorialDescription = tutorial.getDescription();
        String tutorialTitle = tutorial.getName();

        holder.textViewName.setText(tutorialTitle);
        holder.textDescription.setText(tutorialDescription);

        if (holder.imageView != null) {

            new ImageDownloaderTask(holder.imageView).execute(imageUrl);
        }
        holder.imageView.setImageBitmap(bitmap);
        return convertView;
    }
    static class ViewHolder {
        TextView textViewName;
        TextView textDescription;
        ImageView imageView;
    }
}