
package fr.kybox.gencode;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1 extends XmlAdapter<String, Date> {

    public Date unmarshal(String value) {
        if(value != null && !value.isEmpty())
            return (org.apache.cxf.xjc.runtime.DataTypeAdapter.parseDate(value));
        else return null;
    }

    public String marshal(Date value) {
        return (org.apache.cxf.xjc.runtime.DataTypeAdapter.printDate(value));
    }

}
