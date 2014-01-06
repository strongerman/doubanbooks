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
import com.study.doubanbook_for_android.model.BookItem;

public class BookAdapter extends BaseAdapter {

	private ArrayList<BookItem> books;
	private Context context;

	public BookAdapter(ArrayList<BookItem> books, Context context) {
		super();
		this.books = books;
		this.context = context;
	}

	@Override
	public int getCount() {
		return books.size();
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
		BookItem item = books.get(position);
		ViewHolder holder = new ViewHolder();

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.i_bookserchlist, null);
			holder.img = (ImageView) convertView.findViewById(R.id.bookImg_iv);
			holder.title = (TextView) convertView
					.findViewById(R.id.bookName_tv);
			holder.author = (TextView) convertView
					.findViewById(R.id.bookAuthor_tv);
			holder.price = (TextView) convertView
					.findViewById(R.id.bookPrice_tv);
			holder.publisher = (TextView) convertView
					.findViewById(R.id.bookPublisher_tv);
			holder.grade = (TextView) convertView
					.findViewById(R.id.bookGrade_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/* 设置 */
		holder.title.setText(item.getTitle());
		holder.price.setText(item.getPrice());
		holder.publisher.setText(item.getPublisher() + " " + item.getPubdate());
		if (item.getRating() != null)
			holder.grade.setText(item.getRating().getAverage() + "分 "
					+ item.getRating().getNumRaters() + "人已评论");
		// TODO GET THE image's size DONE h:W =3:2,I name it by 7:5
		imgDownloader.download(item.getImage(), holder.img, null);
		StringBuffer stringBuffer = new StringBuffer();
		for (String s : item.getAuthor()) {
			stringBuffer.append(s);
			stringBuffer.append(" ");

		}
		holder.author.setText(stringBuffer.toString());
		return convertView;
	}

	static class ViewHolder {
		ImageView img;
		TextView title;
		TextView author;
		TextView price;
		TextView publisher;
		TextView grade;
	}
}
