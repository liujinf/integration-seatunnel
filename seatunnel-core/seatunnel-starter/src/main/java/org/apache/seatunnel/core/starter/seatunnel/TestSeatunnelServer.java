package org.apache.seatunnel.core.starter.seatunnel;

import org.apache.seatunnel.core.starter.SeaTunnel;
import org.apache.seatunnel.core.starter.enums.MasterType;
import org.apache.seatunnel.core.starter.exception.CommandException;
import org.apache.seatunnel.core.starter.seatunnel.args.ClientCommandArgs;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class TestSeatunnelServer {
    public static void main(String[] args)
            throws FileNotFoundException, URISyntaxException, CommandException {
        String configFile = "D:/_repo/Study/apache-seatunnel-2.3/seatunnel-core/seatunnel-starter/src/main/testex/httpnoschema_to_http.conf";
        ClientCommandArgs clientCommandArgs = new ClientCommandArgs();
        clientCommandArgs.setConfigFile(configFile);
        clientCommandArgs.setCheckConfig(false);
        clientCommandArgs.setJobName(Paths.get(configFile).getFileName().toString());
        // Change Execution Mode to CLUSTER to use client mode, before do this, you should start
        // SeaTunnelEngineServerExample
        clientCommandArgs.setMasterType(MasterType.LOCAL);
        SeaTunnel.run(clientCommandArgs.buildCommand());
    }
}
