package com.ilp.tcs.sitesafety.utils;

import android.text.ParcelableSpan;
import android.text.Spannable;
import android.text.SpannableStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fedric Antony on 06-07-2016.
 */
public class SimpleSpanBuilder {

    private List<SpanSection> spanSections;
    private StringBuilder stringBuilder;

    public SimpleSpanBuilder() {
        stringBuilder = new StringBuilder();
        spanSections = new ArrayList<>();
    }

    public SimpleSpanBuilder append(String text, ParcelableSpan... spans) {
        if (spans != null && spans.length > 0) {
            spanSections.add(new SpanSection(text, stringBuilder.length(), spans));
        }
        stringBuilder.append(text);
        return this;
    }

    public void clear() {
        spanSections.clear();
        stringBuilder.setLength(0);
    }

    public SpannableStringBuilder build() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(stringBuilder.toString());
        for (SpanSection section : spanSections) {
            section.apply(ssb);
        }
        return ssb;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    private class SpanSection {
        private final String text;
        private final int startIndex;
        private final ParcelableSpan[] spans;

        public SpanSection(String text, int startIndex, ParcelableSpan... spans) {
            this.spans = spans;
            this.text = text;
            this.startIndex = startIndex;
        }

        public void apply(SpannableStringBuilder spanStringBuilder) {
            if (spanStringBuilder == null) return;
            for (ParcelableSpan span : spans) {
                spanStringBuilder.setSpan(span, startIndex, startIndex + text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
    }
}
