
package fr.kybox.gencode;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, LocalDateTime>
{


    public LocalDateTime unmarshal(String value) {
        return (fr.kybox.utils.LocalDateTimeAdapter.unmarshal(value));
    }

    public String marshal(LocalDateTime value) {
        return (fr.kybox.utils.LocalDateTimeAdapter.marshal(value));
    }

}
