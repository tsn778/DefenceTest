package com.test.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.test.demo.entity.Institute;

import com.test.demo.mapper.InstituteMapper;

import com.test.demo.service.InstituteService;
import org.springframework.stereotype.Service;

@Service
public class InstituteServiceImpl extends ServiceImpl<InstituteMapper, Institute> implements InstituteService {
}
