package org.gr8conf.trackapp

class StringMetaHelper {

    static void addToDateToStringMetaClass(){
        String.metaClass.'static'.toDate = {
            def dateWithFixedTimezone = delegate.replace('Z', ' +0000')
            Date.parse("yyyy-MM-dd'T'HH:mm:ss Z", dateWithFixedTimezone)
        }
    }

}
