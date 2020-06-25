package com.tuacy.chain.core.scan;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.io.IOException;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 2:27
 */
public class ReverseAssignableTypeFilter extends AssignableTypeFilter {
    public ReverseAssignableTypeFilter(Class<?> targetType) {
        super(targetType);
    }

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        return !super.match(metadataReader, metadataReaderFactory);
    }
}
