import { Outlet} from "react-router-dom";
import Header from 'layouts/Header'
import Footer from 'layouts/Footer'
import React from 'react'
import './style.css';
import Side from "../Side";


export default function Container() {
    return (
        <div id="wrap">
            <Header/>
            <div id="content">
            <div id ="sidebar">
                <Side/>
            </div>
            <div id="wrap-main">
                <Outlet />
            </div>
            </div>
            <Footer/>
        </div>
    );
}