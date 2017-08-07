package com.tianditu.sdkDemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

public class SDKDemoMain extends ExpandableListActivity {

	class SDKDemoItem {
		public String mTitle;
		public String mDescription;
		public Intent mIntent;
	}

	class SDKDemoGroup {
		public String mGroupKey;
		public List<SDKDemoItem> mList = new ArrayList<SDKDemoItem>();
	}

	private static final String mPackage = "com.tianditu.sdkDemo.";
	private ExpandableListAdapter mAdapter;
	private List<SDKDemoGroup> mGroups;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String path = intent.getStringExtra(mPackage + "Path");

		if (path == null) {
			path = "";
		}

		ExpandableListView listView =  getExpandableListView();
		listView.setGroupIndicator(null);
		listView.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return true;
			}
		});
		
		mGroups = getData(path);
		mAdapter = new MyExpandableListAdapter(this);

		setListAdapter(mAdapter);
		int size = mAdapter.getGroupCount();
		for (int i = 0; i < size; i++) {
			listView.expandGroup(i);
		}
	}

	private List<SDKDemoGroup> getData(String prefix) {
		List<SDKDemoGroup> myData = new ArrayList<SDKDemoGroup>();

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_DEFAULT);

		PackageManager pm = getPackageManager();
		List<ResolveInfo> list = pm.queryIntentActivities(mainIntent,
				PackageManager.MATCH_DEFAULT_ONLY | PackageManager.GET_RESOLVED_FILTER);
		
		if (null == list)
			return myData;

		String[] prefixPath;

		if (prefix.equals("")) {
			prefixPath = null;
		} else {
			prefixPath = prefix.split("/");
		}

		int len = list.size();

		Map<String, Boolean> entries = new HashMap<String, Boolean>();

		for (int i = 0; i < len; i++) {
			ResolveInfo info = list.get(i);
			String name = info.activityInfo.name;
			if (!name.contains(mPackage))
				continue;

			CharSequence labelSeq = info.loadLabel(pm);
			String label = labelSeq != null ? labelSeq.toString() : info.activityInfo.name;
			String className = info.activityInfo.name;
			int group = info.activityInfo.descriptionRes == 0 ? R.string.groupkey4
					: info.activityInfo.descriptionRes;
			
			if (prefix.length() == 0 || label.startsWith(prefix)) {

				String[] labelPath = label.split("/");
				String[] classNamePath = className.split("\\.");

				String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];
				String nextClass = classNamePath[classNamePath.length - 1];
				String nextGroup = getString(group);
				
				if (nextLabel != null && nextLabel.equals(getString(R.string.app_name)))
					nextLabel = nextClass;

				if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
					addItem(myData,
							nextLabel,
							nextClass,
							nextGroup,
							activityIntent(info.activityInfo.applicationInfo.packageName,
									info.activityInfo.name));
				} else {
					if (entries.get(nextLabel) == null) {
						addItem(myData, nextLabel, nextClass, nextGroup,
								browseIntent(prefix.equals("") ? nextLabel : prefix + "/"
										+ nextLabel));
						entries.put(nextLabel, true);
					}
				}
			}
		}
		return myData;
	}

	private Intent activityIntent(String pkg, String componentName) {
		Intent result = new Intent();
		result.setClassName(pkg, componentName);
		return result;
	}

	private Intent browseIntent(String path) {
		Intent result = new Intent();
		result.setClass(this, this.getClass());
		result.putExtra(mPackage + "Path", path);
		return result;
	}

	private SDKDemoGroup hasGroup(List<SDKDemoGroup> data, String key) {
		for (SDKDemoGroup group : data) {
			if (group.mGroupKey.equals(key))
				return group;
		}
		return null;
	}

	private void addItem(List<SDKDemoGroup> data, String name, String className, String key,
			Intent intent) {
		SDKDemoItem item = new SDKDemoItem();
		item.mTitle = name;
		item.mDescription = className;
		item.mIntent = intent;

		SDKDemoGroup group = hasGroup(data, key);
		if (group == null) {
			group = new SDKDemoGroup();
			group.mGroupKey = key;
			data.add(group);
		}
		group.mList.add(item);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
			int childPosition, long id) {
		SDKDemoItem item = mGroups.get(groupPosition).mList.get(childPosition);
		startActivity(item.mIntent);
		return true;
	}

	class MyExpandableListAdapter extends BaseExpandableListAdapter {
		Context mContext;

		public MyExpandableListAdapter(Context context) {
			mContext = context;
		}

		@Override
		public int getGroupCount() {
			return mGroups.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return mGroups.get(groupPosition).mList.size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return mGroups.get(groupPosition).mGroupKey;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return mGroups.get(groupPosition).mList.get(childPosition);
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
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				int height = mContext.getResources().getDimensionPixelSize(R.dimen.groupheight);
				int padding = mContext.getResources().getDimensionPixelSize(R.dimen.margin);
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, height);

				TextView textView = new TextView(mContext);
				textView.setLayoutParams(lp);
				textView.setBackgroundColor(Color.DKGRAY);
				textView.setTextColor(Color.WHITE);
				textView.setPadding(padding, 0, padding, 0);
				textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
				convertView = textView;
			}
			TextView textView = (TextView) convertView;
			textView.setText(getGroup(groupPosition).toString());
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(mContext, android.R.layout.simple_list_item_2, null);
			}
			SDKDemoItem item = (SDKDemoItem) getChild(groupPosition, childPosition);
			TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
			textView.setText(item.mTitle);
			textView = (TextView) convertView.findViewById(android.R.id.text2);
			textView.setText(item.mDescription);
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}
}
