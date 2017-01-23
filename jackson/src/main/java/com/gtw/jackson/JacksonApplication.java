package com.gtw.jackson;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gtw.jackson.model.generalAnnotations.GeneralBean;
import com.gtw.jackson.model.inclusionAnnotations.*;
import com.gtw.jackson.model.polymorphic.Zoo;
import com.gtw.jackson.model.serialization.*;
import com.gtw.jackson.model.deserialization.*;
import com.gtw.jackson.utils.ConvertUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class JacksonApplication {

    public static void main(String[] args) throws Exception {
        System.out.println("序列化:");

        //JsonProperty & JsonFormat
        GeneralBean generalBean = new GeneralBean(1, "LISI", new Date());
        System.out.println(ConvertUtils.obj2json(generalBean));
        String json = "{\"id\":1,\"eventDate\":\"13-01-2017 10:23:33\",\"name\":\"LISI\"}";
        System.out.println(ConvertUtils.Json2Obj(json, GeneralBean.class));

        //JsonPropertyOrder指定序列化顺序
        JsonPropertyOrderBean jsonPropertyOrderBean = new JsonPropertyOrderBean(1, "hello world");
        System.out.println(ConvertUtils.obj2json(jsonPropertyOrderBean));

        //JsonRawValue嵌入一些定制的实体json字符串
        JsonRawValueBean jsonRawValueBean = new JsonRawValueBean("张三", "{\"attr\":false}");
        System.out.println(ConvertUtils.obj2json(jsonRawValueBean));

        //JsonValue序列整个实例对象的单一方法
        System.out.println(ConvertUtils.obj2json(JsonValueBean.TYPE2));

        //JsonRootName指定序列化的根名称
        JsonRootNameBean jsonRootNameBean = new JsonRootNameBean(1, "张三");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);//需要指定使用根名称包装
        System.out.println(mapper.writeValueAsString(jsonRootNameBean));

        //JsonUnwrapped将对象解压到只显示属性
        JsonUnwrappedBean.Name name1 = new JsonUnwrappedBean.Name("John", "Doe");
        JsonUnwrappedBean jsonUnwrappedBean = new JsonUnwrappedBean(1, name1);
        System.out.println(ConvertUtils.obj2json(jsonUnwrappedBean));
        String json11 = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"}";
        System.out.println(ConvertUtils.Json2Obj(json11, JsonUnwrappedBean.class));

        //JsonGetter将一个特殊方法标注为指定属性的Getter方法
        JsonGetterBean jsonGetterBean = new JsonGetterBean(1, "world");
        System.out.println(ConvertUtils.obj2json(jsonGetterBean));

        //JsonAnyGetter将Map key-value作为对象标准属性输出
        JsonAnyGetterBean jsonAnyGetterBean = new JsonAnyGetterBean("My bean");
        jsonAnyGetterBean.add("attr1", "val1");
        jsonAnyGetterBean.add("attr2", "val2");
        System.out.println(ConvertUtils.obj2json(jsonAnyGetterBean));

        //JsonSerialize自定义序列化方式
        JsonSerializeBean jsonSerializeBean = new JsonSerializeBean("part", new Date());
        System.out.println(ConvertUtils.obj2json(jsonSerializeBean));


        System.out.println("------------------");
        System.out.println("反序列化:");

        //JsonCreator反序列化时调用的构造函数
        String json1 = "{\"id\":1,\"theName\":\"My bean\"}";
        JsonCreatorBean jsonCreatorBean = ConvertUtils.Json2Obj(json1, JsonCreatorBean.class);
        System.out.println(jsonCreatorBean);

        //JacksonInject某属性值通过注入获得
        String json2 = "{\"name\":\"My bean\"}";
        InjectableValues inject = new InjectableValues.Std().addValue(int.class, 2);//实例化要注入的值
        JsonInjectBean jsonInjectBean = new ObjectMapper().reader(inject)
                                                          .forType(JsonInjectBean.class)
                                                          .readValue(json2);
        System.out.println(jsonInjectBean);

        //JsonSetter:JsonGetter反序列化方法
        String json4 = "{\"id\":1,\"name\":\"My bean\"}";
        JsonSetterBean jsonSetterBean = ConvertUtils.Json2Obj(json4, JsonSetterBean.class);
        System.out.println(jsonSetterBean);

        //JsonAnySetter:JsonAnyGetter反序列方法
        String json3 = "{\"name\":\"My bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";
        JsonAnySetterBean jsonAnySetterBean = new ObjectMapper().readerFor(JsonAnySetterBean.class).readValue(json3);
        System.out.println(jsonAnySetterBean);

        //JsonDeserializeBean:JsonSerialize反序列化方法
        String json5 = "{\"name\":\"party\",\"eventDate\":\"20-12-2014 02:30:00\"}";
        JsonDeserializeBean jsonDeserializeBean = new ObjectMapper().readerFor(JsonDeserializeBean.class).readValue(json5);
        System.out.println(jsonDeserializeBean);

        System.out.println("------------------");
        System.out.println("带有参数的序列化注解:");

        //JsonIgnore & JsonIgnoreProperties//序列化忽略指定的属性
        JsonIgnoreBean jsonIgnoreBean = new JsonIgnoreBean(1, "张三", "男");
        System.out.println(ConvertUtils.obj2json(jsonIgnoreBean));

        //JsonIgnoreType指定类型的所有属性被忽略
        JsonIgnoreTypeBean.Name name = new JsonIgnoreTypeBean.Name("John", "Doe");//Name被指定为IgnoreType，不会被序列化
        JsonIgnoreTypeBean jsonIgnoreTypeBean = new JsonIgnoreTypeBean(1, name);
        System.out.println(ConvertUtils.obj2json(jsonIgnoreTypeBean));

        //JsonInclude参与序列化级别（如：null不参与序列化）
        JsonIncludeBean jsonIncludeBean = new JsonIncludeBean(1,null);//name为null不参与序列化
        System.out.println(ConvertUtils.obj2json(jsonIncludeBean));

        //JsonAutoDetect哪些属性是可见的（如：此处是私有属性序列化时也是可见的）
        JsonAutoDetectBean jsonAutoDetectBean = new JsonAutoDetectBean(1, "张三");
        System.out.println(ConvertUtils.obj2json(jsonAutoDetectBean));

        System.out.println("------------------");
        System.out.println("多态类型处理:");

        //JsonTypeInfo & JsonSubTypes多态
        Zoo.Dog dog = new Zoo.Dog("lacy");
        Zoo zoo = new Zoo(dog);
        System.out.println(ConvertUtils.obj2json(zoo));
        String json6 = "{\"animal\":{\"type\":\"dog\",\"name\":\"lacy\",\"barkVolume\":0.0}}";
        System.out.println(ConvertUtils.Json2Obj(json6, Zoo.class));

        SpringApplication.run(JacksonApplication.class, args);

        //嵌套递归关系的json、自定义Jackson注解
    }

}
