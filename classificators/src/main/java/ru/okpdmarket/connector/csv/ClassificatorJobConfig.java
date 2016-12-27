package ru.okpdmarket.connector.csv;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.okpdmarket.connector.csv.mapper.ClassificatorMapper;
import ru.okpdmarket.model.Classificator;

/**
 * Created by frostymaster on 25.12.2016.
 */
@Configuration
@EnableBatchProcessing
public class ClassificatorJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Classificator> reader() {
        FlatFileItemReader<Classificator> reader = new FlatFileItemReader<Classificator>();
        reader.setResource(new ClassPathResource("sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Classificator>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "code", "name" });
            }});
            setFieldSetMapper(new ClassificatorMapper());
        }});
        return reader;
    }

}
