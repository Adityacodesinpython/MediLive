package com.pharma_manager.MediLive.service.Impl;

import com.pharma_manager.MediLive.dto.UserDto;
import com.pharma_manager.MediLive.entity.UserEntity;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;


// makes custom arguments (in this case UserDTO) for tests
public class UserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of(UserDto.builder().userName("Aditya").passWord("Aditya").firstName("adi").lastName("tya").roles(new String[]{"USER", "ADMIN"}).build()),
            Arguments.of(UserDto.builder().userName("Ronaldo").passWord("Ronaldo").firstName("ro").lastName("naldo").roles(new String[]{"USER"}).build())
        );
    }
}
