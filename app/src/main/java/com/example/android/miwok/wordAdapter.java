package com.example.android.miwok;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class wordAdapter extends ArrayAdapter<word>{
    private int mcolor;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        final word currentword = getItem(position);

        View colorcontainer=listItemView.findViewById(R.id.ll2);
        int color= ContextCompat.getColor(getContext(),mcolor);
        colorcontainer.setBackgroundColor(color);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView EnglishTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        EnglishTextView.setText(currentword.getDefaultTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView GujaratiTextView = (TextView) listItemView.findViewById(R.id.gujarati_text_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        GujaratiTextView.setText(currentword.getGujaratiTranslation());

        ImageView imageView=(ImageView)listItemView.findViewById(R.id.image);
        if (currentword.hasImage()) {
            imageView.setImageResource(currentword.getmImageResourceId());
        }
        else {
            imageView.setVisibility(View.GONE);
        }
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

    public wordAdapter(@NonNull Context context, @NonNull List<word> objects,int color_d) {
        super(context, 0, objects);
        mcolor=color_d;
    }
}
