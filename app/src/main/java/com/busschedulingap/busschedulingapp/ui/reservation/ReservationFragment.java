package com.busschedulingap.busschedulingapp.ui.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.busschedulingap.busschedulingapp.databinding.FragmentReservationBinding;

public class ReservationFragment extends Fragment {

    private FragmentReservationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReservationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
}