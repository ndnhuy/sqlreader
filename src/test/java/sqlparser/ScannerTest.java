package sqlparser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScannerTest {
    @Test
    public void testScanner() {
        Scanner scanner = new Scanner();
        List<String> lexemes = scanner.produceLexemes("SELECT * FROM table_name1, table_name2 WHERE (a=b and x = y) or c = d or e = \"literal string 1, literal string 2\"");
        Assert.assertEquals(Arrays.asList(
                "SELECT",
                "*",
                "FROM",
                "table_name1",
                ",",
                "table_name2",
                "WHERE",
                "(",
                "a",
                "=",
                "b",
                "and",
                "x",
                "=",
                "y",
                ")",
                "or",
                "c",
                "=",
                "d",
                "or",
                "e",
                "=",
                "\"literal string 1, literal string 2\""),
                lexemes);
    }

    /*
        SELECT
           l_orderkey,
           abc,
           o_orderdate,
           o_shippriority
        FROM
           customer,
           orders,
           lineitem
        WHERE
          c_mktsegment = 'FURNITURE' AND
          c_custkey = o_custkey AND
          l_orderkey = o_orderkey AND
          o_orderdate < 2013-12-21 AND
          l_shipdate > 2014-01-06
        GROUP BY
          l_orderkey,
          o_orderdate,
          o_shippriority
        ORDER BY
          revenue,
          o_orderdate;
     */
    @Test
    public void testScanner2() {
        Scanner scanner = new Scanner();
        List<String> lexemes = scanner.produceLexemes("SELECT\n" +
                "   l_orderkey,\n" +
                "   abc,\n" +
                "   o_orderdate,\n" +
                "   o_shippriority\n" +
                "FROM\n" +
                "   customer,\n" +
                "   orders,\n" +
                "   lineitem\n" +
                "WHERE\n" +
                "  c_mktsegment = 'FURNITURE' AND\n" +
                "  c_custkey = o_custkey AND\n" +
                "  l_orderkey = o_orderkey AND\n" +
                "  o_orderdate < 2013-12-21 AND\n" +
                "  l_shipdate > 2014-01-06\n" +
                "GROUP BY\n" +
                "  l_orderkey,\n" +
                "  o_orderdate,\n" +
                "  o_shippriority\n" +
                "ORDER BY\n" +
                "  revenue,\n" +
                "  o_orderdate;");
        System.out.println(lexemes.stream().collect(Collectors.joining("#")));
    }
}
