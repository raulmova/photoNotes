package Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.raul.photonotes.FullscreenPhotoActivity;
import com.example.raul.photonotes.GridAutofitLayoutManager;
import com.example.raul.photonotes.R;

import java.util.ArrayList;

import Adapters.RecycleViewCustomAdapter;
import DB.Modelo.Photo;
import DB.Modelo.PhotosCRUD;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rvList;

    private OnFragmentInteractionListener mListener;
    private static final String TAG = "RecyclerViewFragment";

    private PhotosCRUD crud;
    private ArrayList<Photo> photos;

    protected RecycleViewCustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home2, container, false);
        rootView.setTag(TAG);

        rvList = (RecyclerView) rootView.findViewById(R.id.rvList);

       // rvList.setHasFixedSize(true);
       GridAutofitLayoutManager mLayoutManager = new GridAutofitLayoutManager(getActivity(), 540);
        //mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, true);
      // mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        //mLayoutManager = new GridLayoutManager(getActivity(), 2);
        //mLayoutManager = new LinearLayoutManager(getActivity());
       // mLayoutManager.canScrollHorizontally();
       // mLayoutManager.canScrollVertically();
        rvList.setLayoutManager(mLayoutManager);

        photos = new ArrayList<Photo>();

        crud = new PhotosCRUD(getActivity());
        photos = crud.getPhotos();
        //photos = crud.getPhotos();

        RecycleViewCustomAdapter adapter = new RecycleViewCustomAdapter(this.getContext(),photos, new Adapters.RecyclerViewClickListener() {
            

            @Override
            public void onClick(View view, int position) {
                Log.d("Position: ", ""+position);

               // Toast.makeText(getContext(), position, Toast.LENGTH_LONG).show();
                Intent inten = new Intent(getActivity(), FullscreenPhotoActivity.class);
                inten.putExtra("url",photos.get(position).getPath());
               startActivity(inten);
            }


        });

        rvList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return rootView;
        //return inflater.inflate(R.layout.fragment_home2, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();

        GridAutofitLayoutManager mLayoutManager = new GridAutofitLayoutManager(getActivity(), 540);
        rvList.setLayoutManager(mLayoutManager);

        photos = new ArrayList<Photo>();

        crud = new PhotosCRUD(getActivity());
        photos = crud.getPhotos();

        RecycleViewCustomAdapter adapter = new RecycleViewCustomAdapter(this.getContext(),photos, new Adapters.RecyclerViewClickListener() {


            @Override
            public void onClick(View view, int position) {
                Log.d("Position: ", ""+position);

                // Toast.makeText(getContext(), position, Toast.LENGTH_LONG).show();
                Intent inten = new Intent(getActivity(), FullscreenPhotoActivity.class);
                inten.putExtra("url",photos.get(position).getPath());
                startActivity(inten);
            }


        });

        rvList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
