package com.example.uhf_bt.component;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uhf_bt.BoardCategoryActivity;
import com.example.uhf_bt.BoardLocationActivity;
import com.example.uhf_bt.BoardSubCategoryActivity;
import com.example.uhf_bt.BoardSubLocationActivity;
import com.example.uhf_bt.Globals;
import com.example.uhf_bt.R;
import com.example.uhf_bt.dto.ButtonItem;
import com.example.uhf_bt.json.JsonTaskDeleteItem;

import java.util.List;

public class ListItemView extends ArrayAdapter<ButtonItem> {

    public int type;

    public int id;

    public boolean isUsed;

    private BoardCategoryActivity categoryActivity;

    private BoardLocationActivity locationActivity;

    private BoardSubCategoryActivity subCategoryActivity;

    private BoardSubLocationActivity subLocationActivity;
    public ListItemView(@NonNull Context context, @NonNull List<ButtonItem> objects, BoardCategoryActivity categoryActivity, BoardLocationActivity locationActivity, BoardSubCategoryActivity subCategoryActivity, BoardSubLocationActivity subLocationActivity) {

        super(context, 0, objects);

        this.categoryActivity = categoryActivity;
        this.locationActivity = locationActivity;
        this.subCategoryActivity = subCategoryActivity;
        this.subLocationActivity = subLocationActivity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_view, parent, false);
        }

        ButtonItem item = getItem(position);

        Button mainButton = convertView.findViewById(R.id.mainButton);
        ImageButton editButton = convertView.findViewById(R.id.editButton);
        ImageButton trashButton = convertView.findViewById(R.id.trashButton);

        // Set the data for each view
        mainButton.setText(item.getMainButtonText());

        id = item.id;

        isUsed = item.isUsed;

        type = item.type;

        // Set click listeners for buttons if needed
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type == 1)
                {
                    Intent intent = new Intent(getContext(), BoardSubCategoryActivity.class);
                    intent.putExtra("categoryId", item.id);
                    getContext().startActivity(intent);

                    Log.e("categoryId::", String.valueOf(item.id));

                } else if (type == 2)
                {

                    Intent intent = new Intent(getContext(), BoardSubCategoryActivity.class);
                    intent.putExtra("categoryId", item.id);
                    getContext().startActivity(intent);
                }
            }
        });

        // Set click listeners for buttons if needed
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit button click

                switch(type)
                {
                    case 1:
                        categoryActivity.updateCategory(item.getMainButtonText(), item.id);
                        break;
                    case 2:
                        locationActivity.updateLocation(item.getMainButtonText(), item.id);
                        break;
                    case 3:
                        subCategoryActivity.updateCategory(item.getMainButtonText(), item.id);
                        break;
                    case 4:
                        subCategoryActivity.updateCategory(item.getMainButtonText(), item.id);
                        break;
                }

            }
        });

        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle trash button click

                if (type == 1)
                {
                    String req = Globals.apiUrl + "category/delete?id=" + String.valueOf(item.id);
                    new JsonTaskDeleteItem().execute(req);

                    categoryActivity.reCallAPI();

                } else if (type == 2){
                    String req = Globals.apiUrl + "location/delete?id=" + String.valueOf(item.id);
                    new JsonTaskDeleteItem().execute(req);

                    locationActivity.reCallAPI();
                } else if (type == 3) {
                    String req = Globals.apiUrl + "subcategory/delete?id=" + String.valueOf(item.id);
                    new JsonTaskDeleteItem().execute(req);

                    Log.e("subCategoryId:::", String.valueOf(item.id));
                    subCategoryActivity.reCallAPI();
                } else if (type == 4) {

                }
            }
        });

        return convertView;
    }
}
