import './style.css';
import axios from "axios";
import {ChangeEvent, useEffect, useRef, useState} from "react";
import {useCookies} from "react-cookie";
import {useUserStore} from "../../../../stores/userStore";
const uploadFileApi = async (file: File) => {
    const formData = new FormData();
    formData.append('file', file);
    const response = await axios.post('http://localhost:4040/api/files/upload', formData);
    return response.data; // Returns the URL of the uploaded image
};

const updateProfileImageApi = async (profileImageUrl: string, accessToken: string) => {
    await axios.patch('http://localhost:4040/api/v1/users/profile-image', {
        profileImageUrl: profileImageUrl // <- Change this line
    }, {
        headers: { 'Authorization': `Bearer ${accessToken}` }
    });
};

export default function ProfileImg(){

    const [profileImage, setProfileImage] = useState<string | null>(null);
    const fileInputRef = useRef<HTMLInputElement | null>(null);
    const [cookies] = useCookies(['accessToken']);
    const accessToken = cookies.accessToken;
    const [userInfo, setUserInfo] = useState({ profileImage: '' });

    // Use useUserStore hook to access user state and actions
    const { updateUserProfileImage, user } = useUserStore(state => ({
        updateUserProfileImage: state.updateUserProfileImage,
        user: state.user
    }));

    useEffect(() => {
        // Update local state when user state changes
        if (user && user.profileImageUrl) {
            setProfileImage(user.profileImageUrl);
        }
    }, [user]);

    const onProfileImageChangeHandler = async (event: ChangeEvent<HTMLInputElement>) => {
        if (!event.target.files || event.target.files.length === 0) return;

        const file = event.target.files[0];
        try {
            const uploadedImageUrl = await uploadFileApi(file); // Upload the file and get the URL
            await updateProfileImageApi(uploadedImageUrl, accessToken); // Update profile image in the backend

            // Update profile image in the global state
            updateUserProfileImage(uploadedImageUrl);

            // Optionally, you can update the local state, but it's not necessary if you rely on the global state
            setProfileImage(uploadedImageUrl);
        } catch (error) {
            console.error('Error uploading file:', error);
        }
    };
    const onProfileImageClickHandler = () => {
        if (!fileInputRef.current) return;
        fileInputRef.current.click();
    };

    return(
        <div id="profile">
        <div className="flex flex-col items-center p-4 bg-white dark:bg-gray-700 rounded-lg shadow">
            <div
                className="w-24 h-24 bg-gray-300 dark:bg-gray-500 rounded-full"
                onClick={onProfileImageClickHandler}
                style={{ backgroundImage: `url(${profileImage})`, backgroundSize: 'cover' }}
            >
                {!profileImage && (
                    <div className="w-full h-full flex items-center justify-center">
                        {/* Display a placeholder or icon here if no profile image */}
                    </div>
                )}
                <input
                    ref={fileInputRef}
                    type="file"
                    accept="image/*"
                    style={{ display: 'none' }}
                    onChange={onProfileImageChangeHandler}
                />
            </div>
        </div>
            <div >프로필</div>


        </div>

    )
}


