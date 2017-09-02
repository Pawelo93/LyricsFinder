package hexfan.lyrics;

import java.io.IOException;

import retrofit2.Converter;

/**
 * Created by Pawel on 23.06.2017.
 */

public class SimpleConverterFactory extends Converter.Factory implements Converter<String, String> {

    public static SimpleConverterFactory create(){
        return new SimpleConverterFactory();
    }

    @Override
    public String convert(String value) throws IOException {
        return value;
    }
}
