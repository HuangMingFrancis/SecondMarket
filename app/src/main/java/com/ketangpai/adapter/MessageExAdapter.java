package com.ketangpai.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketangpai.nan.ketangpai.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nan on 2016/3/18.
 */
public class MessageExAdapter extends BaseExpandableListAdapter {

    Context mContext;
    ArrayList<String> mMessageGroups;
    ArrayList<ArrayList<String>> mMessagmeGroupUsers;

    public MessageExAdapter(Context mContext, ArrayList<String> mMessageGroups, ArrayList<ArrayList<String>> mMessagmeGroupUsers) {
        this.mContext = mContext;
        this.mMessageGroups = mMessageGroups;
        this.mMessagmeGroupUsers = mMessagmeGroupUsers;
    }


    @Override
    public int getGroupCount() {
        return mMessageGroups.size();
//    return 10;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mMessagmeGroupUsers.get(groupPosition).size();
//    return 10;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mMessageGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return mMessagmeGroupUsers.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_message_group, parent, false);
            viewHolderGroup = new ViewHolderGroup(convertView);
            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }
        viewHolderGroup.groupName.setText(mMessageGroups.get(groupPosition));
        viewHolderGroup.groupUserCount.setText(getChildrenCount(groupPosition)+"");


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderGroupUser viewHolderGroupUser;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_message_group_item, parent, false);
            viewHolderGroupUser = new ViewHolderGroupUser(convertView);
            convertView.setTag(viewHolderGroupUser);
        } else {
            viewHolderGroupUser = (ViewHolderGroupUser) convertView.getTag();
        }
        viewHolderGroupUser.itemUserName.setText(mMessagmeGroupUsers.get(groupPosition).get(childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolderGroup {
        private TextView groupName;
        private TextView groupUserCount;

        public ViewHolderGroup(View view) {
            this.groupName = (TextView) view.findViewById(R.id.tv_group_name);
            this.groupUserCount = (TextView) view.findViewById(R.id.tv_group_userCount);
        }
    }

    class ViewHolderGroupUser {
        private CircleImageView itemUserIcon;
        private TextView itemUserName;

        public ViewHolderGroupUser(View view) {
            itemUserIcon = (CircleImageView) view.findViewById(R.id.img_message_itemUserIcon);
            itemUserName = (TextView) view.findViewById(R.id.tv_messagme_itemUserName);
        }
    }


}
