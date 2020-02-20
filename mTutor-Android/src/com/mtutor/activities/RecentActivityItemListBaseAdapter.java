package com.mtutor.activities;

import java.util.ArrayList;

import com.mtutor.dashboard.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecentActivityItemListBaseAdapter extends BaseAdapter {
	private static ArrayList<ItemDetails> itemDetailsrrayList;

	private Integer[] imgid = { R.drawable.ic_comment,
			R.drawable.ic_no_comment,

	};

	private LayoutInflater l_Inflater;

	public RecentActivityItemListBaseAdapter(Context context,
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
			convertView = l_Inflater.inflate(R.layout.list_row_normal, null);
			holder = new ViewHolder();
			holder.txt_Header = (TextView) convertView.findViewById(R.id.text1);
			holder.txt_Normal = (TextView) convertView.findViewById(R.id.text2);
			holder.txt_Footer = (TextView) convertView.findViewById(R.id.text3);
			// holder.chk_notify=(CheckBox)
			// convertView.findViewById(R.id.checkBox1);
			// holder.itemImage = (ImageView)
			// convertView.findViewById(R.id.imageview1);
			// holder.position=position;

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
		// holder.chk_notify.setChecked(itemDetailsrrayList.get(position).getNotifyEnbale());
		// holder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(position).getImageNumber()
		// - 1]);

		return convertView;
	}

	static class ViewHolder {
		TextView txt_Header;
		TextView txt_Normal;
		TextView txt_Footer;
		// CheckBox chk_notify;
		// ImageView itemImage;
		// int position;
		// RelativeLayout layoutRow;
	}
}
