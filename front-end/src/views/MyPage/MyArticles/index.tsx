import './style.css';
import {useCookies} from "react-cookie";
import React, {useEffect, useState} from "react";
import Pagination from "../../../components/Pagination";
import {Link, useNavigate} from "react-router-dom";
import axiosInstance from "../../../api/axios";

interface Article {
    id: number;
    title: string;
    favoriteCount: number;
    viewCount: number;
}


export default function MyArticles(){
    const [cookies] = useCookies(['accessToken']);
    const [articles, setArticles] = useState<Article[]>([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const pageSize = 9; // Assuming a constant page size
    const navigate = useNavigate(); // Use the useNavigate hook

  //

    useEffect(() => {
        const fetchMyArticles = async () => {
            try {
                const response = await axiosInstance.get(`/api/v1/users/my-articles?page=${currentPage}&size=${pageSize}`, {
                    headers: {
                        'Authorization': `Bearer ${cookies.accessToken}`,
                    },
                });
                setArticles(response.data.data.users);
                setTotalElements(response.data.data.totalElements);
            } catch (error) {
                console.error('Error fetching my articles:', error);
            }
        };

        fetchMyArticles();
    }, [currentPage, cookies.accessToken]);

    const handlePageChange = (newPage: number) => {
        setCurrentPage(newPage);
    };
    return(
        <section id='main-left-one'>

            <h2 className="flex justify-center">내가쓴 게시글</h2>

            {articles.map((article) => (
                <div key={article.id} className="article-preview">
                    <p>id: {article.id}</p>
                    <Link to={`/articles/${article.id}`} style={{ color: "blue" }}>
                        <h3>제목: {article.title}</h3>
                    </Link>
                    <p>Likes: {article.favoriteCount}, Views: {article.viewCount}</p>


                </div>
            ))}
             <Pagination
                currentPage={currentPage}
                 totalPages={totalElements}
                onPageChange={handlePageChange}
             />
        </section>
    );
};
