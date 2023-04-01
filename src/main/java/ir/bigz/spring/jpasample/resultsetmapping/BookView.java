package ir.bigz.spring.jpasample.resultsetmapping;

public class BookView {

    private long id;
    private String title;
    private String AuthorName;

    public BookView(long id, String title, String authorName) {
        this.id = id;
        this.title = title;
        AuthorName = authorName;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    @Override
    public String toString() {
        return "BookView{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", AuthorName='" + AuthorName + '\'' +
                '}';
    }
}
