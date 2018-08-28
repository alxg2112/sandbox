package com.alxg2112.sandbox.xpath;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * @author Alexander Gryshchenko
 */
public class XpathSandbox {

	private static final String XML_FILE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<request>\n" +
			"\t<ids>\n" +
			"\t\t<value>42</value>\n" +
			"\t\t<value>13</value>\n" +
			"\t\t<value>38</value>\n" +
			"\t\t<value>14</value>\n" +
			"\t</ids>\n" +
			"\t<event type=\"refund\"/>\n" +
			"\t<license_key>4PKZ3-PJ7YG-4RGXE-4LUNX-P73PS</license_key>\n" +
			"</request>";

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document childDocument = builder.parse(IOUtils.toInputStream(XML_FILE, "UTF-8"));
		Element childElement = (Element) childDocument.getFirstChild();
//		XPath xPath = XPathFactory.newInstance().newXPath();
//		String expression = "/request/event/@type";
//		Node idsNode = (Node) xPath.compile(expression).evaluate(childDocument, XPathConstants.NODE);
//		System.out.println("'" + idsNode.getTextContent() + "'");

		Document parentDocument = builder.newDocument();
		Node parentNode = parentDocument.createElement("parent");
		parentDocument.appendChild(parentNode);
		parentDocument.adoptNode(childElement);
		parentNode.appendChild(childElement);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(new DOMSource(parentDocument), new StreamResult(System.out));
	}
}
