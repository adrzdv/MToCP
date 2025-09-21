// Импорты — каждый отвечает за конкретную функциональность в Compose и Material3.
import android.os.Bundle // <- нужен, если будешь использовать Activity (приведён пример ниже)
import androidx.activity.ComponentActivity // <- базовая Activity для Compose
import androidx.activity.compose.setContent // <- устанавливает Compose UI в Activity
import androidx.compose.foundation.clickable // <- делает элемент кликабельным
import androidx.compose.foundation.layout.* // <- Row/Column/Spacer и модификаторы расположения
import androidx.compose.foundation.lazy.LazyColumn // <- ленивый список, как RecyclerView
import androidx.compose.material.icons.Icons // <- корневой объект для иконок
import androidx.compose.material.icons.filled.* // <- стандартные иконки (Info, Lock, ArrowBack и т.д.)
import androidx.compose.material3.* // <- компоненты Material3: Scaffold, TopAppBar, Switch, AlertDialog и т.д.
import androidx.compose.runtime.* // <- состояние: remember, mutableStateOf и т.п.
import androidx.compose.ui.Alignment // <- для вертикального/горизонтального выравнивания
import androidx.compose.ui.Modifier // <- модификатор для composable
import androidx.compose.ui.graphics.vector.ImageVector // <- тип для иконок
import androidx.compose.ui.tooling.preview.Preview // <- аннотация для превью в Android Studio
import androidx.compose.ui.unit.dp // <- dp единицы измерения

