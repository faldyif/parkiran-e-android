package id.markirin.parkirane;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by faldyikhwanfadila on 7/13/17.
 */

public class SlotAdapter extends ArrayAdapter<ParkirSlot>{

    private ArrayList<ParkirSlot> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView textTitle;
        TextView textAvailability;
        LinearLayout layoutAvailability;
    }

    public SlotAdapter(ArrayList<ParkirSlot> data, Context context) {
        super(context, R.layout.list_row, data);
        this.dataSet = data;
        this.mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ParkirSlot parkirSlot = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            viewHolder.textTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.textAvailability = (TextView) convertView.findViewById(R.id.text_availability);
            viewHolder.layoutAvailability = (LinearLayout) convertView.findViewById(R.id.layout_availability);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textTitle.setText(parkirSlot.getName());
        if(parkirSlot.getKondisi().equals("available") && parkirSlot.getReservedBy().equals("default")) {
            // Available
            viewHolder.textAvailability.setText("Available");
            viewHolder.layoutAvailability.setBackgroundResource(android.R.color.holo_green_light);
        } else if(parkirSlot.getKondisi().equals("available") && !parkirSlot.getReservedBy().equals("default")) {
            // Booked
            viewHolder.textAvailability.setText("Booked");
            viewHolder.layoutAvailability.setBackgroundResource(android.R.color.holo_orange_light);
        } else {
            // Occupied
            viewHolder.textAvailability.setText("Occupied");
            viewHolder.layoutAvailability.setBackgroundResource(android.R.color.darker_gray);
        }
        // Return the completed view to render on screen
        return convertView;
    }
}
