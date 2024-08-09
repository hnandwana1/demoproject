package com.allen.machinecoding.demo.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String name;
    private String address;
}
