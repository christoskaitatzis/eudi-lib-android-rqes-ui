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

package eu.europa.ec.rqesui.presentation.navigation.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import eu.europa.ec.rqesui.BuildConfig
import eu.europa.ec.rqesui.presentation.navigation.Screen
import eu.europa.ec.rqesui.presentation.ui.container.EudiRQESContainer

internal fun <T> generateComposableArguments(arguments: Map<String, T>): String {
    if (arguments.isEmpty()) return ""
    return StringBuilder().apply {
        append("?")
        arguments.onEachIndexed { index, entry ->
            if (index > 0) {
                append("&")
            }
            append("${entry.key}=${entry.value}")
        }
    }.toString()
}

internal fun generateComposableDeepLinkUri(screen: Screen, arguments: String): Uri =
    generateComposableDeepLinkUri(screen.screenName, arguments)

internal fun generateComposableDeepLinkUri(screen: String, arguments: String): Uri =
    "${BuildConfig.DEEPLINK}/${screen}$arguments".toUri()

internal fun generateComposableNavigationLink(screen: Screen, arguments: String): String =
    generateComposableNavigationLink(screen.screenName, arguments)

internal fun generateComposableNavigationLink(screen: String, arguments: String): String =
    "${screen}$arguments"

internal fun generateNewTaskDeepLink(
    context: Context,
    screen: Screen,
    arguments: String = "",
    flags: Int = 0
): Intent =
    generateNewTaskDeepLink(context, screen.screenName, arguments, flags)

internal fun generateNewTaskDeepLink(
    context: Context,
    screen: String,
    arguments: String = "",
    flags: Int = 0
): Intent =
    Intent(
        Intent.ACTION_VIEW,
        generateComposableDeepLinkUri(screen, arguments),
        context,
        EudiRQESContainer::class.java
    ).apply {
        addFlags(flags)
    }