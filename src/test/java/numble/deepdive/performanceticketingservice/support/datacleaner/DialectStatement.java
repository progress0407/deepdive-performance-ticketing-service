package numble.deepdive.performanceticketingservice.support.datacleaner;

public abstract class DialectStatement {

    public abstract String dereferenceForeignKey();

    public abstract String referenceForeignKey();

    public abstract String truncate(TableTuple tableTuple);

    public abstract String resetIncrementedId(TableTuple tableTuple);
}
