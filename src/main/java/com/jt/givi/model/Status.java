package com.jt.givi.model;

import com.jt.givi.util.Util;

import java.util.Date;

/**
 * Created by superman on 7/19/2015.
 */
public class Status {
    public enum State {
        RED("RED"), YELLOW("YELLOW"), GREEN("GREEN"), BLACK("BLACK"), NONE("NONE");

        private String name;

        State(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public static State forName(String name) {
            if (name.equalsIgnoreCase("RED")) {
                return RED;
            } else if (name.equalsIgnoreCase("YELLOW")) {
                return YELLOW;
            } else if (name.equalsIgnoreCase("GREEN")) {
                return GREEN;
            } else if (name.equalsIgnoreCase("BLACK")) {
                return BLACK;
            } else {
                return NONE;
            }
        }

        public String getName() {
            return name;
        }
    }

    private State state;
    private Date lastChanged;

    public Status(State state, Date lastChanged) {
        this.state = state;
        this.lastChanged = lastChanged;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Date getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }

    @Override
    public String toString() {
        return Util.timeToString(lastChanged);
    }
}
