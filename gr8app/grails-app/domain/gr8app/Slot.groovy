package gr8app


class Slot {

    String name
    String room
    Date start
    Date end
    boolean pause = false

    static hasMany = [speakers:Speaker]

    static belongsTo = [track:Track]
    static transients = ['offtrack', 'trackName']

    boolean getOfftrack(){
        return trackName == "Offtrack"
    }

    String getTrackName(){
        track.name
    }


    @Override
    String toString() {
        name
    }
}
