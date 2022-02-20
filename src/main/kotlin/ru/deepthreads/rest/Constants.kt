package ru.deepthreads.rest

object Constants {
    object STATUSCODE {
        const val NORMAL = 0
        const val INVALID_REQUEST = 1001
        const val INCORRECT_PASSWORD = 1002
        const val NONEXISTENT_ACCOUNT = 1003
        const val TAKEN_DEEP_ID = 1004
        const val NOT_FOUND = 1005
        const val UNAUTHORIZED = 1006
        const val ACCESS_DENIED = 1007
        const val NOT_A_MEMBER = 1008
        const val ALREADY_A_MEMBER = 1009
        const val GOOGLE_RETURNED_ERROR = 1010
        const val YOU_ARE_BLOCKED_BY_USER = 1011
        const val CONFLICT = 1012
    }
    object OBJECTSTATUS {
        const val NORMAL = 0
        const val DELETED = 1
        const val DISABLED = 2
    }
    object CHATTYPE {
        const val PRIVATE = 0
        const val PUBLIC = 1
    }
    object MESSAGETYPE {
        const val NORMAL = 0
        const val SYSTEM_USER_JOIN = 1
        const val SYSTEM_USER_LEAVE = 2
        const val SYSTEM_BROADCAST = 3
    }
    object ROLE {
        const val USER = 0
        const val ADMIN = 1
        const val DEVELOPER = 2
    }
    object DTS {
        const val extPublic = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvIufSTcSmBFx++C4patxAHUkQrVypr0RwMVQ0fVCfz/au1DD4yTq1HRAapfqniHKb81Yd7qLcYvTOgsjW8uxNjAn2VymyYWvqEaUqtsHnrkGy5RSYgZp5JxSBtG09e6KFRvUPvrDyyb59mfpniUggQL4ij34lfpEgzbgh3QJd0p23W7UiKGIMUBeCSdWJprnMaIGZXKGuSE+MCffzyvi92rL4AXnwQzVWeIJ6QMoFUnuK9qeBuMKOGpHas35Ouennbw89TWGAqW0M2X/0ZedwUWEcpNSwMjupCHs4pxcCJW71bkBH4yw5XspBWy0RtGXGy6FIdwpwnUhI5Nb07APYQIDAQAB"
        const val extPrivate = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC8i59JNxKYEXH74Lilq3EAdSRCtXKmvRHAxVDR9UJ/P9q7UMPjJOrUdEBql+qeIcpvzVh3uotxi9M6CyNby7E2MCfZXKbJha+oRpSq2weeuQbLlFJiBmnknFIG0bT17ooVG9Q++sPLJvn2Z+meJSCBAviKPfiV+kSDNuCHdAl3SnbdbtSIoYgxQF4JJ1YmmucxogZlcoa5IT4wJ9/PK+L3asvgBefBDNVZ4gnpAygVSe4r2p4G4wo4akdqzfk656edvDz1NYYCpbQzZf/Rl53BRYRyk1LAyO6kIezinFwIlbvVuQEfjLDleykFbLRG0ZcbLoUh3CnCdSEjk1vTsA9hAgMBAAECggEADyQrS4bnsFyeSbr3OR62eXHu0oidx3Qhi9iMr//BMlTfbPGEeaZKUXtwfN7sUXynNClKdHr/ncO718pzMXj/JzngyVzebAqXW60nXT0vtHhpaknj/8lCEcDX+YI3xRQ99IoClngu5w6fPkGEClYy8QlbCkcFwo2RmPP8PgR8ih7GdhWWF/RMQHlFz8+zcbNHwsogTSkD3/ESBemY1EeqvT8HD1Y5L4XHQxNAStVaNJdwz9DokEfzsIpLehQnxC6lR/FxRc9D4IMFGf4anCt0JxJCEfPv5KOHXahth+ip2yZk21u5x/em+QcVxIXy237WtDAUFchGyArSJuHp/EPRQQKBgQDVuS3+6CAoRhZWglMYHDlflvzELd0j5AVIsU1gSTDpduJrq3RexcpBG+AEYmgB5BbS2ahHYQvs1jNWH70mP0aQ4VzHiKaex+cz6W5UJZqSZ0ZNvAxPE1VULQjPgdJFK0fSU7MGkakYfCnkVMH+M90YUMLzb5ftdrV+7jQwOjfZ+QKBgQDh13IyaJjoORxlWhpGC//iRLOafMjxvkAZ7cZGnmxCd+8800WUkgzjrkvK9RaM+WzTE3Z3eLl+WcznQUIOjWM+ND0n7Qr0RrcHdoNW7NMNE70aZLZNJZNRQDEia6CDJkEefZGSq8hPdvue4/Lga01/3An7KGYo68xeRVKtBpT6qQKBgAv2NVDTBRm78nwdBzGOQtfKx5LjTkE3/eO3qLD+57HpaXrFOdokD9HdEYitSUzxyyU58WUtVVGIG49yb0+4suZg9qRkTY8NeS86nWYwRJp1FUY8hCPY3Sm864VRqsccSQTIkniHpoMsT9vrsn/bv0AhJQgi8snjnvzfsCQS3hVhAoGAeF2sZoNq43H02WvBTw38LisxkifprTYZ0ffxUhgDWRW08zUjA4Enz31Itf4UK8SQJJtd62TzcA0KcUJNo5Xli/Spl4r16KV7zUVz0LNd9L1NbzC4HLvnHUnaJh84qE2OLWL9YC+gDgI7Lz8MZmGqJ3gt9addc3fhL2lBHCjC5CkCgYArTKIiUQirGlr9gRrCOE8ojE7+WhNDF32S8bo7oxNLYdUZS0bmI3fJZF6hREdfZloHoFCVo5qoheue9v1Z1qanl/4BCI90b36hH94WbksLKnQuA+vYsQGFGYZhN4dyw3jxRGe0xNDp+SWDPQQtg4rXuwnLUO1l+1Z1OszBdkI2UQ=="
    }
    object COMMENTPARENT {
        const val WALL_ITEM = 1
        const val USER_PROFILE = 2
    }
}