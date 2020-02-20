package com.mtutor.watchlist;

import java.util.ArrayList;

import com.mtutor.dashboard.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class WatchListItemListBaseAdapter extends BaseAdapter {

	private static ArrayList<ItemDetails> itemDetailsrrayList;
	private LayoutInflater l_Inflater;

	public WatchListItemListBaseAdapter(Context context,
			ArrayList<ItemDetails> itemSavedSearchList) {

		itemDetailsrrayList = itemSavedSearchList;
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
		final ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.list_row_normal_image,
					null);
			holder = new ViewHolder();
			holder.txt_Header = (TextView) convertView.findViewById(R.id.text1);
			holder.txt_Normal = (TextView) convertView.findViewById(R.id.text2);
			holder.txt_Footer = (TextView) convertView.findViewById(R.id.text3);
			holder.chk_notify = (CheckBox) convertView
					.findViewById(R.id.checkBox1);
			// holder.itemImage = (ImageView)
			// convertView.findViewById(R.id.imageview1);
			// holder.position=position;
			// holder.position = position;
			
			holder.chk_notify
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								// holder.txt_Header.setText("checked"+
								// holder.position);
								holder.chk_notify.setChecked(true);
							} else {
								// holder.txt_Header.setText("unchecked.."+
								// holder.position);
								holder.chk_notify.setChecked(false);
							}
						}
					});
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
		holder.chk_notify.setChecked(itemDetailsrrayList.get(position).getNotifyEnbale());
		// holder.chk_notify.setChecked(itemDetailsrrayList.get(position).getNotifyEnbale());
		// holder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(position).getImageNumber()
		// - 1]);

		return convertView;
	}

	static class ViewHolder {
		TextView txt_Header;
		TextView txt_Normal;
		TextView txt_Footer;
		CheckBox chk_notify;
		// ImageView itemImage;
		// int position;
		// RelativeLayout layoutRow;
	}
}
