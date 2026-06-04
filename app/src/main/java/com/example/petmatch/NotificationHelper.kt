package com.example.petmatch

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object NotificationHelper {

    private const val CHANNEL_ID = "petmatch_alerts_v3"

    fun criarCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                CHANNEL_ID,
                "Alertas PetMatch",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificações importantes do PetMatch"
                enableVibration(true)
                enableLights(true)
            }

            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(canal)
        }
    }

    fun mostrarNotificacao(
        context: Context,
        titulo: String,
        mensagem: String,
        id: Int
    ) {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notificacao = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_petmatch_notification)
            .setContentTitle(titulo)
            .setContentText(mensagem)
            .setStyle(NotificationCompat.BigTextStyle().bigText(mensagem))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_STATUS)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(id, notificacao)
    }
}