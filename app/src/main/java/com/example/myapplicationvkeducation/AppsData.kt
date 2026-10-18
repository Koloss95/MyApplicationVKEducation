package com.example.myapplicationvkeducation

val sampleApps = listOf(
    AppInfo(
        id = 1,
        name = "Заметки",
        description = "Сохраняйте идеи и составляйте списки дел",
        category = "Инструменты",
        iconRes = R.drawable.ic_notes
    ),
    AppInfo(
        id = 2,
        name = "Музыка",
        description = "Любимые песни и подборки на каждый день",
        category = "Музыка и аудио",
        iconRes = R.drawable.ic_music
    ),
    AppInfo(
        id = 3,
        name = "Тренировки",
        description = "Записывайте упражнения и следите за прогрессом",
        category = "Здоровье и фитнес",
        iconRes = R.drawable.ic_workout
    ),
    AppInfo(
        id = 4,
        name = "Погода",
        description = "Прогноз температуры и осадков в вашем городе",
        category = "Погода",
        iconRes = R.drawable.ic_weather
    ),
    AppInfo(
        id = 5,
        name = "Книги",
        description = "Читайте любимые произведения и сохраняйте закладки",
        category = "Книги",
        iconRes = R.drawable.ic_books
    ),
    AppInfo(
        id = 6,
        name = "Рецепты",
        description = "Находите новые блюда для домашних обедов",
        category = "Еда и напитки",
        iconRes = R.drawable.ic_recipes
    ),AppInfo(
        id = 7,
        name = "Планировщик",
        description = "Планируйте дела и отмечайте выполненные задачи",
        category = "Продуктивность",
        iconRes = R.drawable.ic_notes
    ),
    AppInfo(
        id = 8,
        name = "Медитация",
        description = "Делайте паузы и возвращайте концентрацию",
        category = "Здоровье и фитнес",
        iconRes = R.drawable.ic_workout
    ),
    AppInfo(
        id = 9,
        name = "Аудиокниги",
        description = "Слушайте любимые истории в удобное время",
        category = "Книги",
        iconRes = R.drawable.ic_books
    )
)

fun findAppById(appId: Int): AppInfo? {
    return sampleApps.find { app -> app.id == appId }
}