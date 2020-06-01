///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.bits.BulkEmailSenderScheduler.configuration;
//
//
//import com.bits.BulkEmailSenderScheduler.utils.AppUtil;
//import com.google.gson.Gson;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.job.builder.FlowBuilder;
//import org.springframework.batch.core.job.flow.Flow;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.step.tasklet.TaskletStep;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//
///**
// *
// * @author mdshahadat.sarker
// */
//@Configuration
//@EnableBatchProcessing
//public class BatchConfig {
//
//    Logger logger = LoggerFactory.getLogger(BatchConfig.class);
//
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//
//    @Autowired
//    AppUtil appUtil;
//    
//    
//    @Autowired
//    Gson gson;
//
//    @Bean
//    public Job parallelStepsJob() {
//
//        Flow atmJob0 = new FlowBuilder<Flow>("atmJob0").start(taskletStep("0")).build();
//        Flow atmJob1 = new FlowBuilder<Flow>("atmJob1").start(taskletStep("1")).build();
//        Flow atmJob2 = new FlowBuilder<Flow>("atmJob2").start(taskletStep("2")).build();
//        Flow atmJob3 = new FlowBuilder<Flow>("atmJob3").start(taskletStep("3")).build();
//        Flow atmJob4 = new FlowBuilder<Flow>("atmJob4").start(taskletStep("4")).build();
//        Flow atmJob5 = new FlowBuilder<Flow>("atmJob5").start(taskletStep("5")).build();
//        Flow atmJob6 = new FlowBuilder<Flow>("atmJob6").start(taskletStep("6")).build();
//        Flow atmJob7 = new FlowBuilder<Flow>("atmJob7").start(taskletStep("7")).build();
//        
//        Flow atmJob8 = new FlowBuilder<Flow>("atmJob7").start(taskletStep("8")).build();
//        Flow atmJob9 = new FlowBuilder<Flow>("atmJob7").start(taskletStep("9")).build();
//        Flow atmJob10 = new FlowBuilder<Flow>("atmJob7").start(taskletStep("10")).build();
//        Flow atmJob11 = new FlowBuilder<Flow>("atmJob7").start(taskletStep("11")).build();
//        
//        Flow atmFlow = new FlowBuilder<Flow>("atmFlow")
//                .split(new SimpleAsyncTaskExecutor()).add(atmJob0, atmJob1, atmJob2, atmJob3, atmJob4, atmJob5, atmJob6, atmJob7, atmJob8, atmJob9, atmJob10, atmJob11).build();
//
//        Flow completed = new FlowBuilder<Flow>("completed").start(completedStep("completed")).build();
//
//        return (jobBuilderFactory.get("parallelFlowJob")
//                .incrementer(new RunIdIncrementer())
//                .start(atmFlow)
//                .next(completed)
//                .build()).build();
//
//    }
//
//    private TaskletStep taskletStep(String step) {
//
//        return stepBuilderFactory.get(step).tasklet((contribution, chunkContext) -> {
//            
//            logger.info("Spring boot batch job is workking... with STEP: " + step);
//            //Wrtite code to send email in there....
//            
//            return RepeatStatus.FINISHED;
//        }).build();
//
//    }
//
//    private TaskletStep completedStep(String step) {
//        return stepBuilderFactory.get(step).tasklet((contribution, chunkContext) -> {
//            
//            return RepeatStatus.FINISHED;
//        }).build();
//    }
//}
