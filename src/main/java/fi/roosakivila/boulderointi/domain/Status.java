package fi.roosakivila.boulderointi.domain;

public enum Status {
    ALOITETTU("Aloitettu"),
    TOPATTU("Topattu"),
    HYLÄTTY("Hylätty");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
