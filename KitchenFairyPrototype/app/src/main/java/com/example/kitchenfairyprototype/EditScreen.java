package com.example.kitchenfairyprototype;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LIST_KEY = "Tedo";
    private static final String ARG_LIST_TYPE_KEY = "Zazu";

    // TODO: Rename and change types of parameters
    private ArrayList<Integer> tedo;
    private ArrayList<ItemModel> kiara = new ArrayList<>();
    private boolean zazu;

    TextView close;
    ImageView home;
    ListView lvEditScreen;
    ListViewAdapterEdit adapter;

    public EditScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditScreen.
     */
    public static EditScreen newInstance(boolean param2) {
        EditScreen fragment = new EditScreen();
        Bundle args = new Bundle();
//        args.putIntegerArrayList(ARG_LIST_KEY, param1);
        args.putBoolean(ARG_LIST_TYPE_KEY, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserReference ur = UserReference.getInstance();

        if (getArguments() != null) {
//            tedo = getArguments().getIntegerArrayList(ARG_LIST_KEY);
            zazu = getArguments().getBoolean(ARG_LIST_TYPE_KEY);
        }

        if (zazu){
            for (Recipe r : ur.recipes){
                kiara.add(r);
            }
        } else {
            for (Shopping s : ur.shoppingLists){
                kiara.add(s);
            }
        }

        ListViewAdapterEdit adapter = new ListViewAdapterEdit(getContext(), kiara, zazu);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_screen, container, false);

        close = view.findViewById(R.id.closeBtnEdit);
        lvEditScreen = view.findViewById(R.id.lvListEditor);

        lvEditScreen.setAdapter(adapter);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zazu) {
                    Intent intent = new Intent(getContext(), RecipeList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), ShopList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        });

        return view;
    }
}