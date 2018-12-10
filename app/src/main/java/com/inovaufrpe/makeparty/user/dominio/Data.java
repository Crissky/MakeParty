package com.inovaufrpe.makeparty.user.dominio;

import android.support.annotation.NonNull;

import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Data implements List<Ad> {
    private List<Ad> Ads;

    @Override
    public String toString() {
        return "Data[" +
                "Ad=" + Ads +
                ']';
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Ad> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Ad ads) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Ad> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends Ad> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Ad get(int index) {
        return null;
    }

    @Override
    public Ad set(int index, Ad element) {
        return null;
    }

    @Override
    public void add(int index, Ad element) {

    }

    @Override
    public Ad remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<Ad> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<Ad> listIterator(int index) {
        return null;
    }

    @NonNull
    @Override
    public List<Ad> subList(int fromIndex, int toIndex) {
        return null;
    }
}
