package true_.oop.api;

import org.takes.rs.RsJson;

import javax.json.JsonStructure;

/**
 * Source with JSON.
 */
public interface JsonSource extends RsJson.Source {

    /**
     * Get JSON value.
     *
     * @return JSON
     */
    JsonStructure toJson();
}
