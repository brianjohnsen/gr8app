package gr8app

class Track {

    String name

    static hasMany = [slots: Slot]

    static constraints = {
    }
}
