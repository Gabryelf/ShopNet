package com.example.ShopNet.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class IntegrationConfig {
    @Bean
    public MessageChannel mcInput(){
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mcFileWriter(){
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "mcInput", outputChannel = "mcFileWriter")
    public GenericTransformer<String, String> myTransformer(){
        return text -> {return text;};
    }

    @Bean
    @ServiceActivator(inputChannel = "mcFileWriter")
    public FileWritingMessageHandler myFileWriter(){
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File( "D:\\java_lessons\\ShopNet\\src\\main\\resources\\files" ) );
        handler.setExpectReply( false );
        handler.setFileExistsMode( FileExistsMode.APPEND );
        handler.setAppendNewLine( true );
        return handler;
    }
}
