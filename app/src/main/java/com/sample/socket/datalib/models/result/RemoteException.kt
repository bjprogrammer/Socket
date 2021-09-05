package com.sample.socket.datalib.models.result

import com.sample.socket.datalib.models.base.ErrorMessage

data class RemoteException(var mess: ErrorMessage) : Exception(mess.getValue())