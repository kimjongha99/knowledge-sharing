import './style.css';
import React from 'react';

interface PaginationProps {
    currentPage: number;
    totalPages: number;
    onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({ currentPage, totalPages, onPageChange }) => {
    const MAX_PAGES = 5;

    const renderPageNumbers = () => {
        let startPage: number, endPage: number;

        if (totalPages <= MAX_PAGES) {
            startPage = 1;
            endPage = totalPages;
        } else {
            if (currentPage <= 3) {
                startPage = 1;
                endPage = MAX_PAGES;
            } else if (currentPage + 2 >= totalPages) {
                startPage = totalPages - 4;
                endPage = totalPages;
            } else {
                startPage = currentPage - 2;
                endPage = currentPage + 2;
            }
        }

        const pages = [];
        for (let page = startPage; page <= endPage; page++) {
            pages.push(
                <button
                    key={page}
                    onClick={() => onPageChange(page - 1)}
                    className={`text-xs px-2 py-1 border border-gray-300 rounded ${currentPage === page - 1 ? 'bg-blue-500 text-white' : 'bg-white hover:bg-gray-100'}`}
                    disabled={currentPage === page - 1}
                >
                    {page}
                </button>
            );
        }

        return pages;
    };

    return (
        <div className="flex justify-center space-x-2 mt-4">
            {currentPage > 0 && (
                <button
                    onClick={() => onPageChange(currentPage - 1)}
                    className="text-xs px-2 py-1 border border-gray-300 rounded hover:bg-gray-100"
                >
                    Previous
                </button>
            )}

            {renderPageNumbers()}

            {currentPage < totalPages - 1 && (
                <button
                    onClick={() => onPageChange(currentPage + 1)}
                    className="text-xs px-2 py-1 border border-gray-300 rounded hover:bg-gray-100"
                >
                    Next
                </button>
            )}
        </div>
    );
};

export default Pagination;