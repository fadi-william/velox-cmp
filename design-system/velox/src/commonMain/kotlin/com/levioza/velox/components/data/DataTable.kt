package com.levioza.velox.components.data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Describes a typed column in a [DataTable].
 *
 * @param title Primary title displayed by the component.
 * @param weight Relative width allocated to the column.
 * @param cell Composable used to render a value in this column.
 */
data class DataTableColumn<T>(
    val title: String,
    val weight: Float = 1f,
    val cell: @Composable (T) -> Unit,
)

/**
 * Renders tabular data using typed column definitions.
 *
 * @param rows Rows displayed in the table.
 * @param columns Column definitions used to render each row.
 * @param modifier Modifier applied to the component.
 * @param emptyMessage Message displayed when there are no rows.
 * @param contentPadding Padding applied around the component content.
 */
@Composable
fun <T> DataTable(
    rows: List<T>,
    columns: List<DataTableColumn<T>>,
    modifier: Modifier = Modifier,
    emptyMessage: String = "No data",
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                columns.forEach { column ->
                    Text(
                        text = column.title,
                        modifier = Modifier.weight(column.weight),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
            HorizontalDivider()
            if (rows.isEmpty()) {
                Text(
                    text = emptyMessage,
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                LazyColumn(contentPadding = contentPadding) {
                    items(rows) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            columns.forEach { column ->
                                Box(modifier = Modifier.weight(column.weight)) {
                                    column.cell(row)
                                }
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
