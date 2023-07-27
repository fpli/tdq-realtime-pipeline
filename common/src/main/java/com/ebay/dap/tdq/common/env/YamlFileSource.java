package com.ebay.dap.tdq.common.env;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlFileSource extends AbstractEnvironment {

    private final String configFileName;
    private final Integer order;

    public YamlFileSource() {
        this.configFileName = "application";
        this.order = 999;
    }

    public YamlFileSource(String configFileName, Integer order) {
        this.configFileName = configFileName;
        this.order = order;
    }

    @Override
    public void sourceProps() {

        List<String> fileExtensions = Lists.newArrayList(".yml", ".yaml");

        for (String ext : fileExtensions) {
            InputStream yaml = YamlFileSource.class.getClassLoader().getResourceAsStream(configFileName + ext);

            if (yaml != null) {
                ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                try {
                    props = objectMapper.readValue(yaml, new TypeReference<Map<String, Object>>() {
                    });

                    props = flattenProps(props);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // load yaml file and return
                return;
            }
        }

    }

    @Override
    public Integer order() {
        return order;
    }

    private Map<String, Object> flattenProps(Map<String, Object> props) {
        Map<String, Object> flattenedProps = Maps.newHashMap();

        for (String key : props.keySet()) {
            if (props.get(key) instanceof Map) {
                nestReplace(key, (Map<String, Object>) props.get(key), flattenedProps);
            } else {
                flattenedProps.put(key, props.get(key));
            }
        }

        return flattenedProps;
    }

    private void nestReplace(String key, Map<String, Object> map, Map<String, Object> newProps) {
        for (String mapKey : map.keySet()) {
            String newKey = String.format("%s.%s", key, mapKey);
            if (map.get(mapKey) instanceof Map) {
                nestReplace(newKey, (Map<String, Object>) map.get(mapKey), newProps);
            } else {
                newProps.put(newKey, map.get(mapKey));
            }
        }
    }
}
