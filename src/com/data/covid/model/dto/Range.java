package com.data.covid.model.dto;

import java.util.Objects;

public abstract class Range<T> {

    private final T start;
    private final T end;

    public Range(T t) {
        this.start = t;
        this.end = t;
    }

    public Range(T t1, T t2) {
        if (this.compare(t1, t2) < 0) {
            this.start = t1;
            this.end = t2;
        } else {
            this.start = t2;
            this.end = t1;
        }
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }

    private boolean isSingleDate() {
        return this.compare(this.start, this.end) == 0;
    }

    public boolean contains(T t) {
        return this.isSingleDate()
                ? this.compare(this.start, t) == 0
                : !(this.compare(this.start, t) > 0 || this.compare(this.end, t) < 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range<?> that = (Range<?>) o;
        return Objects.equals(this.start, that.start) && Objects.equals(this.end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.start, this.end);
    }

    @Override
    public String toString() {
        return this.isSingleDate()
                ? this.valueToString(this.start)
                : String.format("%s - %s", this.valueToString(this.start), this.valueToString(this.end));
    }

    protected String valueToString(T t) {
        return String.valueOf(t);
    }

    protected abstract int compare(T t1, T t2);
}
