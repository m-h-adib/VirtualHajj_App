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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.hajj.virtualhajj_app.Adapters.MatchAdapter;
import ir.hajj.virtualhajj_app.Adapters.QuestionAdapter;
import ir.hajj.virtualhajj_app.Models.Match;
import ir.hajj.virtualhajj_app.Models.Question;
import ir.hajj.virtualhajj_app.Utility.ApiProvider;
import ir.hajj.virtualhajj_app.Utility.ApiServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompetitionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompetitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompetitionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ApiProvider apiProvider = new ApiProvider();
    ApiServices apiServices;
    List<Match> items=new ArrayList<>();
    Button login,ok;
    TextView empty;
    RecyclerView list;
    MatchAdapter adapter;
    View sep;
    LinearLayout score_bx,count_bx;
    TextView score,count;

    private OnFragmentInteractionListener mListener;

    public CompetitionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompetitionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompetitionFragment newInstance(String param1, String param2) {
        CompetitionFragment fragment = new CompetitionFragment();
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
        View view=inflater.inflate(R.layout.fragment_competition, container, false);
        apiServices = apiProvider.GetApiServices();
        login = view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        ok=view.findViewById(R.id.ok);
        sep=view.findViewById(R.id.sep);
        score_bx=view.findViewById(R.id.score_box);
        count_bx=view.findViewById(R.id.count_bx);
        score=view.findViewById(R.id.score);
        count=view.findViewById(R.id.count);
        empty = view.findViewById(R.id.empty);
        list = view.findViewById(R.id.list);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int emp=0;
                for (int i=0;i<items.size();i++){
                    if(items.get(i).getSelect()==0)
                        emp++;
                }
                if(emp==0){
                    for (int i=0;i<items.size();i++){
                        Call<ResponseBody> call=apiServices.SetMatches("Bearer "+Prefs.getString("Token", ""),items.get(i).getMatchID(),items.get(i).getSelect());
                        final int finalI = i;
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code()==200){
                                    GetData();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    Toast.makeText(getContext(), "لطفا به تمامی سوالات را پاسخ دهید و سپس ثبت نمایید", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetData();
    }

    public void GetData(){
        if (Prefs.getString("Token", "").equals("")) {
            login.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            ok.setVisibility(View.GONE);
            score_bx.setVisibility(View.GONE);
            count_bx.setVisibility(View.GONE);
            sep.setVisibility(View.GONE);
        } else {
            // Toast.makeText(getContext(), ""+Prefs.getString("Token",""), Toast.LENGTH_SHORT).show();
            Call<ResponseBody> call = apiServices.GetMatches("Bearer "+Prefs.getString("Token", ""));
            call.enqueue(new Callback<ResponseBody>() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        items.clear();
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        score.setText(jsonObject.getString("score"));
                        count.setText(jsonObject.getString("count"));
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            Match match=new Match();
                            match.setMatchID(jsonObject1.getInt("matchID"));
                            match.setQuestion(jsonObject1.getString("question"));
                            match.setCh1(jsonObject1.getString("ch1"));
                            match.setCh2(jsonObject1.getString("ch2"));
                            match.setCh3(jsonObject1.getString("ch3"));
                            match.setCh4(jsonObject1.getString("ch4"));
                            match.setCorrect(jsonObject1.getInt("correct"));
                            match.setSelect(0);
                            items.add(match);
                        }
                        if(items.size()==0){
                            empty.setVisibility(View.VISIBLE);
                            login.setVisibility(View.GONE);
                            list.setVisibility(View.GONE);
                            ok.setVisibility(View.GONE);
                            score_bx.setVisibility(View.VISIBLE);
                            sep.setVisibility(View.VISIBLE);
                            count_bx.setVisibility(View.VISIBLE);
                        }
                        else {
                            count_bx.setVisibility(View.VISIBLE);
                            score_bx.setVisibility(View.VISIBLE);
                            sep.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                            login.setVisibility(View.GONE);
                            list.setVisibility(View.VISIBLE);
                            ok.setVisibility(View.VISIBLE);
                            adapter=new MatchAdapter(items,getActivity());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

                            list.setLayoutManager(linearLayoutManager);
                            list.setAdapter(adapter);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    login.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                    list.setVisibility(View.GONE);
                    ok.setVisibility(View.GONE);
                    score_bx.setVisibility(View.GONE);
                    sep.setVisibility(View.GONE);
                    count_bx.setVisibility(View.GONE);
                    Prefs.putString("Token","");
                }
            });
        }
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
