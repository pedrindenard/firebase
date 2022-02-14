package com.pdm.firebase.feature.presentation.fragment.privacy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.firebase.R
import com.pdm.firebase.databinding.FragmentPrivacyBinding
import com.pdm.firebase.feature.domain.model.privacy.Paragraph
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.privacy.adapter.PrivacyAdapter
import com.pdm.firebase.feature.presentation.fragment.privacy.viewmodel.PrivacyViewModel
import com.pdm.firebase.util.ARGS
import com.pdm.firebase.util.addViews
import com.pdm.firebase.util.startErrorActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class PrivacyFragment : BaseFragment() {

    private lateinit var adapter: PrivacyAdapter

    private val viewModel by viewModel<PrivacyViewModel>()
    private var _binding: FragmentPrivacyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivacyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.getPrivacy()
        showProgressDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.toolbar.setNavigationOnClickListener {
            activity?.finish()
        }

        binding.description.removeAllViews()

        initObservers()
    }

    private fun initObservers() {
        viewModel.successGetPrivacy.observe(viewLifecycleOwner, {
            it?.let {
                initAdapter(it.paragraph)
                binding.toolBar.titleScreen.text = it.title
                binding.constraintPrivacy.visibility = View.VISIBLE
                binding.lastUpdate.text = String.format(
                    format = getString(R.string.last_update),
                    args = listOf(it.lastUpdate).toTypedArray()
                )
                binding.description.addViews(
                    requireContext = requireContext(),
                    description = it.description
                )
            }
            hideProgressDialog()
        })

        viewModel.errorResponse.observe(viewLifecycleOwner, {
            hideProgressDialog()
            initErrorActivity()
        })

        viewModel.failureResponse.observe(viewLifecycleOwner, {
            hideProgressDialog()
            initErrorActivity()
        })
    }

    private fun initAdapter(it: List<Paragraph>) {
        adapter = PrivacyAdapter(
            paragraph = it
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PrivacyFragment.adapter
        }

        adapter.setOnItemClickListener(object : PrivacyAdapter.ClickListener {
            override fun onItemClickListener(paragraph: Paragraph) {
                findNavController().navigate(
                    R.id.contentFragment,
                    Bundle().apply {
                        putSerializable(ARGS, paragraph)
                    },
                    NavOptions.Builder().apply {
                        setEnterAnim(R.anim.fade_in)
                        setExitAnim(R.anim.fade_out)
                        setPopEnterAnim(R.anim.fade_in)
                        setPopExitAnim(R.anim.fade_out)
                    }.build()
                )
            }
        })
    }

    private fun initErrorActivity() {
        activity?.startErrorActivity()
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}