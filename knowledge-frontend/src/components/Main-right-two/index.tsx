import React, {useState} from "react";
import './style.css';
import {useUserStore} from "../../stores/user.store";
import {useNavigate} from "react-router-dom";
import { USER_PATH} from "../../constant";

const MainRightTwo = () => {
    const user = useUserStore(state => state.user);

    const navigate = useNavigate();
    function handleMore() {
        if (user && user.userId) {
            navigate(USER_PATH(user.userId)); // Use the USER_PATH function with the user's userId
        } else {
            console.log("User ID is not available.");
            // Handle the case when user or user.userId is not available
        }
    }


    return (
        <section id="main-right-two">
                <div className="profile-image"></div>
                <div className="user-info">
                    <div className="font-semibold text-lg">UserID: <span className="font-normal">{user?.userId || 'Loading...'}</span></div>
                    <div className="font-semibold text-lg">Email: <span className="font-normal">{user?.email || 'Loading...'}</span></div>
                    <div className="font-semibold text-lg">Role: <span className="font-normal">{user?.role || 'Loading...'}</span></div>

                    <div id="button">
                        <button onClick={handleMore}>더보기</button> {/* Add onClick event handler */}
                    </div>

                </div>

        </section>

    );
};

export default MainRightTwo;