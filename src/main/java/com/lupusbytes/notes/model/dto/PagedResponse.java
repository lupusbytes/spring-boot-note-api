package com.lupusbytes.notes.model.dto;

import java.util.List;

public class PagedResponse<T> {
    public int currentPage;
    public int totalPages;
    public long totalRecords;
    public List<T> records;

    public PagedResponse(List<T> records, int currentPage, int totalPages, long totalRecords) {
        this.records = records;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalRecords = totalRecords;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }
}
