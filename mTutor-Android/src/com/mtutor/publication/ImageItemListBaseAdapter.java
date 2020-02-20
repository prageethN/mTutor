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

public class ImageItemListBaseAdapter extends BaseAdapter {
	private static ArrayList<ImageItemDetails> itemDetailsrrayList;

	private Integer[] imgid = { R.drawable.utilities_video,
			R.drawable.utilities_pdf, R.drawable.utilities_image,
			R.drawable.utilities_doc, R.drawable.utilities_ppt,
			R.drawable.utilities_xls };

	private LayoutInflater l_Inflater;

	public ImageItemListBaseAdapter(Context context,
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
			convertView = l_Inflater.inflate(R.layout.list_row_image, null);
			holder = new ViewHolder();
			holder.txt_Header = (TextView) convertView.findViewById(R.id.text1);
			holder.txt_Normal = (TextView) convertView.findViewById(R.id.text2);
			holder.txt_Footer = (TextView) convertView.findViewById(R.id.text3);
			holder.txt_Extra = (TextView) convertView.findViewById(R.id.text4);
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
		holder.txt_Footer.setText(itemDetailsrrayList.get(position)
				.getTextFooter());
		holder.txt_Extra.setText(itemDetailsrrayList.get(position)
				.getTextExtra());
		holder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(
				position).getImageNumber() - 1]);

		return convertView;
	}

	static class ViewHolder {
		TextView txt_Header;
		TextView txt_Normal;
		TextView txt_Footer;
		TextView txt_Extra;
		ImageView itemImage;
	}
}
