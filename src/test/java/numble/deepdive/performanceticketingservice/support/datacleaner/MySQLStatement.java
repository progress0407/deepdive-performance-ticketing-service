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

        // id 초기화와 관련된 이슈 있음 (테스트 격리)
//        String statement = String.format("TRUNCATE TABLE %s;", tableTuple.name);

        String statement = String.format("DELETE FROM %s;", tableTuple.name);

        return statement;
    }

    @Override
    public String resetIncrementedId(TableTuple tableTuple) {

        return String.format("ALTER TABLE %s AUTO_INCREMENT=1;", tableTuple.name);
    }
}
