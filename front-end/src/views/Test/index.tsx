import React, { useState} from 'react';
import './style.css';
import {useCookies} from "react-cookie";
import axios from "axios";







function Test (){


    return (

        <div>
            <div>
                <div className="flex items-center justify-between px-6 py-4 bg-gray-800 text-white rounded-lg">
                    <h1 className="text-lg font-bold">Quiz</h1>
                    <div className="flex items-center gap-4">

                        <span className="text-sm">요소 들어갈꺼면 넣고 </span>
                    </div>
                </div>
                <div className="quiz-main"> </div>

                <div className="flex-1 p-6 space-y-4">
                    <div className="space-y-2  text-center">
                        <h2 className="text-2xl font-bold">Question 1</h2>
                        <p className=" text-2xl dark:text-gray-400">What is the capital of France?</p>
                    </div>
                    <div
                        className="flex items-center justify-center h-[200px] bg-gray-200 hover:bg-gray-300 rounded-md transition-colors">
                        <span className="text-center text-4xl">Paris</span>
                    </div>
                    <div className="flex justify-between">
                        <button className="px-4 py-2 rounded-md bg-gray-200 hover:bg-gray-300 transition-colors">Previous</button>
                        <button className="px-4 py-2 rounded-md bg-gray-200 hover:bg-gray-300 transition-colors">Next</button>
                    </div>
                    <div className="flex items-center gap-4">
                        <span className="text-sm text-gray-500 dark:text-gray-400">Question 1 of 10</span>
                        <div className="h-2 w-full bg-gray-200 rounded">
                            <div className="h-full bg-gray-500 rounded" style={{width: '10%'}} />
                        </div>
                    </div>
                </div>
                </div>
        </div>

    );
};

export default Test;


