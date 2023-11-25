package com.example.epicsys.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.epicsys.R
import com.example.epicsys.databinding.FragmentDetailsBinding
import com.example.epicsys.domain.model.AirlineItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewmodel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val airline = arguments?.getParcelable<AirlineItem>("airline")


        binding.apply {
            Glide.with(requireContext())
                .load(R.drawable.logo)
                .into(airlinePhoto)

            airlineName.text = airline?.name
            airlinePhone.text =
                if (airline!!.phone.isEmpty()) "+41 31 960 22 33" else " ${airline?.phone}"
            airlineSite.text = "Visit us: ${airline?.site}"

            // Saving airline into the db
            favButton.setOnClickListener {
                viewmodel.upsertAirlineDb(airline)
                Toast.makeText(requireContext(), "Added to favorite", Toast.LENGTH_SHORT).show()
            }

            // Open website in external browser
            airlineSite.setOnClickListener {
                val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(airline?.site))
                startActivity(websiteIntent)
            }

            // Launch dialer with pre-filled phone number
            airlinePhone.setOnClickListener {
                if (airline!!.phone.isNotEmpty()) {
                    val dialIntent = Intent(Intent.ACTION_DIAL)
                    dialIntent.data = Uri.parse("tel:${airline?.phone}")
                    startActivity(dialIntent)
                }else {
                    val dialIntent = Intent(Intent.ACTION_DIAL)
                    dialIntent.data = Uri.parse("tel:${"+41 31 960 22 33"}")
                    startActivity(dialIntent)
                }
            }
        }

    }
}

