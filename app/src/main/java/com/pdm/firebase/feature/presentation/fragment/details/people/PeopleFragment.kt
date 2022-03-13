package com.pdm.firebase.feature.presentation.fragment.details.people

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentPeopleBinding
import com.pdm.firebase.feature.domain.model.credit.people.PeopleCredits
import com.pdm.firebase.feature.domain.model.credit.people.PeopleCreditsResponse
import com.pdm.firebase.feature.domain.model.image.Image
import com.pdm.firebase.feature.domain.model.people.People
import com.pdm.firebase.feature.domain.model.people.details.PeopleDetailsResponse
import com.pdm.firebase.feature.domain.model.tagged.Tagged
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.details.movie.adapter.ImageAdapter
import com.pdm.firebase.feature.presentation.fragment.details.people.adapter.CreditsAdapter
import com.pdm.firebase.feature.presentation.fragment.details.people.adapter.PeopleAdapter
import com.pdm.firebase.feature.presentation.fragment.details.people.adapter.TaggedAdapter
import com.pdm.firebase.feature.presentation.fragment.details.people.viewmodel.PeopleViewModel
import com.pdm.firebase.util.ARGS
import com.pdm.firebase.util.delimiterDate
import com.pdm.firebase.util.formatKnowAs
import com.pdm.firebase.util.setOnSingleClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class PeopleFragment : BaseFragment() {

    private val viewModel by viewModel<PeopleViewModel>()
    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!
    private var peopleId: Int? = null
    private var timer: Timer? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            peopleId = it.getSerializable(ARGS) as Int
            viewModel.initViewModel(
                id = peopleId!!
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initActions()
    }

    private fun initActions() {
        binding.seeMore.setOnSingleClickListener {

        }
    }

    private fun initObservers() {
        viewModel.getPeopleDetails.observe(viewLifecycleOwner, {
            binding.namePeople.text = it.name
            binding.originalTitleMovie.text = it.knowByDepartment
            binding.ratingPeople.text = it.popularity.toString()
            initPeopleDetails(it)
        })

        viewModel.getPeopleCredits.observe(viewLifecycleOwner, { it ->
            it.initPeopleCredits().takeIf { !it.isNullOrEmpty() }?.let {
                binding.knowForRecyclerView.apply {
                    adapter = CreditsAdapter(it).apply {
                        setOnItemClickListener(object : CreditsAdapter.ClickListener {
                            override fun onItemClickListener(credits: PeopleCredits) {

                            }
                        })
                    }
                }
            } ?: run {
                binding.knowForConstraint.visibility = View.GONE
            }
        })

        viewModel.getPeopleTagged.observe(viewLifecycleOwner, { it ->
            it.results.takeIf { !it.isNullOrEmpty() }?.let {
                binding.taggedRecyclerView.apply {
                    adapter = TaggedAdapter().apply {
                        updateAdapter(mutableList = it)
                        setOnItemClickListener(object : TaggedAdapter.ClickListener {
                            override fun onItemClickListener(tagged: Tagged) {

                            }
                        })
                    }
                }
            } ?: run { viewModel.getBestActors()
                binding.taggedLabel.text = getString(R.string.popular_actors)
            }
        })

        viewModel.getBestActors.observe(viewLifecycleOwner, { it ->
            it.results.initPeopleAdapter().takeIf { !it.isNullOrEmpty() }?.let {
                binding.taggedRecyclerView.apply {
                    adapter = PeopleAdapter().apply {
                        updateAdapter(mutableList = it)
                        setOnItemClickListener(object : PeopleAdapter.ClickListener {
                            override fun onItemClickListener(people: People) {

                            }
                        })
                    }
                }
            } ?: run {
                binding.taggedConstraint.visibility = View.GONE
            }
        })

        viewModel.getPeopleImages.observe(viewLifecycleOwner, { it ->
            it.profiles.takeIf { !it.isNullOrEmpty() }?.let {
                binding.peopleProfile.apply {
                    initAutomaticSlide(it = it)
                    orientation = ViewPager2.ORIENTATION_HORIZONTAL
                    adapter = ImageAdapter(it).apply {
                        setOnItemClickListener(object : ImageAdapter.ClickListener {
                            override fun onItemClickListener(image: Image) {

                            }
                        })
                    }
                }
            } ?: run {
                /** Do nothing **/
            }
        })

        viewModel.onSuccess.observe(viewLifecycleOwner, {

        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {

        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {

        })

        viewModel.invalidAuth.observe(viewLifecycleOwner, {

        })
    }

    private fun initPeopleDetails(it: PeopleDetailsResponse) {
        binding.birthDatePeople.apply {
            it.birthday.takeIf { !it.isNullOrEmpty() }?.let { text = it.delimiterDate() } ?: run {
                visibility = View.GONE
            }
        }
        binding.premiereMovie.apply {
            it.knowAs.takeIf { !it.isNullOrEmpty() }?.let { text = it.formatKnowAs() } ?: run {
                binding.premiereLabel.visibility = View.GONE
                visibility = View.GONE
            }
        }
        binding.typeMovie.apply {
            it.birthDatePlace.takeIf { !it.isNullOrEmpty() }?.let { text = it } ?: run {
                binding.createdByLabel.visibility = View.GONE
                visibility = View.GONE
            }
        }
        binding.storylineMovie.apply {
            it.overview.takeIf { !it.isNullOrEmpty() }?.let { text = it } ?: run {
                binding.storyline.visibility = View.GONE
                visibility = View.GONE
            }
        }
    }

    private fun PeopleCreditsResponse.initPeopleCredits(): MutableList<PeopleCredits> {
        val mutableList: MutableList<PeopleCredits> = mutableListOf()
        mutableList.addAll(cast)
        mutableList.addAll(crew)
        return mutableList
    }

    private fun MutableList<People>.initPeopleAdapter(): MutableList<People> {
        var people: People? = null; forEach {
            if (it.id == peopleId) {
                people = it
            }
        }
        this.remove(element = people)
        return this
    }

    private fun ViewPager2.initAutomaticSlide(it: List<Image>) {
        val timerTask = object : TimerTask() {
            override fun run() {
                this@initAutomaticSlide.post {
                    this@initAutomaticSlide.currentItem = (
                        this@initAutomaticSlide.currentItem + 1
                    ) % it.size
                }
            }
        }; stopBanner()
        timer = Timer().apply {
            schedule(
                timerTask, 2000, 3000
            )
        }
    }

    private fun stopBanner() {
        timer?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}