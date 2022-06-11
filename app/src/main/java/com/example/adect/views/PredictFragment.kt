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
        descView.text = "APA"

        predictButton = view.findViewById(R.id.predictButton)
        predictButton.setOnClickListener{
            val moveIntent = Intent(context, PredictActivity::class.java)
            startActivity(moveIntent)
        }

        return view
    }
}