package com.qentelli.employeetrackingsystem.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qentelli.employeetrackingsystem.entity.Account;
import com.qentelli.employeetrackingsystem.models.client.request.AccountDetailsDto;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
              .setFieldMatchingEnabled(true)
              .setFieldAccessLevel(AccessLevel.PRIVATE);

        // Safely skip audit fields during mapping
        mapper.typeMap(AccountDetailsDto.class, Account.class)
              .addMappings(m -> {
                  m.skip(Account::setAccountId);
                  m.skip(Account::setCreatedAt);
                  m.skip(Account::setCreatedBy);
                  m.skip(Account::setUpdatedAt);
                  m.skip(Account::setUpdatedBy);
              });

        return mapper;
    }
}