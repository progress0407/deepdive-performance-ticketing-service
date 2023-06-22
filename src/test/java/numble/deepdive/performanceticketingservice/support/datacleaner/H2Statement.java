package numble.deepdive.performanceticketingservice.support.datacleaner;

public class H2Statement extends DialectStatement {

    @Override
    public String dereferenceForeignKey() {

        return "SET REFERENTIAL_INTEGRITY FALSE;";
    }

    @Override
    public String referenceForeignKey() {

        return "SET REFERENTIAL_INTEGRITY TRUE;";
    }

    @Override
    public String truncate(TableTuple tableTuple) {

        return String.format("TRUNCATE TABLE %s;", tableTuple.name);
    }

    @Override
    public String resetIncrementedId(TableTuple tableTuple) {

        return String.format("ALTER TABLE %s ALTER COLUMN %s RESTART WITH 1;", tableTuple.name, tableTuple.id);
    }
}
