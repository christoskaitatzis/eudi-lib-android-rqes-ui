/*
 * Copyright (c) 2023 European Commission
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by the European
 * Commission - subsequent versions of the EUPL (the "Licence"); You may not use this work
 * except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either express or implied. See the Licence for the specific language
 * governing permissions and limitations under the Licence.
 */

package eu.europa.ec.eudi.rqesui.presentation.ui.component.viewer

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.europa.ec.eudi.rqesui.domain.extension.toUri
import eu.europa.ec.eudi.rqesui.infrastructure.config.data.DocumentFileType
import eu.europa.ec.eudi.rqesui.presentation.extension.readTextFromUri
import eu.europa.ec.eudi.rqesui.presentation.ui.component.utils.SPACING_MEDIUM
import eu.europa.ec.eudi.rqesui.presentation.ui.util.formatJsonText
import eu.europa.ec.eudi.rqesui.presentation.ui.util.formatXmlText

@Composable
internal fun SimpleFileViewer(
    fileType: DocumentFileType,
    simpleFileUri: Uri
) {
    val context = LocalContext.current
    val fileToString: String = remember(simpleFileUri) {
        when (fileType) {
            DocumentFileType.XML -> {
                val rawString = simpleFileUri.readTextFromUri(context) ?: ""
                formatXmlText(xml = rawString)
            }

            DocumentFileType.JSON -> {
                val rawString = simpleFileUri.readTextFromUri(context) ?: ""
                formatJsonText(json = rawString)
            }

            else -> ""
        }
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Text(
            text = fileToString,
            style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
            modifier = Modifier
                .padding(horizontal = SPACING_MEDIUM.dp)
                .verticalScroll(state = rememberScrollState())
        )
    }
}

@Preview
@Composable
private fun SimpleFileViewerPreview() {
    SimpleFileViewer(
        fileType = DocumentFileType.JSON,
        simpleFileUri = "sampleUri".toUri()
    )
}
