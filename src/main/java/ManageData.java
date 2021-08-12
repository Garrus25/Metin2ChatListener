import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageData {

    private static class IndexedData {

        private final Integer id;
        private final Integer channel;
        private final String name;

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        IndexedData(Integer id, Integer channel, String name) {
            this.id = id;
            this.channel = channel;
            this.name = name;
        }

        @SuppressWarnings("unused")
        public Integer getId() {
            return id;
        }

        @SuppressWarnings("unused")
        public Integer getChannel() {
            return channel;
        }

        @SuppressWarnings("unused")
        public String getName() {
            return name;
        }
    }

    private static final List<IndexedData> indexedData = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> channelList = new ArrayList<>();

    public String text = """
            Jest to bardzo cenne zrodło informacji o serwerze,

            [CH2] Królowa Pająków został pokonany przez Shiraa!
            [CH3] Olbrzymi Żólw został pokonany przez Salwy!

            [CH2] Olbrzymi Żólw został pokonany przez Salwy!

            Gracz Tatras osiągnął 75 pozior. Gratulacje!

            [CH1] Królowa Pająków został pokanany przez Salvy!
            [CH5] Rhaegal został pokonany przez Gondiil

            [CH8] Królowa Pająków został pokanany przez Salvy!
            [CH7] Królowa Pająków został pokanany przez Salvy!
            [CHE] Królowa Pająków został pokonany przez Salvy!
            [CH1] Set został pokonany przez Isabella!

            [CH1] Martyaxwar został pokonany przez Poliformoria!
            [CH1] Rhaegal został pokonany przez AsHiNa!

            [CH1] Chen został pokonany przez xBoguSalut!

            [CH7] Urmarhy Rozpruwacz został pokonany przez ConoQueEs!
            [CH7] Niebieska Śmierć został pokonany przez ConoQueFEsl
            """;

    public void dataCleanup() {
        text = text.toLowerCase(Locale.ROOT);
        removeEmptyLines();
        removeRedundantLines();
        removePolishSigns();
        extractData();
        indexData();
        for(int i = 0; i<14; i++){
            System.out.println(indexedData.get(i).getId()+" "+indexedData.get(i).getChannel()+" "+indexedData.get(i).getName());
        }
    }

    private void removeEmptyLines() {
        text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
    }

    private void removeRedundantLines() {
        text = text.replaceAll("(?m)^[^\\[].*", "");
        removeEmptyLines();
    }

    private void removePolishSigns() {
        text = text.replace("ó", "o");
        text = text.replace("ą", "a");
        text = text.replace("ę", "e");
        text = text.replace("ż", "z");
        text = text.replace("ł", "l");
        text = text.replace("ć", "c");
        text = text.replace("ś", "s");
    }

    private void indexData() {
        channelList.add("0");
        for(int i = 0; i < nameList.size(); i++){
            indexedData.add(new IndexedData(i+1,Integer.parseInt(channelList.get(i)),nameList.get(i)));
        }
    }

    private void extractData() {
        Pattern p = Pattern.compile("(?<=\\b]\\s)(\\w+)");
        Matcher m = p.matcher(text);
        while (m.find()) {
            nameList.add(m.group(1));
        }
        Pattern e = Pattern.compile("\\[*[0-9]");
        Matcher f = e.matcher(text);
        while (f.find()) {
            channelList.add(f.group(0));
        }
    }
}
