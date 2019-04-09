package com.ceballos;

public abstract class AbstractCLinearListSEO<T> {

	private CElement	p		= null;
	private CElement	prev	= null;
	private CElement	curr	= null;

	private final class CElement {

		private T			data;
		private CElement	next;

		private CElement() {

		}

		private CElement(final T d, final CElement e) {
			this.data = d;
			this.next = e;
		}
	}

	public AbstractCLinearListSEO() {

	}

	public abstract int compare(T obj1, T obj2);

	public boolean isEmpty() {
		return this.p == null;
	}

	public boolean search(final T obj) {
		int r = 0;
		this.prev = null;
		this.curr = null;

		if (isEmpty()) {
			return false;
		}

		this.prev = p;
		this.curr = p;

		r = compare(obj, this.curr.data);
		while (this.curr != null && r > 0) {
			this.prev = this.curr;
			this.curr = this.curr.next;
			r = compare(obj, this.curr.data);
		}

		return r == 0;
	}

	public void add(final T obj) {
		final CElement q = new CElement(obj, null);

		if (isEmpty()) {
			this.p = q;
			this.prev = this.curr;
			this.curr = this.p;
			return;
		}

		search(obj);

		if (this.prev == this.curr) {
			q.next = this.p;
			this.p = q;
			this.prev = this.curr;
			this.curr = this.p;
		} else {
			q.next = this.curr;
			this.prev.next = q;
			this.curr = q;
		}
	}

	public T delete(final T obj) {
		if (isEmpty()) {
			return null;
		}

		if (!search(obj)) {
			return null;
		}

		if (this.curr == this.p) {
			this.p = this.p.next;
		}

		T deleted = this.curr.data;
		this.curr = this.curr.next;

		return deleted;
	}

	public T deleteFirst() {
		if (isEmpty()) {
			return null;
		}

		this.curr = this.p;

		this.p = p.next;

		T deleted = this.curr.data;
		this.prev = this.curr;
		this.curr = this.p;

		return deleted;
	}

	public T getFirst() {
		if (isEmpty()) {
			return null;
		}

		this.curr = this.prev;
		this.prev = this.p;
		return this.p.data;
	}

	public T getCurr() {
		if (isEmpty() || this.curr == null) {
			return null;
		}
		return this.curr.data;
	}

	public T getNext() {
		if (isEmpty() || this.curr == null) {
			return null;
		}

		this.prev = this.curr;
		this.curr = this.prev.next;

		if (this.curr != null) {
			return this.curr.data;
		} else {
			return null;
		}
	}

}
