package ir.bigz.spring.jpasample.resultsetmapping;

public class AuthorDto {

    private long authorId;
    private String firstName;
    private String lastName;
    private String authorNumber;
    private BookDto bookDto;

    public static Builder builder() {
        return new Builder();
    }

    public AuthorDto() {
    }

    private AuthorDto(Builder builder) {
        this.authorId = builder.authorId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.authorNumber = builder.authorNumber;
        this.bookDto = builder.bookDto;
    }

    public long getAuthorId() {
        return authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAuthorNumber() {
        return authorNumber;
    }

    public BookDto getBookDto() {
        return bookDto;
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "authorId=" + authorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", authorNumber='" + authorNumber + '\'' +
                ", bookDto=" + bookDto +
                '}';
    }

    public static class Builder {

        private long authorId;
        private String firstName;
        private String lastName;
        private String authorNumber;
        private BookDto bookDto;

        public Builder authorId(long authorId){
            this.authorId = authorId;
            return this;
        }

        public Builder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder authorNumber(String authorNumber){
            this.authorNumber = authorNumber;
            return this;
        }

        public Builder bookDto(BookDto bookDto){
            this.bookDto = bookDto;
            return this;
        }

        public AuthorDto build(){
            return new AuthorDto(this);
        }
    }
}
