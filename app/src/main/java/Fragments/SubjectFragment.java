package Fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.raul.photonotes.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import Adapters.RecycleViewCustomAdapter;
import Adapters.RecycleViewCustomAdapterCourses;
import DB.Modelo.Materia;
import DB.Modelo.PhotosCRUD;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubjectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubjectFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvCourses;
    private static final String TAG = "RecyclerViewFragment";
    private FloatingActionButton fabAddCourse;
    protected RecyclerView.LayoutManager mLayoutManager;

    private EditText ed_nombre;
    private OnFragmentInteractionListener mListener;
    private PhotosCRUD crud;

    public SubjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubjectFragment newInstance(String param1, String param2) {
        SubjectFragment fragment = new SubjectFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_subject, container, false);
        rootView.setTag(TAG);
        crud = new PhotosCRUD(getActivity());

        rvCourses = (RecyclerView) rootView.findViewById(R.id.rvCourses);
        rvCourses.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvCourses.setLayoutManager(mLayoutManager);

        fabAddCourse= (FloatingActionButton) rootView.findViewById(R.id.fabAddCourse);
        fabAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Course", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                AlertDialog.Builder mBuilder = new  AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater(getArguments()).inflate(R.layout.dialog_spinner,null);
                mBuilder.setTitle("Añadir Materia");
                final Spinner mSpinner =(Spinner)mView.findViewById(R.id.spinner);
                //final TextView prueba =(TextView)mView.findViewById(R.id.prueba);

                ed_nombre= (EditText)mView.findViewById(R.id.edit_nombre);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.horario));

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                Button bFrom = (Button) mView.findViewById(R.id.button_from);
                Button bTo = (Button) mView.findViewById(R.id.button_to);
                final TextView txt_from=(TextView) mView.findViewById(R.id.txt_from);
                final TextView txt_to=(TextView) mView.findViewById(R.id.txt_to);

                //Time Picker
                bFrom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO Auto-generated method stub
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                txt_from.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, DateFormat.is24HourFormat(getActivity()));//Yes 24 hour time

                        mTimePicker.show();
                    }
                });

                bTo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO Auto-generated method stub
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                txt_to.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, DateFormat.is24HourFormat(getActivity()));//Yes 24 hour time

                        mTimePicker.show();
                    }
                });



                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String v_nombre="";
                        String v_fecha="";
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Choose a day")){
                            v_nombre=ed_nombre.getText().toString();


                            if(v_nombre.isEmpty())
                            {
                                Toast.makeText(getActivity(),"Error: Algun campo esta vacio"
                                        , Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            /*
                            Si todo Sale bien
                            * */
                            int id= crud.newMateria(new Materia(0,v_nombre));

                            RecycleViewCustomAdapterCourses adapter = new RecycleViewCustomAdapterCourses(getActivity(),crud.getMaterias(), new Adapters.RecyclerViewClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    Toast.makeText(getContext(),"Position: " +position, Toast.LENGTH_SHORT);
                                }
                            });
                            rvCourses.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            Log.d("ID:",id+"");
                            Toast.makeText(getActivity(),mSpinner.getSelectedItem().toString()+" "
                                    + v_nombre+" "+v_fecha, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }
                        else{
                            Toast.makeText(getActivity(),"Error: Debes de seleccionar un dia"
                                    , Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog =mBuilder.create();
                dialog.show();

            }
        });

        //ArrayList<Materia> courses = crud.getMaterias();

        RecycleViewCustomAdapterCourses adapter = new RecycleViewCustomAdapterCourses(getActivity(),crud.getMaterias(), new Adapters.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getContext(),"Position: " +position, Toast.LENGTH_SHORT);
            }
        });
        rvCourses.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
