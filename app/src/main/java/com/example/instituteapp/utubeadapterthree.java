package com.example.instituteapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.List;

public class utubeadapterthree extends RecyclerView.Adapter<utubeadapterthree.VideoViewHolder> {
    List<utubevidone> youtubeVideoList;

    public utubeadapterthree() {
    }
    public utubeadapterthree(List<utubevidone> youtubeVideoList) {
        this.youtubeVideoList = youtubeVideoList;
    }
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videoviewthree, parent, false);

        return new VideoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(utubeadapterthree.VideoViewHolder holder, int position) {

        holder.videoWeb.loadData(youtubeVideoList.get(position).getVideoUrl(), "text/html", "utf-8");

    }
    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        WebView videoWeb;

        public VideoViewHolder(View itemView) {
            super(itemView);

            videoWeb = (WebView) itemView.findViewById(R.id.videoWebView);

            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient() {

            });
        }
    }
}
