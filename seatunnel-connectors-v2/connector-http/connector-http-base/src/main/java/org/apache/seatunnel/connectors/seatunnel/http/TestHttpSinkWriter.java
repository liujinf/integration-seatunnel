package org.apache.seatunnel.connectors.seatunnel.http;

import org.apache.seatunnel.api.sink.SinkWriter;
import org.apache.seatunnel.api.table.type.BasicType;
import org.apache.seatunnel.api.table.type.SeaTunnelDataType;
import org.apache.seatunnel.api.table.type.SeaTunnelRow;
import org.apache.seatunnel.api.table.type.SeaTunnelRowType;
import org.apache.seatunnel.connectors.seatunnel.http.config.HttpParameter;
import org.apache.seatunnel.connectors.seatunnel.http.sink.HttpSinkWriter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestHttpSinkWriter {
    public static void main(String[] args) throws IOException {
        SeaTunnelDataType[] seaTunnelDataTypes = new SeaTunnelDataType[] {BasicType.STRING_TYPE};
        String[] fieldNames = new String[] {"id"};
        SeaTunnelRowType seaTunnelRowType = new SeaTunnelRowType(fieldNames, seaTunnelDataTypes);
        HttpParameter httpParameter = new HttpParameter();
        httpParameter.setUrl("http://httpbin.org/post");
        Map<String, String> headers = Collections.singletonMap("Authorization", "Basic 9e32e859ef044462a257e1fc76730066");
        httpParameter.setHeaders(headers);
        HttpSinkWriter sinkWriter = new HttpSinkWriter(seaTunnelRowType, httpParameter);
        SeaTunnelRow seaTunnelRow = new SeaTunnelRow(1);
        seaTunnelRow.setField(0, "{\"id\":\"3366\"}");
        sinkWriter.write(seaTunnelRow);
    }
}
