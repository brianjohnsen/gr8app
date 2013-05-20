package gr8app


class Slot {

    String name
    List<String> speakers
    Date start
    Date end
    boolean pause = false
    String room

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
