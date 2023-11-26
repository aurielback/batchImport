package com.batchdemo.batchdemo.config;

import com.batchdemo.batchdemo.entity.Customer;
import com.batchdemo.batchdemo.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private final CustomerRepository customerRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;



    public SpringBatchConfig(CustomerRepository customerRepository, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.customerRepository = customerRepository;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    //ItemReader
    @Bean
    public FlatFileItemReader<Customer> customerFlatFileItemReader() {
        return new FlatFileItemReader<>() {{
            setResource(new FileSystemResource("src/main/resources/customers.csv"));
            setName("csvReader");
            setLinesToSkip(1);
            setLineMapper(lineMapper());
        }};
    }

    //ItemProcessor
    @Bean
    public CustomerProcessor customerProcessor() {
        return new CustomerProcessor();
    }

    //ItemWriter
    @Bean
    public RepositoryItemWriter<Customer> customerWriter() {
        return new RepositoryItemWriter<>() {{
            setRepository(customerRepository);
            setMethodName("save");
        }};
    }

    //Step
    @Bean
    public Step step1() {
        return new StepBuilder("csv-step", jobRepository)
                .<Customer, Customer>chunk(10, transactionManager)
                .reader(customerFlatFileItemReader())
                .processor(customerProcessor())
                .writer(customerWriter())
                //.taskExecutor(taskExecutor())
                .build();
    }

    //Job
    @Bean
    public Job runJob() {
        return new JobBuilder("importCustomers", jobRepository)
                .start(step1())
                //.next(step2())
                .build();
    }

    /*@Bean
    public TaskExecutor taskExecutor() {
        try (SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor()) {
            taskExecutor.setConcurrencyLimit(10);
            return taskExecutor;
        }
    }*/

    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");
        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

}
