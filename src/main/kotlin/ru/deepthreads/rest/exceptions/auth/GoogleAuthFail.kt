package ru.deepthreads.rest.exceptions.auth

import ru.deepthreads.rest.models.other.GoogleAuthException

class GoogleAuthFail(val googleAuthException: GoogleAuthException) : Exception(googleAuthException.errorDescription)