package com.chancy.quartz;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class Parse {
    public static void main1(String[] args) {
        String data = "/rds:radius/rds:radius-profiles/rds:radius-profile";
        String[] split = data.split("/");
        List<String> resultList = new ArrayList<>(split.length);
        for (int i = 1; i < split.length; i++) {
            resultList.add(split[i].substring(split[i].indexOf(":") + 1));
        }
        System.out.println(resultList.toString());
    }

    /**
     * AbsoluteSchemaPath{path=[
     * (http://chinaunicom.com/yang/securitymanage/chinaunicom-radius?revision=2020-09-16)radius,
     * (http://chinaunicom.com/yang/securitymanage/chinaunicom-radius?revision=2020-09-16)radius-profiles,
     * (http://chinaunicom.com/yang/securitymanage/chinaunicom-radius?revision=2020-09-16)radius-profile,
     * (http://chinaunicom.com/yang/securitymanage/chinaunicom-radius?revision=2020-09-16)domain-included]}
     */
    public static void main2(String[] args) {
        String data = "AbsoluteSchemaPath{path=[" +
                "(http://chinaunicom.com/yang/securitymanage/chinaunicom-radius?revision=2020-09-16)radius," +
                "(http://chinaunicom.com/yang/securitymanage/chinaunicom-radius?revision=2020-09-16)radius-profiles," +
                "(http://chinaunicom.com/yang/securitymanage/chinaunicom-radius?revision=2020-09-16)radius-profile," +
                "(http://chinaunicom.com/yang/securitymanage/chinaunicom-radius?revision=2020-09-16)domain-included]}";
        String priex = "/rds:";
        String[] split = data.split(",");
        StringBuilder resultList = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (i < split.length - 1) {
                resultList.append(priex + split[i].substring(split[i].indexOf(")") + 1));
            } else {
                String s = priex + split[i].substring(split[i].indexOf(")") + 1);
                resultList.append(s.substring(0, s.indexOf("]")));
            }
        }
        System.out.println(resultList.toString());
    }

    /*public static void main(String[] args) {
        String data = "[[0..128]]";
        data = data.replaceFirst("\\[", "").replaceFirst("\\]", "");
        String path = "/pm-oper:pm-clean/pm-oper:input/pm-oper:resource";
        String substring = path.substring(path.indexOf(":") + 1);
        System.out.println(path.substring(1,path.indexOf(":")));
    }*/

    public static void main6(String[] args) throws ParserConfigurationException, IOException, SAXException {

        /*获得（文件创建工厂）实例*/
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            /*获取DocumentBuilder对象，单例模式*/
            DocumentBuilder db = factory.newDocumentBuilder();
            /*The Document interface represents the entire HTML or XML document*/
            Document document = db.newDocument();
            document.setXmlStandalone(true);
            // Element bookstore=document.createElement("bookstore");
            Element bookstore = document.createElement("bookstore");
            Element book = document.createElement("book");
            Element name = document.createElement("name");
            Element name2 = document.createElement("name2");
            Element name3 = document.createElement("name3");
            Document document2 = db.newDocument();
            Element dsad = document2.createElement("dsad");
            bookstore.appendChild(dsad);
            //name.setTextContent("华");
            book.setAttribute("id", "1");
//            name.appendChild(name2);
//            book.appendChild(name);
            document.appendChild(bookstore).appendChild(name).appendChild(name2);
            document.appendChild(name3);
            bookstore.setAttribute("xmlns", "http://chinaunicom.com/yang/interface/ethinterface/chinaunicom-ethif-phy");
            bookstore.appendChild(book);
            output(bookstore);
            /*输出文件到XML中*/
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            try {
                /*转换器*/
                Transformer tf = transformerFactory.newTransformer();

                /**设置输出性质  Provides string constants that can be used to set
                 * output properties for a Transformer, or to retrieve output
                 *  properties from a Transformer or Templates object.
                 *  提供字符串常数被用去设置输出属性从转换器中，或者去恢复输出属性从转换器或模版对象中。
                 *
                 *  */
                tf.setOutputProperty(OutputKeys.INDENT, "yes");
                /*输出文件到XML中*/
                tf.transform(new DOMSource(document),
                        new StreamResult(new File("book2.xml")));


            } catch (TransformerConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public static void output(Node node) {//将node的XML字符串输出到控制台
        TransformerFactory transFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.setOutputProperty("indent", "yes");

            DOMSource source = new DOMSource();
            source.setNode(node);
            StreamResult result = new StreamResult();
            result.setOutputStream(System.out);

            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void main5(String[] args) throws ParserConfigurationException {
        String s = "/rds:radius/rds:radius-profiles/rds:radius-profile/rds:radius-servers/rds:radius-server";
        String[] split = s.split("/");
        String value1 = "BBB";
        String operPath = "/rds:radius/rds:radius-profiles/rds:radius-profile/rds:radius-profile-name";
        String name = operPath.split("/")[operPath.split("/").length - 1].
                substring(operPath.split("/")[operPath.split("/").length - 1].indexOf(":") + 1);
        String s1 = operPath.split("/")[operPath.split("/").length - 2];
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        /*获取DocumentBuilder对象，单例模式*/
        DocumentBuilder db = factory.newDocumentBuilder();
        /*The Document interface represents the entire HTML or XML document*/
        Document document = db.newDocument();
        document.setXmlStandalone(true);
        List<Element> elements = new ArrayList<>();
        for (String value : split) {
            if (value.contains(":")) {
                String labelName = value.substring(value.indexOf(":") + 1);
                Element element = document.createElement(labelName);
                element.setAttribute("xmlns", "http://chinaunicom.com/yang/securitymanage/chinaunicom-radius");
                elements.add(element);
            }
        }
        for (int j = 0; j < elements.size() - 1; j++) {
            elements.get(j).appendChild(elements.get(j + 1));
        }
        Element element = document.createElement("server-type");
        element.setTextContent("acct");
        Element element1 = document.createElement("server-addr");
        element1.setTextContent("133.175.114.14%2");
        Element element2 = document.createElement("vpn-name");
        Element element3 = document.createElement("server-mode");
        element3.setTextContent("authen-secondary-server");
        Element element4 = document.createElement("server-port");
        element4.setTextContent("1");
        Element element5 = document.createElement("source-ip");
        element5.setTextContent("134.157.107.43%۰");
        Element element6 = document.createElement("weight");
        element6.setTextContent("0");
        elements.get(elements.size() - 1).appendChild(element);
        elements.get(elements.size() - 1).appendChild(element1);
        elements.get(elements.size() - 1).appendChild(element2);

        elements.get(elements.size() - 1).appendChild(element3);
        output(elements.get(0));
    }

    public static void main7(String[] args) throws IOException {

        Properties pro = new Properties();
        FileInputStream fis = new FileInputStream("C:\\Users\\HZ08950\\Desktop\\lock.json");
        pro.load(fis);//读取键值对 pro.load(fr);
        String str = pro.getProperty("lock");//取值
        fis.close(); //fr.close();
        System.out.println(str);
    }

    public static void main8(String[] args) throws ParserConfigurationException {
        String s = "power-cmd";
        String[] split = s.split("/");
        String value1 = "BBB";
        String operPath = "/rds:radius/rds:radius-profiles/rds:radius-profile/rds:radius-profile-name";
        String name = operPath.split("/")[operPath.split("/").length - 1].
                substring(operPath.split("/")[operPath.split("/").length - 1].indexOf(":") + 1);
        String s1 = operPath.split("/")[operPath.split("/").length - 2];
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        /*获取DocumentBuilder对象，单例模式*/
        DocumentBuilder db = factory.newDocumentBuilder();
        /*The Document interface represents the entire HTML or XML document*/
        Document document = db.newDocument();
        document.setXmlStandalone(true);
        List<Element> elements = new ArrayList<>();
        for (String value : split) {
            if (value.contains(":")) {
                String labelName = value.substring(value.indexOf(":") + 1);
                Element element = document.createElement(labelName);
                element.setAttribute("xmlns", "http://chinaunicom.com/yang/securitymanage/chinaunicom-radius");
                elements.add(element);
            }
        }
        for (int j = 0; j < elements.size() - 1; j++) {
            elements.get(j).appendChild(elements.get(j + 1));
        }
        Element element = document.createElement("server-type");
        element.setTextContent("acct");
        Element element1 = document.createElement("server-addr");
        element1.setTextContent("133.175.114.14%2");
        Element element2 = document.createElement("vpn-name");
        Element element3 = document.createElement("server-mode");
        element3.setTextContent("authen-secondary-server");
        Element element4 = document.createElement("server-port");
        element4.setTextContent("1");
        Element element5 = document.createElement("source-ip");
        element5.setTextContent("134.157.107.43%۰");
        Element element6 = document.createElement("weight");
        element6.setTextContent("0");
        elements.get(elements.size() - 1).appendChild(element);
        elements.get(elements.size() - 1).appendChild(element1);
        elements.get(elements.size() - 1).appendChild(element2);

        elements.get(elements.size() - 1).appendChild(element3);
        output(elements.get(0));
    }

    public static void main23(String[] args) {
        String path = "/if:if/if:interfaces/if:interface/ethphyif:ethphy-interface/ethphyif:sf-cfg";
        String substring = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(":"));
        System.out.println(substring);
    }

    public static void main29(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        /*获取DocumentBuilder对象，单例模式*/
        DocumentBuilder db = factory.newDocumentBuilder();

        /*The Document interface represents the entire HTML or XML document*/
        String name = "<data xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">" +
                "<isis:isis xmlns:isis=\"http://chinaunicom.com/yang/ipprotocol/routing/chinaunicom-isis\">" +
                "<isis:isis-name>150</isis:isis-name>" +
                "<isis:isis-instances>" +
                "<isis:isis-instance>" +
                "<isis:instance-name>2</isis:instance-name>" +
                "<isis:lsp-mtu>1492</isis:lsp-mtu>" +
                "<isis:lsp-lifetime>1200</isis:lsp-lifetime>" +
                "<isis:fast-flood-num>20</isis:fast-flood-num>" +
                "<isis:sid-map-receive>" +
                "<isis:enable>enable</isis:enable>" +
                "</isis:sid-map-receive>" +
                "<isis:micro-loop-avoidance>" +
                "<isis:avoidance-flag>enable</isis:avoidance-flag>" +
                "<isis:t0-timer>4</isis:t0-timer>" +
                "<isis:t1-timer>4</isis:t1-timer>" +
                "<isis:t2-timer>4</isis:t2-timer>" +
                "</isis:micro-loop-avoidance>" +
                "<isis:lsp-refresh-interval>" +
                "<isis:interval>900</isis:interval>" +
                "</isis:lsp-refresh-interval>" +
                "<isis:overload-bit>" +
                "<isis:interval>600</isis:interval>" +
                "</isis:overload-bit>" +
                "</isis:isis-instance>" +
                "<isis:isis-instance>" +
                "<isis:instance-name>44</isis:instance-name>" +
                "<isis:lsp-mtu>1492</isis:lsp-mtu>" +
                "<isis:lsp-lifetime>1200</isis:lsp-lifetime>" +
                "<isis:fast-flood-num>20</isis:fast-flood-num>" +
                "<isis:sid-map-receive>" +
                "<isis:enable>enable</isis:enable>" +
                "</isis:sid-map-receive>" +
                "<isis:micro-loop-avoidance>" +
                "<isis:avoidance-flag>enable</isis:avoidance-flag>" +
                "<isis:t0-timer>5</isis:t0-timer>" +
                "<isis:t1-timer>5</isis:t1-timer>" +
                "<isis:t2-timer>5</isis:t2-timer>" +
                "</isis:micro-loop-avoidance>" +
                "<isis:lsp-refresh-interval>" +
                "<isis:interval>900</isis:interval>" +
                "</isis:lsp-refresh-interval>" +
                "<isis:overload-bit>" +
                "<isis:interval>600</isis:interval>" +
                "</isis:overload-bit>" +
                "</isis:isis-instance>" +
                "<isis:isis-instance>" +
                "<isis:instance-name>555</isis:instance-name>" +
                "<isis:lsp-mtu>1492</isis:lsp-mtu>" +
                "<isis:lsp-lifetime>1200</isis:lsp-lifetime>" +
                "<isis:fast-flood-num>20</isis:fast-flood-num>" +
                "<isis:sid-map-receive>" +
                "<isis:enable>enable</isis:enable>" +
                "</isis:sid-map-receive>" +
                "<isis:micro-loop-avoidance>" +
                "<isis:avoidance-flag>enable</isis:avoidance-flag>" +
                "<isis:t0-timer>5</isis:t0-timer>" +
                "<isis:t1-timer>5</isis:t1-timer>" +
                "<isis:t2-timer>5</isis:t2-timer>" +
                "</isis:micro-loop-avoidance>" +
                "<isis:lsp-refresh-interval>" +
                "<isis:interval>900</isis:interval>" +
                "</isis:lsp-refresh-interval>" +
                "<isis:overload-bit>" +
                "<isis:interval>600</isis:interval>" +
                "</isis:overload-bit>" +
                "</isis:isis-instance>" +
                "</isis:isis-instances>" +
                "<isis:graceful-restart>" +
                "<isis:gr-period-timer>65535</isis:gr-period-timer>" +
                "</isis:graceful-restart>" +
                "</isis:isis>" +
                "</data>";
        String string = "<isis:isis xmlns:isis=\"http://chinaunicom.com/yang/ipprotocol/routing/chinaunicom-isis\">" +
                "<isis:isis-instances>" +
                "<isis:isis-instance>" +
                "<isis:instance-name>2</isis:instance-name>" +
                "<isis:lsp-mtu>1492</isis:lsp-mtu>" +
                "<isis:lsp-lifetime>1200</isis:lsp-lifetime>" +
                "<isis:fast-flood-num>20</isis:fast-flood-num>" +
                "<isis:sid-map-receive>" +
                "<isis:enable>enable</isis:enable>" +
                "</isis:sid-map-receive>" +
                "<isis:micro-loop-avoidance>" +
                "<isis:avoidance-flag>enable</isis:avoidance-flag>" +
                "<isis:t0-timer>4</isis:t0-timer>" +
                "<isis:t1-timer>4</isis:t1-timer>" +
                "<isis:t2-timer>4</isis:t2-timer>" +
                "</isis:micro-loop-avoidance>" +
                "<isis:lsp-refresh-interval>" +
                "<isis:interval>900</isis:interval>" +
                "</isis:lsp-refresh-interval>" +
                "<isis:overload-bit>" +
                "<isis:interval>600</isis:interval>" +
                "</isis:overload-bit>" +
                "<isis:af>" +
                "<isis:af-name>ipv4</isis:af-name>" +
                "<isis:saf-name>unicast</isis:saf-name>" +
                "<isis:af-data>" +
                "<isis:redistribution>" +
                "<isis:protocol-name>static</isis:protocol-name>" +
                "<isis:protocol-instance-id>0</isis:protocol-instance-id>" +
                "<isis:protocol-instance-name>nil</isis:protocol-instance-name>" +
                "<isis:levels>level2</isis:levels>" +
                "<isis:route-policy>" +
                "<isis:config>enable</isis:config>" +
                "<isis:route-policy-name>test</isis:route-policy-name>" +
                "</isis:route-policy>" +
                "</isis:redistribution>" +
                "<isis:redistribution>" +
                "<isis:protocol-name>ospf</isis:protocol-name>" +
                "<isis:protocol-instance-id>2</isis:protocol-instance-id>" +
                "<isis:protocol-instance-name>nil</isis:protocol-instance-name>" +
                "<isis:levels>level2</isis:levels>" +
                "<isis:route-policy>" +
                "<isis:config>enable</isis:config>" +
                "<isis:route-policy-name>2</isis:route-policy-name>" +
                "</isis:route-policy>" +
                "</isis:redistribution>" +
                "</isis:af-data>" +
                "</isis:af>" +
                "</isis:isis-instance>" +
                "<isis:isis-instance>" +
                "<isis:instance-name>44</isis:instance-name>" +
                "<isis:lsp-mtu>1492</isis:lsp-mtu>" +
                "<isis:lsp-lifetime>1200</isis:lsp-lifetime>" +
                "<isis:fast-flood-num>20</isis:fast-flood-num>" +
                "<isis:sid-map-receive>" +
                "<isis:enable>enable</isis:enable>" +
                "</isis:sid-map-receive>" +
                "<isis:micro-loop-avoidance>" +
                "<isis:avoidance-flag>enable</isis:avoidance-flag>" +
                "<isis:t0-timer>5</isis:t0-timer>" +
                "<isis:t1-timer>5</isis:t1-timer>" +
                "<isis:t2-timer>5</isis:t2-timer>" +
                "</isis:micro-loop-avoidance>" +
                "<isis:lsp-refresh-interval>" +
                "<isis:interval>900</isis:interval>" +
                "</isis:lsp-refresh-interval>" +
                "<isis:overload-bit>" +
                "<isis:interval>600</isis:interval>" +
                "</isis:overload-bit>" +
                "<isis:af>" +
                "<isis:af-name>ipv4</isis:af-name>" +
                "<isis:saf-name>unicast</isis:saf-name>" +
                "<isis:af-data>" +
                "<isis:redistribution>" +
                "<isis:protocol-name>ospf</isis:protocol-name>" +
                "<isis:protocol-instance-id>2</isis:protocol-instance-id>" +
                "<isis:protocol-instance-name>nil</isis:protocol-instance-name>" +
                "<isis:levels>level2</isis:levels>" +
                "<isis:route-policy>" +
                "<isis:config>enable</isis:config>" +
                "<isis:route-policy-name>2</isis:route-policy-name>" +
                "</isis:route-policy>" +
                "</isis:redistribution>" +
                "</isis:af-data>" +
                "</isis:af>" +
                "</isis:isis-instance>" +
                "<isis:isis-instance>" +
                "<isis:instance-name>555</isis:instance-name>" +
                "<isis:lsp-mtu>1492</isis:lsp-mtu>" +
                "<isis:lsp-lifetime>1200</isis:lsp-lifetime>" +
                "<isis:fast-flood-num>20</isis:fast-flood-num>" +
                "<isis:sid-map-receive>" +
                "<isis:enable>enable</isis:enable>" +
                "</isis:sid-map-receive>" +
                "<isis:micro-loop-avoidance>" +
                "<isis:avoidance-flag>enable</isis:avoidance-flag>" +
                "<isis:t0-timer>5</isis:t0-timer>" +
                "<isis:t1-timer>5</isis:t1-timer>" +
                "<isis:t2-timer>5</isis:t2-timer>" +
                "</isis:micro-loop-avoidance>" +
                "<isis:lsp-refresh-interval>" +
                "<isis:interval>900</isis:interval>" +
                "</isis:lsp-refresh-interval>" +
                "<isis:overload-bit>" +
                "<isis:interval>600</isis:interval>" +
                "</isis:overload-bit>" +
                "</isis:isis-instance>" +
                "<isis:isis-instance>" +
                "<isis:instance-name>chancy</isis:instance-name>" +
                "<isis:lsp-mtu>1492</isis:lsp-mtu>" +
                "<isis:lsp-lifetime>1200</isis:lsp-lifetime>" +
                "<isis:fast-flood-num>20</isis:fast-flood-num>" +
                "<isis:sid-map-receive>" +
                "<isis:enable>enable</isis:enable>" +
                "</isis:sid-map-receive>" +
                "<isis:micro-loop-avoidance>" +
                "<isis:t0-timer>200</isis:t0-timer>" +
                "<isis:t1-timer>10000</isis:t1-timer>" +
                "<isis:t2-timer>20000</isis:t2-timer>" +
                "</isis:micro-loop-avoidance>" +
                "<isis:lsp-refresh-interval>" +
                "<isis:interval>900</isis:interval>" +
                "</isis:lsp-refresh-interval>" +
                "<isis:overload-bit>" +
                "<isis:interval>600</isis:interval>" +
                "</isis:overload-bit>" +
                "<isis:isis-interfaces>" +
                "<isis:isis-interface>" +
                "<isis:interface-name>sss</isis:interface-name>" +
                "<isis:retransmit-lsp-interval>" +
                "<isis:interval>5</isis:interval>" +
                "</isis:retransmit-lsp-interval>" +
                "<isis:min-lsp-interval>" +
                "<isis:interval>33</isis:interval>" +
                "</isis:min-lsp-interval>" +
                "<isis:ldp-sync>" +
                "<isis:holddown-timer>70000</isis:holddown-timer>" +
                "</isis:ldp-sync>" +
                "<isis:hello-password>" +
                "<isis:level>level1</isis:level>" +
                "<isis:algorithm>simple</isis:algorithm>" +
                "<isis:auth-type>send-only</isis:auth-type>" +
                "<isis:password>2</isis:password>" +
                "<isis:keychain-name>2</isis:keychain-name>" +
                "</isis:hello-password>" +
                "</isis:isis-interface>" +
                "</isis:isis-interfaces>" +
                "</isis:isis-instance>" +
                "</isis:isis-instances>" +
                "<isis:graceful-restart>" +
                "<isis:gr-period-timer>65535</isis:gr-period-timer>" +
                "</isis:graceful-restart>" +
                "</isis:isis>";
        Document docMsg = db.parse(new InputSource(new StringReader(string)));
        docMsg.setXmlStandalone(true);
        key.put("isis:instance-name", "isis:isis-instance");
        key.put("isis:af-name", "isis:af");
        key.put("isis:saf-name", "isis:af");
        key.put("isis:protocol-name", "isis:redistribution");
        key.put("isis:protocol-instance-id", "isis:redistribution");
        key.put("isis:protocol-instance-name", "isis:redistribution");

        pd.add("isis:isis");
        pd.add("isis:isis-instances");
        pd.add("isis:isis-instance");
        pd.add("isis:af");
        pd.add("isis:af-data");
        pd.add("isis:redistribution");
        pd.add("isis:route-policy");
        pd.add("isis:config");
        pd.add("isis:route-policy-name");
        Set<String> strings = key.keySet();
        for (String s : strings) {
            pre.add(key.get(s));
        }
        parses(docMsg);
        System.out.println(data);
    }

    static List<String> pre = new ArrayList<>();
    static Map<String, String> key = new LinkedHashMap<>();
    static Map<String, String> keys = new LinkedHashMap<>();
    static Map<Map<String, String>, Map<String, String>> data = new LinkedHashMap<>();
    static String name = "isis:route-policy";
    static List<String> pd = new ArrayList<>();

    private static void parses(Node firstChild) {
        NodeList childNodes = firstChild.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (Objects.equals(childNodes.item(i).getNodeName(), name)) {
                add(childNodes.item(i));
            } else {
                parses(childNodes.item(i));
            }
            if (key.containsKey(childNodes.item(i).getNodeName())) {
                keys.put(childNodes.item(i).getNodeName(), childNodes.item(i).getFirstChild().getNodeValue());
            }

        }
    }

    private static void add(Node item) {
        NodeList childNodes = item.getChildNodes();
        Map<String, String> da = new LinkedHashMap<>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            da.put(childNodes.item(i).getNodeName(), childNodes.item(i).getFirstChild().getNodeValue());
        }
        Map<String, String> key = new LinkedHashMap<>();
        Set<String> strings = keys.keySet();
        for (String s : strings) {
            key.put(s, keys.get(s));
        }
        data.put(key, da);
    }

    public static void main43(String[] args) {
        String perf = "PerSubIfCur.V0";
        String[] split = perf.split(".V", 2);
        System.out.println(split);
    }


    public static void main434(String[] args) {
        String data = "(http://chinaunicom.com/yang/vpnservice/chinaunicom-l3vpn?revision=2019-10-24)chinaunicom-l3vpn";
        System.out.println(data.substring(data.indexOf("=") + 1, data.indexOf(")")));
    }

    public static void main2324(String[] args) {
        String softwareVersion = "V1.1.0.3_sp21";
        softwareVersion = softwareVersion.replaceFirst("V", "").substring(0, 5);
        System.out.println(softwareVersion);
    }

    public static void main222(String[] args) {
        Map<String, List<String>> action = new LinkedHashMap<>();

        String jsonString;
        jsonString = "{\n" +
                "    \"list\": [\n" +
                "        {\n" +
                "            \"list\": \"/alarm:alarm/alarm:inverse-ports/alarm:inverse-port\",\n" +
                "            \"action\": [\n" +
                "                \"modify\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"list\": \"/alarm:alarm/alarm:alarm-cfgs/alarm:alarm-cfg\",\n" +
                "            \"action\": [\n" +
                "                \"modify\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        JsonArray field_refers = jsonObject.getAsJsonArray("list");
        for (JsonElement jsonElement : field_refers) {
            String list = jsonElement.getAsJsonObject().get("list").getAsString();
            JsonArray actions = jsonElement.getAsJsonObject().getAsJsonArray("action");
            List<String> strings = new LinkedList<>();
            for (JsonElement element : actions) {
                strings.add(element.getAsString());
            }

            action.put(list, strings);
        }

        System.out.println(bytes2HexString(Integer.parseInt("-1")));
    }

    public static String bytes2HexString(int b) {
        String ret = "";
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        ret += hex;

        return ret;
    }

    public static void main(String[] args) {
        String when = "(../../if:type = 'ETH-SUBIF' or ../if:type = 'DCN')";
        StringBuilder sb = getStringBuilder(when);
        System.out.println(sb);



       /* String[] split = when.split("[(]");
        StringBuilder builder = new StringBuilder();
        for (String s : split) {
            if (s.contains("../")) {
                if (s.contains("and")) {
                    String[] ors = s.split("and");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String or : ors) {
                        StringBuilder sb = new StringBuilder();
                        if (s.contains("or")) {
                            String[] strings = s.split("or");
                            for (int i = 0; i < strings.length; i++) {
                                if (strings[i].contains("../")) {
                                    sb.append("../").append(strings[i].trim());
                                } else {
                                    sb.append(strings[i]);
                                }
                                if (i != strings.length - 1) {
                                    sb.append(" or ");
                                }
                            }
                            stringBuilder.append("../").append(or.trim()).append(" and ");
                        }
                        stringBuilder.append("../").append(or.trim()).append(" and ");
                    }
                }
                String s1 = "(";
                builder.append(s1);
            }
        }*/
    }

    private static StringBuilder getStringBuilder(String when) {
        String replace = when.replaceAll("\\s", " ");
        System.out.println(replace);
        StringBuilder sb = new StringBuilder();
        if (when.contains(" and ")) {
            String[] split = replace.split(" and ");
            for (int i = 0; i < split.length; i++) {
                if (split[i].contains(" or ")) {
                    String[] split1 = split[i].split(" or ");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < split1.length; j++) {
                        stringBuilder.append(extracted(split1[j]));
                        if (j != split.length - 1) {
                            stringBuilder.append(" or ");
                        }
                    }
                    sb.append(stringBuilder);
                } else {
                    sb.append(extracted(split[i]));
                }
                if (i != split.length - 1) {
                    sb.append(" and ");
                }
            }
        } else if(when.contains(" or ")){
            String[] split1 = when.split(" or ");
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < split1.length; j++) {
                stringBuilder.append(extracted(split1[j]));
                if (j != split1.length - 1) {
                    stringBuilder.append(" or ");
                }
            }
            sb.append(stringBuilder);

        }else {
            sb.append(extracted(when));
        }
        return sb;
    }

    private static StringBuilder extracted(String split1) {
        StringBuilder stringBuilder1 = new StringBuilder();
        if (split1.contains("../")) {
            String s3 = split1.replaceFirst("../", "../../");
            stringBuilder1.append(s3);
        } else if (split1.trim().startsWith("/") || split1.trim().startsWith("(/")) {
            stringBuilder1.append(split1);
        } else if (split1.startsWith("(")) {
            stringBuilder1.append("(").append("../../").append(split1.trim().substring(1));
        } else {
            stringBuilder1.append("../../").append(split1.trim());
        }
        return stringBuilder1;
    }


}
