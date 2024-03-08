package com.pazindorb.statblockConverter.model.feature;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Feature {
    private String name;
    private String description;
    private FeatureType featureType;
}