package ir.shayandaneshvar.services.processors;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class CompressedInfoExtractor implements TextDecoder<String, Map<String
        , String>, String>,
        InfoExtractor<Pair<String, String>, String> {

    public String getPassword(Pair<String, String> pair) {
        String header = pair.getKey().split("===")[0];
        int start = header.indexOf("<pass>");//<pass>sth</pass>
        int end = header.lastIndexOf("</pass>");
        return header.substring(start + 6, end);
    }

    public boolean getExtremeSecurityStatus(Pair<String, String> pair) {
        String header = pair.getKey().split("===")[0];
        int start = header.indexOf("<X>");//<X>T/F</X>
        int end = header.lastIndexOf("</X>");
        return header.substring(start + 3, end).equals("T");
    }

    /**
     * @param readContent content read with CompressedFilePersistence read method
     * @return mapped values in this form : "001" => a
     */
    public Map<String, String> getDictionary(Pair<String, String> readContent) {
        String dic = readContent.getKey().split("===")[1];
        StringTokenizer tokenizer = new StringTokenizer(dic, "|");
        Map<String, String> map = new HashMap<>();
        while (tokenizer.hasMoreTokens()) {
            String[] mappedValue = tokenizer.nextToken().split("=>");
            map.put(mappedValue[1], mappedValue[0]);
        }
        return map;
    }


    @Override
    public String extract(Pair<String, String> readContent) {
        Map<String, String> dic = getDictionary(readContent);
        return decode(readContent.getValue(), dic);
    }

    @Override
    public String decode(String cipher, Map<String, String> dic) {
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < cipher.length(); i++) {
            String subDecode = dic.get(cipher.substring(0, i));
            if (subDecode != null) {
                builder.append(subDecode);
                cipher = cipher.substring(i);
                i = 0;
            }
        }
        if (cipher.length() != 0) {
            builder.append(dic.get(cipher));
        }
        return builder.toString();
    }

    public String makeHeader(boolean xtreme, String password) {
        password = "<pass>" + password + "</pass>";
        String xt = xtreme ? "T" : "F";
        xt = "<X>" + xt + "</X>";
        return password + xt;
    }
}
