package org.ollide.rosandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.Collections;
import java.util.List;

import static org.ollide.rosandroid.MapsFragment.createDrawableFromView;
import static org.ollide.rosandroid.MapsFragment.markerIcon;

import static org.ollide.rosandroid.MapsFragment.chosenMarkerArrayList;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.RecyclerViewHolder> {

        private final Context mCtx;
       // LinkedHashSet<String> Markers = new LinkedHashSet<String>();
       // List<String> uniqueStrings = new ArrayList<String>(Markers);
        List<String> Markers;


        public RecyclerListAdapter(Context mCtx,  List<String>  Markers) {
            this.mCtx = mCtx;
            this.Markers =  Markers;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.card_layout, null);

            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
            final String Marker=Markers.get(position);
            holder.CardText.setText(Marker.toString());
            holder.deleteCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("Position", holder.getAdapterPosition()+"");
                    Markers.remove(holder.getAdapterPosition());
                    MapsFragment.DestinationCount--;
                    MapsFragment.markerView.setImageResource(R.drawable.ic_marker_black);
                    MapsFragment.markerText.setText(MapsFragment.chosenMarkerArrayList.get(holder.getAdapterPosition()).getTitle());
                    MapsFragment.chosenMarkerArrayList.get(holder.getAdapterPosition()).setIcon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(markerIcon.getContext(), markerIcon)));
                    chosenMarkerArrayList.remove(holder.getAdapterPosition());
                    if(MapsFragment.DestinationCount == 0) {
                        MapsFragment.BottomSheetText.setText("Pick a drop-off location");
                        MapsFragment.BottomSheetText.setAlpha((float) 0.54);
                    }else {
                        MapsFragment.BottomSheetText.setText(chosenMarkerArrayList.get(chosenMarkerArrayList.size()-1).getTitle());
                        MapsFragment.BottomSheetText.setAlpha((float) 0.87);
                    }
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });
        }

        @Override
        public int getItemCount() {
            return Markers.size();
        }

        class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView CardText;
            ImageView deleteCard;

            public RecyclerViewHolder(View itemView) {
                super(itemView);
                CardText = itemView.findViewById(R.id.CardText);
                deleteCard = itemView.findViewById(R.id.delete);
            }

        }



        public void onMove(int fromPos, int toPos) {
            if (fromPos < toPos) {
                for (int i = fromPos; i < toPos; i++) {
                Collections.swap(Markers, i, i + 1);
                }
            } else {
                for (int i = fromPos; i > toPos; i--) {
                    Collections.swap(Markers, i, i - 1);

                }
            }
            notifyItemMoved(fromPos, toPos);
        }
    }

