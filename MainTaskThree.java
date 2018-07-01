package ua.prog.kiev.lesson2.taskThree;

public class MainTaskThree {

    public static void main(String[] args) {
        String xmlQueryUrl = "https://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote?format=xml";
        try {
            GoogleFinanceParser parserXml = new GoogleFinanceParser(xmlQueryUrl);
            String xmlResponce = parserXml.getGoogleResponce();
            String xmlResponceJustResourcesData = parserXml.cutUselessInfo(xmlResponce);
            Resources resourcesList = new Resources();
            System.out.println(parserXml.parseDataByDOM(resourcesList, xmlResponceJustResourcesData).toString());
        } catch (WrongUrlException ex) {
        }
    }
}
