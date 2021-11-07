package cn.malong.blog.service;


import cn.malong.blog.pojo.Type;

public interface TypesService {
    String getTypesByLimit(int page,int limit);

    String typeAdd(Type type);

    String typeUpdate(Type type);

    String typeDelete(int id);
}
