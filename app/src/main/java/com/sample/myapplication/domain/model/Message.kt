package com.sample.myapplication.domain.model

sealed class Message {
    object UnableToCrateNote : Message()
    object UnableToEditNote : Message()
    object UnableToFetchNotes : Message()
}