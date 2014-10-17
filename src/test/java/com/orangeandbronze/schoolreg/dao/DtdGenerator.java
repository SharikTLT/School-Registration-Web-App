package com.orangeandbronze.schoolreg.dao;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatDtdDataSet;

public class DtdGenerator {
	
	public static void main(String[] args) throws Exception
    {
        // database connection
        Connection jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost/school_registration", "root", "");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // write DTD file
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("dataset.dtd"));
    }

}
