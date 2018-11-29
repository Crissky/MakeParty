package com.inovaufrpe.makeparty.usuario.dominio;

import android.support.annotation.NonNull;

import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Data implements List<com.inovaufrpe.makeparty.fornecedor.dominio.Ads> {
    private List<Ads> Ads;

    @Override
    public String toString() {
        return "Data[" +
                "Ads=" + Ads +
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
    public Iterator<com.inovaufrpe.makeparty.fornecedor.dominio.Ads> iterator() {
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
    public boolean add(com.inovaufrpe.makeparty.fornecedor.dominio.Ads ads) {
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
    public boolean addAll(Collection<? extends com.inovaufrpe.makeparty.fornecedor.dominio.Ads> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends com.inovaufrpe.makeparty.fornecedor.dominio.Ads> c) {
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
    public com.inovaufrpe.makeparty.fornecedor.dominio.Ads get(int index) {
        return null;
    }

    @Override
    public com.inovaufrpe.makeparty.fornecedor.dominio.Ads set(int index, com.inovaufrpe.makeparty.fornecedor.dominio.Ads element) {
        return null;
    }

    @Override
    public void add(int index, com.inovaufrpe.makeparty.fornecedor.dominio.Ads element) {

    }

    @Override
    public com.inovaufrpe.makeparty.fornecedor.dominio.Ads remove(int index) {
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
    public ListIterator<com.inovaufrpe.makeparty.fornecedor.dominio.Ads> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<com.inovaufrpe.makeparty.fornecedor.dominio.Ads> listIterator(int index) {
        return null;
    }

    @NonNull
    @Override
    public List<com.inovaufrpe.makeparty.fornecedor.dominio.Ads> subList(int fromIndex, int toIndex) {
        return null;
    }
}
