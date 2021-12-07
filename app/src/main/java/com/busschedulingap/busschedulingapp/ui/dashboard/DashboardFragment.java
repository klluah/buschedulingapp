package com.busschedulingap.busschedulingapp.ui.dashboard;
// TODO: ADD VIEW MODEL TO EACH FRAGMENT

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.busschedulingap.busschedulingapp.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*// Go to myurl.com when clicking on logo
        ImageView img = (ImageView)findViewById(R.id.logoId);

        img.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://casidiablo.net"));
                startActivity(intent);
            }*/

        return root;
    }
}