package com.jiva.mandi.ui.productsell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

	private final List<String> items;
	private final List<String> originalValues;
	private final Context mContext;
	private final int mResource;

	public AutoCompleteAdapter(@NonNull Context context) {
		super(context, android.R.layout.simple_dropdown_item_1line);
		mContext = context;
		mResource = android.R.layout.simple_dropdown_item_1line;
		items = new ArrayList<>();
		originalValues = new ArrayList<>();
	}

	/** @return number of item contains in the list */
	@Override
	public int getCount() {
		return items.size();
	}

	/**
	 * @param position
	 *            of the item in the list
	 * @return instance of Username at specified position in the list
	 */
	@Nullable
	@Override
	public String getItem(int position) {
		return items.get(position);
	}

	/**
	 * @param position
	 *            current item position
	 * @param convertView
	 *            View that will have custom XML file
	 * @param parent
	 *            ViewGroup of parent view
	 * @return customized view
	 */
	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		final View view;
		final TextView text;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			view = inflater.inflate(mResource, parent, false);
		} else {
			view = convertView;
		}
		text = (TextView) view;
		final String item = getItem(position);
		if (item != null) {
			text.setText(item);
		}
		return view;
	}

	/**
	 * Add all given items to the original and filtered list
	 *
	 * @param items
	 *            list of all username
	 */
	public void addAllData(List<String> items) {
		this.items.addAll(items);
		this.originalValues.addAll(items);
	}

	/** @return instance of filter */
	@NonNull
	@Override
	public Filter getFilter() {
		return mFilter;
	}

	private final Filter mFilter = new Filter() {
		/**
		 * @param constraint
		 *            text based on which search initiate
		 * @return result containing matching output
		 */
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			List<String> filteredList = new ArrayList<>();
			if (constraint == null || constraint.length() == 0) {
				filteredList.addAll(originalValues);
			} else {
				String filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim();

				for (String name : originalValues) {
					if (name.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
						filteredList.add(name);
					}
				}
			}
			FilterResults results = new FilterResults();
			results.values = filteredList;
			return results;
		}

		/**
		 * Notify data change event for the adapter when search result fetched
		 *
		 * @param constraint
		 *            text based on which search initiate
		 * @param results
		 *            result coming from performFiltering method
		 */
		@Override
		@SuppressWarnings("unchecked")
		protected void publishResults(CharSequence constraint, FilterResults results) {

			items.clear();
			items.addAll((List<String>) results.values);
			notifyDataSetChanged();
		}

		/**
		 * @param resultValue
		 *            object containing the details of Username
		 * @return string containing username
		 */
		@Override
		public CharSequence convertResultToString(Object resultValue) {
			return (String) resultValue;
		}
	};
}
