package gr8app

class Track {

    String name
    String room

    static hasMany = [slots: Slot]

    static constraints = {
    }
}
