import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "./style.css";
import {useCookies} from "react-cookie";
import {Link, useNavigate} from "react-router-dom";

interface ArticleData {
    boardId: number;
    title: string;
    favoriteCount: number;
    viewCount: number;
    hashtags: string[];
    imageUrls: string[];
    writer: string;
}


interface Pageable {
    pageNumber: number;
    pageSize: number;
    sort: {
        sorted: boolean; // Keep this if you're using sorting, otherwise you can remove it
    };
}



function Article() {
    const [articles, setArticles] = useState<ArticleData[]>([]);
    const [page, setPage] = useState<number>(0);
    const [size, setSize] = useState<number>(7);
    const [totalPages, setTotalPages] = useState<number>(0);
    const [cookies] = useCookies(['accessToken']);
    const navigator = useNavigate();

    useEffect(() => {
        const fetchArticles = async () => {
            try {
                const response = await axios.get(`http://localhost:4040/api/v1/articles?page=${page}&size=${size}`, {
                    headers: {
                        Authorization: `Bearer ${cookies.accessToken}`,
                    },
                });
                if (response.data && response.data.code === 'SU') {
                    setArticles(response.data.articles.content);
                    setTotalPages(response.data.articles.totalPages);
                } else {
                    // Handle failure (e.g., invalid token or no articles found)
                }
            } catch (error) {
                // Handle error (e.g., network error or server error)
            }
        };

        fetchArticles();
    }, [page, size, cookies.accessToken]); // Re-run the effect if page, size, or accessToken changes

    // Handler for changing the page
    const handlePageChange = (newPage: number) => {
        setPage(newPage);
    };
    const handleArticlePostPage  = () => {
        navigator('/articles/post');
    };
    return (
        <div id="warp">
            <div id="main-contain">
                <div id="post-button">
                    <button onClick={handleArticlePostPage}>Button</button>
                </div>


                <table>
                    <thead>
                    <tr>
                        <th>ID </th>
                        <th>Title</th>
                        <th>Views</th>
                        <th>Favorites</th>
                        <th>Hashtags</th>
                        <th>Writer</th>
                        <th>이미지</th>
                    </tr>
                    </thead>
                    <tbody>
                    {articles.map((article) => (
                        <tr key={article.boardId}>
                            <td><Link to={`/articles/${article.boardId}`}>{article.boardId}</Link></td>
                            <td>{article.title}</td>
                            <td>{article.viewCount}</td>
                            <td>{article.favoriteCount}</td>
                            <td>{article.hashtags.join(', ')}</td>
                            <td>{article.writer}</td>
                            <td>{article.imageUrls}</td>

                        </tr>
                    ))}
                    </tbody>
                </table>
                <div className="pagination">
                    <button onClick={() => handlePageChange(page - 1)} disabled={page === 0}>
                        Previous
                    </button>
                    <span>Page {page + 1} of {totalPages}</span>
                    <button onClick={() => handlePageChange(page + 1)} disabled={page === totalPages - 1}>
                        Next
                    </button>
                </div>
            </div>
        </div>
    );
}

export default Article;
