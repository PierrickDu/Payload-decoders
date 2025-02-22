/*
 * Copyright (C) 2019 Orange S.A.
 * 
 * This software is distributed under the terms and conditions of the '3-Clause BSD License'
 * license which can be found in the file 'LICENSE.txt' in this package distribution. 
 */

package com.orange.lo.decoder.js;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orange.decoder.exception.DecodingException;
import com.orange.decoder.exception.js.JsDecoderBuildingException;
import com.orange.iaes.commons.json.JsonEncodingException;
import com.orange.iaes.commons.json.JsonUtils;
import com.orange.decoder.split.SplitDecoderResult;
import com.orange.lo.decoder.js.exception.JsDecodingException;
import com.orange.lo.decoder.js.utils.DecoderUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class TestBase {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * This method loads the javascript code, formats the code as it will be provisioned (tabulations are removed, carriage returns are removed, slashes are
     * doubled), then runs the javascript decoder on a specified encoded input value.
     * 
     * @param scriptPath
     *            : the path of the javascript code (directory and file name without .js)
     * @param input
     *            : an encoded input test value
     * @param clazz
     *            : the class which contains the deserialized output
     * @return: T : instance of object containing the deserialized output
     * @throws JsDecodingException
     */
    protected <T> T formatAndDecode(String scriptPath, String input, Class<T> clazz) throws JsDecodingException {
        return formatAndDecode(scriptPath, input, null, clazz);
    }

    /**
     * This method loads the javascript code, formats the code as it will be provisioned (tabulations are removed, carriage returns are removed, slashes are
     * doubled), then runs the javascript decoder on a specified encoded input value.
     * 
     * @param scriptPath:
     *            the path of the javascript code (directory and file name without .js)
     * @param input:
     *            an encoded input test value
     * @param inputDataMessage:
     *            the full data message (if the decoding process needs to access any data message field)
     * @param clazz:
     *            the class which contains the deserialized output
     * @return: T : instance of object containing the deserialized output
     */
    protected <T> T formatAndDecode(String scriptPath, String input, String inputDataMessage, Class<T> clazz) throws JsDecodingException {
        String decodedJson = DecoderUtils.formatAndDecode(scriptPath, input, inputDataMessage);
        return GSON.fromJson(decodedJson, clazz);
    }

    /**
     * This method loads the javascript code, formats the code as it will be provisioned (tabulations are removed, carriage returns are removed, slashes are
     * doubled), then runs the javascript split decoder on a specified input DataMessage.
     *
     * @param scriptPath:
     *            the path of the javascript split code (directory and file name without .js)
     * @param inputDataMessage:
     *            the full data message
     * @param clazz:
     *            the class which contains the deserialized output
     * @return: T : instance of object containing the deserialized output
     * @throws JsDecodingException, JsonEncodingException
     */
    protected static <T extends ArrayList> T formatAndDecodeSplit(String scriptPath, String inputDataMessage, Class<T> clazz) throws JsDecodingException, JsonEncodingException {
        SplitDecoderResult decoderResult = DecoderUtils.formatAndDecodeSplit(scriptPath, inputDataMessage);

        String decoderResultAsString = JsonUtils.encode(decoderResult);
        T outputPojo = JsonUtils.decode(decoderResultAsString, clazz);

        return outputPojo;
    }

    protected void checkScript(String scriptPath) throws JsDecodingException {
        List<String> constraintValidationfailures = DecoderUtils.validateJavascript(scriptPath);
        try {
            constraintValidationfailures = DecoderUtils.validateJavascript(scriptPath);
            assertThat(constraintValidationfailures.size()).isEqualTo(0);
        } catch (JsDecodingException e) {
            throw new JsDecodingException(e.getMessage());
        }
	}

    /**
     * Please use the formatAndDecode method which will load the script, format the script and decode a test value.
     * 
     */
    @Deprecated
    protected static String loadJavascriptFile(final String scriptPath) throws IOException {
        return DecoderUtils.loadJavascriptFile(scriptPath);
    }

    /**
     * Please use the formatAndDecode method instead.
     * 
     */
    @Deprecated
    protected <T> T decode(String script, String input, Class<T> clazz) throws DecodingException, JsDecoderBuildingException, IOException {
        return decode(script, input, null, clazz);
    }

    /**
     * Please use the formatAndDecode method instead.
     * 
     */
    @Deprecated
    protected <T> T decode(String jsScript, String input, String inputDataMessage, Class<T> clazz) throws DecodingException, JsDecoderBuildingException, IOException {
        String decodedJson = DecoderUtils.decode(jsScript, input, inputDataMessage);
        return GSON.fromJson(decodedJson, clazz);
    }
}
