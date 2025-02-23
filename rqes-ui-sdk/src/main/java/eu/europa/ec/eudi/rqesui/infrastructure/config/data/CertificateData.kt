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

package eu.europa.ec.eudi.rqesui.infrastructure.config.data

import eu.europa.ec.eudi.rqes.CredentialInfo

/**
 * Data class representing a certificate.
 *
 * @property name The name of the certificate. This property will be used in the UI to display
 *                the certificate name to the user.
 * @property certificate The credential information associated with the certificate.
 *                       This is a pass-through property containing all domain-specific data for the certificate.
 */
internal data class CertificateData(
    val name: String,
    val certificate: CredentialInfo,
)

/**
 * Transforms a list of [CredentialInfo] objects into a list of [CertificateData] objects.
 *
 * This function iterates through the list of [CredentialInfo] and converts each one into a [CertificateData] object.
 * It uses the provided [createDefaultName] function to generate a default name for each certificate if needed.
 *
 * @param createDefaultName A function that takes an index and returns a default name for the certificate at that index.
 * @return A list of [CertificateData] objects, transformed from the original list of [CredentialInfo] objects.
 */
internal fun List<CredentialInfo>.toCertificatesData(createDefaultName: (Int) -> String): List<CertificateData> {
    return this.mapIndexed { index, credentialInfo ->
        credentialInfo.toCertificateData(defaultName = createDefaultName(index))
    }
}

/**
 * Converts a [CredentialInfo] object to a [CertificateData] object.
 *
 * This function takes a [CredentialInfo] object and extracts the necessary information
 * to create a [CertificateData] object. If the description of the credential is available,
 * it is used as the name of the certificate. Otherwise, the provided [defaultName] is used.
 *
 * @param defaultName The name to use for the certificate if the credential description is not available.
 * @return A [CertificateData] object representing the credential.
 */
internal fun CredentialInfo.toCertificateData(defaultName: String) = CertificateData(
    name = this.description?.value ?: defaultName,
    certificate = this,
)