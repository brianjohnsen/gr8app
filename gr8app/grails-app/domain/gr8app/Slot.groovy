package gr8app


class Slot {

    String name
    List<String> speakers
    Date start
    Date end
    boolean pause = false
    String room

    static constraints = {
    }

    @Override
    String toString() {
        name
    }
}
