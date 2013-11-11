package org.gr8conf.trackapp


class Slot {

    String name
    String room
    String trackName
    Date start
    Date end
    boolean pause = false
    String uri


    static hasMany = [speakers:Speaker]
    static transients = ['offtrack']

    boolean getOfftrack(){
        return trackName == "Offtrack"
    }


    @Override
    String toString() {
        name
    }
}
