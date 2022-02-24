package com.pdm.firebase.feature.domain.usecase

import com.pdm.firebase.feature.domain.usecase.people.GetBestActorsUseCase
import com.pdm.firebase.feature.domain.usecase.people.details.GetPeopleCreditsUseCase
import com.pdm.firebase.feature.domain.usecase.people.details.GetPeopleDetailsUseCase
import com.pdm.firebase.feature.domain.usecase.people.details.GetPeopleImagesUseCase
import com.pdm.firebase.feature.domain.usecase.people.details.GetPeopleTaggedUseCase

data class PeopleUseCase(
    val getPeopleCredits: GetPeopleCreditsUseCase,
    val getPeopleDetails: GetPeopleDetailsUseCase,
    val getPeopleImages: GetPeopleImagesUseCase,
    val getPeopleTagged: GetPeopleTaggedUseCase,
    val getBestActors: GetBestActorsUseCase
)