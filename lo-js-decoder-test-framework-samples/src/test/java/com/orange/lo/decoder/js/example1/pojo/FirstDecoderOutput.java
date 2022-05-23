/*
 * Copyright (C) 2019 Orange S.A.
 * 
 * This software is distributed under the terms and conditions of the '3-Clause BSD License'
 * license which can be found in the file 'LICENSE.txt' in this package distribution. 
 */

package com.orange.lo.decoder.js.example1.pojo;

import java.util.OptionalDouble;

import com.orange.lo.decoder.js.doc.annotation.FieldDescription;
import com.orange.lo.decoder.js.doc.annotation.OutputPojo;
import lombok.Data;

@Data
@OutputPojo
public class FirstDecoderOutput {
    @FieldDescription(description = "field description")
    private double field1;

    public OptionalDouble getField1() {
        OptionalDouble od = OptionalDouble.of(field1);
        return od;
    }
}
