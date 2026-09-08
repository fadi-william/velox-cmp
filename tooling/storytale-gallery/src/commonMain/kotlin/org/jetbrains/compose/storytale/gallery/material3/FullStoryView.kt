package org.jetbrains.compose.storytale.gallery.material3

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.coroutines.launch
import org.jetbrains.compose.storytale.Story
import org.jetbrains.compose.storytale.gallery.ui.theme.UseCustomDensity
import org.jetbrains.compose.storytale.storiesStorage

private val storyCategories = mapOf(
    "Foundation" to setOf("Badge", "Divider", "ProgressIndicator"),
    "Basic Inputs" to setOf("Button", "Checkbox", "IconButton", "RadioButton", "Switch"),
    "Complex Inputs" to setOf("Autocomplete", "Chip", "Select", "Slider", "TextField"),
    "Actions" to setOf("ExtendedFAB", "FAB", "SegmentedButton"),
    "Containment" to setOf("Card", "List", "Menu", "Tooltip"),
    "Communication" to setOf("Dialog", "Skeleton", "Snackbar"),
    "Sheets" to setOf("BottomSheet", "SideSheet"),
    "Navigation" to setOf("NavigationBar", "NavigationDrawer", "NavigationRail", "Tabs"),
    "Pickers" to setOf("DateField", "DatePicker", "DateRangeField", "TimeField", "TimePicker"),
    "Form" to setOf(
        "FormAutocomplete",
        "FormCheckbox",
        "FormDateField",
        "FormDateRangeField",
        "FormField",
        "FormRadioGroup",
        "FormSelect",
        "FormSlider",
        "FormSwitch",
        "FormTextField",
        "FormTimeField",
    ),
    "Data" to setOf("DataTable"),
    "Surfaces & Search" to setOf(
        "Breadcrumbs",
        "CardGrid",
        "Collapsible",
        "FormSheetScaffold",
        "HorizontalScrollView",
        "LinearGradientSurface",
        "ListView",
        "PullToRefreshScrollView",
        "ScreenHeader",
        "SearchBar",
    ),
)

private fun Story.category(): String = storyCategories.entries
    .firstOrNull { (_, storyNames) -> name in storyNames }
    ?.key
    ?: group

