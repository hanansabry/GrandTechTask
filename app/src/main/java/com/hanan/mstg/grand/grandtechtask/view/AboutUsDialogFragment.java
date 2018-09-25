package com.hanan.mstg.grand.grandtechtask.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hanan.mstg.grand.grandtechtask.R;

public class AboutUsDialogFragment extends DialogFragment {
    private static final String TITLE = "TITLE";
    private static final String CONTENT = "CONTENT";

    public static AboutUsDialogFragment newInstance(String title, String content){
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(CONTENT, content);

        AboutUsDialogFragment dialog = new AboutUsDialogFragment();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.aboutus_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString(TITLE);
        String content = getArguments().getString(CONTENT);

        getDialog().setTitle(title);

        TextView aboutUs = view.findViewById(R.id.aboutus);
        aboutUs.setText(content);

        Button back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
