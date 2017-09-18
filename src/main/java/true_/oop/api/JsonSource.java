package true_.oop.api;

import org.takes.rs.RsJson;

import javax.json.JsonStructure;

public interface JsonSource extends RsJson.Source {

    JsonStructure toJson();
}
