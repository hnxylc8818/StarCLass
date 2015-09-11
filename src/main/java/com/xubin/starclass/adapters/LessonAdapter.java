package com.xubin.starclass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xubin.starclass.R;
import com.xubin.starclass.entity.Lesson;
import com.xubin.starclass.https.XUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xubin on 2015/9/11.
 */
public class LessonAdapter extends BaseAdapter {

    private List<Lesson> lessonList;
    private LayoutInflater lif;
    public LessonAdapter(Context context,List<Lesson> lessonList){
        if (lessonList == null){
            this.lessonList=new ArrayList<>();
        }else{
            this.lessonList=lessonList;
        }
        this.lif=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lessonList == null?0:lessonList.size();
    }

    @Override
    public Lesson getItem(int position) {
        return lessonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lessonList.get(position).getLid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null){
            convertView=lif.inflate(R.layout.layout_item_lessons,null);
            holder=new ViewHolder();
            holder.thumb= (ImageView) convertView.findViewById(R.id.lessons_item_thumb);
            holder.tvName= (TextView) convertView.findViewById(R.id.lessons_item_name);
            holder.tvDesc= (TextView) convertView.findViewById(R.id.lessons_item_desc);
            holder.numsAndStarts= (TextView) convertView.findViewById(R.id.lessons_item_nums_stars);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Lesson lesson=getItem(position);
        XUtils.bitmapUtils.display(holder.thumb,XUtils.BURL+lesson.getThumbUrl());
        holder.tvName.setText(lesson.getLname());
        holder.tvDesc.setText(lesson.getLdesc());
        holder.numsAndStarts.setText(
                String.format("学习人数%s  |  星币%s",String.valueOf(lesson.getNums()),
                        lesson.getNeedStars()==0?"免费":String.valueOf(lesson.getNeedStars())));
        return convertView;
    }

    public void clear(){
        this.lessonList.clear();
    }

    public void addAll(List<Lesson> lessonList){
        if (null != lessonList) {
            this.lessonList.addAll(lessonList);
            notifyDataSetChanged();
        }
    }

    class ViewHolder{
        ImageView thumb;
        TextView tvName;
        TextView tvDesc;
        TextView numsAndStarts;
    }
}
