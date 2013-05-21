package gr8app

class Track {

    String name
    String uri


    static hasMany = [slots: Slot]

    @Override
    String toString() {
        name
    }
}
