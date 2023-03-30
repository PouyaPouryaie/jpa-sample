package ir.bigz.spring.jpasample.resultsetmapping;

public class BookDto {

    private long id;
    private String title;

    private BookDto(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static BookDtoBuilder builder(){
        return new BookDtoBuilder();
    }

    static class BookDtoBuilder{

        private long id;
        private String title;

        public BookDtoBuilder() {
        }

        public BookDtoBuilder bookId(long bookId){
            this.id = bookId;
            return this;
        }

        public BookDtoBuilder title(String title){
            this.title = title;
            return this;
        }

        public BookDto build(){
            return new BookDto(this.id, this.title);
        }
    }
}
