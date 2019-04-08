package com.santander.bks.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONSQLParser {
	
	private final class ComplexType {

		private String propertyName;
		private String propertyType;
		private String propertyTypeName;

		public String getPropertyName() {
			return propertyName;
		}

		public void setPropertyName(final String propertyName) {
			this.propertyName = propertyName;
		}

		public String getPropertyType() {
			return propertyType;
		}

		public void setPropertyType(final String propertyType) {
			this.propertyType = propertyType;
		}

		public String getPropertyTypeName() {
			return propertyTypeName;
		}

		public void setPropertyTypeName(final String propertyTypeName) {
			this.propertyTypeName = propertyTypeName;
		}

	}

	public static final Logger LOGGER = LoggerFactory.getLogger(JSONSQLParser.class);

	private final Map<String, String> simpleTypes = new HashMap<String, String>();

	private final Map<String, List<ComplexType>> complexTypes = new HashMap<String, List<ComplexType>>();
	
	private final Map<String, List<ComplexType>> tmpComplexTypes = new HashMap<String, List<ComplexType>>();

	public JSONSQLParser() throws SQLException {

	}

	public void printTypes() throws ClassNotFoundException, SQLException {
		fillSimpleTypes();
		fillComplexTypes();
		// fillTmpComplexTypes();
		print();
	}
	
	public void print() {
		LOGGER.info("[\n");
		for (final String name : complexTypes.keySet()) {
			LOGGER.info(" {\n");
			LOGGER.info("  \"" + name + "\": {\n");
			printComplexTypeList(complexTypes.get(name), 4);
			LOGGER.info("  }\n },\n");
		}
		LOGGER.info("]");
	}
	
	public void printComplexTypeList(final List<ComplexType> complexTypes, final int depth) {
		for (final ComplexType complexType : complexTypes) {
			printComplexType(complexType, depth);
		}
	}
	
	public void printComplexType(final ComplexType complexType, final int depth) {
		if ("complex".equals(complexType.getPropertyType())) {
			LOGGER.info(StringUtils.repeat(" ", depth) + "\"" + complexType.getPropertyName() + "\" : {\n");
			printComplexTypeList(complexTypes.get(complexType.getPropertyTypeName()), depth + 1);
			LOGGER.info(StringUtils.repeat(" ", depth) + "},\n");
		} else {
			LOGGER.info(StringUtils.repeat(" ", depth) + "\"" + complexType.getPropertyName() + "\" : ");
			printSimpleType(complexType.getPropertyTypeName());
			LOGGER.info(",\n");
		}
	}
	
	public void printSimpleType(final String name) {
		LOGGER.info("{ \"" + name + "\": " + this.simpleTypes.get(name) + " }");
	}

	public void fillSimpleTypes() throws ClassNotFoundException, SQLException {
		final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/BKS", "banksphere", "banksphere");
		try {
			Statement stmt = conn.createStatement();
			try {
				ResultSet rs = stmt.executeQuery("select * from simpleType c order by c.name asc");

				try {
					while (rs.next()) {
						int numColumns = rs.getMetaData().getColumnCount();
						for (int i = 1; i <= numColumns; i++) {

							final String name = rs.getString("name");
							final String base = rs.getString("base");

							simpleTypes.put(name, base);
						}
					}
				} finally {
					rs.close();
				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}
	}

	public void fillTmpComplexTypes() throws ClassNotFoundException, SQLException {
		final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/BKS", "banksphere", "banksphere");
		try {
			Statement stmt = conn.createStatement();
			try {
				ResultSet rs = stmt.executeQuery("SELECT * FROM complexType c where c.name = 'ACCION_CSMCOR' order by c.name asc");
				try {
					while (rs.next()) {
						final String name = rs.getString("name");

						final ComplexType complexType = new ComplexType();
						complexType.setPropertyName(rs.getString("propertyName"));
						complexType.setPropertyType(rs.getString("propertyType"));
						complexType.setPropertyTypeName(rs.getString("propertyTypeName"));

						if (!tmpComplexTypes.containsKey(name)) {
							tmpComplexTypes.put(name, new ArrayList<ComplexType>());
						}

						tmpComplexTypes.get(name).add(complexType);
					}
				} finally {
					rs.close();
				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}
	}
	
	public void fillComplexTypes() throws ClassNotFoundException, SQLException {
		final Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/BKS", "banksphere", "banksphere");
		try {
			Statement stmt = conn.createStatement();
			try {
				ResultSet rs = stmt.executeQuery("SELECT * FROM complexType c order by c.name asc");
				try {
					while (rs.next()) {
						final String name = rs.getString("name");

						final ComplexType complexType = new ComplexType();
						complexType.setPropertyName(rs.getString("propertyName"));
						complexType.setPropertyType(rs.getString("propertyType"));
						complexType.setPropertyTypeName(rs.getString("propertyTypeName"));

						if (!complexTypes.containsKey(name)) {
							complexTypes.put(name, new ArrayList<ComplexType>());
						}

						complexTypes.get(name).add(complexType);
					}
				} finally {
					rs.close();
				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}
	}

}
