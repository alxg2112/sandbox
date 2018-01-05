package com.alxg2112.sandbox.greenplum;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.postgresql.PGConnection;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Gryshchenko
 */
@Component
public class CsvUpdater {

	private final DataSource dataSource;

	public CsvUpdater(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void doCopy(String copyStatement, String filename) {
		try (Connection connection = dataSource.getConnection()) {
			CopyManager copyManager = new CopyManager((BaseConnection) connection.unwrap(PGConnection.class));
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
			copyManager.copyIn(copyStatement, inputStream);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}
