package ir.hajj.virtualhajj_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import ir.hajj.virtualhajj_app.Adapters.QuestionAdapter;
import ir.hajj.virtualhajj_app.Models.Question;
import ir.hajj.virtualhajj_app.Utility.ApiProvider;
import ir.hajj.virtualhajj_app.Utility.ApiServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ApiProvider apiProvider = new ApiProvider();
    ApiServices apiServices;
    List<Question> items=new ArrayList<>();
    FloatingActionButton fab;
    Button login;
    TextView empty;
    RecyclerView list;

    private OnFragmentInteractionListener mListener;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
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
    public void onResume() {
        super.onResume();
        if (Prefs.getString("Token", "").equals("")) {
            login.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        } else {
            // Toast.makeText(getContext(), ""+Prefs.getString("Token",""), Toast.LENGTH_SHORT).show();
            Call<List<Question>> call = apiServices.GetQuestions("Bearer "+Prefs.getString("Token", ""));
            call.enqueue(new Callback<List<Question>>() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                    items=response.body();
                    if(items.size()==0){
                        fab.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.VISIBLE);
                        login.setVisibility(View.GONE);
                        list.setVisibility(View.GONE);
                    }
                    else {
                        fab.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);
                        login.setVisibility(View.GONE);
                        list.setVisibility(View.VISIBLE);
                        QuestionAdapter adapter=new QuestionAdapter(items,getActivity());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        list.setLayoutManager(linearLayoutManager);
                        list.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Question>> call, Throwable t) {
                    login.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                    list.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                    Prefs.putString("Token","");
                }
            });
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        apiServices = apiProvider.GetApiServices();
        login = view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        fab=view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddQuestion.class));
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        empty = view.findViewById(R.id.empty);
        list = view.findViewById(R.id.list);

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
