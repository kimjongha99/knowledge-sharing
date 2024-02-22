import './style.css';
import {useEffect, useState} from "react";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";

interface top3Articles{
    id: number ;
    title: string;
    content: string;

}

export default function Top3() {
    const [articles, setArticles] = useState<top3Articles[]>([]);

    useEffect(() => {
        // Using Axios to fetch data
        axios.get('http://localhost:4040/api/v1/articles/top-favorites')
            .then(response => {
                // Check if the status code is 200 and data is present
                if (response.status === 200 && response.data.data) {
                    setArticles(response.data.data);
                }
            })
            .catch(error => {
                console.error('There was an error fetching the articles:', error);
            });
    }, []);// 빈 종속성 배열은 이 효과가 마운트 시 한 번 실행된다는 의미입니다.

    return (
        <div>
            <h2 id='top3-like'>주간 좋아요 TOP3</h2>
        <div className="flex-container">


            {articles.map(article => (
                <div key={article.id} className="card-container">
                    <div className="card-header">
                        <h1 className="text-lg font-semibold">
                            {article.title}
                        </h1>
                    </div>
                    <div className="card-body">
                        <p>
                            {article.content}
                        </p>
                    </div>
                    <div className="card-footer">
                        <Link to={`/articles/${article.id}`}>
                        <div className="button">게시글 바로보기</div>
                        </Link>

                </div>
                </div>
            ))}

        </div>
        </div>
    );
}


