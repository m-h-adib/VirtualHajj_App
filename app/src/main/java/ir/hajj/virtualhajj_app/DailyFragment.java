package ir.hajj.virtualhajj_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ir.hajj.virtualhajj_app.Adapters.DayAdapter;
import ir.hajj.virtualhajj_app.Adapters.PostAdapter;
import ir.hajj.virtualhajj_app.Models.Day;
import ir.hajj.virtualhajj_app.Models.Post;
import ir.hajj.virtualhajj_app.Utility.ApiProvider;
import ir.hajj.virtualhajj_app.Utility.ApiServices;
import ir.hajj.virtualhajj_app.Utility.JalaliCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DailyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DailyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyFragment newInstance(String param1, String param2) {
        DailyFragment fragment = new DailyFragment();
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
        View view=inflater.inflate(R.layout.fragment_daily, container, false);
        final LinearLayout loading = view.findViewById(R.id.loading);
        RecyclerView list = view.findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final List<Day> items = new ArrayList<>();
        final DayAdapter adapter = new DayAdapter(getActivity(), items);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        //end list


        //getdata
        ApiProvider apiProvider = new ApiProvider();
        ApiServices apiServices = apiProvider.GetApiServices();
        //Toast.makeText(getContext(), ""+, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), ""+new JalaliCalendar().toString(), Toast.LENGTH_SHORT).show();
        Call<List<Day>> call = apiServices.GetDaily(new JalaliCalendar().toString());
        call.enqueue(new Callback<List<Day>>() {
            @Override
            public void onResponse(Call<List<Day>> call, Response<List<Day>> response) {
               // Toast.makeText(getContext(), ""+response.body().size(), Toast.LENGTH_SHORT).show();
                items.addAll(response.body());
                adapter.notifyDataSetChanged();
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Day>> call, Throwable t) {
                loading.setVisibility(View.GONE);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
