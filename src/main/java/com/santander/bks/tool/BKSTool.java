package com.santander.bks.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.Xslt30Transformer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;

public final class BKSTool {

	public static final Logger LOGGER = LoggerFactory.getLogger(BKSTool.class);

	public static final short BULK_TO_CONSOLE = 0;
	public static final short BULK_TO_FILE = 1;

	public static final short SIMPLE = 0;
	public static final short COMPLEX = 1;
	public static final short OPI_TYPES = 2;
	public static final short OPI_MAPPING = 3;
	public static final short TRXOP = 4;
	public static final short VB = 5;

	private BKSTool() {

	}

	public static void main(final String[] args) throws IOException, ParserConfigurationException, SAXException,
			TransformerFactoryConfigurationError, TransformerException, ClassNotFoundException, SQLException {

		// final String path = new File("").toURI().toURL().getPath();

		// final String operation = args[0];

		final BKSTool bKSTool = new BKSTool();
		// bKSTool.scan(path, operation);
		/* final String[] repos = {
				"MPOPER", "IOCCMC", "CATPRO", "CONTCR", "TABGEN", };
		for (final String repo : repos) {
			bKSTool.parseCvs(repo);
		} */

		// bKSTool.parseOpiMapping();
		
		bKSTool.parseSQLvbTypes();
		bKSTool.parseSQLTrxOpTypes();
		bKSTool.parseSQLTrxOpMapping();

		// final JSONSQLParser jSONSQLParser = new JSONSQLParser();
		// jSONSQLParser.printTypes();

	}
	
	@SuppressWarnings("unused")
	private void loopScan(final String path, final short op) throws ParserConfigurationException, SAXException,
			IOException, TransformerFactoryConfigurationError, TransformerException {

		switch (Short.valueOf(op)) {
		case OPI_TYPES:
			compile(path, "opi/opiTrxopTypes.xsl", null, BKSTool.OPI_TYPES, true);
			break;
		case OPI_MAPPING:
			compile(path, "opi/opiTrxopMapping.xsl", null, BKSTool.OPI_MAPPING, true);
			break;
		case TRXOP:
			compile(path, "trxop/opiTrxop.xsl", null, BKSTool.TRXOP, true);
			break;
		case VB:
			compile(path, "vb/vbTypes.xsl", null, BKSTool.VB, true);
			break;
		default:
		}

	}

	public String calculateVersion(final String version) {
		final String[] versions = version.split("\\.");
		String result = "";
		for (final String number : versions) {
			result += String.format("%05d", Integer.valueOf(number));
		}
		result = String.format("%-45s", result).replace(' ', '0');
		return StringUtils.stripStart(result, "0");
	}

