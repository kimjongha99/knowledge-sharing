import React, {useEffect, useState} from 'react';
import './style.css';
import {useNavigate, useParams} from "react-router-dom";
import axiosInstance from "../../../api/axios";



interface FlashCard {
    flashCardId: number;
    realId: string;
    term: string;
    definition: string;
}

interface ApiResponse {
    statusCode: number;
    data: FlashCard[];
}

function QuizDetail (){
    const { cardSetId } = useParams<{ cardSetId: string }>(); // Ensure the type matches the expected parameter

    const [cards, setCards] = useState<FlashCard[]>([]);
    const [currentIndex, setCurrentIndex] = useState(0); // 현재 표시되고 있는 카드의 인덱스
    const [showDefinition, setShowDefinition] = useState(false); // 정의를 보여줄지 여부를 결정하는 상태
    const navigator = useNavigate();

    useEffect(() => {
        const fetchCards = async () => {
            const response = await axiosInstance.get<ApiResponse>( `/api/v1/card-set/${cardSetId}`);
            setCards(response.data.data);
        };

        fetchCards();
    }, []);


    const handleTermClick = () => {
        setShowDefinition(!showDefinition); // 용어 클릭 시 정의를 표시하거나 숨깁니다.
    };




    const handlePreviousClick = () => {
        setCurrentIndex((prevIndex) => Math.max(prevIndex - 1, 0));
        setShowDefinition(false); // 이전 버튼 클릭 시 정의 숨김
    };

    const handleNextClick = () => {
        if (currentIndex < cards.length - 1) {
            setCurrentIndex(currentIndex + 1);
            setShowDefinition(false);
        } else {
            // 모든 카드를 확인한 후
            setShowDefinition(false); // 이전 버튼 클릭 시 정의 숨김

            const restart = window.confirm("다시 보시겠습니까?  취소를 누르면 이전페이지로 넘어갑니다.");
            if (restart) {
                setCurrentIndex(0); // 첫 번째 카드로 리셋
            } else {
                navigator("/practice")
            }
        }
    };
    const progressWidth = cards.length > 0 ? ((currentIndex + 1) / cards.length) * 100 : 0;


    return (
        <div>
            <div className="flex items-center justify-between px-6 py-4 bg-gray-800 text-white rounded-lg">
                <h1 className="text-lg font-bold">Quiz</h1>

                <div className="flex items-center gap-4">

                    <span className="text-sm"> 질문을 클릭하면 정답이 보입니다!! </span>
                </div>
            </div>
            <div className="quiz-main"> </div>
            <div className="quiz-main"> </div>
            <div className="quiz-main"> </div>
            <div className="quiz-main"> </div>
            <div className="quiz-main"> </div>


            {cards.length > 0 && (
                <div className="flex-1 p-6 space-y-4">
                    <div className="space-y-2 text-center">
                        <h2 className="text-2xl font-bold">
                            Question {cards[currentIndex].realId}
                        </h2>
                        <div className="quiz-main"> </div>
                        <div className="quiz-main"> </div>
                        <p className="text-2xl dark:text-gray-400" onClick={handleTermClick}>
                         {cards[currentIndex].term}
                        </p>

                        {showDefinition && (
                            <div
                                className="flex items-center justify-center h-[200px] bg-gray-200 hover:bg-gray-300 rounded-md transition-colors">
                                <p className="text-center text-4xl">{cards[currentIndex].definition}</p>
                            </div>

                        )}
                    </div>



                    <div className="flex justify-between">
                        <button onClick={handlePreviousClick} className="px-4 py-2 rounded-md bg-gray-200 hover:bg-gray-300 transition-colors">Previous</button>
                        <button onClick={handleNextClick} className="px-4 py-2 rounded-md bg-gray-200 hover:bg-gray-300 transition-colors">Next</button>
                    </div>
                </div>
            )}

            <div className="flex items-center gap-4 mt-4">
                <span className="text-sm text-gray-500 dark:text-gray-400">Question {currentIndex + 1} of {cards.length}</span>
                <div className="h-2 w-full bg-gray-200 rounded">
                    <div className="h-full bg-gray-500 rounded" style={{ width: `${progressWidth}%` }} />
                </div>
            </div>
        </div>
    );
};
export default QuizDetail;


