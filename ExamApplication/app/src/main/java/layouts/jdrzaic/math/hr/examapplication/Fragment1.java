package layouts.jdrzaic.math.hr.examapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import layouts.jdrzaic.math.hr.examapplication.R;

public class Fragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                     ViewGroup container, Bundle savedInstanceState) {

        //---Inflate the layout for this fragment---
        return inflater.inflate(
                R.layout.fragment_fragment1, container, false);
    }

}