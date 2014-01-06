package com.study.doubanbook_for_android.adapter;

import java.util.ArrayList;

import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.adapter.UserListAdapter.ViewHolder;
import com.study.doubanbook_for_android.imagedownloader.ImageDownloader;
import com.study.doubanbook_for_android.model.AuthorUser;
import com.study.doubanbook_for_android.model.Entry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private ArrayList<Entry> entrys;
	private Context context;

	public CommentAdapter(ArrayList<Entry> books, Context context) {
		super();
		this.entrys = books;
		this.context = context;
	}

	@Override
	public int getCount() {
		return entrys.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Entry item = entrys.get(position);
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.i_comment,
					null);
			holder.title = (TextView) convertView
					.findViewById(R.id.title_tv);
			holder.summary = (TextView) convertView
					.findViewById(R.id.summary_tv);
			holder.userName = (TextView) convertView
					.findViewById(R.id.userName_tv);
			holder.updatedTime = (TextView) convertView
					.findViewById(R.id.updatedTime_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(item.getTitle());
		holder.summary.setText(item.getSummay());
		holder.userName.setText(item.getAuthor().getName());
		holder.updatedTime.setText(item.getUpdated());
		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView summary;
		TextView userName;
		TextView updatedTime;
	}
}
