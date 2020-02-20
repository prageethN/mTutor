package com.mtutor.publication;

import java.util.ArrayList;

import com.mtutor.dashboard.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AttachmentItemListBaseAdapter extends BaseAdapter {
	private static ArrayList<ImageItemDetails> itemDetailsrrayList;

	private Integer[] imgid = { R.drawable.utilities_video,
			R.drawable.utilities_pdf, R.drawable.utilities_image,
			R.drawable.utilities_doc, R.drawable.utilities_ppt,
			R.drawable.utilities_xls };

	private LayoutInflater l_Inflater;

	public AttachmentItemListBaseAdapter(Context context,
			ArrayList<ImageItemDetails> results) {
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return itemDetailsrrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemDetailsrrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater
					.inflate(R.layout.list_row_attachment, null);
			holder = new ViewHolder();
			holder.txt_Header = (TextView) convertView.findViewById(R.id.text1);
			holder.txt_Normal = (TextView) convertView.findViewById(R.id.text2);
			holder.itemImage = (ImageView) convertView
					.findViewById(R.id.list_image);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txt_Header.setText(itemDetailsrrayList.get(position)
				.getTextHeader());
		holder.txt_Normal.setText(itemDetailsrrayList.get(position)
				.getTextNormal());

		holder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(
				position).getImageNumber() - 1]);

		return convertView;
	}

	static class ViewHolder {
		TextView txt_Header;
		TextView txt_Normal;
		ImageView itemImage;
	}
}
