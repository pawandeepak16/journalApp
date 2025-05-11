package com.deepak.journalapp.service;

import com.deepak.journalapp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentSourceProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {

       return  Stream.of(
                    Arguments.of(User.builder().userName("dipak").password("dipak").build()),
                    Arguments.of(User.builder().userName("seema").password("seema").build()));
    }
}
