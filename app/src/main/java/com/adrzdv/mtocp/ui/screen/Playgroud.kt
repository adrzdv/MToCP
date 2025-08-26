@file:Suppress("LongMethod", "FunctionNaming")

// ----- Redesigned Main Menu in Jetpack Compose (Material 3) -----
// Цели редизайна:
// 1) Современный вид (MD3), воздушные отступы, крупная типографика.
// 2) "Карточки"-кнопки 2×3 с чёткой иерархией и разными иконками.
// 3) Убрать кнопку "Выход" (необязательно), но оставить возможность включить её параметром.
// 4) Доступность: достаточный контраст, понятные подписи, большие hit-targets (минимум 48dp).
// 5) Лёгкая конфигурируемость: список пунктов меню передаётся данными.

package com.example.mainmenu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.* // Импорты layout-компонентов для отступов/контейнеров
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.* // Используем Material3 для современного стиля
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adrzdv.mtocp.ui.theme.AppTypography

// --- Модель одного пункта меню ---
data class MenuAction(
    val title: String, // Текст под иконкой
    val icon: ImageVector, // Иконка, соответствующая действию
    val onClick: () -> Unit // Обработчик нажатия; передаём извне, чтобы не тянуть ViewModel сюда
)

// --- Главный экран меню ---
@OptIn(ExperimentalFoundationApi::class) // Для LazyVerticalGrid API
@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier, // Позволяет родителю задавать размеры/отступы
    showExit: Boolean = true, // Флаг, чтобы по желанию показать кнопку "Выход"
    appVersion: String? = null, // Версия, если хотим отобразить внизу (например, в бете)
    onStartCheck: () -> Unit, // Коллбеки действий – инжектим из VM/навигатора
    onReference: () -> Unit,
    onSettings: () -> Unit,
    onWebRequests: () -> Unit,
    onHelp: () -> Unit,
    onExit: () -> Unit = {} // По умолчанию пусто, если showExit=false
) {
    // Собираем динамический список пунктов
    val items = buildList {
        add(MenuAction("Начать проверку", Icons.Outlined.PlaylistAdd) { onStartCheck() }) // Ясная метафора "добавить задачу/проверку"
        add(MenuAction("Справочник", Icons.Outlined.List) { onReference() }) // Список для справочника
        add(MenuAction("Настройки", Icons.Outlined.Build) { onSettings() }) // Гаечный ключ заменяем на Build/Settings
        if (showExit) add(MenuAction("Выход", Icons.Outlined.Logout) { onExit() }) // Опционально добавляем "Выход"
        add(MenuAction("WEB‑запросы", Icons.Outlined.Public) { onWebRequests() }) // Глобус – сеть/веб
        add(MenuAction("Помощь", Icons.Outlined.Help) { onHelp() }) // Вопрос – помощь
    }

    // Корневая колонка для заголовка, грида и футера
    Column(
        modifier = modifier
            .fillMaxSize() // Экран на всю высоту/ширину
            .padding(horizontal = 20.dp, vertical = 16.dp), // Боковые поля для "воздуха"
        verticalArrangement = Arrangement.Top, // Элементы идут сверху вниз
        horizontalAlignment = Alignment.CenterHorizontally // Центруем грид относительно экрана
    ) {
        // Заголовок экрана
        Text(
            text = "Выберите действие", // Более конкретно, чем "Главное меню"
            style = MaterialTheme.typography.headlineMedium, // Крупный, но не кричащий заголовок
            color = MaterialTheme.colorScheme.onBackground, // Корректный контраст с фоном темы
            modifier = Modifier
                .fillMaxWidth() // Растягиваем, чтобы текст занимал всю ширину
                .padding(top = 8.dp, bottom = 16.dp) // Отступы сверху/снизу
        )

        // Сам грид 2 колонки, адаптивная высота
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Задаём фиксированно 2 колонки, как на твоём макете
            contentPadding = PaddingValues(4.dp), // Внутренние отступы для аккуратного края
            verticalArrangement = Arrangement.spacedBy(16.dp), // Вертикальный интервал между рядами
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Горизонтальные интервалы между колонками
            modifier = Modifier
                .fillMaxWidth() // Пусть грид занимает доступную ширину
                .weight(1f) // Занимает всё доступное пространство между заголовком и футером
        ) {
            // Рендерим карточки по списку
            items(items) { item ->
                MenuCard(
                    title = item.title, // Подпись
                    icon = item.icon, // Иконка
                    onClick = item.onClick, // Обработчик
                    modifier = Modifier
                        .fillMaxWidth() // Карточка растягивается по доступной ширине ячейки
                        .aspectRatio(1.15f) // Пропорции, близкие к макету (чуть выше ширины)
                )
            }
        }

        // Футер с версией приложения (необязателен, удобно в бете)
        if (!appVersion.isNullOrBlank()) {
            Text(
                text = appVersion!!, // Печатаем версию, как на твоём скрине
                style = MaterialTheme.typography.bodySmall, // Ненавязчивый мелкий текст
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Более спокойный цвет
                textAlign = TextAlign.Center, // По центру
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

// --- Отдельная карточка пункта меню ---
@Composable
private fun MenuCard(
    title: String, // Подпись под иконкой
    icon: ImageVector, // Иконка пункта
    onClick: () -> Unit, // Обработчик нажатия
    modifier: Modifier = Modifier // Возможность задать размер/позицию извне
) {
    // Используем ElevatedCard из MD3: мягкая тень + большие скругления (современный вид)
    ElevatedCard(
        onClick = onClick, // Вся карточка кликабельна, hit-target > 48dp
        shape = MaterialTheme.shapes.extraLarge, // Большой радиус (по гайдлайнам – 24dp+)
        modifier = modifier
            .heightIn(min = 96.dp), // Минимальная высота для удобного касания
    ) {
        // Контент карточки – вертикальная колонка с центровкой
        Column(
            modifier = Modifier
                .fillMaxSize() // Занимаем всю карточку
                .padding(20.dp), // Внутренние поля → дыхание и баланс иконки и текста
            horizontalAlignment = Alignment.CenterHorizontally, // Центруем по горизонтали
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically) // Ровная дистанция между иконкой и текстом
        ) {
            // Иконка действия
            Icon(
                imageVector = icon, // Переданная иконка
                contentDescription = title, // Для TalkBack – произносится как название действия
                tint = MaterialTheme.colorScheme.onSurface, // Контрастный цвет иконки
                modifier = Modifier.size(42.dp) // Достаточный размер для читаемости
            )
            // Подпись
            Text(
                text = title, // Название действия
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp), // Чуть крупнее для лучшей читабельности
                color = MaterialTheme.colorScheme.onSurface, // Контрастный цвет текста
                textAlign = TextAlign.Center, // Центрируем, чтобы длинные подписи переносились красиво
                lineHeight = 20.sp // Плотный, но читабельный межстрочный интервал
            )
        }
    }
}

// --- Превью для быстрой проверки в IDE ---
@Preview(showBackground = true, name = "MainMenu — Light")
@Composable
private fun PreviewMainMenuLight() {
    // Используем материал-тему по умолчанию; в проекте подставишь свою AppTheme
    MaterialTheme(
        typography = AppTypography
    ) {
        MainMenuScreen(
            onStartCheck = {}, // Стабовые коллбеки, чтобы превью не падало
            onReference = {},
            onSettings = {},
            onWebRequests = {},
            onHelp = {},
            onExit = {},
            showExit = false, // В превью скрываем "Выход" согласно рекомендациям
            appVersion = "0.9.1-beta-hf:21082025" // Пример строки версии
        )
    }
}

@Preview(showBackground = true, name = "MainMenu — Dark", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewMainMenuDark() {
    MaterialTheme {
        MainMenuScreen(
            onStartCheck = {},
            onReference = {},
            onSettings = {},
            onWebRequests = {},
            onHelp = {},
            onExit = {},
            showExit = true, // Во второй превью показываем, как выглядит с "Выход"
            appVersion = null // Без версии в футере
        )
    }
}
