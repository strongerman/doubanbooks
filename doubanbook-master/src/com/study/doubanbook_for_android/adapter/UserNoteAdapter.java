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
import com.study.doubanbook_for_android.model.Annotations;

public class UserNoteAdapter extends BaseAdapter {

	private ArrayList<Annotations> annotationList;
	private Context context;

	public UserNoteAdapter(ArrayList<Annotations> books, Context context) {
		super();
		this.annotationList = books;
		this.context = context;
	}

	@Override
	public int getCount() {
		return annotationList.size();
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
		Annotations item = annotationList.get(position);
		ViewHolder holder = new ViewHolder();

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.i_note_list, null);
			holder.img = (ImageView) convertView
					.findViewById(R.id.userAvatar_iv);
			holder.userName = (TextView) convertView
					.findViewById(R.id.userName_tv);
			holder.abstracts = (TextView) convertView
					.findViewById(R.id.abstract_tv);
			// TODO leave for use later
			// holder.price = (TextView) convertView
			// .findViewById(R.id.bookPrice_tv);
			// holder.publisher = (TextView) convertView
			// .findViewById(R.id.bookPublisher_tv);
			// holder.grade = (TextView) convertView
			// .findViewById(R.id.bookGrade_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/* 设置 */
		holder.userName.setText(item.getAuthor_user().getName());
		holder.abstracts.setText(item.getAbstracts());
		// holder.publisher.setText(item.getPublisher() + " " +
		// item.getPubdate());
		// if (item.getRating() != null)
		// holder.grade.setText(item.getRating().getAverage() + "分 "
		// + item.getRating().getNumRaters() + "人已评论");
		// // TODO GET THE image's size DONE h:W =3:2,I name it by 7:5
		imgDownloader.download(item.getAuthor_user().getAvatar(), holder.img,
				null);
		// StringBuffer stringBuffer = new StringBuffer();
		// for (String s : item.getAuthor()) {
		// stringBuffer.append(s);
		// stringBuffer.append(" ");
		//
		// }
		// holder.author.setText(stringBuffer.toString());
		return convertView;
	}

	static class ViewHolder {
		ImageView img;
		TextView userName;
		TextView abstracts;

		// leave for use later
		TextView price;
		TextView publisher;
		TextView grade;
	}
}
