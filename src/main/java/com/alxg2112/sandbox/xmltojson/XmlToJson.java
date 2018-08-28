package com.alxg2112.sandbox.xmltojson;

import org.json.XML;

/**
 * @author Alexander Gryshchenko
 */
public class XmlToJson {

	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<order merchant=\"gtest2\" affiliate=\"\" id=\"GTS2-U2303182214-22LLL\" date=\"1521843285\" event_id=\"1\" ref=\"SNH4H\" alias=\"\" merchant_sub_id=\"N/A\">\n" +
				"  <event type=\"sale\" date=\"2018-03-23 22:14:45\" status_code=\"SA\">\n" +
				"    <sale amount=\"5.00\" amount_usd=\"5.00\" method=\"VISA\" currency=\"USD\" processor=\"SafeCart API\"/>\n" +
				"    <coupon code=\"TESTCOUPON\" discount=\"-5.00\" initial_sale_price=\"10.00\"/>\n" +
				"    <tax amount=\"0.60\" amount_usd=\"0.60\" currency=\"USD\"/>\n" +
				"    <payout amount=\"4.25\" currency=\"USD\"/>\n" +
				"  </event>\n" +
				"  <customer>\n" +
				"    <name>Michael</name>\n" +
				"    <email>mluciani@revenuewire.com</email>\n" +
				"    <address/>\n" +
				"    <region>BC</region>\n" +
				"    <country>CA</country>\n" +
				"    <zip_postal>V8V1J9</zip_postal>\n" +
				"    <phone_number>123123</phone_number>\n" +
				"    <language>EN</language>\n" +
				"    <currency>USD</currency>\n" +
				"  </customer>\n" +
				"  <affiliate/>\n" +
				"  <products>\n" +
				"    <item sku=\"product2/1pk\" next_expected_date=\"\" quantity=\"1\">\n" +
				"      <sale amount=\"5.00\" currency=\"USD\"/>\n" +
				"      <tax amount=\"0.60\" currency=\"USD\"/>\n" +
				"      <payout amount=\"4.25\" currency=\"USD\"/>\n" +
				"      <license-key>OLX33-XZF33-38VYY-43EET</license-key>\n" +
				"    </item>\n" +
				"  </products>\n" +
				"  <extra>\n" +
				"    <request>AnyExtraTextHere%26test%3Dextra%26</request>\n" +
				"  </extra>\n" +
				"  <void/>\n" +
				"</order>";

		String json = XML.toJSONObject(xml).toString(4);

		System.out.println(json);
	}
}
