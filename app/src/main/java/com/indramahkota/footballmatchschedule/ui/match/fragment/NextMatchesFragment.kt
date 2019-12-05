package com.indramahkota.footballmatchschedule.ui.match.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.ui.detail.MatchDetailsActivity
import com.indramahkota.footballmatchschedule.ui.detail.MatchDetailsActivity.Companion.PARCELABLE_MATCH_DATA
import com.indramahkota.footballmatchschedule.ui.match.adapter.MatchAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.content_match_tab.*
import org.jetbrains.anko.support.v4.intentFor

class NextMatchesFragment : Fragment() {
    companion object {
        private const val ARG_SECTION_TITLE = "section_title"

        @JvmStatic
        fun newInstance(title: String): NextMatchesFragment {
            return NextMatchesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SECTION_TITLE, title)
                }
            }
        }
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var matchAdapter: MatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.content_match_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutManager = LinearLayoutManager(view.context)
        rv_category.layoutManager = linearLayoutManager
        rv_category.setHasFixedSize(true)

        matchAdapter = MatchAdapter(null){ matchDetailsApiModel ->
            startActivity(intentFor<MatchDetailsActivity>(PARCELABLE_MATCH_DATA to matchDetailsApiModel))
        }
        rv_category.adapter = matchAdapter
    }
}