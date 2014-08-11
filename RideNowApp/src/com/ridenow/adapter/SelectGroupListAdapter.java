package com.ridenow.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ridenow.R;

public class SelectGroupListAdapter extends ArrayAdapter<ListModel> implements Filterable {

	private ArrayList<ListModel> items;
	private Context context;

	private final Object lock = new Object();
	private ArrayList<ListModel> originalValues;

	private ListModelFilter ListModelFilter;

	public SelectGroupListAdapter(Context context, ArrayList<ListModel> items) {
		super(context, R.layout.group_list_row,
				R.id.ic_text_username__, items);

		this.items = items;
		this.context = context;
	}

	@Override
	public ListModel getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewWrapper viewWrapper;
		ListModel currentListModel = items.get(position);

		if (view == null) {
			view = ((LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.group_list_row, null);
			viewWrapper = new ViewWrapper(view);
			view.setTag(viewWrapper);
		} else {
			viewWrapper = (ViewWrapper) view.getTag();
		}

		viewWrapper.getName().setText(currentListModel.getName());
		viewWrapper.getSelected().setVisibility(currentListModel.isSelected()?View.VISIBLE:View.INVISIBLE);
//		viewWrapper.getLocation().setText(
//				currentListModel.getLocation().toUpperCase(Locale.getDefault()));
//		viewWrapper.getPlace().setText(
//				currentListModel.getPlace().toUpperCase(Locale.getDefault()));

		return view;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Filter getFilter() {
		if (ListModelFilter == null) {
			ListModelFilter = new ListModelFilter();
		}
		return ListModelFilter;
	}

	class ViewWrapper {
		TextView name;
		ImageView userthumb;
		ImageView isSelected;
		View base;

		public ViewWrapper(View base) {
			this.base = base;
		}

		public TextView getName() {
			if (name == null)
				name = (TextView) base
						.findViewById(R.id.ic_text_username__);
			return name;
		}

		public ImageView getUserThumb() {
			if (userthumb == null)
				userthumb = (ImageView) base
						.findViewById(R.id.ic_newrork_user_thumb);
			return userthumb;
		}

		public ImageView getSelected() {
			if (isSelected == null)
				isSelected = (ImageView) base
						.findViewById(R.id.ic_img_tickmark);
			return isSelected;
		}

	}

	private class ListModelFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();

			if (originalValues == null) {
				synchronized (lock) {
					originalValues = new ArrayList<ListModel>(items);
				}
			}

			if (prefix == null || prefix.length() == 0) {
				synchronized (lock) {
					ArrayList<ListModel> list = new ArrayList<ListModel>(
							originalValues);
					results.values = list;
					results.count = list.size();
				}
			} else {
				String prefixString = prefix.toString().toLowerCase();

				final ArrayList<ListModel> values = originalValues;
				final int count = values.size();

				final ArrayList<ListModel> newValues = new ArrayList<ListModel>(count);

				for (int i = 0; i < count; i++) {
					final ListModel value = values.get(i);
					final String valueText = value.getName().toLowerCase();

					// First match against the whole, non-splitted value
					if (valueText.startsWith(prefixString)) {
						newValues.add(value);
					} else {
						final String[] words = valueText.split(" ");
						final int wordCount = words.length;

						for (int k = 0; k < wordCount; k++) {
							if (words[k].startsWith(prefixString)) {
								newValues.add(value);
								break;
							}
						}
					}
				}

				results.values = newValues;
				results.count = newValues.size();
			}

			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			items = (ArrayList<ListModel>) results.values;
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}

	}

}
