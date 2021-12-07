package com.busschedulingap.busschedulingapp.ui.available_bus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.busschedulingap.busschedulingapp.databinding.FragmentAvailableBusBinding;

public class AvailableBusFragment extends Fragment {

    private FragmentAvailableBusBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAvailableBusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

}