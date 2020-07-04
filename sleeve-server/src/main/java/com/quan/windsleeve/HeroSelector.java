package com.quan.windsleeve;

import com.quan.windsleeve.OCPTest.reflect.HeroConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class HeroSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[] {HeroConfiguration.class.getName()};
    }
}
