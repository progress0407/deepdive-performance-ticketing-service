package numble.deepdive.performanceticketingservice.support.datacleaner;

public class MySQLStatement extends DialectStatement {

    @Override
    public String dereferenceForeignKey() {

        return "SET FOREIGN_KEY_CHECKS=0;";
    }

    @Override
    public String referenceForeignKey() {

        return "SET FOREIGN_KEY_CHECKS=1;";
    }

    @Override
    public String truncate(TableTuple tableTuple) {

        return String.format("TRUNCATE TABLE %s;", tableTuple.name);
    }

    @Override
    public String resetIncrementedId(TableTuple tableTuple) {

        return String.format("ALTER TABLE %s AUTO_INCREMENT=1;", tableTuple.name);
    }
}
