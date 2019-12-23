package com.indramahkota.footballapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.data.source.locale.entity.LeagueEntity
import dagger.android.support.AndroidSupportInjection

class ClassementFragment : Fragment() {
    companion object {
        private const val ARG_SECTION_FRAGMENT = "section_fragment"
        private const val ARG_SAVE_DATA = "save_data"

        @JvmStatic
        fun newInstance(fragment: String) = ClassementFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SECTION_FRAGMENT, fragment)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val fake = arrayListOf<LeagueEntity>()
        outState.putParcelableArrayList(ARG_SAVE_DATA, fake)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_classement, container, false)
    }
}
