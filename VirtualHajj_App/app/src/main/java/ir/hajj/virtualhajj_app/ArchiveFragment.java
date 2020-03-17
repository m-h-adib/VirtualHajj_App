package ir.hajj.virtualhajj_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static ir.hajj.virtualhajj_app.R.id.tarikh;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArchiveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArchiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArchiveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;

    private OnFragmentInteractionListener mListener;

    public ArchiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArchiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArchiveFragment newInstance(String param1, String param2) {
        ArchiveFragment fragment = new ArchiveFragment();
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
        view=inflater.inflate(R.layout.fragment_archive, container, false);
        ImageView tarikh=view.findViewById(R.id.tarikh);
        tarikh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 18 );
                PostFragment postFragment=new PostFragment();
                postFragment.setArguments(bundle);
                openFragmentstack(postFragment);
            }
        });
        ImageView ejrai=view.findViewById(R.id.ejrai);
        ejrai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 25 );
                PostFragment postFragment=new PostFragment();
                postFragment.setArguments(bundle);
                openFragmentstack(postFragment);
            }
        });
        ImageView tafsir=view.findViewById(R.id.tafsir);
        tafsir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 24 );
                PostFragment postFragment=new PostFragment();
                postFragment.setArguments(bundle);
                openFragmentstack(postFragment);
            }
        });
        ImageView sabk=view.findViewById(R.id.sabk);
        sabk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 22 );
                PostFragment postFragment=new PostFragment();
                postFragment.setArguments(bundle);
                openFragmentstack(postFragment);
            }
        });
        ImageView siasi=view.findViewById(R.id.siasi);
        siasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 20 );
                PostFragment postFragment=new PostFragment();
                postFragment.setArguments(bundle);
                openFragmentstack(postFragment);
            }
        });
        ImageView kalam=view.findViewById(R.id.kalam);
        kalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 21 );
                PostFragment postFragment=new PostFragment();
                postFragment.setArguments(bundle);
                openFragmentstack(postFragment);
            }
        });
        ImageView behdasht=view.findViewById(R.id.behdasht);
        behdasht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 19 );
                PostFragment postFragment=new PostFragment();
                postFragment.setArguments(bundle);
                openFragmentstack(postFragment);
            }
        });
        ImageView manasek=view.findViewById(R.id.manasek);
        manasek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 17 );
                PostFragment postFragment=new PostFragment();
                postFragment.setArguments(bundle);
                openFragmentstack(postFragment);
            }
        });
        ImageView akhlagh =view.findViewById(R.id.akhlagh);
        akhlagh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 16 );
                PostFragment postFragment=new PostFragment();
                postFragment.setArguments(bundle);
                openFragmentstack(postFragment);
            }
        });
        ImageView asrar=view.findViewById(R.id.asrar);
        asrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 15 );
                PostFragment postFragment=new PostFragment();
                postFragment.setArguments(bundle);
                openFragmentstack(postFragment);
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

    private void openFragment(final Fragment fragment)   {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.your_placeholder, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
    private void openFragmentstack(final Fragment fragment)   {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.your_placeholder, fragment);
        transaction.addToBackStack("tag");
        transaction.commit();

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
