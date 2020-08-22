package com.example.reimbursementonlinebackend.util;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Named("InstantUtil")
@Component
public class InstantsUtil {

    @Named("ConvertInstantToEpochSecond")
    public Long convertInstantToEpoch(Instant source) {
        return (null == source) ? 0L : source.getEpochSecond();
    }
}
