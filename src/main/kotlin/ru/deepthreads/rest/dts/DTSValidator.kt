package ru.deepthreads.rest.dts

import jakarta.servlet.http.HttpServletRequest
import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.exceptions.other.InvalidRequest
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.X509EncodedKeySpec
import java.util.*
import java.util.stream.Collectors
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object DTSValidator {
    fun verifyRequest(request: HttpServletRequest): Boolean {
        return true
    }

    fun verifyContext(clientContextString: String, clientNonce: String, clientRequestPath: String): Boolean {
        val mac = Mac.getInstance("HmacSHA1")
        mac.init(SecretKeySpec(clientNonce.toByteArray(Charsets.UTF_8), "HmacSHA1"))
        mac.update(clientRequestPath.toByteArray(Charsets.UTF_8))
        val macBytes = mac.doFinal()
        val result = Base64.getEncoder().encodeToString("09".toByteArray(Charsets.UTF_8) + macBytes)
        return clientContextString == result
    }

    fun verifyExtension(clientExtensionString: String, requestBodyContent: ByteArray): Boolean {
        val signature = Signature.getInstance("SHA1withRSA")
        val keySpec = X509EncodedKeySpec(Base64.getDecoder().decode(Constants.DTS.extPublic))
        val keyFactory = KeyFactory.getInstance("RSA")
        val key = keyFactory.generatePublic(keySpec)
        signature.initVerify(key)
        signature.update(requestBodyContent)
        return signature.verify(Base64.getDecoder().decode(clientExtensionString))
    }

}