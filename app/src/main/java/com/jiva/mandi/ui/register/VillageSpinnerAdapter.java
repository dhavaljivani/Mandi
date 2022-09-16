package com.jiva.mandi.ui.register;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.color.MaterialColors;
import com.jiva.mandi.R;

public class VillageSpinnerAdapter extends ArrayAdapter<CharSequence> {

	private static final int LABEL_POSITION = 0;
	private final int dropdownTile;
	private final int spinner;

	public VillageSpinnerAdapter(Context context, String[] leaveList, @LayoutRes int dropdownTile, @LayoutRes int spinner) {
		super(context, 0, leaveList);
		this.dropdownTile = dropdownTile;
		this.spinner = spinner;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		return initView(position, convertView, parent);
	}

	/**
	 * @param position
	 *            item position
	 * @param convertView
	 *            the UI view that is used for item
	 * @param parent
	 *            id for parent view
	 * @return customized view
	 */
	@Override
	public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(dropdownTile, parent, false);
		}
		if (position == LABEL_POSITION) {
			((AppCompatTextView) convertView).setTextColor(Color.GRAY);
		} else {
			((AppCompatTextView) convertView)
					.setTextColor(MaterialColors.getColor(convertView, androidx.appcompat.R.attr.editTextColor));
		}
		return initView(position, convertView, parent);
	}

	/**
	 * @param position
	 *            item position
	 * @param convertView
	 *            the UI view that is used for item
	 * @param parent
	 *            id for parent view
	 * @return customized view
	 */
	private View initView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(spinner, parent, false);
		}

		AppCompatTextView tvTitle = convertView.findViewById(R.id.tv_title);
		CharSequence currentItem = getItem(position);

		if (currentItem != null) {
			tvTitle.setText(currentItem.toString());
		}
		return convertView;
	}

	/**
	 * @param position
	 *            item position
	 * @return boolean for is item is enable or not
	 */
	@Override
	public boolean isEnabled(int position) {
		return position != LABEL_POSITION;
	}
}
