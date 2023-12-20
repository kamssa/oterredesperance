package com.operantic.coreapi.event;

import lombok.Getter;

public abstract class BaseEvent<T> {
	@Getter
	T id;

	public BaseEvent(T id) {
		super();
		this.id = id;
	}

}
