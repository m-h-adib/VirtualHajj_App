package ir.hajj.virtualhajj_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import ir.hajj.virtualhajj_app.Adapters.PostAdapter;
import ir.hajj.virtualhajj_app.Models.Post;
import ir.hajj.virtualhajj_app.Utility.ApiProvider;
import ir.hajj.virtualhajj_app.Utility.ApiServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";

    // TODO: Rename and change types of parameters
    private int id;

    private OnFragmentInteractionListener mListener;

    public PostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(int id) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_post, container, false);
        //list
        final LinearLayout loading = v.findViewById(R.id.loading);
        RecyclerView list = v.findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final List<Post> items = new ArrayList<>();
        final PostAdapter adapter = new PostAdapter(getActivity(), items,"post");
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        //end list


        //getdata
        ApiProvider apiProvider = new ApiProvider();
        ApiServices apiServices = apiProvider.GetApiServices();
        Call<List<Post>> call = apiServices.GetItems(id);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                //Toast.makeText(getContext(), ""+response.body().get(0).getTitle(), Toast.LENGTH_SHORT).show();
                items.addAll(response.body());
                adapter.notifyDataSetChanged();
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                loading.setVisibility(View.GONE);
            }
        });
        return v;
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
