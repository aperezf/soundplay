package com.aperezf.soundplay;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListMediasAdapter extends RecyclerView.Adapter<ListMediasAdapter.MediaViewHolder> implements SectionIndexer {

    private List<MediaData> mediaDataList;
    private ItemClickListener onItemClickListener;
    private ArrayList<Integer> mSectionPositions;

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = mediaDataList.size(); i < size; i++) {
            String section = String.valueOf(mediaDataList.get(i).getTitle().charAt(0)).toUpperCase();
            if (!Character.isLetter(section.charAt(0))) section = "#";
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    class MediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvArtist;
        CardView cvItem;
        ItemClickListener listener;

        MediaViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.txtTitle);
            tvArtist = itemView.findViewById(R.id.txtArtist);
            cvItem = itemView.findViewById(R.id.cvItem);

            cvItem.setOnClickListener(this);
        }

        void setItemClickListener(ItemClickListener icl){
            this.listener = icl;
        }

        @Override
        public void onClick(View v) {
            this.listener.onItemClick(this.getAdapterPosition());
        }
    }

    ListMediasAdapter(List<MediaData> mediaData, ItemClickListener listener){
        this.mediaDataList = mediaData;
        this.onItemClickListener = listener;

    }


    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);
        return new MediaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {

        holder.tvTitle.setText(mediaDataList.get(position).getTitle());
        holder.tvArtist.setText(mediaDataList.get(position).getArtist());
        holder.setItemClickListener(onItemClickListener);

    }

    @Override
    public int getItemCount() {
        return mediaDataList.size();
    }




}