@Composable
fun FullStorytaleGallery(
    appState: StorytaleGalleryAppState,
    navController: NavHostController = rememberNavController(),
    initialStory: Story? = storiesStorage.firstOrNull(),
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val isHome = remember { mutableStateOf(true) }

    val activeStoryItem = remember {
        mutableStateOf(initialStory?.let { StoryListItemType.StoryItem(it) })
    }

    LaunchedEffect(Unit) {
        navController.currentBackStack.collect { backstack ->
            val entry = backstack.lastOrNull()
            val isStory = entry?.destination?.route?.startsWith("story/") == true
            if (entry != null && isStory) {
                val story = entry.toRoute<StoryScreen>()
                activeStoryItem.value = storiesStorage.firstOrNull {
                    it.name == story.storyName
                }?.let {
                    StoryListItemType.StoryItem(it)
                }
            }
        }
    }

    if (isHome.value) {
        GalleryHome(
            appState = appState,
            drawerState = drawerState,
            onBrowseComponents = { isHome.value = false },
        )
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        GalleryTopAppBar(
            appState = appState,
            drawerState = drawerState,
            isHome = false,
            onToggleSection = { isHome.value = true },
        )
        HorizontalDivider()
        Box(modifier = Modifier.weight(1f)) {
            ResponsiveNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent(appState, drawerState, navController, activeStoryItem.value)
                },
                content = {
                    NavHost(
                        navController = navController,
                        startDestination = StoryScreen(activeStoryItem.value?.story?.name ?: ""),
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { ExitTransition.None },
                    ) {
                        composable<StoryScreen> {
                            StoryContent(
                                activeStory = activeStoryItem.value?.story,
                                isRtl = appState.isRtl(),
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun DrawerContent(
    appState: StorytaleGalleryAppState,
    drawerState: DrawerState,
    navController: NavHostController,
    activeStoryItem: StoryListItemType.StoryItem?,
) {
    val filterValue = remember { mutableStateOf("") }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        UseCustomDensity {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = filterValue.value,
                onValueChange = { filterValue.value = it },
                singleLine = true,
                shape = RoundedCornerShape(percent = 50),
                placeholder = { Text("Search stories") },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
            )
        }
    }

    val fullList = remember {
        // Storytale 0.0.5 replaces the supplied group with the story file name.
        // Keep the catalog taxonomy here until upstream preserves the group argument.
        val grouped = storiesStorage.groupBy { it.category() }
grouped.keys.sorted().map { key ->
    StoryListItemType.Group(
        groupLabel = key,
        children = grouped[key]!!
            .sortedBy { it.name }
            .map { StoryListItemType.StoryItem(it) },
    )
}
    }
    val filteredList = remember(filterValue.value) {
        if (filterValue.value.length < 2 || filterValue.value.isBlank()) {
            fullList
        } else {
    fullList.mapNotNull { group ->
        val matchingStories = group.children.filter { item ->
            val story = (item as? StoryListItemType.StoryItem)?.story
            story?.name?.contains(filterValue.value, ignoreCase = true) == true
        }
        if (matchingStories.isEmpty()) {
            null
        } else {
            StoryListItemType.Group(group.groupLabel, matchingStories)
        }
    }
}
    }

    val coroutineScope = rememberCoroutineScope()

    StoryList(
        activeStory = activeStoryItem?.story,
        storyListItems = filteredList,
        expandedGroups = appState.expandedGroups,
        onItemClick = {
            if (it is StoryListItemType.Group) {
                if (appState.expandedGroups.contains(it)) {
                    appState.expandedGroups.remove(it)
                } else {
                    appState.expandedGroups.add(it)
                }
            } else if (it is StoryListItemType.StoryItem) {
                if (activeStoryItem?.story?.name != it.story.name) {
                    navController.navigate(StoryScreen(it.story.name))

                    coroutineScope.launch {
                        drawerState.close()
                    }
                }
            }
        },
    )
}

@Composable
internal fun GalleryTopAppBar(
    drawerState: DrawerState,
    appState: StorytaleGalleryAppState,
    isHome: Boolean,
    onToggleSection: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val currentWindowWidthClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    val isExpanded = currentWindowWidthClass == WindowWidthSizeClass.EXPANDED

    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(64.dp)
                .padding(horizontal = if (isExpanded) 24.dp else 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(!isHome && !isExpanded, enter = fadeIn(), exit = fadeOut()) {
                FilledTonalIconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (drawerState.isOpen) drawerState.close() else drawerState.open()
                        }
                    },
                ) {
                    val icon = if (drawerState.isOpen || drawerState.currentOffset > -200f) {
                        MenuOpenImageVector
                    } else {
                        Icons.Default.Menu
                    }
                    AnimatedContent(
                        targetState = icon,
                        transitionSpec = { scaleIn().togetherWith(scaleOut()) },
                    ) {
                        Icon(imageVector = icon, contentDescription = "Open component navigation")
                    }
                }
            }

            Row(
                modifier = Modifier.padding(start = if (!isHome && !isExpanded) 8.dp else 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(if (isExpanded) 10.dp else 8.dp),
            ) {
                VeloxMark(markSize = if (isExpanded) 36.dp else 32.dp)
                if (isExpanded || isHome) {
                    Text(
                        text = if (isExpanded) "Velox CMP" else "Velox",
                        style = if (isExpanded) {
                            MaterialTheme.typography.titleMedium
                        } else {
                            MaterialTheme.typography.labelLarge
                        },
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (isExpanded) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    GalleryNavigationButton(
                        label = "Home",
                        selected = isHome,
                        onClick = { if (!isHome) onToggleSection() },
                    )
                    GalleryNavigationButton(
                        label = "Components",
                        selected = !isHome,
                        onClick = { if (isHome) onToggleSection() },
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            } else {
                GalleryNavigationButton(
                    label = if (isHome) "Components" else "Home",
                    selected = false,
                    onClick = onToggleSection,
                )
            }

            ThemeSwitcherIconButton(appState)
            if (!isHome) {
                LayoutDirectionSwitcher(appState)
            }
        }
    }
}

@Composable
private fun GalleryNavigationButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        color = if (selected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer,
        contentColor = if (selected) {
            MaterialTheme.colorScheme.onSecondaryContainer
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        shape = RoundedCornerShape(percent = 50),
        onClick = onClick,
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
internal fun LayoutDirectionSwitcher(appState: StorytaleGalleryAppState) {
    TextButton(onClick = {
        appState.switchLayoutDirection(!appState.isRtl())
    }) {
        Text(if (appState.isRtl()) "LTR" else "RTL")
    }
}

@Composable
internal fun ThemeSwitcherIconButton(appState: StorytaleGalleryAppState) {
    IconButton(onClick = {
        appState.switchTheme(!appState.isDarkTheme())
    }) {
        AnimatedContent(targetState = appState.isDarkTheme()) { isDarkTheme ->
            if (isDarkTheme) {
                Icon(imageVector = Light_mode, contentDescription = null)
            } else {
                Icon(imageVector = Dark_mode, contentDescription = null)
            }
        }
    }
}
