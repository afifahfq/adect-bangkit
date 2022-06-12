package com.example.adect.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adect.R
import com.example.adect.databinding.ActivityPredictBinding
import com.example.adect.databinding.ActivityPredictResultBinding

class PredictResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictResultBinding
    private lateinit var result: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predict_result)

        binding = ActivityPredictResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        result = intent.getStringExtra(EXTRA_RESULT)!!
        binding.resultText.text = "Result : " + result

        binding.descResultView.text = "ADECT is detecting anemia using Computer Vision and Machine Learning, by converting an image of palpebral conjunctiva (lower eyelid) to check signs of anemia. By checking lower eyelid, hemoglobin level of blood can be examined without requiring blood drawing and blood test.\n" +
                "\n" +
                "The standard procedure of anemia diagnosis is to examine the color distribution of palpebral conjunctiva. Since color perception is not always consistent and ADECT is relying on your device's camera quality, please take this result with a grain of salt and consult with your doctor to further check.\n" +
                "\n" +
                "Machine Learning model is developed by ADECT's team using dataset from https://www.kaggle.com/datasets/guptajanavi/palpebral-conjunctiva-to-detect-anaemia?select=anaemicvsnonanaemic.h5.\n"
    }

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }
}