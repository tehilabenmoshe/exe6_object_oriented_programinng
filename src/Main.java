 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose from the following options:\n" +
                "a: Art\n" +
                "p: Papers");
        String choice = scanner.nextLine();
        if (choice.equals("a")){
            artMenu(scanner);
        }
        if (choice.equals("p")){
            paperMenu(scanner);
        }
    }
    public static Painting readElementDetails(String path) throws IOException {
        Painting painting = new Painting();
        Map<String, SimpleElement> files = new HashMap();

        Files.lines(Paths.get(path))
            .map(str -> ElementDetailsFactory.getPaintingElement(str))
            .forEach(e-> painting.addElement(e));
        return painting;
    }
    public static void artMenu(Scanner scanner) throws IOException {
        System.out.println("Enter the path of the painting description");
        String path=scanner.nextLine();
        Painting root= readElementDetails(path);
        System.out.println("Choose from the following options:\n" +
                "q: quit\n" +
                "c: count elements\n" +
                "lp: long print\n" +
                "sh: short print\n" +
                "ta: total area");
        String myString;
        while (!(myString = scanner.nextLine()).equals("q")) {
            switch (myString) {
                case "c":
                    ElementCountVisitor countVisitor = new ElementCountVisitor();
                    root.accept(countVisitor);
                    System.out.println(countVisitor.getCount());
                    break;
                case "sh":
                    ShortPrintVisitor shortPrint = new ShortPrintVisitor();
                    root.accept(shortPrint);
                    System.out.println(shortPrint.getShortPrint());
                    break;
                case "ta":
                    AreaCalculatorVisitor areaCalculatorVisitor = new AreaCalculatorVisitor();
                    root.accept(areaCalculatorVisitor);
                    System.out.println(areaCalculatorVisitor.getTotalArea());
                    break;
                case "lp":
                    LongPrintVisitor longPrint = new LongPrintVisitor();
                    root.accept(longPrint);
                    System.out.println(longPrint.getLongPrint());
                    break;


            }

        }
    }

    public static void paperMenu(Scanner scanner){
        System.out.println("Choose from the following paper:\n" +
                "ac: academic paper\n" +
                "cn: contract\n" +
                "jr: journal article\n" +
                "bk: book");
        String paperType = scanner.nextLine();
        Paper paper = PaperFactory.createPaper(paperType);
        String choice="";
        while (!choice.equals("s")) {
            System.out.println("Choose from the following options:\n" +
                    "a: add element\n" +
                    "s: submit");
            choice = scanner.nextLine();
            if (choice.equals("a")) {
                paper = paperElementMenu(scanner, paper);
            }
            if (choice.equals("s")) {
                System.out.println(paper.write());
            }
        }


    }
   public static Paper paperElementMenu(Scanner scanner, Paper paper){
        System.out.println("Choose from the following elements:\n" +
                "tb: table\n" +
                "eq: equation\n" +
                "d: diagram\n" +
                "nt: note");
       String elementType = scanner.nextLine();
       return PaperFactory.addAttribute(paper, elementType);
    }
}
