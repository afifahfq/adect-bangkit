package com.example.adect.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.adect.R
import com.example.adect.databinding.FragmentPredictBinding

class PredictFragment : Fragment() {
    private var _binding: FragmentPredictBinding? = null
    private val binding get() = _binding!!
    private lateinit var predictButton: Button
    private lateinit var descView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPredictBinding.inflate(inflater, container, false)
        val view = binding.root

        descView = view.findViewById(R.id.descView)
        descView.text = "Anemia is a condition in which you lack enough healthy red blood cells to carry adequate oxygen to your body's tissues. Having anemia, also referred to as low hemoglobin, can make you feel tired and weak.\n" +
                "\n" +
                "There are many forms of anemia, each with its own cause. Anemia can be temporary or long term and can range from mild to severe. In most cases, anemia has more than one cause. See your doctor if you suspect that you have anemia. It can be a warning sign of serious illness.\n" +
                "\n" +
                "Treatments for anemia, which depend on the cause, range from taking supplements to having medical procedures. You might be able to prevent some types of anemia by eating a healthy, varied diet."

        predictButton = view.findViewById(R.id.predictButton)
        predictButton.setOnClickListener{
            val moveIntent = Intent(context, PredictActivity::class.java)
            startActivity(moveIntent)
        }

        return view
    }
}