import React from "react";
import './style.css';
import MainRightOne from "../../components/Main-right-one";
import MainRightTwo from "../../components/Main-right-two";
import MainLeftTwo from "../../components/Main-left-two";

export default function Main() {

    return(
            <main id="Main">
                <div id="main-left">
                    <section id="main-left-one"></section>
                    <MainLeftTwo />
                    <section id="main-left-three"></section>
                </div>
                <div id="main-right">
                        <MainRightOne />
                       <MainRightTwo/>
                    <section id="main-right-three"></section>
                    <section id="main-right-four"></section>
                </div>
            </main>
    )
}
