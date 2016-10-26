/*
* Copyright 2016 Jose Antonio Maestre Celdran
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.futuredrama.jomaceld.CircularProgressbar_ExampleApp;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import net.futuredrama.jomaceld.circularprogressbarLib.BarComponent;
import net.futuredrama.jomaceld.circularprogressbarLib.CircleProgressbarView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ComplexProgressbarFragment extends Fragment {

    CircleProgressbarView pBar;
    boolean bIsLoading = false;

    public ComplexProgressbarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_multibar_example, container, false);

        // Get a pointer to the layout's progressbar
        pBar = (CircleProgressbarView) rootView.findViewById(R.id.pbar);

        if(pBar != null)
            setupProgressbar();

        ToggleButton toogleButton = (ToggleButton) rootView.findViewById(R.id.anim_toggleButton);
        toogleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setLoading(b);
            }
        });
        return rootView;
    }

    private void setupProgressbar()
    {
        int[] colors = { Color.parseColor("#FF9800"),
                Color.parseColor("#E91E63"),
                Color.parseColor("#3F51B5")};

        // Set the numbers of bars
        pBar.setNumberOfBars(3);
        // Set up background color and thickness
        pBar.setBackgroundColor(Color.argb(50,0,0,0));
        pBar.setProgressbarBackgroundThickness(25, TypedValue.COMPLEX_UNIT_DIP);
        // Set bars colors
        pBar.setBarsColors(colors);
        // Set bars thickness
        pBar.setAllBarsThickness(20, TypedValue.COMPLEX_UNIT_DIP);
        // Animate the progress of all bars from current value to the desired values
        pBar.setProgressWithAnimation(0.3f,0.2f,0.1f);
    }

    public void setLoading(boolean b)
    {
        if(bIsLoading == b || pBar == null)
            return;

        ArrayList<BarComponent> barComponentsArray = pBar.getBarComponentsArray();
        if(b) {
            pBar.bStackBars = false;
            int numOfBars = pBar.getBarComponentsArray().size();
            for (int i = 0; i < numOfBars; i++) {
                BarComponent bar = pBar.getBarComponentsArray().get(i);
                bar.animateAngleOffset(i * (360/numOfBars),500);
                bar.animateProgress(0.1f);
            }
            pBar.spinBars(ObjectAnimator.INFINITE,ObjectAnimator.RESTART,1000);
        }else {

            pBar.cancelSpin();
            for (int i = 0; i < barComponentsArray.size(); i++) {
                BarComponent bar = barComponentsArray.get(i);
                float auxValue = bar.getProgress();
                bar.animateProgress(auxValue);
                bar.animateAngleOffset(360,1000);
            }

            pBar.bStackBars = true;
        }
        bIsLoading = b;
    }
}
