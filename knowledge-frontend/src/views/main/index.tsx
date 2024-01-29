import React from "react";
import './style.css';
import MainRightOne from "../../components/Main-right-one";

export default function Main() {

    return(
            <main id="Main">
                <div id="main-left">
                    <section id="main-left-one"></section>
                    <section id="main-left-two"></section>
                    <section id="main-left-three"></section>
                </div>
                <div id="main-right">
                        <MainRightOne />
                    <section id="main-right-two"></section>
                    <section id="main-right-three"></section>
                    <section id="main-right-four"></section>
                </div>
            </main>
    )
}
