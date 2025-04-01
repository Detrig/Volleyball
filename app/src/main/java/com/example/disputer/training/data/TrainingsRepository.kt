package com.example.disputer.training.data

import com.example.disputer.authentication.data.Training
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

interface TrainingsRepository {

    fun getFutureTrainings() : List<Training>
    fun getPastTrainings() : List<Training>
    fun getAllTrainings() : List<Training>
    fun addTraining(training: Training)
    fun deleteTraining(training: Training)

    class Base(
        private val firebaseAuth: FirebaseAuth,
        private val firestore: FirebaseFirestore
    ) : TrainingsRepository {

        private val trainingList = arrayListOf(
            Training(
                id = "1",
                time = "17:00",
                date = "Пн, 5 июня",
                addressInfo = "Спортивный комплекс 'Триумф', зал волейбола",
                address = "ул. Хабарова, 27, г. Якутск",
                group = "3",
                birthYear = "2010-2012"
            ),
            Training(
                id = "2",
                time = "19:30",
                date = "Вт, 6 июня",
                addressInfo = "СК '50 лет Победы', основной зал",
                address = "ул. Петра Алексеева, 6, г. Якутск",
                coachName = "Алексей",
                group = "2",
                birthYear = "2004-2006"
            ),
            Training(
                id = "3",
                time = "18:00",
                date = "Ср, 7 июня",
                addressInfo = "СВФУ им. М.К. Аммосова, спортивный зал КФЕН",
                address = "ул. Кулаковского, 48, г. Якутск",
                group = "1",
                birthYear = "2012-2014"
            )
        )

        override fun getFutureTrainings(): List<Training> = trainingList
        override fun getPastTrainings(): List<Training> = trainingList
        override fun getAllTrainings(): List<Training> = trainingList

        override fun addTraining(training: Training) {
            trainingList.add(training)
        }

        override fun deleteTraining(training: Training) {
            trainingList.remove(training)
        }

    }
}