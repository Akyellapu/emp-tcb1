package com.qentelli.employeetrackingsystem.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qentelli.employeetrackingsystem.entity.Account;
import com.qentelli.employeetrackingsystem.entity.Project;
import com.qentelli.employeetrackingsystem.models.client.request.AccountDetailsDto;
import com.qentelli.employeetrackingsystem.models.client.request.ProjectDTO;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
              .setFieldMatchingEnabled(true)
              .setFieldAccessLevel(AccessLevel.PRIVATE);

        // Skip audit and ID fields for Account mapping
        mapper.typeMap(AccountDetailsDto.class, Account.class)
              .addMappings(m -> {
                  m.skip(Account::setAccountId);
                  m.skip(Account::setCreatedAt);
                  m.skip(Account::setCreatedBy);
                  m.skip(Account::setUpdatedAt);
                  m.skip(Account::setUpdatedBy);
              });

        // Skip audit and ID fields for Project mapping
        mapper.typeMap(ProjectDTO.class, Project.class)
              .addMappings(m -> {
                  m.skip(Project::setProjectId);
                  m.skip(Project::setCreatedAt);
                  m.skip(Project::setCreatedBy);
                  m.skip(Project::setUpdatedAt);
                  m.skip(Project::setUpdatedBy);
              });

        return mapper;
    }
}