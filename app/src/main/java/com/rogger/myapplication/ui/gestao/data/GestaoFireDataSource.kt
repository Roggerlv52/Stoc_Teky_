package com.rogger.myapplication.ui.gestao.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rogger.myapplication.ui.setting.data.SettingCallback
import com.rogger.myapplication.ui.setting.data.SettingDataSource

class GestaoFireDataSource: SettingDataSource {
    override fun deleteAccount(callback: SettingCallback) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            callback.onFailure("Usuário não autenticado")
            callback.onComplete()
            return
        }

        // Primeiro, deleta os dados do usuário no Firestore 
        FirebaseFirestore.getInstance().collection("/users")
            .document(user.uid)
            .delete()
            .addOnSuccessListener {
                // Após deletar os dados, deleta a conta do FirebaseAuth
                user.delete()
                    .addOnSuccessListener {
                        callback.onSuccess()
                    }
                    .addOnFailureListener { exception ->
                        callback.onFailure(exception.message ?: "Erro ao deletar conta do usuário")
                    }
                    .addOnCompleteListener {
                        callback.onComplete()
                    }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro ao deletar dados do usuário")
                callback.onComplete()
            }
    }
}
