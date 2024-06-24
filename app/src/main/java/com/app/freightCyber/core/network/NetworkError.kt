
package com.app.freightCyber.core.network
class NetworkError(val errorCode: Int, override val message: String?) : Throwable(message)
