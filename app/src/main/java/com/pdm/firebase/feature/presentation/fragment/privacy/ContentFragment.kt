package com.pdm.firebase.feature.presentation.fragment.privacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdm.firebase.databinding.FragmentPrivacyBinding
import com.pdm.firebase.feature.domain.model.privacy.Content
import com.pdm.firebase.feature.domain.model.privacy.Paragraph
import com.pdm.firebase.feature.presentation.base.BaseFragment
import com.pdm.firebase.feature.presentation.fragment.privacy.adapter.ContentAdapter
import com.pdm.firebase.util.ARGS

class ContentFragment : BaseFragment() {

    private lateinit var adapter: ContentAdapter
    private lateinit var paragraph: Paragraph

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paragraph = it.getSerializable(ARGS) as Paragraph
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.constraintPrivacy.visibility = View.VISIBLE
        binding.toolBar.titleScreen.text = paragraph.title
        binding.lastUpdate.visibility = View.GONE

        initAdapter(paragraph.content)
    }

    private fun initAdapter(it: List<Content>) {
        adapter = ContentAdapter(
            content = it
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ContentFragment.adapter
        }

        adapter.setOnItemClickListener(object : ContentAdapter.ClickListener {
            override fun onItemClickListener(content: Content) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}