	@SuppressWarnings("unused")
	private void parseSQLvbTypes() throws IOException {
		// final String path = "cvs/rlog-tags.txt";
		// final String path = "/C:/dev/BKS/rlog-tags-brief.txt";
		final String path = "/C:/dev/BKS/BKS_CVS/vb-types.txt";
		final String output = "/C:/dev/BKS/BKS_CVS/vb-types.sql";
		final String tableName = "vbType";

		BufferedReader reader = null;
		BufferedWriter writer = null;
		InputStream inputStream = null;
		OutputStream outputStrem = null;

		String header = "operation;name;fulltype;type;mandatory;initialFormat;description\n";

		String operation = "";
		String name = "";
		String fullType = "";
		String type = "";
		String mandatory = "";
		String initialFormat = "";
		String description = "";

		String sql = "";

		boolean hasHeader = false;
		boolean stdout = false;

		try {
			// inputStream =
			// Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			inputStream = new FileInputStream(path);
			outputStrem = new FileOutputStream(output);
			if (inputStream == null || outputStrem == null) {
				throw new FileNotFoundException(path);
			}
			reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			writer = new BufferedWriter(new OutputStreamWriter(outputStrem, StandardCharsets.UTF_8));

			if (hasHeader) {
				writer.write(header);
			}

			String line = reader.readLine();
			// while (line != null && rcs < Integer.valueOf("1000")) {
			while (line != null) {

				String[] fields = line.split(";");

				if (fields.length == 1) {
					line = reader.readLine();
					continue;
				}

				operation = fields[0];
				name = fields[1];
				fullType = fields[2];
				type = fields[2];

				if ("".compareTo(fullType) != 0) {
					String[] bksTypes = fullType.split("\\.");
					if (bksTypes.length > 0) {
						type = bksTypes[bksTypes.length - 1];
					}

				}

				mandatory = fields[3];

				if (fields.length > 4) {
					initialFormat = fields[4];
				} else {
					initialFormat = "";
				}

				if (fields.length > 5) {
					description = fields[5].replaceAll("'", "''");
				} else {
					description = "";
				}

				sql = "INSERT INTO `bks`.`" + tableName
						+ "` (`operation`, `name`, `fullType`, `type`, `mandatory`, `initialFormat`, `description`) VALUES ('"
						+ operation + "', '" + name + "', '" + fullType + "', '" + type + "', '" + mandatory + "', '"
						+ initialFormat + "', '" + description + "');\n";

				if (stdout) {
					LOGGER.info(sql);
				} else {
					writer.write(sql);
				}
				// read next line
				line = reader.readLine();
			}

		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (writer != null) {
					writer.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStrem != null) {
					outputStrem.close();
				}
			} catch (IOException e) {
				throw new IOException(e);
			}
		}

	}

	@SuppressWarnings("unused")
	private void parseSQLTrxOpTypes() throws IOException {
		// final String path = "cvs/rlog-tags.txt";
		// final String path = "/C:/dev/BKS/rlog-tags-brief.txt";
		final String path = "/C:/dev/BKS/BKS_CVS/trxop-types.txt";
		final String output = "/C:/dev/BKS/BKS_CVS/trxop-types.sql";
		final String tableName = "trxopType";

		BufferedReader reader = null;
		BufferedWriter writer = null;
		InputStream inputStream = null;
		OutputStream outputStrem = null;

		String header = "operation;name;fullType;type;mandatory;initialFormat;description\n";

		String operation = "";
		String name = "";
		String fullType = "";
		String type = "";
		String mandatory = "";
		String initialFormat = "";
		String description = "";

		String sql = "";

		boolean hasHeader = false;
		boolean stdout = false;

		try {
			// inputStream =
			// Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			inputStream = new FileInputStream(path);
			outputStrem = new FileOutputStream(output);
			if (inputStream == null || outputStrem == null) {
				throw new FileNotFoundException(path);
			}
			reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			writer = new BufferedWriter(new OutputStreamWriter(outputStrem, StandardCharsets.UTF_8));

			if (hasHeader) {
				writer.write(header);
			}

			String line = reader.readLine();
			// while (line != null && rcs < Integer.valueOf("1000")) {
			while (line != null) {

				String[] fields = line.split(";");

				operation = fields[0];
				name = fields[1];
				fullType = fields[2];
				type = fields[2];

				if ("".compareTo(fullType) != 0) {
					String[] bksTypes = fullType.split("\\.");
					if (bksTypes.length > 0) {
						type = bksTypes[bksTypes.length - 1];
					}

				}

				mandatory = fields[3];

				if (fields.length > 4) {
					initialFormat = fields[4];
				} else {
					initialFormat = "";
				}

				if (fields.length > 5) {
					description = fields[5];
				} else {
					description = "";
				}

				sql = "INSERT INTO `bks`.`" + tableName
						+ "` (`operation`, `name`, `fullType`, `type`, `mandatory`, `initialFormat`, `description`) VALUES ('"
						+ operation + "', '" + name + "', '" + fullType + "', '" + type + "', '" + mandatory + "', '"
						+ initialFormat + "', '" + description + "');\n";

				if (stdout) {
					LOGGER.info(sql);
				} else {
					writer.write(sql);
				}
				// read next line
				line = reader.readLine();
			}

		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (writer != null) {
					writer.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStrem != null) {
					outputStrem.close();
				}
			} catch (IOException e) {
				throw new IOException(e);
			}
		}

	}

	@SuppressWarnings("unused")
	private void parseSQLTrxOpMapping() throws IOException {
		// final String path = "cvs/rlog-tags.txt";
		// final String path = "/C:/dev/BKS/rlog-tags-brief.txt";
		final String path = "/C:/dev/BKS/BKS_CVS/trxop-mapping.txt";
		final String output = "/C:/dev/BKS/BKS_CVS/trxop-mapping.sql";
		final String tableName = "trxopMapping";

		BufferedReader reader = null;
		BufferedWriter writer = null;
		InputStream inputStream = null;
		OutputStream outputStrem = null;

		int rcs = 0;

		String header = "bksoperation;transaction;operation;version;direction;bksFullType;bksType;hostType;converter\n";

		String bksoperation = "";
		String transaction = "";
		String operation = "";
		String version = "";
		String direction = "";
		String bksType = "";
		String bksFullType = "";

		String hostType = "";
		String converter = "";

		String sql = "";

		boolean hasHeader = false;
		boolean stdout = false;

		try {
			// inputStream =
			// Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			inputStream = new FileInputStream(path);
			outputStrem = new FileOutputStream(output);
			if (inputStream == null || outputStrem == null) {
				throw new FileNotFoundException(path);
			}
			reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			writer = new BufferedWriter(new OutputStreamWriter(outputStrem, StandardCharsets.UTF_8));

			if (hasHeader) {
				writer.write(header);
			}

			String line = reader.readLine();
			// while (line != null && rcs < Integer.valueOf("1000")) {
			while (line != null) {

				String[] fields = line.split(";");

				bksoperation = fields[0];
				transaction = fields[1];
				operation = fields[2];
				version = fields[3];
				direction = fields[4];
				bksType = fields[5];
				bksFullType = fields[5];

				if ("".compareTo(bksFullType) != 0) {
					String[] bksTypes = bksFullType.split("\\.");
					if (bksTypes.length > 0) {
						bksType = bksTypes[bksTypes.length - 1];
					}

				}

				hostType = fields[Integer.valueOf("6")];
				converter = fields[Integer.valueOf("7")];

				sql = "INSERT INTO `bks`.`" + tableName
						+ "` (`bksoperation`, `transaction`, `operation`, `version`, `direction`, `bksFullType`, `bksType`, `hostType`, `converter`) VALUES ('"
						+ bksoperation + "', '" + transaction + "', '" + operation + "', '" + version + "', '"
						+ direction + "', '" + bksFullType + "', '" + bksType + "', '" + hostType + "', '" + converter
						+ "');\n";

				if (stdout) {
					LOGGER.info(sql);
				} else {
					writer.write(sql);
				}
				// read next line
				line = reader.readLine();
			}

		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (writer != null) {
					writer.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStrem != null) {
					outputStrem.close();
				}
			} catch (IOException e) {
				throw new IOException(e);
			}
		}

	}

	@SuppressWarnings("unused")
	private void parseCvs(final String repo) throws IOException {
		// final String path = "cvs/rlog-tags.txt";
		// final String path = "/C:/dev/BKS/rlog-tags-brief.txt";
		final String path = "/C:/dev/BKS/BKS_CVS/" + repo + "/" + repo + "-rlog-tags.txt";
		final String output = "/C:/dev/BKS/BKS_CVS/" + repo + "/" + repo + "-output.sql";
		final String tableName = "repository_" + repo.toLowerCase();

		BufferedReader reader = null;
		BufferedWriter writer = null;
		InputStream inputStream = null;
		OutputStream outputStrem = null;

		int rcs = 0;

		String root = "/cvs2/ucm/des/ebusiness/";

		String header = "module;subModule;function;rcsFile;tag;version\n";

		String rcsFile = "";
		String tag = "";
		String version = "";

		String module = "";
		String subModule = "";
		String function = "";

		String stringRunner = "";

		boolean hasHeader = false;
		boolean stdout = false;
		boolean sql = true;
		boolean includeBranches = true;

		boolean hasBranch = false;

		try {
			// inputStream =
			// Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			inputStream = new FileInputStream(path);
			outputStrem = new FileOutputStream(output);
			if (inputStream == null || outputStrem == null) {
				throw new FileNotFoundException(path);
			}
			reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			writer = new BufferedWriter(new OutputStreamWriter(outputStrem, StandardCharsets.UTF_8));

			if (hasHeader) {
				writer.write(header);
			}

			String line = reader.readLine();
			// while (line != null && rcs < Integer.valueOf("1000")) {
			while (line != null) {

				String[] fields = line.split(":");
				final String name = fields[0];
				if ("RCS file".equals(name)) {
					rcsFile = fields[1];
					rcsFile = rcsFile.substring(root.length() + 1);
					module = rcsFile.substring(0, rcsFile.indexOf("/"));

					stringRunner = rcsFile.substring(rcsFile.indexOf("/") + 1);

					if (stringRunner.length() > 0 && stringRunner.contains("/")) {

						subModule = stringRunner.substring(0, stringRunner.indexOf("/"));

						stringRunner = stringRunner.substring(stringRunner.indexOf("/") + 1);

						if (stringRunner.length() > 0 && stringRunner.contains("/")) {
							function = stringRunner.substring(0, stringRunner.indexOf("/"));
						}
					}

					rcsFile = rcsFile.substring(0, rcsFile.length() - 2);

					rcs++;
				} else if ("symbolic names".equals(name)) {
					line = reader.readLine();
					fields = line.split(":");
					tag = fields[0];
					while (!"keyword substitution".equals(tag)) {
						tag = fields[0].replaceAll("^\\s+", "");
						version = fields[1].trim();

						/*
						 * String[] versions = version.split("\\.");
						 * 
						 * int weight = 0; for (int i = 0; i < versions.length; i++) { weight = weight +
						 * Integer.valueOf(versions[i]); }
						 * 
						 * if (weight == 0) { weight = Integer.valueOf(version); }
						 */

						final String weight = calculateVersion(version);

						hasBranch = tag.startsWith("br");
						if (!hasBranch || (hasBranch && includeBranches)) {
							String trace = "";
							if (sql) {
								trace = "INSERT INTO `bks`.`" + tableName
										+ "` (`module`, `subModule`, `function`, `rcsFile`, `tag`, `version`, `weight`) VALUES ('"
										+ module + "', '" + subModule + "', '" + function + "', '" + rcsFile + "', '"
										+ tag + "', '" + version + "', '" + weight + "');\n";
							} else {
								trace = module + ";" + subModule + ";" + function + ";" + rcsFile + ";" + tag + ";"
										+ version + ";" + String.valueOf(weight) + "\n";
							}
							if (stdout) {
								LOGGER.info(trace);
							} else {
								writer.write(trace);
							}
						}

						line = reader.readLine();
						fields = line.split(":");
						tag = fields[0];
					}
					function = "";
				}
				// LOGGER.info(line + "\n");
				// read next line
				line = reader.readLine();
			}

		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (writer != null) {
					writer.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStrem != null) {
					outputStrem.close();
				}
			} catch (IOException e) {
				throw new IOException(e);
			}
		}

		if (stdout) {
			LOGGER.info(String.valueOf(rcs));
		}

	}

	@SuppressWarnings("unused")
	private void scan(final String path, final String operation)
			throws NumberFormatException, ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		/*
		 * bKSTool.scan(
		 * "/C:/dev/BKS/consultardatospersona/STDUSA_ValidaciontratamientoE_LN/vega/Applications/ALN_STDUSA_ValidaciontratamientoE/consultaUnitType.xmlopi",
		 * BKSTool.TRXOP); bKSTool.scan(
		 * "/C:/dev/BKS/consultardatospersona/BDPUSA_ConsultarDatosPersona_E_LN/vega/Applications/BDPUSA_ConsultarDatosPersona_E_LN/consBasFisUltAct_USA.xmlopi",
		 * BKSTool.TRXOP); bKSTool.scan(
		 * "/C:/dev/BKS/consultardatospersona/BDPUSA_ConsultarDatosPersona_E_LN/vega/Applications/BDPUSA_ConsultarDatosPersona_E_LN/consultarBasFisFATCA.xmlopi",
		 * BKSTool.TRXOP); bKSTool.scan(
		 * "/C:/dev/BKS/consultardatospersona/BDPUSA_ConsultarDatosPersona_E_LN/vega/Applications/BDPUSA_ConsultarDatosPersona_E_LN/consNomRTS_Lista.xmlopi",
		 * BKSTool.TRXOP);
		 */

		final Scanner scan = new Scanner(System.in, StandardCharsets.UTF_8.displayName());
		try {
			while (scan.hasNextLine()) {

				final String line = scan.nextLine();
				// LOGGER.info(path + line);
				loopScan(path + line, Short.valueOf(operation));
			}

		} finally {
			scan.close();
		}

		/*
		 * final BKSTool bKSTool = new BKSTool(); try { bKSTool.parseOpiMapping(); }
		 * catch (Exception e) { LOGGER.error(ExceptionUtils.getMessage(e)); }
		 */
	}

	@SuppressWarnings("unused")
	private void parseTrxOp() throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		compile("src/main/resources/trxop/consultarBasFisFATCA.xmlopi", "src/main/resources/trxop/opiTrxop.xsl",
				"src/main/resources/trxop/opiTrxop.csv", BULK_TO_FILE, false);
	}

	@SuppressWarnings("unused")
	private void parseVbTypes() throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		compile("src/main/resources/vb/ConsultarBasFisicaFATCA.xmlvb", "src/main/resources/vb/vbTypes.xsl",
				"src/main/resources/vb/types/ConsultarBasFisicaFATCA-types.csv", BULK_TO_FILE, false);
	}

	@SuppressWarnings("unused")
	private void parseVbSTypes() throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		compile("src/main/resources/vb/ConsultaBasFisFATCA_S.xmlvb", "src/main/resources/vb/vbTypes.xsl",
				"src/main/resources/vb/types/ConsultaBasFisFATCA_S-types.csv", BULK_TO_FILE, false);
	}

	@SuppressWarnings("unused")
	private void parseVbETypes() throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		compile("src/main/resources/vb/ConsultarBasFisFATCA_E.xmlvb", "src/main/resources/vb/vbTypes.xsl",
				"src/main/resources/vb/types/ConsultarBasFisFATCA_E-types.csv", BULK_TO_FILE, false);
	}

	@SuppressWarnings("unused")
	private void parseOpiTypes() throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		compile("src/main/resources/opi/consultarBasFisFATCA.xmlopi", "src/main/resources/opi/opiTrxopTypes.xsl",
				"src/main/resources/vb/types/consultarBasFisFATCA-types.csv", BULK_TO_FILE, false);
	}

	@SuppressWarnings("unused")
	private void parseOpiMapping() throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		compile("src/main/resources/opi/consultarBasFisFATCA.xmlopi", "src/main/resources/opi/opiTrxopMapping.xsl",
				"src/main/resources/opi/consultarBasFisFATCA-mapping.csv", BULK_TO_FILE, false);
	}

	@SuppressWarnings("unused")
	private void parseSimpleTypes() throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		compile("src/main/resources/simpleTypes/ws_TDCs.xsd", "src/main/resources/simpleTypes/simpleType.xsl",
				"src/main/resources/simpleTypes/simpleTypes1.csv", BULK_TO_FILE, false);
	}

	@SuppressWarnings("unused")
	private void parseComplexTypes() throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		compile("src/main/resources/complexTypes/ws_TDCc.xsd", "src/main/resources/complexTypes/complexType.xsl",
				"src/main/resources/complexTypes/complexTypes.csv", BULK_TO_FILE, false);
	}

	@SuppressWarnings("unused")
	private void parseVB() throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		compile("src/main/resources/consultarBasFisFATCA.xmlopi", "src/main/resources/xmlvb.xsl", "result.csv",
				BULK_TO_FILE, false);
	}

	@SuppressWarnings("unused")
	private void parseExample() throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		compile("src/main/resources/data.xml", "src/main/resources/style.xsl", "result.csv", BULK_TO_FILE, false);
	}

	@SuppressWarnings("unused")
	private void compile30(final String xmlSource, final String xslt, final String output)
			throws FileNotFoundException, SaxonApiException {

		final Processor processor = new Processor(false);
		final XsltCompiler compiler = processor.newXsltCompiler();
		final XsltExecutable stylesheet = compiler.compile(new StreamSource(new FileInputStream(xslt)));

		final Serializer out = processor.newSerializer(new File(output));
		out.setOutputProperty(Serializer.Property.METHOD, "text");
		out.setOutputProperty(Serializer.Property.INDENT, "yes");

		final Xslt30Transformer transformer = stylesheet.load30();
		transformer.transform(new StreamSource(xmlSource), out);
	}

	@SuppressWarnings("unused")
	private void compile(final String xmlSource, final String xslt, final String output, final short out,
			final boolean fromClasspath) throws ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {

		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.parse(xmlSource);

		final Transformer transformer;

		if (fromClasspath) {

			InputStream inputStream = null;
			byte[] byteArray = null;
			try {
				inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xslt);
				if (inputStream == null) {
					throw new FileNotFoundException(xslt);
				}
				final StreamSource stylesource = new StreamSource(inputStream);
				transformer = TransformerFactory.newInstance().newTransformer(stylesource);
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					throw new IOException(xmlSource);
				}
			}

		} else {
			final StreamSource stylesource = new StreamSource(xslt);
			transformer = TransformerFactory.newInstance().newTransformer(stylesource);
		}

		final Source source = new DOMSource(document);

		if (output == null || "".equals(output)) {
			final StringWriter stringWriter = new StringWriter();
			final Result outputTarget = new StreamResult(stringWriter);
			transformer.transform(source, outputTarget);
			LOGGER.info(stringWriter.toString());
		} else {
			final Result outputTarget = new StreamResult(new File(output));
			transformer.transform(source, outputTarget);
		}

	}

}