// ----------------- Главный экран настроек -----------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit // <- коллбэк для кнопки "назад" (Activity/навигатор передаёт finish() или navBack())
) {
    // локальное состояние переключателя уведомлений (можно заменить на ViewModel)
    var notificationsEnabled by remember { mutableStateOf(true) }

    // локальное состояние выбранного пункта (для примера RadioPreference)
    var selectedOptionIndex by remember { mutableStateOf(0) }

    // Scaffold — базовый макет с topBar, snackbarHost, floatingActionButton и т.д.
    Scaffold(
        topBar = {
            // Верхняя панель с заголовком и кнопкой назад
            TopAppBar(
                title = { Text("Настройки") }, // <- простой текст заголовка
                navigationIcon = {
                    // Кнопка навигации слева
                    IconButton(onClick = onBackClick) {
                        // Иконка стрелки назад
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // LazyColumn — имитация системного списка настроек (экономит память при большом количестве элементов)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize() // занимать весь доступный размер
                .padding(innerPadding) // учитывать внутренние отступы Scaffold
        ) {
            // Категория — "Общие"
            item {
                // Заголовок категории — простой Text с серыми цветами по дизайну
                Text(
                    text = "Общие", // <- в реальном приложении лучше stringResource()
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Элемент: О приложении
            item {
                SettingsRow(
                    icon = Icons.Default.Info, // <- иконка слева
                    title = "О приложении", // <- основной текст
                    subtitle = "Версия 1.0.0", // <- дополнительный текст справа / снизу
                    onClick = { /* открыть экран "О приложении" */ }
                )
            }

            // Элемент: переключатель уведомлений
            item {
                SwitchRow(
                    icon = Icons.Default.Notifications, // <- иконка уведомлений
                    title = "Уведомления", // <- текст настройки
                    initialChecked = notificationsEnabled, // <- текущее значение
                    onCheckedChange = { checked ->
                        // сохраняем новое состояние (в реальном приложении — в ViewModel / DataStore)
                        notificationsEnabled = checked
                    }
                )
            }

            // Ещё одна категория — "Приватность"
            item {
                Text(
                    text = "Приватность",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Элемент: пароль (строка со стрелкой)
            item {
                SettingsRow(
                    icon = Icons.Default.Lock,
                    title = "Пароль",
                    subtitle = "Изменить пароль",
                    onClick = { /* открыть экран смены пароля */ }
                )
            }

            // Элемент: выбор из списка (RadioPreference пример)
            item {
                RadioPreference(
                    icon = Icons.Default.Info,
                    title = "Выбор варианта",
                    options = listOf("Вариант 1", "Вариант 2", "Вариант 3"),
                    selectedIndex = selectedOptionIndex,
                    onSelect = { index ->
                        // обновляем выбранный индекс
                        selectedOptionIndex = index
                    }
                )
            }
        }
    }
}

// ----------------- Компонента строки настройки -----------------
@Composable
fun SettingsRow(
    icon: ImageVector, // <- иконка слева
    title: String, // <- заголовок
    subtitle: String? = null, // <- опциональный подзаголовок
    onClick: () -> Unit // <- действие при клике по строке
) {
    // Row — горизонтальное расположение: иконка | текст | стрелка
    Row(
        modifier = Modifier
            .fillMaxWidth() // строка во всю ширину
            .clickable { onClick() } // делаем всю строку кликабельной
            .padding(16.dp), // внутренний padding
        verticalAlignment = Alignment.CenterVertically // выравнивание по центру вертикально
    ) {
        // Иконка слева
        Icon(
            imageVector = icon,
            contentDescription = null // если иконка декоративная, contentDescription = null
        )

        // Отступ между иконкой и текстом
        Spacer(modifier = Modifier.width(16.dp))

        // Column для заголовка и подзаголовка; weight(1f) занимает все оставшееся пространство
        Column(modifier = Modifier.weight(1f)) {
            // Основной текст
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )

            // Подзаголовок (если есть) — показываем меньшим и серым
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Иконка-стрелочка справа для индикации навигации
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null
        )
    }
}

// ----------------- Компонента строки с переключателем -----------------
@Composable
fun SwitchRow(
    icon: ImageVector, // <- иконка слева
    title: String, // <- текст настройки
    initialChecked: Boolean, // <- начальное состояние переключателя
    onCheckedChange: (Boolean) -> Unit // <- обработчик изменения значения
) {
    // Локальное состояние для переключателя (внешнее состояние синхронизируется через onCheckedChange)
    var checked by remember { mutableStateOf(initialChecked) }

    // Горизонтальная строка: иконка | текст | Switch
    Row(
        modifier = Modifier
            .fillMaxWidth() // строка во всю ширину
            .padding(16.dp), // внутренний padding
        verticalAlignment = Alignment.CenterVertically // выравнивание по центру
    ) {
        // Иконка слева
        Icon(imageVector = icon, contentDescription = null)

        // Отступ
        Spacer(modifier = Modifier.width(16.dp))

        // Текст занимает оставшееся пространство
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        // Сам Switch — когда пользователь переключает, мы обновляем локальное состояние
        // и отсылаем событие наружу через onCheckedChange для сохранения
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it // обновляем локально
                onCheckedChange(it) // сигнализируем внешнему слою (ViewModel / репозиторий)
            }
        )
    }
}

// ----------------- Компонента выбора варианта (аналог ListPreference) -----------------
@Composable
fun RadioPreference(
    icon: ImageVector, // <- иконка слева
    title: String, // <- заголовок настройки
    options: List<String>, // <- варианты выбора
    selectedIndex: Int, // <- индекс текущего выбранного варианта
    onSelect: (Int) -> Unit // <- коллбэк при выборе варианта
) {
    // Флаг, показывать ли диалог выбора
    var showDialog by remember { mutableStateOf(false) }

    // Строка, которая показывает текущий выбранный вариант и открывает диалог по клику
    Row(
        modifier = Modifier
            .fillMaxWidth() // во всю ширину
            .clickable { showDialog = true } // открываем диалог по клику
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Иконка слева
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))

        // Заголовок и текущий вариант
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = options.getOrNull(selectedIndex) ?: "", // показываем выбранный текст (безопасно)
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Стрелочка справа
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
    }

    // Диалог с радио-кнопками (появляется при showDialog = true)
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // закрыть при тапе вне диалога
            confirmButton = {
                // Кнопка OK просто закрывает диалог (выбор уже обработан при клике по строке с RadioButton)
                TextButton(onClick = { showDialog = false }) {
                    Text("ОК")
                }
            },
            dismissButton = {
                // Кнопка Отмена закрывает диалог
                TextButton(onClick = { showDialog = false }) {
                    Text("Отмена")
                }
            },
            title = { Text(title) }, // заголовок диалога
            text = {
                // содержимое — список опций с RadioButton
                Column {
                    options.forEachIndexed { index, option ->
                        // каждая строка кликабельна — при клике выбирается опция и диалог закрывается
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelect(index) // сообщаем выбор наружу
                                    showDialog = false // закрываем диалог
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = index == selectedIndex,
                                onClick = {
                                    onSelect(index) // тоже поддерживаем выбор через сам RadioButton
                                    showDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(option) // текст варианта
                        }
                    }
                }
            }
        )
    }
}

// ----------------- Preview для Android Studio -----------------
@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    // Превью вызывает экран с пустой логикой кнопки назад
    SettingsScreen(onBackClick = {})
}