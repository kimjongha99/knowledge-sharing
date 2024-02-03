import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "./style.css";
import {useCookies} from "react-cookie";

interface Article {
    boardId: number;
    title: string;
    favoriteCount: number;
    viewCount: number;
    hashtags: string[];
    imageUrls: string[];
    writer: string;
}


function Article() {

    return (
        <div id="warp">
            <div id="main-contain">

            </div>
        </div>
    );
}

export default Article;
