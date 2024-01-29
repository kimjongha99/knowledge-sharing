import { Outlet} from "react-router-dom";
import Header from 'layouts/Header'
import Footer from 'layouts/Footer'
import React from 'react'
import './style.css';


export default function Container() {
    return (
        <div id="wrap">
            <Header />
            <div id="wrap-center">
                <div id="header"></div>
                <div id="search"></div>
                {/* The Outlet will render the nested route component here */}
                <Outlet />
            </div>
            <Footer/>
        </div>
    );
}