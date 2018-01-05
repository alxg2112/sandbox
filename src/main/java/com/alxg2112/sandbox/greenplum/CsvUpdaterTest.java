package com.alxg2112.sandbox.greenplum;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Alexander Gryshchenko
 */
public class CsvUpdaterTest {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(CsvUpdaterContext.class);
		CsvUpdater csvUpdater = context.getBean(CsvUpdater.class);
		csvUpdater.doCopy("COPY test_csv_table FROM STDIN DELIMITER ',' CSV", "test.csv"); //  ESCAPE '\'
	}
}
