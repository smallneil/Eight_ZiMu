package com.geetol.zimu.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.geetol.videoedit.whole.editVideo.adpaters.BubbleAdapter;
import com.shichai.zimu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ListFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ry)
    RelativeLayout ry;
    Unbinder unbinder;
    private int type = 1;//1涂鸦  2贴图  3颜色  4样式

    public static ListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_list, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt("type");

        setDataType();
    }

    private void initView() {
        /*BubbleAdapter pasterAdapter = new BubbleAdapter(getActivity(), images);
        pasterAdapter.setPasterItemSelectListener(this);
        recyclerView.setAdapter(pasterAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));*/
    }

    private void setDataType() {
        switch (type) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
