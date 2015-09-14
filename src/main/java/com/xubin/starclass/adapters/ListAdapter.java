package com.xubin.starclass.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.xubin.starclass.MyApp;
import com.xubin.starclass.R;
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
    private Context mContext;
    private int childFontSize;
    private int childLeftMargin;

    public ListAdapter(Context context, List<Unit> units) {
        this.mContext = context;
        childFontSize = mContext.getResources().getDimensionPixelSize(R.dimen.font_14);
        childLeftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.left_margin);
        if (units == null) {
            this.units = new ArrayList<>();
        } else {
            this.units = units;
        }
        this.lif = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return this.units == null ? 0 : units.size();
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = lif.inflate(R.layout.layout_item_lesson, null);
            holder = new ViewHolder();
            holder.tvIcn = (TextView) convertView.findViewById(R.id.item_lesson_icn);
            holder.tvName = (TextView) convertView.findViewById(R.id.item_lesson_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Unit unit = getGroup(groupPosition);
        holder.tvName.setText(unit.getName());
        holder.tvIcn.setText(String.valueOf(groupPosition + 1));
        holder.tvIcn.setBackgroundResource(R.drawable.shape_group_icn);
        View p= (View) holder.tvName.getParent();
        p.setBackgroundColor(Color.LTGRAY);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = lif.inflate(R.layout.layout_item_lesson, null);
            holder = new ViewHolder();
            holder.tvIcn = (TextView) convertView.findViewById(R.id.item_lesson_icn);
            holder.tvName = (TextView) convertView.findViewById(R.id.item_lesson_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Part part = getChild(groupPosition, childPosition);
        holder.tvName.setText(part.getName());
//        holder.tvIcn.setText(String.valueOf(childPosition + 1));
        holder.tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                childFontSize);
        holder.tvIcn.setBackgroundResource(R.drawable.shape_child_icn);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.tvIcn.getLayoutParams();
        params.leftMargin = childLeftMargin;
        holder.tvIcn.setLayoutParams(params);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void clear() {
        this.units.clear();
    }

    public void addAll(List<Unit> units) {
        if (null != units) {
            this.units.addAll(units);
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        TextView tvIcn;
        TextView tvName;
    }
}
