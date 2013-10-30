package ru.compscicenter.trends.ner.model;


/**
 * @author alexeyev
 */
public class NamedEntity {
    private Tag tag;
    private String words;

    public NamedEntity(Tag tag, String words) {
        this.tag = tag;
        this.words = words;
    }

    public Tag getTag() {
        return tag;
    }

    public String getWords() {
        return words;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + tag + "," + words + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o == this ||
                o instanceof NamedEntity &&
                        ((NamedEntity) o).getTag().equals(tag) &&
                        ((NamedEntity) o).getWords().equals(words);
    }
}
