package com.gtw.querydsl.core;

import com.querydsl.core.annotations.QuerySupertype;
import lombok.Data;

@QuerySupertype
@Data
public class AbstractEntity {

	private Long id;

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
			return false;
		}

		AbstractEntity that = (AbstractEntity) obj;

		return this.id.equals(that.getId());
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
}
