package itmo.nick.nickfolio.ui.offer_description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import itmo.nick.nickfolio.R

class OfferDescriptionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val offerName = arguments?.getString("offerName")

        // Устанавливаем заголовок фрагмента
        (activity as AppCompatActivity).supportActionBar?.title = offerName
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offer_description, container, false)
    }

}