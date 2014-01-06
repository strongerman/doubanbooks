package com.study.doubanbook_for_android.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.doubanbook_for_android.R;
import com.study.doubanbook_for_android.imagedownloader.ImageDownloader;
import com.study.doubanbook_for_android.model.AuthorUser;

public class UserListAdapter extends BaseAdapter {

	private ArrayList<AuthorUser> users;
	private Context context;

	public UserListAdapter(ArrayList<AuthorUser> books, Context context) {
		super();
		this.users = books;
		this.context = context;
	}

	@Override
	public int getCount() {
		return users.size();
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

		ImageDownloader imgDownloader = new ImageDownloader(context);
		AuthorUser item = users.get(position);
		ViewHolder holder = new ViewHolder();

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.i_user,
					null);
			holder.img = (ImageView) convertView.findViewById(R.id.portrait_iv);
			holder.userName = (TextView) convertView
					.findViewById(R.id.userName_tv);
			holder.introduce = (TextView) convertView
					.findViewById(R.id.introduce_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/* 设置 */
		if (item.getName() == null)
			holder.userName.setVisibility(View.GONE);
		else
			holder.userName.setText(item.getName().trim() + "");
		holder.introduce.setText(item.getCreated().trim()+"  " + item.getLoc_name());
		// TODO change to get the w and h is must?
		imgDownloader.download(item.getAvatar(), holder.img, null);
		return convertView;
	}

	static class ViewHolder {
		ImageView img;
		TextView userName;
		TextView introduce;
	}

}
