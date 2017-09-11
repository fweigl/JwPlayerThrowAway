package com.example.jwplayerthrowaway;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.events.AdCompleteEvent;
import com.longtailvideo.jwplayer.events.listeners.AdvertisingEvents;
import com.longtailvideo.jwplayer.media.ads.Ad;
import com.longtailvideo.jwplayer.media.ads.AdBreak;
import com.longtailvideo.jwplayer.media.ads.AdSource;
import com.longtailvideo.jwplayer.media.playlists.MediaSource;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    JWPlayerView playerView;

    static final String TAG = "xyz";
    static final String VAST_URL = "http://ww251.smartadserver.com/ac?siteid=121510&pgname=wirtschaft_video&fmtid=9446&visit=M&out=vast&tmstp=1505125240027&uid=110bc462-fb6d-4a97-a622-55d4a7280f43&tgt=contentId%3D158900624%3Bvpp%3Dnative";
    static final String VIDEO_URL = "https://weltsfclips-vh.akamaihd.net/i/2017/09/05/20170905-141630_onl_EIG_Freiburg_oL/20170905-141630_onl_EIG_Freiburg_oL_,2000,1500,1000,200,.mp4.csmil/master.m3u8";

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = (JWPlayerView) findViewById(R.id.player);
        playerView.addOnAdStartedListener(new AdvertisingEvents.OnAdStartedListener() {
            @Override
            public void onAdStarted(final String s, final String s1) {
                Log.d(TAG, "Ad started");
            }
        });

        playerView.addOnAdCompleteListener(new AdvertisingEvents.OnAdCompleteListenerV2() {
            @Override
            public void onAdComplete(final AdCompleteEvent adCompleteEvent) {
                Log.d(TAG, "Ad completed");
            }
        });

        PlaylistItem.Builder builder = new PlaylistItem.Builder();

        ArrayList<AdBreak> adBreaks = new ArrayList<>();
        Ad ad = new Ad(AdSource.VAST, VAST_URL);
        AdBreak adBreak = new AdBreak("pre", ad);
        adBreaks.add(adBreak);

        builder.adSchedule(adBreaks);

        PlaylistItem playlistItem = builder.build();
        ArrayList<MediaSource> sources = new ArrayList();
        sources.add(new MediaSource(VIDEO_URL));
        playlistItem.setSources(sources);

        playerView.load(playlistItem);
        playerView.play();
    }
}
