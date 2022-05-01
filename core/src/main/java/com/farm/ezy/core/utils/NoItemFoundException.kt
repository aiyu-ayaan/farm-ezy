package com.farm.ezy.core.utils

import java.lang.Exception

class NoItemFoundException(
    override val message : String
) : Exception(message) {
}