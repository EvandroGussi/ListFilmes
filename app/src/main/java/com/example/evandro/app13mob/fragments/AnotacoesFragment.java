package com.example.evandro.app13mob.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.evandro.app13mob.R;
import com.example.evandro.app13mob.fragments.dummy.DummyContent;
import com.example.evandro.app13mob.fragments.dummy.DummyContent.DummyItem;
import com.example.evandro.app13mob.model.Anotacoes;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AnotacoesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RecyclerView mRecyclerView;
    RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AnotacoesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AnotacoesFragment newInstance(int columnCount) {
        AnotacoesFragment fragment = new AnotacoesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anotacoes_list, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mRecyclerView = null;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        List<Anotacoes> lstAnotacoes = Anotacoes.listAll(Anotacoes.class);
        final ArrayAdapter<Anotacoes> adapter = new ArrayAdapter<Anotacoes>(getContext(), R.layout.fragment_anotacoes_list, lstAnotacoes);
        adapter.notifyDataSetChanged();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this.getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Anotacoes anotacoes = adapter.getItem(position);

                Toast.makeText(getActivity(), anotacoes.getNome(), Toast.LENGTH_SHORT).show();

                EditFragment editFragment = new EditFragment();
                Bundle b = new Bundle();
                b.putString("id", anotacoes.getId().toString());
                editFragment.setArguments(b);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, editFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.content_frame)).commit();

                //mRecyclerView.removeAllViews();
                //mRecyclerView.removeAllViewsInLayout();


                try {
                    AnotacoesFragment.this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1,
//                                    int position, long arg3) {
//                // TODO Auto-generated method stub
//
//                int category = categories[position];
//                Class activityClass = lookupActivityClass_byName(category);
//                //You could lookup by position, but "name" is more general
//
//                Intent intent = new Intent(getActivity(), activityClass);
//                startActivity(intent);
//
//
//            }
//        });

        // Set the adapter
        //if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
           // DummyItem a = DummyContent.ITEMS;
            recyclerView.setAdapter(new MyAnotacoesRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        //}
        return view;
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
