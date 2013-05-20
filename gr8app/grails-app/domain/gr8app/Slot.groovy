package gr8app


class Slot {

    String name
    List<String> speakers
    Date start
    Date end
    boolean pause = false


    static constraints = {
    }

    @Override
    String toString() {
        name
    }
}
