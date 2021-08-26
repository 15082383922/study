package org.chancy;

import com.chancy.utils.TestUtils;
import org.opendaylight.yangtools.yang.model.api.Module;
import org.opendaylight.yangtools.yang.model.api.SchemaContext;
import org.opendaylight.yangtools.yang.model.parser.api.YangSyntaxErrorException;
import org.opendaylight.yangtools.yang.parser.spi.meta.ReactorException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException, YangSyntaxErrorException, ReactorException, URISyntaxException {
        URL resource = App.class.getResource("/yang");
        File file = new File(resource.toURI());
        SchemaContext schema = TestUtils.parseYangSources2(file);
        Optional<Module> module = Optional.empty();
        if (schema != null) {
            module = schema.getModules().stream().filter(e->e.getName().equals("chinaunicom-ipfrr-oper")).findFirst();
        }
        if (module.isPresent()) {
            Module module1 = module.get();
            System.out.printf(module1.getPrefix());
        }

    }


}
