package com.example.evandro.app13mob.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evandro.app13mob.R;

/*
 */
public class SobreFragment extends Fragment {
    View vSobre;

    public SobreFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vSobre = inflater.inflate(R.layout.sobre_layout, container, false);

        return vSobre;
    }
}
