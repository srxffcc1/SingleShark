package com.businessframehelp.module.tree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import java.util.List;
/**
 * http://blog.csdn.net/lmj623565791/article/details/40212367
 * @author zhy
 *
 * @param <T>
 */
abstract public  class TreeListViewAdapter<T> extends BaseAdapter
{	private boolean isSetOnItenClick=false;
	int defaultExpandLevel;
	protected Context mContext;
	/**
	 * 存储所有可见的Node
	 */
	private List<T> datas;
	protected List<Node> mNodes;
	protected LayoutInflater mInflater;
	/**
	 * 存储所有的Node
	 */
	protected List<Node> mAllNodes;

	/**
	 * 点击的回调接口
	 */
//	private OnTreeNodeClickListener onTreeNodeClickListener;
	abstract void onClick(Node node, int position);;


//	public interface OnTreeNodeClickListener
//	{
//		void onClick(Node node, int position);
//	}
//
//	public void setOnTreeNodeClickListener(
//			OnTreeNodeClickListener onTreeNodeClickListener)
//	{
//		this.onTreeNodeClickListener = onTreeNodeClickListener;
//	}

	/**
	 * 
//	 * @param mTree
	 * @param context
	 * @param datas
	 * @param defaultExpandLevel
	 *            默认展开几级树
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public TreeListViewAdapter(Context context, List<T> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException
	{
		mContext = context;
		this.datas=datas;
		this.defaultExpandLevel=defaultExpandLevel;
		/**
		 * 对所有的Node进行排序
		 */
		initList(datas);
		mInflater = LayoutInflater.from(context);

		/**
		 * 设置节点点击时，可以展开以及关闭；并且将ItemClick事件继续往外公布
		 */


	}

	private void initList(List<T> datas) throws IllegalAccessException {
		mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
		/**
		 * 过滤出可见的Node
		 */
		mNodes = TreeHelper.filterVisibleNode(mAllNodes);
	}

	/**
	 * 相应ListView的点击事件 展开或关闭某节点
	 * 
	 * @param position
	 */
	public void expandOrCollapse(int position)
	{
		Node n = mNodes.get(position);

		if (n != null)// 排除传入参数错误异常
		{
			if (!n.isLeaf())
			{
				n.setExpand(!n.isExpand());
				mNodes = TreeHelper.filterVisibleNode(mAllNodes);
				notifyDataSetChanged();// 刷新视图
			}
		}
	}

	@Override
	public void notifyDataSetChanged() {
		try {
			initList(datas);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		super.notifyDataSetChanged();
	}

	@Override
	abstract public int getItemViewType(int position);
	@Override
	abstract public int getViewTypeCount() ;

	@Override
	public int getCount()
	{
		return mNodes.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mNodes.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(!isSetOnItenClick){
			((AbsListView)parent).setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id)
				{
					expandOrCollapse(position);

//				if (onTreeNodeClickListener != null)
//				{
					onClick(mNodes.get(position),
							position);
//				}
				}

			});
			isSetOnItenClick=true;
		}
		Node node = mNodes.get(position);
		convertView = getConvertView(node, position, convertView, parent);
		// 设置内边距
		convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
		return convertView;
	}

	public abstract View getConvertView(Node node, int position,
			View convertView, ViewGroup parent);

}
