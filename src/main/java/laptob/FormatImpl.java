package laptob;

public class FormatImpl implements Format {

    @Override
    public String setLaptop(String name) {
        return "Laptop " + name + "!";
    }
}