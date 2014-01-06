package com.study.doubanbook_for_android.callback;

import com.study.doubanbook_for_android.api.WrongMsg;

public class AsynCallback<T> {
	// private static final String TAG = AsynCallback.class.getSimpleName();

	public void onStart() {
	}

	public void onDone() {
	}

	public void onSuccess(T data) {
	};

	public void onFailure(final WrongMsg caught) {
	}
}
