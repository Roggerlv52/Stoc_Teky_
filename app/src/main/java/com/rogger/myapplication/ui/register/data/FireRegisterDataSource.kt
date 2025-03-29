package com.rogger.myapplication.ui.register.data

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

class FireRegisterDataSource : RegisterDataSource {
    override fun createEmail(email: String, callback: RegisterCallback) {

        val fakePassword = "fake123456" // Qualquer senha válida

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, fakePassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Criado só pra verificar — vamos excluir esse usuário agora
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.delete()?.addOnCompleteListener {
                        callback.onSuccess()
                        callback.onComplete()
                    }
                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        callback.onFailure("Usuário já cadastrado")
                    } else {
                        callback.onFailure(exception?.message ?: "Erro interno")
                    }
                    callback.onComplete()
                }
            }
    }

    override fun createNameAndPassword(
        email: String,
        name: String,
        password: String,
        callback: RegisterCallback
    ) {

        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    if (firebaseUser != null) {
                        // Opcional: criar um documento no Firestore para armazenar dados adicionais do usuário
                        val userData = hashMapOf(
                            "name" to name,
                            "email" to email
                        )
                        FirebaseFirestore.getInstance().collection("users")
                            .document(firebaseUser.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                callback.onSuccess()
                            }
                            .addOnFailureListener { exception ->
                                callback.onFailure(
                                    exception.message
                                        ?: "Erro interno ao criar documento do usuário")
                            }
                    } else {
                        callback.onFailure("Erro ao recuperar dados do usuário")
                    }
                } else {
                    callback.onFailure(task.exception?.message ?: "Erro interno no servidor")
                }
                callback.onComplete()
            }

    }

    override fun createSetting(
        name: String,
        pais: String,
        moeda: String,
        comercio: String,
        termos: Boolean,
        callback: RegisterCallback) {
        // Usa o usuário autenticado para atualizar seu documento no Firestore
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            callback.onFailure("Usuário não autenticado")
            callback.onComplete()
            return
        }
        val settingsData = hashMapOf(
            "name" to name,
            "pais" to pais,
            "moeda" to moeda,
            "comercio" to comercio,
            "termos" to termos
        )
        FirebaseFirestore.getInstance().collection("users")
            .document(user.uid)
            .update(settingsData as Map<String, Any>)
            .addOnSuccessListener { callback.onSuccess() }
            .addOnFailureListener { callback.onFailure(it.message ?: "Erro interno ao atualizar configurações") }
            .addOnCompleteListener { callback.onComplete() }
    }

}