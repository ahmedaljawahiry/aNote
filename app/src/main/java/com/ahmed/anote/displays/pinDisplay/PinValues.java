package com.ahmed.anote.displays.pinDisplay;

public class PinValues {

    private String key;
    private String hint;
    private String pin;
    private boolean secure;

    public PinValues() {}

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

    public boolean isSecure() {
        return secure;
    }

    public void setIsSecure(boolean secure) {
        this.secure = secure;
    }

    public void setIsSecure(int secure) {
        this.secure = (secure == 1) ? true : false;
    }
}
