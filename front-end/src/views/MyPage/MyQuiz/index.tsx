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
    const pageSize = 2; // Adjusted to match the example response size
    const navigate = useNavigate(); // Use the useNavigate hook
    const handleDelete = async (quizId: any) => {
        try {
            const response = await axios.delete(`http://localhost:4040/api/v1/card-set/${quizId}`, {
                headers: {
                    'Authorization': `Bearer ${cookies.accessToken}`, // 헤더에 Authorization 토큰을 포함
                },
            });

            if (response.status === 200) {
                // 성공적으로 삭제되었을 때의 로직
                alert('삭제가 완료되었습니다.');
                // 삭제 후 상태 업데이트를 통해 페이지를 새로 고침하지 않고 목록에서 바로 항목을 제거합니다.
                setQuizzes(quizzes.filter(quiz => quiz.id !== quizId));
            } else {
                // 서버에서 오류 응답을 반환했을 때
                alert('삭제에 실패했습니다.');
            }
        } catch (error) {
            console.error('삭제 요청 중 오류가 발생했습니다.', error);
            alert('삭제 요청 중 오류가 발생했습니다.');
        }
    };
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
        <section id='main-left-three' className="h-90 overflow-y-auto">
            <h2 className="flex justify-center text-2xl font-bold py-4">내 퀴즈</h2>
            {quizzes.map((quiz) => (
                <div key={quiz.id} className="mb-4 p-4 shadow-lg rounded-lg">
                    <p className="text-sm text-gray-600">id: {quiz.id}</p>
                    <Link to={`/card-set/${quiz.id}`} className="text-blue-600 hover:text-blue-800 transition-colors">
                        <h3 className="text-xl font-semibold">제목: {quiz.title}</h3>
                    </Link>
                    <p className="text-gray-800 mt-2">내용: {quiz.content}</p>
                    <Link to={`/card-set/edit/${quiz.id}`} className="inline-block mt-4">
                        <button className="bg-orange-500 hover:bg-orange-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
                            수정하기
                        </button>
                    </Link>
                    <span className="inline-block mt-4">
                     <button className="ml-5 bg-red-500 hover:bg-orange-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                             onClick={() => handleDelete(quiz.id)}>
                        삭제하기
                    </button>
                    </span>
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