package com.xubin.starclass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.xubin.starclass.entity.Part;
import com.xubin.starclass.entity.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xubin on 2015/9/11.
 */
public class ListAdapter extends BaseExpandableListAdapter {

    private List<Unit> units;
    private LayoutInflater lif;

    public ListAdapter(Context context, List<Unit> units) {
        if (units == null){
            this.units=new ArrayList<>();
        }else {
            this.units = units;
        }
        this.lif=LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return this.units==null?0:units.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return units.get(groupPosition).getParts().size();
    }

    @Override
    public Unit getGroup(int groupPosition) {
        return units.get(groupPosition);
    }

    @Override
    public Part getChild(int groupPosition, int childPosition) {
        return units.get(groupPosition).getParts().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return units.get(groupPosition).getUid();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return units.get(groupPosition).getParts().get(childPosition).getPid();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
