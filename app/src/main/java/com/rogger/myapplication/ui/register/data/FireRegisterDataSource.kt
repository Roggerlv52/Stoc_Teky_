package com.rogger.myapplication.ui.register.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FireRegisterDataSource : RegisterDataSource {
    override fun createEmail(email: String, callback: RegisterCallback) {

        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods
                    if (signInMethods.isNullOrEmpty()) {
                        // O email ainda não está cadastrado, pode seguir para a próxima etapa
                        callback.onSuccess()
                    } else {
                        // Já existe um método de login para esse email, ou seja, o usuário já foi cadastrado
                        callback.onFailure("Usuário já cadastrado")
                    }
                } else {
                    callback.onFailure(task.exception?.message ?: "Erro interno no servidor")
                }
                callback.onComplete()
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
                                        ?: "Erro interno ao criar documento do usuário"
                                )
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
        callback: RegisterCallback
    ) {

    }
    /*
        override fun create(email: String, name: String, password: String, callback: RegisterCallback) {
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->

                    val uid = result.user?.uid
                    if (uid == null) {
                        callback.onFailure("Erro interno no servidor")
                        // return@addOnSuccessListener
                    } else {
                        FirebaseFirestore.getInstance()
                            .collection("/users")
                            .document(uid)
                            .set(
                                hashMapOf(
                                    "name" to name,
                                    "email" to email,
                                   // "followers" to 0,
                                   // "following" to 0,
                                   // "postCount" to 0,
                                    "uuid" to uid
                                    //"photoUrl" to null
                                )
                            )
                            .addOnSuccessListener {
                                callback.onSuccess()
                            }
                            .addOnFailureListener { exeption ->
                                callback.onFailure(exeption.message ?: "Erro interno")
                            }
                            .addOnCompleteListener {
                                callback.onComplete()
                            }
                    }
                }
                .addOnFailureListener { exeption ->
                    callback.onFailure(exeption.message ?: "Erro interno no servidor")
                }

        }

     */

}