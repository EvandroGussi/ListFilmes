package com.example.evandro.app13mob.fragments.dummy;

import com.example.evandro.app13mob.model.Anotacoes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    //private static final int COUNT = 25;

    static {

        List<Anotacoes> lstAnotacoes = Anotacoes.listAll(Anotacoes.class);

        for (Anotacoes anotacoes : lstAnotacoes) {

            addItem(createDummyItem(Integer.parseInt(anotacoes.getId().toString()), anotacoes.getNome().toString()));
        }
    }

    private static void addItem(DummyItem item) {
        //ITEM_MAP.clear();
        //ITEMS.clear();
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(Integer position, String nome) {
        return new DummyItem(String.valueOf(position), nome, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

//        @Override
//        public String toString() {
//            return content;
//        }
    }
}
