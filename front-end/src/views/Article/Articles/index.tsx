import './style.css';
import {useCookies} from "react-cookie";
import {useEffect, useState} from "react";
import axios from "axios";
import {useUserStore} from "../../../stores/userStore";
import Pagination from "../../../components/Pagination";
import {Link, useNavigate} from "react-router-dom";

interface Article {
    boardId: number;
    title: string;
    favoriteCount: number;
    viewCount: number;
    hashtags: string[];
    imageUrls: string[];
    writer: string;
}


export default function Articles(){
    const [cookies] = useCookies(['accessToken']);
    const { user } = useUserStore();
    const [articles, setArticles] = useState<Article[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const navigator = useNavigate();


    const handleArticlePostPage  = () => {
        navigator('/articles/post');
    };
    const isLoggedIn = cookies.accessToken;

    useEffect(() => {
        const fetchArticles = async () => {
            setLoading(true);
            try {
                const response = await axios.get(`http://localhost:4040/api/v1/articles?page=${currentPage}&size=5`, {
                    headers: {
                        Authorization: `Bearer ${cookies.accessToken}`,
                        'Accept': '*/*',
                    },
                });

                if (response.status === 200) {
                    setArticles(response.data.data.content);
                    setTotalPages(response.data.data.totalPages);
                } else {
                    setError('Failed to fetch articles.');
                }
            } catch (err) {
                console.error('Error fetching articles:', err);
                setError('Error occurred while fetching articles.');
            } finally {
                setLoading(false);
            }
        };

        fetchArticles();
    }, [currentPage, cookies.accessToken]);

    const handlePageChange = (newPage: number) => {
        setCurrentPage(newPage);
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;
    return(
        <div>
            {isLoggedIn &&(
            <div id="post-button">
                <button onClick={handleArticlePostPage}>글 작성하기</button>
            </div>
                )}

            <div className="flex flex-col gap-2">
                {articles.map((article) => (
                    <Link to={`/articles/${article.boardId}`} key={article.boardId} className="relative grid gap-2 p-2 md:gap-3 md:p-4">
                        <hr className="border-gray-200 w-full dark:border-gray-800 my-4" />
                        <div className="grid gap-4 py-4">
                            <div className="flex items-center justify-between">
                                <p className="text-sm font-medium">Post: {article.boardId}</p>
                                <div className="flex-1 text-center mx-4">
                                    <span className="text-lg font-semibold leading-snug">{article.title}</span>
                                </div>
                                <div className="flex items-center gap-2">
                                    <span className="text-sm font-medium">Views: {article.viewCount}</span>
                                    <span className="text-sm font-medium">Likes: {article.favoriteCount}</span>
                                    {article.hashtags.map((hashtag, index) => (
                                        <span key={index} className="text-sm font-medium text-blue-500">#{hashtag}</span>
                                    ))}
                                </div>
                            </div>
                        </div>
                    </Link>
                ))}

            <Pagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
            />
        </div>


        </div>
    )
}


