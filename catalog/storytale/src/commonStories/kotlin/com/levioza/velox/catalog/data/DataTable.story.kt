@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.levioza.velox.catalog.data

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levioza.velox.components.data.DataTable
import com.levioza.velox.components.data.DataTableColumn
import org.jetbrains.compose.storytale.story

// Declared at file level: a local class inside the story lambda fails to link on Wasm with
// "Property accessor uses unlinked class symbol", which takes the whole gallery down.
private data class RowData(val name: String, val role: String)

private val People =
    listOf(
        RowData("Ada", "Designer"),
        RowData("Linus", "Engineer"),
        RowData("Grace", "Researcher"),
        RowData("Alan", "Engineer"),
        RowData("Katherine", "Analyst"),
    )

val `DataTable` by
    story(group = "Data") {
        val rowCount by parameter(2)
        val showRole by parameter(true)
        val width by parameter(520)
        val height by parameter(220)

        DataTable(
            People.take(rowCount.coerceIn(0, People.size)),
            buildList {
                add(DataTableColumn<RowData>("Name") { Text(it.name) })
                if (showRole) add(DataTableColumn<RowData>("Role") { Text(it.role) })
            },
            modifier = Modifier.width(width.dp).height(height.dp),
        )
    }
