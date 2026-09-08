package org.jetbrains.compose.storytale.gallery.material3

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun GalleryHome(
    appState: StorytaleGalleryAppState,
    drawerState: DrawerState,
    onBrowseComponents: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        GalleryTopAppBar(
            drawerState = drawerState,
            appState = appState,
            isHome = true,
            onToggleSection = onBrowseComponents,
        )
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val viewportHeight = maxHeight

                Column(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Hero(onBrowseComponents, minHeight = viewportHeight)
                    Features()
                    PlatformCallout(onBrowseComponents)
                    Text(
                        text = "Velox Compose Multiplatform",
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 32.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
private fun Hero(onBrowseComponents: () -> Unit, minHeight: Dp) {
    val dotColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.24f)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val step = 32.dp.toPx()
                val radius = 1.2.dp.toPx()
                var x = step / 2f
                while (x < size.width) {
                    var y = step / 2f
                    while (y < size.height) {
                        drawCircle(dotColor, radius, Offset(x, y))
                        y += step
                    }
                    x += step
                }
            },
    ) {
        val isCompact = maxWidth < 600.dp

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = minHeight)
                .padding(
                    horizontal = if (isCompact) 20.dp else 24.dp,
                    vertical = if (isCompact) 48.dp else 72.dp,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.widthIn(max = 800.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(if (isCompact) 20.dp else 24.dp),
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = RoundedCornerShape(percent = 50),
                ) {
                    Text(
                        text = if (isCompact) "Material 3 · Multiplatform" else "Material Design 3",
                        modifier = Modifier.padding(
                            horizontal = if (isCompact) 14.dp else 16.dp,
                            vertical = 8.dp,
                        ),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
                Text(
                    text = if (isCompact) {
                        "Build beautifully.\nShip everywhere."
                    } else {
                        "Build beautiful multiplatform experiences"
                    },
                    style = if (isCompact) {
                        MaterialTheme.typography.headlineLarge
                    } else {
                        MaterialTheme.typography.displayLarge
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = if (isCompact) {
                        "One thoughtful Material 3 design system for Android, iOS, and desktop."
                    } else {
                        "A thoughtful Compose Multiplatform design system for Android, iOS, and desktop—consistent by default and flexible where it matters."
                    },
                    modifier = Modifier.widthIn(max = 660.dp).fillMaxWidth(),
                    style = if (isCompact) {
                        MaterialTheme.typography.bodyLarge
                    } else {
                        MaterialTheme.typography.titleMedium
                    },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Button(
                    onClick = onBrowseComponents,
                    modifier = if (isCompact) {
                        Modifier.fillMaxWidth().padding(top = 4.dp).heightIn(min = 52.dp)
                    } else {
                        Modifier.padding(top = 8.dp).heightIn(min = 52.dp)
                    },
                    shape = RoundedCornerShape(percent = 50),
                    contentPadding = ButtonDefaults.ContentPadding,
                ) {
                    Text("Explore components")
                }
            }
        }
    }
}

@Composable
private fun Features() {
    Column(
        modifier = Modifier.widthIn(max = 1200.dp).fillMaxWidth().padding(horizontal = 24.dp, vertical = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            shape = RoundedCornerShape(percent = 50),
        ) {
            Text(
                text = "WHY VELOX",
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
            )
        }
        Text(
            text = "Built for modern product teams",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "One system for designing, building, and reviewing every interface.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        ) {
            val columns = when {
                maxWidth >= 900.dp -> 3
                maxWidth >= 600.dp -> 2
                else -> 1
            }
            val gap = 20.dp
            val cardWidth = (maxWidth - gap * (columns - 1)) / columns

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = columns,
                horizontalArrangement = Arrangement.spacedBy(gap),
                verticalArrangement = Arrangement.spacedBy(gap),
            ) {
                FeatureCard(
                    marker = "K",
                    title = "Compose Multiplatform",
                    description = "Share a single Kotlin component model while delivering native experiences on every supported platform.",
                    modifier = Modifier.width(cardWidth),
                )
                FeatureCard(
                    marker = "M3",
                    title = "Material Design 3",
                    description = "Modern color, typography, shape, and interaction foundations with first-class light and dark themes.",
                    modifier = Modifier.width(cardWidth),
                )
                FeatureCard(
                    marker = "Aa",
                    title = "Inclusive by design",
                    description = "Theme-aware components with global RTL previews, clear states, and accessible defaults.",
                    modifier = Modifier.width(cardWidth),
                )
            }
        }
    }
}

@Composable
private fun FeatureCard(
    marker: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.heightIn(min = 230.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Surface(
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(marker, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            Text(
                description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun PlatformCallout(onBrowseComponents: () -> Unit) {
    Surface(
        modifier = Modifier.widthIn(max = 1100.dp).fillMaxWidth().padding(horizontal = 24.dp, vertical = 48.dp),
        shape = RoundedCornerShape(40.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text(
                text = "Designed once. Previewed everywhere.",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                listOf("Android", "iOS", "Desktop").forEach { platform ->
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainerHighest,
                        shape = RoundedCornerShape(percent = 50),
                    ) {
                        Text(
                            platform,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
            Button(onClick = onBrowseComponents, shape = RoundedCornerShape(percent = 50)) {
                Text("Browse the catalog")
            }
        }
    }
}

@Composable
internal fun VeloxMark(
    modifier: Modifier = Modifier,
    markSize: Dp = 36.dp,
) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(modifier = modifier.size(markSize)) {
        val hexagon = Path().apply {
            moveTo(size.width * 0.5f, size.height * 0.06f)
            lineTo(size.width * 0.881f, size.height * 0.28f)
            lineTo(size.width * 0.881f, size.height * 0.72f)
            lineTo(size.width * 0.5f, size.height * 0.94f)
            lineTo(size.width * 0.119f, size.height * 0.72f)
            lineTo(size.width * 0.119f, size.height * 0.28f)
            close()
        }
        drawPath(
            path = hexagon,
            color = color,
            style = Stroke(width = size.minDimension * 0.07f, join = StrokeJoin.Round),
        )

        val letter = Path().apply {
            moveTo(size.width * 0.32f, size.height * 0.38f)
            lineTo(size.width * 0.5f, size.height * 0.68f)
            lineTo(size.width * 0.68f, size.height * 0.38f)
        }
        drawPath(
            path = letter,
            color = color,
            style = Stroke(
                width = size.minDimension * 0.09f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            ),
        )
    }
}
