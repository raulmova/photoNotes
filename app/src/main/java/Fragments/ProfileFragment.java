package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.raul.photonotes.R;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String URL;
    String finalCoverPhoto;

    ImageView ivHeader;
    ImageView ivUser;
    TextView tvUser;
    TextView tvMore;

    Uri profile;
    Profile p;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ivHeader = (ImageView)rootView.findViewById(R.id.header_cover_image);
        ivUser = (ImageView)rootView.findViewById(R.id.user_profile_photo);
        tvUser = (TextView) rootView.findViewById(R.id.user_profile_name);
        tvMore = (TextView) rootView.findViewById(R.id.user_profile_more);

        ///////////////////HEADER COVER

        AccessToken token = AccessToken.getCurrentAccessToken();
        URL = "https://graph.facebook.com/" + token.getUserId() + "?fields=cover&access_token=" + token.getToken();
        Log.d("String ID: ",URL);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject JODetails = new JSONObject(response);

                            if (JODetails.has("cover")) {
                                String getInitialCover = JODetails.getString("cover");

                                if (getInitialCover.equals("null")) {
                                    finalCoverPhoto = null;
                                } else {
                                    JSONObject JOCover = JODetails.optJSONObject("cover");

                                    if (JOCover.has("source")) {
                                        finalCoverPhoto = JOCover.getString("source");
                                        Log.d("COVER: ", finalCoverPhoto);
                                        Picasso.with(getActivity()).load(finalCoverPhoto).into(ivHeader);
                                    } else {
                                        finalCoverPhoto = null;
                                    }
                                }
                            }
                        }
                        catch (Throwable t) {
                            Log.e("My App", "Could not parse malformed JSON:" + response);
                            //Toast.makeText(getApplicationContext(), " Error retrieving data " , Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", "Bad");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        ////////////////////////////////////////////

        p = new Profile(token.getUserId(),null,null,null,null,null);
        profile = p.getProfilePictureUri(202,202);
        Glide.with(getActivity()).load(profile.toString()).into(ivUser);
        Profile p2 = Profile.getCurrentProfile();
        String middleName;
        if(p2.getMiddleName() == "null") {
            middleName = "";
        }
        else
        {
            middleName = p2.getMiddleName();
        }
        tvUser.setText(p2.getFirstName()+" "+middleName +" "+p2.getLastName());
       // tvMore.setText(p2.getLinkUri().toString());
        tvMore.setClickable(true);
        tvMore.setMovementMethod(LinkMovementMethod.getInstance());
        String text = p2.getLinkUri().toString();
        tvMore.setText(Html.fromHtml(text));
        return rootView;
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
}
