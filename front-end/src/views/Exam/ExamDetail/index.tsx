import React, { useEffect, useState } from 'react';
import './style.css';
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";

interface QuizDetail {
    realId: Number;
    term: string;
    answer: string;
    failureAnswer1: string;
    failureAnswer2: string;
    failureAnswer3: string;
}

interface Quiz {
    title: string;
    description: string;
    hashtag: string[];
    quizDetailList: QuizDetail[];
}

interface ApiResponse {
    statusCode: number;
    data: Quiz[];
}

function ExamDetail() {
    const { cardSetId } = useParams<{ cardSetId: string }>(); // Ensure the type matches the expected parameter

    const [quizzes, setQuizzes] = useState<Quiz[]>([]);
    const [currentIndex, setCurrentIndex] = useState(0); // 현재 표시되고 있는 문제의 인덱스
    const [selectedAnswers, setSelectedAnswers] = useState<{ [key: number]: string }>({}); // 인덱스별 선택한 답변 상태
    const [choices, setChoices] = useState<string[]>([]); // 선택지 상태 추가
    const navigate = useNavigate(); // 네비게이트 함수 사용


    const currentQuiz = quizzes[0]; // 현재는 첫 번째 퀴즈만 사용한다고 가정
    const totalQuestions = currentQuiz ? currentQuiz.quizDetailList.length : 0;
    const progressWidth = totalQuestions > 0 ? ((currentIndex + 1) / totalQuestions) * 100 : 0;

    useEffect(() => {
        const fetchQuizzes = async () => {
            const response = await axios.get<ApiResponse>(`http://localhost:4040/api/v1/quiz/${cardSetId}`);
            setQuizzes(response.data.data);
        };

        fetchQuizzes();
    }, []);

    // 문제 변경 또는 컴포넌트 마운트 시 선택지 섞기
    useEffect(() => {
        if (quizzes.length > 0) {
            const currentQuestion = quizzes[0].quizDetailList[currentIndex];
            shuffleChoices(currentQuestion);
        }
    }, [currentIndex, quizzes]);

    // 선택지 섞기 로직을 useEffect 내부로 이동

    const shuffleChoices = (currentQuestion: QuizDetail) => {
        let choices = [
            currentQuestion.answer,
            currentQuestion.failureAnswer1,
            currentQuestion.failureAnswer2,
            currentQuestion.failureAnswer3,
        ];

        for (let i = choices.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [choices[i], choices[j]] = [choices[j], choices[i]];
        }

        setChoices(choices); // 선택지 상태 업데이트
    };






    useEffect(() => {
        if (quizzes.length > 0) {
            const currentQuestion = quizzes[0].quizDetailList[currentIndex];
            shuffleChoices(currentQuestion);
        }
    }, [currentIndex, quizzes]);

    // 정답을 선택하는 함수
    const handleAnswerClick = (choice: string) => {
        setSelectedAnswers(prev => ({ ...prev, [currentIndex]: choice })); // 현재 인덱스에 대한 선택 저장
    };


    // 답변 버튼의 스타일을 결정하는 함수
    const getButtonStyle = (choice: string) => {
        const selectedAnswer = selectedAnswers[currentIndex];
        if (choice === selectedAnswer) {
            return choice === currentQuestion?.answer ? "bg-green-500 hover:bg-green-600" : "bg-red-500 hover:bg-red-600";
        }
        return "bg-gray-200 hover:bg-gray-300";
    };

    useEffect(() => {
        const fetchQuizzes = async () => {
            const response = await axios.get<ApiResponse>('http://localhost:4040/api/v1/quiz/5');
            setQuizzes(response.data.data);
        };

        fetchQuizzes();
    }, []);

    const currentQuestion = currentQuiz?.quizDetailList[currentIndex];

    const handlePrevious = () => {
        setCurrentIndex(prev => Math.max(prev - 1, 0));
        // 선택 초기화 로직은 제거 (사용자가 이전 선택을 볼 수 있도록 유지)
    };

    const handleNext = () => {
        if (currentIndex < currentQuiz?.quizDetailList.length - 1) {
            // 아직 확인하지 않은 카드가 있다면 다음 카드로 이동
            setCurrentIndex(prev => prev + 1);
        } else {
            // 모든 카드를 확인했다면 사용자에게 재시작 여부를 확인
            const restart = window.confirm("다시 보시겠습니까? 취소를 누르면 이전 페이지로 넘어갑니다.");
            if (restart) {
                setCurrentIndex(0); // 첫 번째 카드로 리셋
                setSelectedAnswers({}); // 선택한 답변 상태 초기화

            } else {
                navigate("/exam"); // 사용자가 취소를 선택하면 이전 페이지로 이동
            }
        }
    };


    return (
        <div>
            <div className="flex items-center justify-between px-6 py-4 bg-gray-800 text-white rounded-lg">
                <h1 className="text-lg font-bold">Quiz</h1>
                <div className="flex items-center gap-4">

                    <span className="text-sm">문제를 읽고 답을 선택해서 공부하세요. </span>
                </div>
            </div>
            <div className="quiz-main"> </div>


            {currentQuiz && (
                    <div className="flex-1 p-6 space-y-4">
                        <div className="space-y-2  text-center">
                            <h2 className="text-2xl font-bold">{currentQuiz.title}</h2>
                            <p className=" text-2xl dark:text-gray-400">{currentQuiz.description}</p>
                            <div className="flex justify-center flex-wrap gap-2">
                                {currentQuiz.hashtag.map((tag, index) => (
                                    <span key={index} className="px-3 py-1 bg-gray-200 text-gray-800 rounded-full text-sm">
                        #{tag}
                    </span>
                                ))}
                            </div>
                        </div>

                        {currentQuiz && currentQuestion && (
                        <div>
                            <div className="quiz-main"> </div>
                            <div className="quiz-main"> </div>
                            <div className="space-y-2  text-center">

                            <p className=" text-2xl dark:text-gray-400">Question {currentIndex + 1}:
                                {currentQuestion.term}</p>
                            </div>

                            <div className="grid gap-4 mt-3">
                                {choices.map((choice, index) => (
                                    <button
                                        key={index}
                                        className={`px-4 py-2 mb-4 rounded-md transition-colors ${getButtonStyle(choice)}`}
                                        onClick={() => handleAnswerClick(choice)}
                                    >
                                        {choice}
                                    </button>
                                ))}
                            </div>
                            <div className="flex justify-between">
                                <button className="px-4 py-2 rounded-md bg-gray-200 hover:bg-gray-300 transition-colors"onClick={handlePrevious}>Previous</button>
                                <button className="px-4 py-2 rounded-md bg-gray-200 hover:bg-gray-300 transition-colors" onClick={handleNext} >Next</button>
                            </div>


                        </div>
                    )}
                </div>
            )}

            <div className="flex items-center gap-4 mt-4">
                <span className="text-sm text-gray-500 dark:text-gray-400">Question {currentIndex + 1} of {totalQuestions}</span>
                <div className="h-2 w-full bg-gray-200 rounded">
                    <div className="h-full bg-gray-500 rounded" style={{ width: `${progressWidth}%` }} />
                </div>
            </div>
        </div>
    );
}

export default ExamDetail;