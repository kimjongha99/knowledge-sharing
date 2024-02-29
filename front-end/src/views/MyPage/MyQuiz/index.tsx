import './style.css';
import {useCookies} from "react-cookie";
import React, {useEffect, useState} from "react";
import axios from "axios";
import Pagination from "../../../components/Pagination";
import {Link, useNavigate} from "react-router-dom";

interface Quiz {
    id: number;
    title: string;
    content: string;
}

export default function MyQuiz() {
    const [cookies] = useCookies(['accessToken']);
    const [quizzes, setQuizzes] = useState<Quiz[]>([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const pageSize = 10; // Adjusted to match the example response size
    const navigate = useNavigate(); // Use the useNavigate hook

    useEffect(() => {
        const fetchMyQuiz = async () => {
            try {
                const response = await axios.get(`http://localhost:4040/api/v1/users/my-quiz?page=${currentPage}&size=${pageSize}`, {
                    headers: {
                        'Authorization': `Bearer ${cookies.accessToken}`,
                    },
                });
                setQuizzes(response.data.data.quiz);
                setTotalElements(response.data.data.totalElements);
            } catch (error) {
                console.error('Error fetching my quizzes:', error);
            }
        };

        fetchMyQuiz();
    }, [currentPage, cookies.accessToken]);

    const handlePageChange = (newPage: number) => {
        setCurrentPage(newPage);
    };

    return (
        <section id='main-left-three'>
            <h2 className="flex justify-center">내 퀴즈</h2>
            {quizzes.map((quiz) => (
                <div key={quiz.id} className="quiz-preview">
                    <p>id: {quiz.id}</p>
                    <Link to={`/quizzes/${quiz.id}`} style={{ color: "blue" }}>
                        <h3>제목: {quiz.title}</h3>
                    </Link>
                    <p>내용: {quiz.content}</p>
                </div>
            ))}
            <Pagination
                currentPage={currentPage}
                totalPages={Math.ceil(totalElements / pageSize)}
                onPageChange={handlePageChange}
            />
        </section>
    );
}