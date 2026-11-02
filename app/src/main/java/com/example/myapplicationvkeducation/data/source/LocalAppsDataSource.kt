package com.example.myapplicationvkeducation.data.source

import com.example.myapplicationvkeducation.data.dto.AppDto

class LocalAppsDataSource {

    private val apps = listOf(
        AppDto(
            id = 1,
            name = "Заметки",
            description = "Сохраняйте идеи и составляйте списки дел",
            category = "Инструменты",
            iconKey = "notes"
        ),
        AppDto(
            id = 2,
            name = "Музыка",
            description = "Любимые песни и подборки на каждый день",
            category = "Музыка и аудио",
            iconKey = "music"
        ),
        AppDto(
            id = 3,
            name = "Тренировки",
            description = "Записывайте упражнения и следите за прогрессом",
            category = "Здоровье и фитнес",
            iconKey = "workout"
        ),
        AppDto(
            id = 4,
            name = "Погода",
            description = "Прогноз температуры и осадков в вашем городе",
            category = "Погода",
            iconKey = "weather"
        ),
        AppDto(
            id = 5,
            name = "Книги",
            description = "Читайте любимые произведения и сохраняйте закладки",
            category = "Книги",
            iconKey = "books"
        ),
        AppDto(
            id = 6,
            name = "Рецепты",
            description = "Находите новые блюда для домашних обедов",
            category = "Еда и напитки",
            iconKey = "recipes"
        ),
        AppDto(
            id = 7,
            name = "Планировщик",
            description = "Планируйте дела и отмечайте выполненные задачи",
            category = "Продуктивность",
            iconKey = "notes"
        ),
        AppDto(
            id = 8,
            name = "Медитация",
            description = "Делайте паузы и возвращайте концентрацию",
            category = "Здоровье и фитнес",
            iconKey = "workout"
        ),
        AppDto(
            id = 9,
            name = "Аудиокниги",
            description = "Слушайте любимые истории в удобное время",
            category = "Книги",
            iconKey = "books"
        )
    )

    fun getApps(): List<AppDto> = apps
}