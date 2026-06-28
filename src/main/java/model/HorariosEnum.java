package model;

public enum HorariosEnum {
    M1("07:00", "07:50"),
    M2("07:50", "08:40"),
    M3("08:40", "09:30"),
    M4("09:30", "10:20"),
    M5("10:20", "11:10"),
    M6("11:10", "12:00"),
    T1("13:00", "13:50"),
    T2("13:50", "14:40"),
    T3("14:40", "15:30"),
    T4("15:30", "16:20"),
    T5("16:20", "17:10"),
    T6("17:10", "18:00"),
    N1("18:00", "18:50"),
    N2("18:50", "19:40"),
    N3("19:40", "20:30"),
    N4("20:30", "21:20"),
    N5("21:20", "22:10"),
    N6("22:10", "23:00");

    private final String hi;
    private final String hf;

    HorariosEnum(String hi, String hf) {
        this.hi = hi;
        this.hf = hf;
    }

    public String getHi() { return hi; }
    public String getHf() { return hf; }

    @Override
    public String toString() { return name() + "  " + hi + " – " + hf; }
}
