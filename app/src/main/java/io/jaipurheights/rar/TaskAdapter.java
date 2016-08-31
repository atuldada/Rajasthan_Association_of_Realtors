/**
 * Copyright (c) 2015 Cloudant, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package io.jaipurheights.rar;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class TaskAdapter extends BaseAdapter implements ListAdapter {

    private final Context context;
    private final List<Task> tasks;
    private ImageLoader ob;

    public TaskAdapter(Context context, List<Task> tasks) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return this.tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return this.tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return Adapter.IGNORE_ITEM_VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_item, parent, false);
        }

        TextView desc = (TextView) convertView.findViewById(R.id.task_description);
        TextView subdesc = (TextView) convertView.findViewById(R.id.sub_description);
        TextView location = (TextView) convertView.findViewById(R.id.location);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        CheckBox completed = (CheckBox) convertView.findViewById(R.id.checkbox_completed);
        ImageView image=(ImageView) convertView.findViewById(R.id.imageofproperty);
        Button call =(Button)convertView.findViewById(R.id.call);
        Button message =(Button)convertView.findViewById(R.id.message);
       final Task t = this.tasks.get(position);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + t.getPhone()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
              //  Toast.makeText(context, "commming soon", Toast.LENGTH_SHORT).show();
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + t.getPhone()));
                intent.putExtra("sms_body", " ");
                view.getContext().startActivity(intent);
               // Toast.makeText(context, "commming soon", Toast.LENGTH_SHORT).show();
            }
        });

        desc.setText(t.getDescription());
        subdesc.setText(t.getSubdescription());
        location.setText(t.getLocation());
        price.setText("PRICE:"+t.getPrice()+"/-");
        completed.setChecked(t.isCompleted());
        completed.setId(position);
        if(t.getImagename()!=null) {
            String url = "https://181bdeb6-e0bd-4d54-8024-e62d940ae4b9-bluemix.cloudant.com/rar/" + t.getId() + "/" + t.getImagename();
            System.out.println(url);
            Context context = parent.getContext();
            ImageLoader imgLoader = new ImageLoader(context);
            imgLoader.DisplayImage(url, (ImageView) convertView.findViewById(R.id.imageofproperty));

         //   new DownloadImageTask((ImageView) convertView.findViewById(R.id.imageofproperty))
          //          .execute(url);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( v.getContext(),Displaysearch.class);
                intent.putExtra("phone",t.getPhone());
                intent.putExtra("url", "https://181bdeb6-e0bd-4d54-8024-e62d940ae4b9-bluemix.cloudant.com/rar/" + t.getId() + "/" + t.getImagename());
                intent.putExtra("description", t.getDescription());
                intent.putExtra("subdescription", t.getSubdescription());
                intent.putExtra("name", t.getNamee());
                intent.putExtra("price",t.getPrice()+"/-");
                intent.putExtra("area", t.getArea());
                intent.putExtra("city", t.getCity());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

            }
        });

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    /**
     * Add the given Task at the end of the list
     */
    public void add(Task t) {
        this.tasks.add(t);
        this.notifyDataSetChanged();
    }

    /**
     * Put the give Task at specified position
     */
    public void set(int position, Task t) {
        this.tasks.set(position, t);
        this.notifyDataSetChanged();
    }

    /**
     * Remove the Task at specified position
     */
    public void remove(int position) {
        this.tasks.remove(position);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
