package com.gtw.jpa.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass// 表示它本身并不是受管理的实体，而是由其他的实体类进行扩展
@Data
public class AbstractEntity {

    /**
     * 主键生成策略:
     * AUTO：根据底层数据库自动选择（默认），若数据库支持自动增长类型，则为自动增长。
     * IDENTITY：根据数据库的Identity字段生成，支持DB2、MySQL、MS、SQL Server、SyBase与HyperanoicSQL数据库的Identity类型主键。
     * SEQUENCE：使用Sequence来决定主键的取值，适合Oracle、DB2等支持Sequence的数据库，一般结合@SequenceGenerator使用。
     * TABLE：使用指定表来决定主键取值，结合@TableGenerator使用。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * id相同则对象相同
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null){
            return false;
        }

        if (getClass() != obj.getClass()){
            return false;
        }

        AbstractEntity other = (AbstractEntity) obj;
        if (id == null) {
            if (other.id != null){
                return false;
            }
        } else if (!id.equals(other.id)){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
