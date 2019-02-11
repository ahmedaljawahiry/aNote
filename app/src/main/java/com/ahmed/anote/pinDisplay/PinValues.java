package com.ahmed.anote.pinDisplay;

public class PinValues {

    private String key;
    private String hint;
    private String pin;

    public PinValues() {}

    public PinValues(String key, String hint, String pin) {
        this.key = key;
        this.hint = hint;
        this.pin = pin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
