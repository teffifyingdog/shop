package com.wjc.service;

import com.wjc.entity.Goods;

import java.util.List;

public interface ISearchService {
    List<Goods> search(String keyword);
}
